package com.nico.noson.handler.convert.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.entity.NoHashMap.NoRecord;
import com.nico.noson.entity.TypeBean;
import com.nico.noson.exception.NosonException;
import com.nico.noson.exception.NosonFormatException;
import com.nico.noson.handler.convert.ConvertHandler;
import com.nico.noson.policy.NoPolicy;
import com.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 上午11:04:43
 */

public class ConvertListHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws NosonException {
		if(clazz.equals(List.class)){
			if(null == obj) return null;
			if(obj instanceof List){
				return (T) allocation(obj, TypeUtils.getGenericityType(clazz));
			}else if(obj instanceof Noson){
				List<Object> list = new ArrayList<Object>();
				list.add(allocation(obj, new TypeBean<>(Map.class)));
				return  (T) list;
			}
		}
		return nextHandler.handle(obj, clazz);
	}
	
}
