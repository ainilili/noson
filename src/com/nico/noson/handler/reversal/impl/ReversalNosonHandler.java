package com.nico.noson.handler.reversal.impl;

import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.entity.NoMap.NoRecord;
import com.nico.noson.handler.reversal.ReversalHandler;
import com.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月27日 下午11:03:03
 */

public class ReversalNosonHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj instanceof Noson){
			return handleNoson((Noson)obj);
		}
		return nextHandler.handle(obj);
	}

}
