package ru.otus.crm.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.cachehw.MyCache;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DbServiceClientTest extends AbstractHibernateTest {

    private static final Logger logger = LoggerFactory.getLogger(DbServiceClientTest.class);

    @Test
    @DisplayName(" Должен быть быстрее с кэшированием")
    void shouldCorrectSaveClient() {
        DBServiceClient dbServiceClientBare = new DbServiceClientImpl(transactionManager, clientTemplate, null);
        List<Long> ids = addTestData(dbServiceClientBare);
        long timeWithoutCache = measureTime(dbServiceClientBare, ids);

        DBServiceClient dbServiceClientCached = new DbServiceClientImpl(transactionManager, clientTemplate, new MyCache<>());
        ids = addTestData(dbServiceClientCached);
        long timeWithCache = measureTime(dbServiceClientCached, ids);

        logger.info("without cache: {}", timeWithoutCache);
        logger.info("with cache: {}", timeWithCache);

        assertThat(timeWithCache).isLessThan(timeWithoutCache);
    }

    private List<Long> addTestData(DBServiceClient dbServiceClient) {
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Client client = new Client((long) i, "Client#" + i, new Address(null, "Street#" + i),
                    List.of(new Phone(null, "123-45-6" + i), new Phone(null, "765-43-2" + i)));
            list.add(dbServiceClient.saveClient(client).getId());
        }
        return list;
    }

    private long measureTime(DBServiceClient dbServiceClient, List<Long> list) {
        long start = System.currentTimeMillis();
        for (Long id : list) {
            dbServiceClient.getClient(id);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }
}