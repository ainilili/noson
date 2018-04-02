package org.nico.noson.handler.convert.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.adapter.JsonParseAdapter;
import org.nico.noson.exception.NosonException;
import org.nico.noson.exception.NosonFormatException;
import org.nico.noson.exception.TypeNotMatchException;
import org.nico.noson.exception.TypeNotSupportException;
import org.nico.noson.handler.convert.ConvertHandler;
import org.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 上午11:27:44
 */

public class ConvertObjectHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws NosonException {
		if(null == obj) return null;
		if(obj instanceof Noson){
			return (T) handleObject((Noson)obj, TypeUtils.getGenericityType(clazz));
		}
		return null;
	}
	
}
