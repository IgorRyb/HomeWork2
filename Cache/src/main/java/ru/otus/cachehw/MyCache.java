package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


public class MyCache<K, V> implements HwCache<K, V> {

    private Map<K, V> hashMap = new WeakHashMap<>();

    private List<HwListener<K,V>> hwListeners = new ArrayList<>();

    private Logger log = LoggerFactory.getLogger(MyCache.class);

    @Override
    public void put(K key, V value) {
        hashMap.put(key, value);
        sendNotify(key, value, "put");
    }

    @Override
    public void remove(K key) {
        sendNotify(key, null, "remove");
         hashMap.remove(key);
    }

    @Override
    public V get(K key) {
        V res = hashMap.get(key);
        sendNotify(key, null, "get");
        return res;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        hwListeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        hwListeners.remove(listener);
    }

    private void sendNotify(K key, V value, String action) {
        for (HwListener<K, V> hwListener : hwListeners) {
            try {
                hwListener.notify(key, value, action);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }
}
