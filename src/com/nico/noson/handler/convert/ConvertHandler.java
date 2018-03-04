package com.nico.noson.handler.convert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nico.noson.Noson;
import com.nico.noson.annotation.NoIgnore;
import com.nico.noson.entity.TypeBean;
import com.nico.noson.exception.NosonException;
import com.nico.noson.exception.TypeNotMatchException;
import com.nico.noson.exception.TypeNotSupportException;
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

	protected  List<Object> handleArray(List<Object> list, TypeBean<?> typeBean) throws TypeNotMatchException, TypeNotSupportException{
		List<Object> arrays = new ArrayList<Object>();
		for(Object value: list){
			arrays.add(allocation(value, typeBean));
		}
		return arrays;
	}

	protected Map<Object, Object> handleNoson(Noson noson, TypeBean<?> typeBean) throws TypeNotMatchException, TypeNotSupportException{
		Map<Object, Object> map = new HashMap<Object, Object>();
		for(Entry<String, Object> record: noson.getNoMap().recordSet()){
			map.put(record.getKey(), allocation(record.getValue(), typeBean));
		}
		return map;
	}

	public <T> T handleObject(Noson noson, TypeBean<T> typeBean) throws TypeNotMatchException, TypeNotSupportException{
		T target = null;
		try {
			Class<T> clazz = typeBean.getMainClass();
			target =  clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			if(fields != null){
				for(Field field: fields){
					if(noson.get(field.getName()) == null) continue;
					if(field.getDeclaredAnnotation(NoIgnore.class) != null) continue;
					if(TypeUtils.isInseparable(field.getType())){
						FieldUtils.set(field, target, clazz, noson.get(field.getName()));
					}else{
						FieldUtils.set(field, target, clazz, allocation(noson.get(field.getName()), new TypeBean<>(field.getType())));
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

	/**
	 * 公用的类型处理方法
	 * 
	 * @param referencedClass 被参照Class
	 * @param value 要转换的value
	 * @return Object
	 * @throws TypeNotMatchException 类型不匹配
	 * @throws TypeNotSupportException 类型不支持
	 */
	public Object allocation(Object value, TypeBean<?> typeBean) throws TypeNotMatchException, TypeNotSupportException{
		Class<?> referencedClass = typeBean.getMainClass();
		TypeBean<?>[] genericityBeans = typeBean.getGenericityBeans();
		if(referencedClass.getClass().getName().equals(Object.class.getName())){
			referencedClass = value.getClass();
		}
		if(referencedClass.getName().equals(Object.class.getName())){
			if(value instanceof Noson){
				referencedClass = Map.class;
			}else if(value instanceof List){
				referencedClass = List.class;
			}
		}
		if(Map.class.isAssignableFrom(referencedClass)){
			if(value instanceof Noson){
				if(genericityBeans == null){
					genericityBeans = new TypeBean[]{new TypeBean<>(String.class), new TypeBean<>(Object.class)};
				}
				if(genericityBeans.length == 1){
					throw new TypeNotMatchException("Type not match：" + value.getClass().getName() + " need two genericity class");
				}
				if(String.class.isAssignableFrom(genericityBeans[0].getMainClass())){
					return handleNoson((Noson)value, genericityBeans[1]);
				}else{
					throw new TypeNotSupportException("Do not supported the type " + genericityBeans[0].getClass().getName() + " As the key of Map");
				}
			}else{
				throw new TypeNotMatchException(value.getClass().getName() + " can not cast to Map");
			}
		}else if(List.class.isAssignableFrom(referencedClass) && value instanceof List){
			if(genericityBeans == null){
				genericityBeans = new TypeBean[]{new TypeBean(Object.class)};
			}
			if(genericityBeans.length == 1){
				return handleArray((List<Object>)value, genericityBeans[0]);
			}else{
				throw new TypeNotMatchException("Type not match：" + value.getClass().getName() + " need two genericity class");
			}
		}else if(value instanceof Noson){
			return handleObject((Noson)value, typeBean);
		}else{
			return value;
		}
	}

	public abstract <T> T handle(Object object, Class<T> clazz) throws NosonException;

}
