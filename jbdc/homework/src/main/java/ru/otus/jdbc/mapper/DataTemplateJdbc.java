package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData
    ) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    try {
                        T createdClass = entityClassMetaData.getConstructor().newInstance();
                        entityClassMetaData.getAllFields().forEach(field -> {
                            field.setAccessible(true);
                            try {
                                field.set(createdClass, rs.getObject(field.getName()));
                            } catch (IllegalAccessException | SQLException e) {
                                throw new DataTemplateException(e);
                            }
                            finally {
                                field.setAccessible(false);
                            }
                        });
                        return createdClass;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> tArrayList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T entity = entityClassMetaData.getConstructor().newInstance();
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            field.set(entity, rs.getObject(field.getName()));
                        } catch (IllegalAccessException | SQLException e) {
                            throw new DataTemplateException(e);
                        }
                    });
                    tArrayList.add(entity);
                }
                return tArrayList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            List<Object> objectList;
            objectList = entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> {
                field.setAccessible(true);
                try {
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } finally {
                    field.setAccessible(false);
                }
            }).toList();
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    objectList);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T entity) {
        List<Object> objectList;
        objectList = entityClassMetaData.getAllFields().stream().map(field -> {
            try {
                return field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    objectList);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
