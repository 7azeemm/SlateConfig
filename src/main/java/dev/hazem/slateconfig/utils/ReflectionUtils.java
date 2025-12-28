package dev.hazem.slateconfig.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;

public class ReflectionUtils {

    public static <T> T createClassInstance(@NotNull Class<T> clazz) {
        if (clazz.isInterface() || clazz.isEnum() || Modifier.isAbstract(clazz.getModifiers())) {
            throw ExceptionUtils.throwException("Cannot instantiate interface, enum or abstract class: " + clazz.getName());
        }
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw ExceptionUtils.throwException("No public no-argument constructor found for class: " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw ExceptionUtils.throwException("Constructor is not accessible for class: " + clazz.getName(), e);
        } catch (Exception e) {
            throw ExceptionUtils.throwException("Unexpected error instantiating class: " + clazz.getName(), e);
        }
    }

}
