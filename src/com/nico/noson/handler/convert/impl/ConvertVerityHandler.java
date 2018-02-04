package com.nico.noson.handler.convert.impl;

import com.nico.noson.handler.convert.ConvertHandler;
import com.nico.noson.util.string.StringUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:51:29
 */

public class ConvertVerityHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) {
		if(obj == null || clazz == null){
			return null;
		}
		return nextHandler.handle(obj, clazz);
	}

}
