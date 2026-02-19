package dev.hazem.slateconfig;

import dev.hazem.slateconfig.annotations.Description;
import dev.hazem.slateconfig.annotations.Group;
import dev.hazem.slateconfig.annotations.Option;
import dev.hazem.slateconfig.annotations.Page;
import dev.hazem.slateconfig.annotations.Tab;
import dev.hazem.slateconfig.nodes.ConfigNode;
import dev.hazem.slateconfig.nodes.GroupNode;
import dev.hazem.slateconfig.nodes.OptionNode;
import dev.hazem.slateconfig.nodes.PageNode;
import dev.hazem.slateconfig.nodes.TabNode;
import dev.hazem.slateconfig.options.OptionRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MetadataScanner {

    public static <T> ConfigMetadata scan(T instance, Class<T> rootClass) {
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
            Object pageInstance = getInstance(field, instance);

            scanPage(rootClass, pageInstance, field.getType(), pageNode);
            pageNode.init();

            pages.add(pageNode);
        }

        //TODO: support no pages
        if (pages.isEmpty()) {
            throw error("Root Config class must have at least one page!");
        }

        return new ConfigMetadata(pages);
    }

    private static void scanPage(Class<?> rootClass, Object instance, Class<?> pageClass, PageNode pageNode) {
        for (Field field : pageClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Tab.class)) {
                validateField(field, Tab.class);
                validateContainerField(rootClass, field, "Tab");

                Tab tab = field.getAnnotation(Tab.class);
                TabNode tabNode = new TabNode(tab.title());
                Object tabInstance = getInstance(field, instance);

                scanTab(rootClass, tabInstance, field.getType(), tabNode);

                pageNode.tabs.add(tabNode);
                continue;
            }

            if (field.isAnnotationPresent(Group.class)) {
                validateField(field, Group.class);
                validateContainerField(rootClass, field, "Group");

                Group group = field.getAnnotation(Group.class);
                GroupNode groupNode = new GroupNode(group.title());
                Object groupInstance = getInstance(field, instance);

                scanGroup(groupInstance, field.getType(), groupNode);

                pageNode.children.add(groupNode);
                continue;
            }

            if (field.isAnnotationPresent(Option.class)) {
                scanOption(instance, field, pageNode);
            }
        }
    }

    private static void scanTab(Class<?> rootClass, Object instance, Class<?> tabClass, TabNode tabNode) {
        for (Field field : tabClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Group.class)) {
                validateField(field, Group.class);
                validateContainerField(rootClass, field, "Group");

                Group group = field.getAnnotation(Group.class);
                GroupNode groupNode = new GroupNode(group.title());
                Object groupInstance = getInstance(field, instance);

                scanGroup(groupInstance, field.getType(), groupNode);

                tabNode.children.add(groupNode);
                continue;
            }

            if (field.isAnnotationPresent(Option.class)) {
                scanOption(instance, field, tabNode);
                continue;
            }

            if (field.isAnnotationPresent(Tab.class)) {
                throw error("Tab cannot be nested inside another Tab: " + field.getName());
            }
        }
    }

    private static void scanGroup(Object instance, Class<?> groupClass, GroupNode groupNode) {
        for (Field field : groupClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Option.class)) {
                scanOption(instance, field, groupNode);
                continue;
            }

            if (field.isAnnotationPresent(Tab.class) || field.isAnnotationPresent(Group.class)) {
                throw error("Group can only contain options: " + field.getName());
            }
        }
    }

    private static void scanOption(Object instance, Field field, ConfigNode parent) {
        validateField(field, Option.class);

        Class<?> adapter = OptionRegistry.resolve(field.getType());
        if (adapter == null) {
            throw error("Unsupported option type: " + field.getType().getName() + " at " + field.getName());
        }

        Option option = field.getAnnotation(Option.class);
        String description = field.isAnnotationPresent(Description.class)
                ? field.getAnnotation(Description.class).value() : null;

        OptionNode<?> optionNode = createOptionNode(instance, field, adapter, option.title(), description);

        parent.children.add(optionNode);
    }

    @SuppressWarnings("unchecked")
    private static <T> OptionNode<T> createOptionNode(Object instance, Field field, Class<T> adapter, String title, String description) {
        field.setAccessible(true);

        T defaultValue;
        try {
            defaultValue = (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw error("Cannot read default value for field " + field.getName() + ": " + e);
        }

        if (defaultValue == null) {
            throw error("Field " + field.getName() + " has null default value.");
        }

        Supplier<T> getter = () -> {
            try {
                return (T) field.get(instance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot access field " + field.getName(), e);
            }
        };

        Consumer<T> setter = value -> {
            try {
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot write field " + field.getName(), e);
            }
        };

        return new OptionNode<>(title, description, adapter, defaultValue, getter, setter);
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

    private static Object getInstance(Field field, Object parent) {
        try {
            Object instance = field.get(parent);
            if (instance == null) {
                instance = field.getType().getDeclaredConstructor().newInstance();
                field.set(parent, instance);
            }
            return instance;
        } catch (Exception e) {
            throw error("Failed to create instance of field " + field.getName() + ": " + e);
        }
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
