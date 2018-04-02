package org.nico.noson.scanner.depot;

import java.util.List;

import org.nico.noson.Noson;
import org.nico.noson.adapter.JsonParseAdapter;
import org.nico.noson.entity.NoType;
import org.nico.noson.exception.NosonException;
import org.nico.noson.handler.convert.ConvertHandlerQueue;
import org.nico.noson.handler.convert.impl.*;
import org.nico.noson.handler.reversal.ReversalHandlerQueue;
import org.nico.noson.handler.reversal.impl.*;
import org.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午7:31:54
 */

public class NoDepot {
	
	private static ConvertHandlerQueue handlerQueue;
	
	private static ReversalHandlerQueue handleQueue;
	
	static{
		/** Convert handle list add **/
		{
			handlerQueue = new ConvertHandlerQueue();
			handlerQueue.add(new ConvertVerityHandler());
			handlerQueue.add(new ConvertNoTypeHandler());
			handlerQueue.add(new ConvertMapHandler());
			handlerQueue.add(new ConvertListHandler());
			handlerQueue.add(new ConvertObjectHandler());
		}
		/** Reversal handle list add **/
		{
			handleQueue = new ReversalHandlerQueue();
			handleQueue.add(new ReversalVerityHandler());
			handleQueue.add(new ReversalNosonHandler());
			handleQueue.add(new ReversalListHandler());
			handleQueue.add(new ReversalMapHandler());
			handleQueue.add(new ReversalObjectHandler());
		}
	}
	
	public static Object parseObject(String json){
		Object object = null;
		try{
			object =  JsonParseAdapter.policy(json);
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			clear();
		}
		return object;
	}
	
	public static Noson parseNoson(String json){
		Noson noson = null;
		try{
			noson = (Noson) JsonParseAdapter.policy(json);
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			clear();
		}
		return noson;
	}
	
	public static List<Object> parseArray(String json){
		List<Object> list = null;
		try{
			Object obj = JsonParseAdapter.policy(json);
			if(obj instanceof List<?>){
				list = (List<Object>) obj;
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			clear();
		}
		return list;
	}
	
	/**
	 * recycling
	 */
	private static void clear(){
		TypeUtils.keyCache.clearCache();
		TypeUtils.valueCache.clearCache();
	}
	
	public static <T> T convert(Object obj, Class<T> clazz){
		try {
			return handlerQueue.handle(obj, clazz);
		} catch (NosonException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T convert(Object obj, NoType<T> type){
		try {
			return (T)handlerQueue.handle(obj, type.getClass());
		} catch (NosonException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String reversal(Object obj){
		return handleQueue.handle(obj);
	}
}
