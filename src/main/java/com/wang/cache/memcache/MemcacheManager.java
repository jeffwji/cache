package com.wang.cache.memcache;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wang.cache.interfaces.ICacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by wangji on 2016/5/30.
 */
public class MemcacheManager<A> implements ICacheManager<A> {
    String cacheName = "defaultCache";
    public /*@Value("${presence.list.mem.size}")*/ Integer presenceCacheSize;
    public /*@Value("${presence.list.mem.timeToLiveSeconds}")*/ Integer presenceTimeToLiveSeconds = 0;
    public /*@Value("${presence.list.mem.timeToIdleSeconds}")*/ Integer presenceTimeToIdleSeconds = 0;

    public MemcacheManager(){};
    public MemcacheManager(String name){
        String cacheName = name;
    }

    Cache presenceCache;
    static CacheManager cacheManager;

    private static synchronized CacheManager getCacheManager() {
        if (null == cacheManager) {
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    @PostConstruct
    public void init() {
        cacheManager = getCacheManager();

        presenceCache = cacheManager.getCache(cacheName);
        if (null == presenceCache) {
            presenceCache = new Cache(cacheName, presenceCacheSize, false, false, presenceTimeToLiveSeconds,
                    presenceTimeToIdleSeconds);
            cacheManager.addCache(presenceCache);
        }
    }

    public Cache getPresenceCache() {
        return presenceCache;
    }

    public void setPresenceCache(Cache presenceCache) {
        this.presenceCache = presenceCache;
    }

    public List<String> getUsers() {
        return this.presenceCache.getKeys();
    }

    @Override
    public void add(String name, A item) {
        List<A> items = get(name);
        if (null != items) {
            items.add(item);
        }
        else {
            items = new ArrayList<A>();
            items.add(item);
        }

        Element element = new Element(name, items);
        presenceCache.put(element);
    }

    @Override
    public List<A> get(String name) {
        Element element = presenceCache.get(name);
        return (List<A>) (null == element ? null : element.getObjectValue());
    }

    @Override
    public boolean remove(String name) {
        return presenceCache.remove(name);
    }
}
