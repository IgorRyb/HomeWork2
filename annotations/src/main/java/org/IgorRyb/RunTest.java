package org.IgorRyb;

import org.IgorRyb.Annotations.After;
import org.IgorRyb.Annotations.Before;
import org.IgorRyb.Annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunTest {

    public static void main(String[] args) {
        Class<?> clazz = CatTest.class;
        Method[] methods = clazz.getDeclaredMethods();

        Method[] beforeMethods = getAnnotatedMethods(methods, Before.class);
        Method[] testMethods = getAnnotatedMethods(methods, Test.class);
        Method[] afterMethods = getAnnotatedMethods(methods, After.class);

        int passed = 0;

        for (Method testMethod : testMethods) {
            Object testClass = getInstance(clazz);
            invoke(testClass, beforeMethods);
            try {
                testMethod.invoke(testClass);
                passed++;
            } catch (Exception e) {
                System.out.println("Тест провален: " + e.getCause());
            } finally {
                invoke(testClass, afterMethods);
            }
        }

        outputResult(testMethods.length, passed);
    }

    private static Method[] getAnnotatedMethods(Method[] methods, Class<? extends Annotation> annotation) {
        List<Method> methodArrayList = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                methodArrayList.add(method);
            }
        }
        Method[] array = methodArrayList.toArray(new Method[0]);
        return array;
    }

    private static Object getInstance(Class<?> clazz) {
        Object instance = null;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static void invoke(Object testClass, Method[] methods) {
        for (Method method : methods) {
            try {
                method.invoke(testClass);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void outputResult(int allTests, int passed) {
        System.out.println("Всего тестов:  " + allTests + ", Пройдено тестов: " + passed + ", Провалено тестов: " + (allTests - passed));
    }

}