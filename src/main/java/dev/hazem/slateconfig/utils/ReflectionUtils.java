package dev.hazem.slateconfig.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;

public class ReflectionUtils {

    public static <T> T createClassInstance(@NotNull Class<T> clazz, Object... args) {
        if (clazz.isInterface() || clazz.isEnum() || Modifier.isAbstract(clazz.getModifiers())) {
            throw ExceptionUtils.throwException("Cannot instantiate interface, enum or abstract class: " + clazz.getName());
        }
        try {
            // Get the parameter types from the provided arguments
            Class<?>[] paramTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                paramTypes[i] = args[i].getClass();
            }
            return clazz.getConstructor(paramTypes).newInstance(args);
        } catch (NoSuchMethodException e) {
            throw ExceptionUtils.throwException("No compactible constructor found for class: " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw ExceptionUtils.throwException("Constructor is not accessible for class: " + clazz.getName(), e);
        } catch (Exception e) {
            throw ExceptionUtils.throwException("Unexpected error instantiating class: " + clazz.getName(), e);
        }
    }
}
