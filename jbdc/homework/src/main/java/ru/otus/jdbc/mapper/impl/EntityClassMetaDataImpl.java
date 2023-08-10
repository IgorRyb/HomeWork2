package ru.otus.jdbc.mapper.impl;

import ru.otus.crm.model.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private Class<T> tClass;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public String getName() {
        return tClass.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return tClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public Field getIdField() {
        Field field = null;
        for (Field checkField : tClass.getDeclaredFields()) {
            if (checkField.isAnnotationPresent(Id.class)) {
                field = checkField;
            }
        }
        return field;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(tClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> list = new ArrayList<>();
        for (Field field : tClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                list.add(field);
            }
        }
        return list;
    }
}
