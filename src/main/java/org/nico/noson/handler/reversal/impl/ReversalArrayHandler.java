package org.nico.noson.handler.reversal.impl;

import java.lang.reflect.Array;

import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

public class ReversalArrayHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj.getClass().isArray()){
			return handleArray(obj, new ReversalRecorder());
		}
		return nextHandler.handle(obj);
	}

}
