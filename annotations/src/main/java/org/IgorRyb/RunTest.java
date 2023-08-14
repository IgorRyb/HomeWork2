package org.IgorRyb;

import org.IgorRyb.Annotations.After;
import org.IgorRyb.Annotations.Before;
import org.IgorRyb.Annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CatTest cat = new CatTest();
        Method[] methods = cat.getClass().getDeclaredMethods();

        Method[] beforeMethods = getBeforeMethods(methods);
        Method[] testMethods = getTestMethods(methods);
        Method[] afterMethods = getAfterMethods(methods);

        for(Method method : testMethods) {
            try {
                invoke(beforeMethods, cat);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                invoke(afterMethods, cat);
            }
        }
        cat.outputResult();
    }

    private static void invoke(Method[] methods, Object obj) throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < methods.length; i++) {
            methods[i].invoke(obj);
        }
    }

    private static Method[] getBeforeMethods(Method[] methods) throws NoSuchMethodException {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(Before.class)) {
                String name = methods[i].getName();
                methods = new Method[]{CatTest.class.getMethod(name)};
            }
        }
        return methods;
    }

    private static Method[] getTestMethods(Method[] methods) throws NoSuchMethodException {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(Test.class)) {
                String name = methods[i].getName();
                methods = new Method[]{CatTest.class.getMethod(name)};
            }
        }
        return methods;
    }

    private static Method[] getAfterMethods(Method[] methods) throws NoSuchMethodException {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(After.class)) {
                String name = methods[i].getName();
                methods = new Method[]{CatTest.class.getMethod(name)};
            }
        }
        return methods;
        }
}