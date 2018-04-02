package org.nico.noson.handler.reversal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.annotation.NoIgnore;
import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.util.reflect.FieldUtils;
import org.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午10:06:43
 */

public abstract class ReversalHandler {

	public ReversalHandler nextHandler;

	public String handleArray(Collection<Object> list, ReversalRecorder recorder){
		recorder.add(list);
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int index = 0;
		for(Object value: list){
			value = cycleCheck(recorder, value);
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}
		builder.append("]");
		recorder.remove(list);
		return builder.toString();
	}

	public String handleNoson(Noson noson, ReversalRecorder recorder){
		recorder.add(noson);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int index = 0;
		for(Entry<String, Object> record: noson.recordSet()){
			Object value = cycleCheck(recorder, record.getValue());
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append("\"" + record.getKey() +"\":");
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}

		builder.append("}");
		recorder.remove(noson);
		return builder.toString();
	}

	public String handleMap(Map<String, Object> map, ReversalRecorder recorder){
		recorder.add(map);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int index = 0;
		for(Entry<String, Object> entry: map.entrySet()){
			Object value = cycleCheck(recorder, entry.getValue());
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append("\"" + entry.getKey() +"\":");
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}
		builder.append("}");
		recorder.remove(map);
		return builder.toString();
	}

	public String handleObject(Object obj, ReversalRecorder recorder){
		recorder.add(obj);
		if(obj == null) return "{}";
		StringBuilder builder = new StringBuilder();
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		builder.append("{");
		int index = 0;
		for(Field field: fields){
			Object value;
			try {
				value = cycleCheck(recorder, FieldUtils.get(field, obj, clazz));
				if(recorder.contains(value) && recorder.getCount(value) > NosonConfig.ALLOW_CYCLE_MAX_COUNT){

				}
				if(value != null || NosonConfig.ALLOW_EMPTY){
					if(index > 0){
						builder.append(",");
					}
					if(field.getDeclaredAnnotation(NoIgnore.class) != null) continue;
					if(! NosonConfig.ALLOW_MODIFY.contains(Modifier.toString(field.getModifiers()))) continue;
					builder.append("\"" + field.getName() +"\":");
					builder.append(handleCommon(field.getType(), value, recorder));
					index ++;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		builder.append("}");
		recorder.remove(obj);
		return builder.toString();
	}

	public String handleCommon(Class<?> type, Object currentObj, ReversalRecorder recorder){
		StringBuilder builder = new StringBuilder();
		if(TypeUtils.isInseparable(type) || currentObj == null){
			builder.append(TypeUtils.typeWrap(type, currentObj));
		}else if(Collection.class.isAssignableFrom(type)){
			builder.append(handleArray((Collection<Object>)currentObj, recorder));
		}else if(Noson.class.isAssignableFrom(type)){
			builder.append(handleNoson((Noson)currentObj, recorder));
		}else if(Map.class.isAssignableFrom(type)){
			builder.append(handleMap((Map<String, Object>)currentObj, recorder));
		}else{
			builder.append(handleObject(currentObj, recorder));
		}
		return builder.toString();
	}

	public Object cycleCheck(ReversalRecorder recorder, Object value){
		if(value == null){
			return null;
		}
		if(recorder.contains(value) && recorder.getCount(value) > NosonConfig.ALLOW_CYCLE_MAX_COUNT){
			return null;
		}
		return value;
	}

	public abstract String handle(Object obj);
}
