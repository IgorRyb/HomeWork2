package ru.otus.jdbc.mapper.impl;

import org.apache.commons.lang3.StringUtils;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + entityClassMetaData.getName() + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        String value = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        String fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName())
                .collect(Collectors.joining(", "));

        return "INSERT INTO " + entityClassMetaData.getName() + " (" + fields + ") VALUES (" + value + ")";
    }

    @Override
    public String getUpdateSql() {
        String fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> {return field.getName() + " = ?";})
                .collect(Collectors.joining(", "));

        return "UPDATE " + entityClassMetaData.getName() + " SET " + fields + "WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

}
