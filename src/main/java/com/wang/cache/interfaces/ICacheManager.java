package com.wang.cache.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangji on 2016/5/30.
 */
public interface ICacheManager<A> {
    public void add(String name, A item);
    public List<A> get(String name);
    public boolean remove(String name);
}
