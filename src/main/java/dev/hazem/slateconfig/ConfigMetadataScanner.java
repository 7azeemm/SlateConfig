package dev.hazem.slateconfig;

import dev.hazem.slateconfig.annotations.Group;
import dev.hazem.slateconfig.annotations.Option;
import dev.hazem.slateconfig.annotations.Page;
import dev.hazem.slateconfig.annotations.Tab;
import dev.hazem.slateconfig.nodes.*;
import dev.hazem.slateconfig.options.OptionTypeRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigMetadataScanner {

    public static ConfigMetadata scan(Class<?> rootClass) {
        validateClass(rootClass, "Root Config");

        List<PageNode> pages = new ArrayList<>();

        for (Field field : rootClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Page.class)) {
                continue;
            }

            validateField(field, Page.class);
            validateContainerField(rootClass, field, "Page");

            Page page = field.getAnnotation(Page.class);
            PageNode pageNode = new PageNode(page.title());

            scanPage(rootClass, field.getType(), pageNode);

            pages.add(pageNode);
        }

        return new ConfigMetadata(pages);
    }

    private static void validateClass(Class<?> clazz, String name) {
        int modifiers = clazz.getModifiers();

        if (!Modifier.isPublic(modifiers)) {
            throw error(name + " class must be public: " + clazz.getName());
        }
        if (Modifier.isAbstract(modifiers)) {
            throw error(name + " class must not be abstract: " + clazz.getName());
        }
    }

    private static void validateField(Field field, Class<?> expectedAnnotation) {
        int modifiers = field.getModifiers();

        if (!Modifier.isPublic(modifiers)) {
            throw error(expectedAnnotation.getSimpleName() + " field must be public: " + field.getName());
        }
        if (Modifier.isStatic(modifiers)) {
            throw error(expectedAnnotation.getSimpleName() + " field must not be static: " + field.getName());
        }
        if (Modifier.isFinal(modifiers)) {
            throw error(expectedAnnotation.getSimpleName() + " field must not be final: " + field.getName());
        }

        int count = 0;
        if (field.isAnnotationPresent(Page.class)) count++;
        if (field.isAnnotationPresent(Tab.class)) count++;
        if (field.isAnnotationPresent(Group.class)) count++;
        if (field.isAnnotationPresent(Option.class)) count++;

        if (count != 1) {
            throw error("Field must have exactly one config annotation: " + field.getName());
        }
    }

    private static void validateContainerField(Class<?> rootClass, Field field, String name) {
        Class<?> type = field.getType();

        if (type.isPrimitive()
                || type.isEnum()
                || type.isRecord()
                || type.equals(String.class)
                || type.equals(Boolean.class)
                || Number.class.isAssignableFrom(type)) {
            throw error(name + " must be an object type: " + field.getName());
        }

        if (!sameCodeSource(rootClass, type)) {
            throw error(name + " class must belong to the mod: " + type.getName());
        }

        validateClass(type, name);
    }

    private static void scanPage(Class<?> rootClass, Class<?> pageClass, PageNode pageNode) {
        for (Field field : pageClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Tab.class)) {
                validateField(field, Tab.class);
                validateContainerField(rootClass, field, "Tab");

                Tab tab = field.getAnnotation(Tab.class);
                TabNode tabNode = new TabNode(tab.title());

                scanTab(rootClass, field.getType(), tabNode);

                pageNode.addChild(tabNode);
                continue;
            }

            if (field.isAnnotationPresent(Group.class)) {
                validateField(field, Group.class);
                validateContainerField(rootClass, field, "Group");

                Group group = field.getAnnotation(Group.class);
                GroupNode groupNode = new GroupNode(group.title());

                scanGroup(field.getType(), groupNode);

                pageNode.addChild(groupNode);
                continue;
            }

            if (field.isAnnotationPresent(Option.class)) {
                scanOption(field, pageNode);
            }
        }
    }

    private static void scanTab(Class<?> rootClass, Class<?> tabClass, TabNode tabNode) {
        for (Field field : tabClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Group.class)) {
                validateField(field, Group.class);
                validateContainerField(rootClass, field, "Group");

                Group group = field.getAnnotation(Group.class);
                GroupNode groupNode = new GroupNode(group.title());

                scanGroup(field.getType(), groupNode);

                tabNode.addChild(groupNode);
                continue;
            }

            if (field.isAnnotationPresent(Option.class)) {
                scanOption(field, tabNode);
                continue;
            }

            if (field.isAnnotationPresent(Tab.class)) {
                throw error("Tab cannot be nested inside another Tab: " + field.getName());
            }
        }
    }

    private static void scanGroup(Class<?> groupClass, GroupNode groupNode) {
        for (Field field : groupClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Option.class)) {
                scanOption(field, groupNode);
                continue;
            }

            if (field.isAnnotationPresent(Tab.class) || field.isAnnotationPresent(Group.class)) {
                throw error("Group can only contain options: " + field.getName());
            }
        }
    }

    private static void scanOption(Field field, ConfigNode parent) {
        validateField(field, Option.class);

        if (!OptionTypeRegistry.isSupported(field.getType())) {
            throw error("Unsupported option type: " + field.getType().getName() + " at " + field.getName());
        }

        Option option = field.getAnnotation(Option.class);
        OptionNode optionNode = new OptionNode(option.title());

        parent.addChild(optionNode);
    }

    private static boolean sameCodeSource(Class<?> a, Class<?> b) {
        CodeSource srcA = a.getProtectionDomain().getCodeSource();
        CodeSource srcB = b.getProtectionDomain().getCodeSource();

        if (srcA == null || srcB == null) {
            return false;
        }

        return Objects.equals(srcA.getLocation(), srcB.getLocation());
    }

    private static RuntimeException error(String msg) {
        return new IllegalStateException("[ConfigScanner] " + msg);
    }
}
