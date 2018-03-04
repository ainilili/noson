package com.nico.noson.entity;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.nico.noson.entity.NoHashMap.NoRecord;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月24日 下午9:58:53
 */

public interface NoMap<K, V> {

	
	public V put(K key, V value);
	
	public boolean contains(K key);

	public V get(K key);
	
	public void remove(K key);
	
	public int size();
	
	public Set<Entry<K, V>> recordSet();
	
	public Set<K> keySet();
	
}
