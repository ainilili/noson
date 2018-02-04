package com.nico.noson.handler.reversal.impl;

import java.util.List;

import com.nico.noson.handler.reversal.ReversalHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月27日 下午11:03:03
 */

public class ReversalObjectHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		return handleObject(obj);
	}

}
