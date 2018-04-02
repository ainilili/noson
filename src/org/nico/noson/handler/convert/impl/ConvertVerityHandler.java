package org.nico.noson.handler.convert.impl;

import org.nico.noson.exception.NosonException;
import org.nico.noson.handler.convert.ConvertHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:51:29
 */

public class ConvertVerityHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws NosonException {
		if(obj == null || clazz == null){
			throw new NullPointerException();
		}
		return nextHandler.handle(obj, clazz);
	}

}
