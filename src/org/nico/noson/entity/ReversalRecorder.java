package org.nico.noson.entity;

import java.util.ArrayList;
import java.util.Collection;

/** 
 * Used to store parsed objects in the parsing process.
 * 
 * @author nico
 * @version createTime：2018年3月26日 下午8:59:34
 */
public class ReversalRecorder {

	/**
	 * Store the objects that have been loaded.
	 */
	private Collection<Object> loaders;
	
	/**
	 * The number of times the object is loaded.
	 * Used to determine whether or not to continue loading.
	 */
	private NoMap<Object, Integer> loaderRecord; 
	
	public ReversalRecorder(){
		loaders = new ArrayList<Object>();
		loaderRecord = new NoHashMap<Object, Integer>();
	}

	public boolean contains(Object obj){
		return loaders.contains(obj);
	}
	
	public int getCount(Object obj){
		return loaderRecord.get(obj) == null ? 0 : loaderRecord.get(obj);
	}
	
	public Object add(Object obj){
		loaders.add(obj);
		if(loaderRecord.contains(obj)){
			loaderRecord.put(obj, loaderRecord.get(obj) + 1);
		}else{
			loaderRecord.put(obj, 1);
		}
		return obj;
	}
	
	public Object remove(Object obj){
		loaders.remove(obj);
		if(loaderRecord.contains(obj)){
			loaderRecord.put(obj, loaderRecord.get(obj) - 1);
		}else{
			loaderRecord.put(obj, 0);
		}
		return obj;
	}
}
