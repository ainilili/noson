package org.nico.noson;


import java.util.List;
import java.util.Map.Entry;

import org.nico.noson.entity.NoLinkedMap;
import org.nico.noson.entity.NoMap;
import org.nico.noson.entity.NoType;
import org.nico.noson.scanner.depot.NoDepot;

import java.util.Set;

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

	/**
	 * 将Json字符串解析成Noson数据结构（Json串以'{'开头)
	 * 
	 * @param json 要被解析的Json字符串
	 * @return {@link Noson}
	 */
	public static Noson parseNoson(String json){
		return NoDepot.parseNoson(json);
	}

	/**
	 * 将Json字符串解析成List数据结构（Json串以'['开头)
	 * 
	 * @param json 要被解析的Json字符串
	 * @return {@link List}
	 */
	public static List<Object> parseArray(String json){
		return NoDepot.parseArray(json);
	}

	/**
	 * 将Json串转换成Class数据结构
	 * 
	 * @param json 要被转换的Json字符串
	 * @param clazz 要转换的数据结构
	 * @return 目标转换对象
	 */
	public static <T> T convert(String json, Class<T> clazz){
		return NoDepot.convert(NoDepot.parseObject(json), clazz);
	}

	/**
	 * 将obj转换成Class数据结构
	 * 
	 * @param obj 要被转换的对象
	 * @param clazz 要转换的数据结构
	 * @return 目标转换对象
	 */
	public static <T> T convert(Object obj, Class<T> clazz){
		return NoDepot.convert(obj, clazz);
	}

	/**
	 * 将noson转换成Class数据结构
	 * 
	 * @param noson 被转换的noson
	 * @param clazz 要转换的数据结构
	 * @return 目标转换对象
	 */
	public static <T> T convert(Noson noson, Class<T> clazz){
		return NoDepot.convert(noson, clazz);
	}

	/**
	 * 将List转换成Class数据结构
	 * 
	 * @param objs 被转换的List
	 * @param clazz 要转换的数据结构
	 * @return 目标转换对象
	 */
	public static <T> T convert(List<Object> objs, Class<T> clazz){
		return NoDepot.convert(objs, clazz);
	}

	/**
	 * 将制定对象转换成NoType<T>中的'T'类型数据结构，该方法支持Json转换成
	 * 复杂的数据结构<br>
	 * 例如：<br>
	 * Map<String, User> map = Noson.convert(json, new NoType<<b>Map&lt;String, User&gt;</b>>(){});<br>
	 * 以上代码会将json转换成Map<String, User>的格式
	 * 
	 * @param obj 被转换的对象
	 * @param type 转换成泛型对应的数据结构
	 * @return 目标转换对象
	 */
	public static <T> T convert(Object obj, NoType<T> type){
		return NoDepot.convert(obj instanceof String? NoDepot.parseObject((String)obj) : obj, type);
	}

	/**
	 * 将对象序列化成Json串
	 * 
	 * @param obj 要被序列化的对象
	 * @return 序列化后的字符串
	 */
	public static String reversal(Object obj){
		return NoDepot.reversal(obj);
	}

	/**
	 * Noson自身序列化成Json
	 */
	public String toString(){
		return reversal(this);
	}

}
