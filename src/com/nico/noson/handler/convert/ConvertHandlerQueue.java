package com.nico.noson.handler.convert;

import java.util.LinkedList;

import com.nico.noson.exception.NosonException;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:39:00
 */

public class ConvertHandlerQueue {

	private LinkedList<ConvertHandler> handlers;

	public ConvertHandlerQueue(){
		handlers = new LinkedList<ConvertHandler>();
	}

	public void add(ConvertHandler handler){
		if(handlers.size() > 0){
			handlers.getLast().nextHandler = handler;
		}
		handlers.add(handler);
	}

	public <T> T handle(Object object, Class<T> clazz) throws NosonException{
		if(handlers.size() > 0){
			return handlers.getFirst().handle(object, clazz);
		}
		return null;
	}
}
