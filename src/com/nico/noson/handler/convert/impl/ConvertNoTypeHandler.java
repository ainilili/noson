package com.nico.noson.handler.convert.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.entity.NoType;
import com.nico.noson.entity.TypeBean;
import com.nico.noson.exception.NosonException;
import com.nico.noson.handler.convert.ConvertHandler;
import com.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:35:46
 */

public class ConvertNoTypeHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws NosonException {
 		if(NoType.class.isAssignableFrom(clazz)){
			TypeBean<?> typeBean = TypeUtils.getGenericityType(clazz);
			if(typeBean != null){
				return (T) allocation(obj, typeBean.getGenericityBeans()[0]);
			}else{
				throw new NullPointerException();
			}
		}
		return nextHandler.handle(obj, clazz);
	}
	
	
	
	
	
}
