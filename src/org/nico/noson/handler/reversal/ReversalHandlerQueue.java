package org.nico.noson.handler.reversal;

import java.util.LinkedList;
import java.util.List;

import org.nico.noson.handler.NoHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:39:00
 */

public class ReversalHandlerQueue {

	private LinkedList<ReversalHandler> handlers;

	public ReversalHandlerQueue(){
		handlers = new LinkedList<ReversalHandler>();
	}

	public void add(ReversalHandler handler){
		if(handlers.size() > 0){
			handlers.getLast().nextHandler = handler;
		}
		handlers.add(handler);
	}

	public String handle(Object object){
		if(handlers.size() > 0){
			return handlers.getFirst().handle(object);
		}
		return null;
	}
}
