package com.nico.noson;


import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.nico.noson.entity.NoLinkedMap;
import com.nico.noson.entity.NoMap;
import com.nico.noson.entity.NoType;
import com.nico.noson.scanner.depot.NoDepot;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月24日 下午9:58:06
 */

public class Noson {
	
	private NoMap<String, Object> noMap;
	
	public Noson(){
		this.noMap = new NoLinkedMap<String, Object>();
	}
	
	public Set<Entry<String, Object>> recordSet(){
		return noMap.recordSet();
	}
	
	public int size(){
		return noMap.size();
	}
	
	public NoMap<String, Object> getNoMap() {
		return noMap;
	}

	public Object put(String key, Object value){
		return noMap.put(key, value);
	}
	
	public Object get(String key){
		return noMap.get(key);
	}
	
	public boolean containsKey(String key){
		return noMap.contains(key);
	}
	
	public static Noson parseNoson(String json){
		return NoDepot.parseNoson(json);
	}
	
	public static List<Object> parseArray(String json){
		return NoDepot.parseArray(json);
	}
	
	public static <T> T convert(String json, Class<T> clazz){
			return NoDepot.convert(NoDepot.parseObject(json), clazz);
	}
	
	public static <T> T convert(Object obj, Class<T> clazz){
		return NoDepot.convert(obj, clazz);
	}
	
	public static <T> T convert(Noson noson, Class<T> clazz){
		return NoDepot.convert(noson, clazz);
	}
	
	public static <T> T convert(List<Object> objs, Class<T> clazz){
		return NoDepot.convert(objs, clazz);
	}
	
	public static String reversal(Object obj){
		return NoDepot.reversal(obj);
	}
	
	public static <T> T convert(Object obj, NoType<T> type){
		return NoDepot.convert(obj instanceof String? NoDepot.parseObject((String)obj) : obj, type);
	}
	
	public String toString(){
		return reversal(this);
	}
	
}
