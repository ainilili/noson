package com.nico.noson.handler.convert.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.exception.NosonFormatException;
import com.nico.noson.exception.TypeNotMatchException;
import com.nico.noson.exception.TypeNotSupportException;
import com.nico.noson.handler.convert.ConvertHandler;
import com.nico.noson.policy.NoPolicy;
import com.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 上午11:27:44
 */

public class ConvertEntityHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws TypeNotMatchException, TypeNotSupportException {
		if(null == obj) return null;
		if(obj instanceof Noson){
			return (T) handleObject((Noson)obj, TypeUtils.getGenericityType(clazz));
		}
		return null;
	}
	
}
