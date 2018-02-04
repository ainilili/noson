package com.nico.noson.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.nico.noson.config.NoConfig;
import com.nico.noson.util.string.StringUtils;


/**
 * FieldUtils
 * @author nico
 * @date 2017年12月29日
 */
public class FieldUtils {
	
	/**
	 * 数字类型集合
	 */
	public static final Set<Class<?>> NUM_CLASS_SET = new HashSet<Class<?>>(){
		private static final long serialVersionUID = -1925389609848043520L;
		{
			add(int.class);
			add(short.class);
			add(double.class);
			add(long.class);
			add(float.class);
			add(byte.class);
		}
	};
	
	
	/**
	 * Field Set操作
	 * @param field 字段
	 * @param obj 对象
	 * @param clazz class
	 * @param value 参数
	 */
	public static void set(Field field, Object obj, Class<?> clazz, Object value){
		Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
		if(currentMethod == null) return;
		try {
			value = convertType(field, value);
			MethodUtils.invoke(currentMethod, obj, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ParseException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Field Get操作
	 * @param field 字段
	 * @param obj 对象
	 * @param clazz class
	 * @return 结果
	 */
	public static Object get(Field field, Object obj, Class<?> clazz){
		Method currentMethod = MethodUtils.getGetterMethod(field, clazz);
		if(currentMethod == null) return null;
		try {
			return MethodUtils.invoke(currentMethod, obj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * Get field from this clazz or it's super class
	 * @param fieldName field name what do you want to get !
	 * @param clazz the class where is the field's home 
	 * @return target field
	 * @throws NoSuchFieldException no found the field from the class
	 */
	public static Field getField(String fieldName, Class<?> clazz) throws NoSuchFieldException{
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				field = getField(fieldName, clazz.getSuperclass());
			}else{
				e.printStackTrace();
			}
		}
		return field;
	}
	
	
	/**
	 * 将target转为clazz的类型对象
	 * @param clazz 要转换的目标对象类型
	 * @param target 待转对象
	 * @return 转换后的对象
	 * @throws ParseException 
	 */
	public static Object convertType(Field field, Object target) throws ParseException{
		Class<?> clazz = field.getType();
		Object obj = null;
		if(Number.class.isAssignableFrom(clazz) || NUM_CLASS_SET.contains(clazz)){
			Number value = StringUtils.isBlank(target) ? 0 : (Number)Double.parseDouble(String.valueOf(target));
			if(clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)){
				obj = value.intValue();
			}else if(clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)){
				obj = value.doubleValue();
			}else if(clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class)){
				obj = value.intValue();
			}else if(clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)){
				obj = value.intValue();
			}else if(clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class)){
				obj = value.shortValue();
			}else{
				obj = value.byteValue();
			}
		}else if(String.class.isAssignableFrom(clazz)){
			obj = String.valueOf(target);
		}else if(Date.class.isAssignableFrom(clazz)){
			if(target instanceof Date){
				obj = target;
			}else{
				obj = NoConfig.defaultDateFormat.parse(String.valueOf(target));
			}
		}else if(Enum.class.isAssignableFrom(clazz)){
			if(target instanceof Enum){
				obj = target;
			}else{
				Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
				obj = Enum.valueOf(enumClass, String.valueOf(target));
			}
		}else if(clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class)){
			obj = Boolean.parseBoolean(String.valueOf(target));
		}else{
			obj = target;
		}
		return obj;
	}
	
	
	
	
}
