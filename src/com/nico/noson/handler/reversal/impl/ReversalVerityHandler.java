package com.nico.noson.handler.reversal.impl;

import com.nico.noson.handler.reversal.ReversalHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月27日 下午11:02:06
 */

public class ReversalVerityHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj == null){
			return null;
		}
		return this.nextHandler.handle(obj);
	}

}
