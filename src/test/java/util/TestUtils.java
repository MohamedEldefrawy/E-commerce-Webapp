package util;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObjects(Object target, String filedName, Object toInject) {
        boolean wasPrivate = false;
        try {
            Field field = target.getClass().getDeclaredField(filedName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
                wasPrivate = true;
            }
            field.set(target, toInject);
            if (wasPrivate) {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}