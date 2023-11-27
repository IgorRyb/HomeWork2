package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<Method> componentMethods = Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();

        try {
            Object newInstance = configClass.getDeclaredConstructor().newInstance();

            for (Method method : componentMethods) {

                if (appComponentsByName.containsKey(method.getAnnotation(AppComponent.class).name())) {
                    throw new RuntimeException("Could not find specific method");
                }

                var args = Arrays.stream(method.getParameterTypes())
                        .map(this::getAppComponent)
                        .toArray();

                Object bean = method.invoke(newInstance, args);
                String name = method.getAnnotation(AppComponent.class).name();
                appComponentsByName.put(name, bean);
            }

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        try {
            List<Object> beanList = appComponentsByName.values().stream()
                    .filter(bean -> componentClass.isAssignableFrom(bean.getClass()))
                    .toList();

            return (C) beanList.get(0);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not find bean with class %s", componentClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) throws Exception {
        return Optional.of((C) appComponentsByName.get(componentName))
                .orElseThrow(() -> new Exception(String.format("Could not find bean with name %s", componentName)));
    }
}
