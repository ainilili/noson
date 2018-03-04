package com.nico.noson.scanner.depot;

import java.util.List;

import com.nico.noson.Noson;
import com.nico.noson.entity.NoType;
import com.nico.noson.exception.NosonException;
import com.nico.noson.handler.convert.ConvertHandlerQueue;
import com.nico.noson.handler.convert.impl.*;
import com.nico.noson.handler.reversal.ReversalHandlerQueue;
import com.nico.noson.handler.reversal.impl.*;
import com.nico.noson.policy.NoPolicy;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午7:31:54
 */

public class NoDepot {
	
	private static ConvertHandlerQueue handlerQueue;
	
	private static ReversalHandlerQueue handleQueue;
	
	static{
		/** convert handle list add **/
		{
			handlerQueue = new ConvertHandlerQueue();
			handlerQueue.add(new ConvertVerityHandler());
			handlerQueue.add(new ConvertNoTypeHandler());
			handlerQueue.add(new ConvertMapHandler());
			handlerQueue.add(new ConvertListHandler());
			handlerQueue.add(new ConvertEntityHandler());
		}
		/** reversal handle list add **/
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
			object =  NoPolicy.policy(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}
	
	public static Noson parseNoson(String json){
		Noson noson = null;
		try{
			noson = (Noson) NoPolicy.policy(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		return noson;
	}
	
	public static List<Object> parseArray(String json){
		List<Object> list = null;
		try{
			Object obj = NoPolicy.policy(json);
			if(obj instanceof List<?>){
				list = (List<Object>) obj;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static <T> T convert(Object obj, Class<T> clazz){
		try {
			return handlerQueue.handle(obj, clazz);
		} catch (NosonException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T convert(Object obj, NoType<T> type){
		try {
			return (T)handlerQueue.handle(obj, type.getClass());
		} catch (NosonException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String reversal(Object obj){
		return handleQueue.handle(obj);
	}
}
