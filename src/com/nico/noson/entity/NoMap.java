package com.nico.noson.entity;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月24日 下午9:58:53
 */

public class NoMap<K, V> {

	private Integer defaultLength = 5;

	private Integer expand = 2;

	private Double loadFactor = 0.75;

	private V nullValue;
	
	private Lock lock = new ReentrantLock();

	private int size;
	
	private Set<NoRecord<K, V>> recordSet;
	
	private Set<K> keySet;

	private NoRecord<K, V>[] tables = (NoRecord<K, V>[]) Array.newInstance(NoRecord.class, defaultLength);

	public NoMap(){
		recordSet = new HashSet<NoRecord<K, V>>();
		keySet = new HashSet<K>();
	}
	
	final int getHash(K key) {
		int h = 0;
		h ^= key.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	public int getIndex(int hash){
		return hash & (tables.length - 1);
	}

	public V put(K key, V value){
		lock.lock();
		if(size > tables.length / loadFactor){
			NoRecord<K, V>[] newTables = new NoRecord[tables.length * expand];
			for(NoRecord<K, V> record: tables){
				for(NoRecord<K, V> th = record; th != null; th = th.getNext()){
					int index = th.getHash() & (newTables.length - 1);
					NoRecord<K, V> newRecord = new NoRecord<K, V>(th.getKey(), th.getValue(), th.getHash(), newTables[index]);
					newTables[index] = newRecord;
				}
			}
			tables = newTables;
		}
		if(key == null){
			nullValue = value;
		}else{
			int hash = getHash(key);
			int index = getIndex(hash);
			NoRecord<K, V> record = tables[index];
			if(record != null){
				NoRecord<K, V> temp = record;
				boolean update = false;
				while(temp != null){
					if(temp.getKey().equals(key) && temp.getHash() == hash){
						temp.setValue(value);
						update = true;
						break;
					}else{
						temp = temp.next;
					}
				}
				if(!update){
					NoRecord<K, V> newRecord = new NoRecord<K, V>(key, value, hash, record);
					tables[index] = newRecord;
					recordSet.add(tables[index]);
					keySet.add(key);
					size ++;
				}
			}else{
				tables[index] = new NoRecord<K, V>(key, value, hash);
				recordSet.add(tables[index]);
				keySet.add(key);
				size ++;
			}
		}
		lock.unlock();
		return value;
	}
	
	public boolean contains(K key){
		if(key == null){
			if(nullValue != null)
				return true;
		}else{
			int hash = getHash(key);
			int index = getIndex(hash);
			NoRecord<K, V> record = tables[index];
			if(record != null){
				NoRecord<K, V> temp = record;
				while(temp != null){
					if(temp.getKey().equals(key) && temp.getHash() == hash){
						return true;
					}else{
						temp = temp.next;
					}
				}
			}
		}
		return false;
	}

	public V get(K key){
		if(key == null){
			return nullValue;
		}else{
			int hash = getHash(key);
			int index = getIndex(hash);
			NoRecord<K, V> record = tables[index];
			if(record != null){
				NoRecord<K, V> temp = record;
				while(temp != null){
					if(temp.getKey().equals(key) && temp.getHash() == hash){
						return temp.getValue();
					}else{
						temp = temp.next;
					}
				}
			}
		}
		return null;
	}
	
	public void remove(K key){
		if(key == null){
			recordSet.remove(nullValue);
			nullValue = null;
		}else{
			int hash = getHash(key);
			int index = getIndex(hash);
			NoRecord<K, V> record = tables[index];
			if(record != null){
				NoRecord<K, V> temp = record;
				while(temp != null){
					if(temp.getKey().equals(key) && temp.getHash() == hash){
						recordSet.remove(temp);
						keySet.remove(key);
						temp = temp.next;
						break;
					}else{
						temp = temp.next;
					}
				}
			}
		}
	}
	
	public int size(){
		return size;
	}
	
	public Set<NoRecord<K, V>> recordSet(){
		return recordSet;
	}
	
	public Set<K> keySet(){
		return keySet;
	}
	
	public static class NoRecord<K, V>{

		private K key;

		private V value;

		private int hash;

		private NoRecord<K, V> next;

		public NoRecord(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public NoRecord(K key, V value, int hash) {
			this.key = key;
			this.value = value;
			this.hash = hash;
		}

		public NoRecord(K key, V value, NoRecord<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public NoRecord(K key, V value, int hash, NoRecord<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.hash = hash;
			this.next = next;
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public NoRecord<K, V> getNext() {
			return next;
		}

		public void setNext(NoRecord<K, V> next) {
			this.next = next;
		}

		public int getHash() {
			return hash;
		}

		public void setHash(int hash) {
			this.hash = hash;
		}
	}

	public static void main(String[] args) {
		NoMap<Integer, Integer> map = new NoMap<Integer, Integer>();
		int size = 10000 * 100;
		for(Integer i = 0; i < size; i ++){
			map.put(i, i);
		}
		int random = (int)(Math.random()*size);
		System.out.println(random);
		System.out.println(map.get(random));
		
	}
}
