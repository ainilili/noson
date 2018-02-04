package com.nico.noson.handler.reversal;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nico.noson.Noson;
import com.nico.noson.annotation.NoIgnore;
import com.nico.noson.entity.NoMap.NoRecord;
import com.nico.noson.handler.convert.ConvertHandler;
import com.nico.noson.util.reflect.FieldUtils;
import com.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午10:06:43
 */

public abstract class ReversalHandler {

	public ReversalHandler nextHandler;

	public String handleArray(List<Object> list){
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int index = 0; index < list.size(); index ++){
			Object value = list.get(index);
			if(value != null){
				builder.append(handleCommon(value.getClass(), value));
				if(index < list.size() - 1){
					builder.append(",");
				}
			}
		}
		builder.append("]");
		return builder.toString();
	}

	public String handleNoson(Noson noson){
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int index = 0;
		for(NoRecord<String, Object> record: noson.recordSet()){
			Object value = record.getValue();
			if(value != null){
				builder.append("\"" + record.getKey() +"\":");
				builder.append(handleCommon(value.getClass(), value));
				if(index < noson.size() - 1){
					builder.append(",");
				}
			}
			index ++;
		}
		builder.append("}");
		return builder.toString();
	}

	public String handleMap(Map<String, Object> map){
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int index = 0;
		for(Entry<String, Object> entry: map.entrySet()){
			Object value = entry.getValue();
			if(value != null){
				builder.append("\"" + entry.getKey() +"\":");
				builder.append(handleCommon(value.getClass(), value));
				if(index < map.size() - 1){
					builder.append(",");
				}
			}
			index ++;
		}
		builder.append("}");
		return builder.toString();
	}

	public String handleObject(Object obj){
		if(obj == null) return "{}";
		StringBuilder builder = new StringBuilder();
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		builder.append("{");
		int index = 0;
		for(Field field: fields){
			Object value;
			try {
				value = FieldUtils.get(field, obj, clazz);
				if(field.getDeclaredAnnotation(NoIgnore.class) != null) continue;
				builder.append("\"" + field.getName() +"\":");
				builder.append(handleCommon(field.getType(), value));
				if(index < fields.length - 1){
					builder.append(",");
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			index ++;
		}
		builder.append("}");
		return builder.toString();
	}

	public String handleCommon(Class<?> type, Object value){
		StringBuilder builder = new StringBuilder();
		if(TypeUtils.isInseparable(type)){
			builder.append(TypeUtils.typeWrap(value));
		}else if(Collection.class.isAssignableFrom(type)){
			builder.append(handleArray((List<Object>)value));
		}else if(type.equals(Noson.class)){
			builder.append(handleNoson((Noson)value));
		}else if(Map.class.isAssignableFrom(type)){
			builder.append(handleMap((Map<String, Object>)value));
		}else{
			builder.append(handleObject(value));
		}
		return builder.toString();
	}
	
	public abstract String handle(Object obj);
}
