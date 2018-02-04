package com.nico.noson.handler.convert.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.entity.NoMap.NoRecord;
import com.nico.noson.exception.NosonFormatException;
import com.nico.noson.handler.convert.ConvertHandler;
import com.nico.noson.policy.NoPolicy;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 上午11:04:43
 */

public class ConvertListHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) {
		if(clazz.equals(List.class)){
			if(null == obj) return null;
			if(obj instanceof List){
				return (T) handleArray((List<Object>)obj);
			}else if(obj instanceof Noson){
				List<Object> list = new ArrayList<Object>();
				list.add(handleNoson((Noson)obj));
				return  (T) list;
			}
		}
		return nextHandler.handle(obj, clazz);
	}
	
}
