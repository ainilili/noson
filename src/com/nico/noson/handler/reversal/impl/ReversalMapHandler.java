package com.nico.noson.handler.reversal.impl;

import java.util.List;
import java.util.Map;

import com.nico.noson.handler.reversal.ReversalHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月27日 下午11:03:03
 */

public class ReversalMapHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj instanceof Map){
			return handleMap((Map<String, Object>)obj);
		}
		return nextHandler.handle(obj);
	}

}
