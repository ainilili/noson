package com.nico.noson.handler.convert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.annotation.NoIgnore;
import com.nico.noson.entity.NoMap.NoRecord;
import com.nico.noson.handler.NoHandler;
import com.nico.noson.util.reflect.FieldUtils;
import com.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:33:57
 */

public abstract class ConvertHandler extends NoHandler{
	
	public ConvertHandler nextHandler;
	
	protected List<Object> handleArray(List<Object> list){
		List<Object> arrays = new ArrayList<Object>();
		for(Object value: list){
			if(value instanceof List){
				arrays.add(handleArray((List<Object>)value));
			}else if(value instanceof Noson){
				arrays.add(handleNoson((Noson)value));
			}else{
				arrays.add(value);
			}
		}
		return arrays;
	}
	
	protected Map<String, Object> handleNoson(Noson noson){
		Map<String, Object> map = new HashMap<String, Object>();
		for(NoRecord<String, Object> record: noson.getNoMap().recordSet()){
			Object value = record.getValue();
			if(value != null){
				if(value instanceof List){
					map.put(record.getKey(), handleArray((List<Object>)record.getValue()));
				}else if(value instanceof Noson){
					map.put(record.getKey(), handleNoson((Noson)value));
				}else{
					map.put(record.getKey(), record.getValue());
				}
			}
		}
		return map;
	}
	
	public <T> T handleObject(Noson noson, Class<T> clazz){
		T target = null;
		try {
			target = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			if(fields != null){
				for(Field field: fields){
					if(noson.get(field.getName()) == null) continue;
					if(field.getDeclaredAnnotation(NoIgnore.class) != null) continue;
					if(TypeUtils.isInseparable(field.getType())){
						FieldUtils.set(field, target, clazz, noson.get(field.getName()));
					}else if(field.getType().isAssignableFrom(Map.class)){
						FieldUtils.set(field, target, clazz, handleNoson((Noson)noson.get(field.getName())));
					}else if(field.getType().isAssignableFrom(List.class)){
						FieldUtils.set(field, target, clazz, handleArray((List<Object>)noson.get(field.getName())));
					}else{
						FieldUtils.set(field, target, clazz, handleObject((Noson)noson.get(field.getName()),field.getType()));
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return target;
	}
	
	public abstract <T> T handle(Object object, Class<T> clazz);
	
}
