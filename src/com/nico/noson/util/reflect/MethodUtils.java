package com.nico.noson.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * MethodUtils
 * @author nico
 * @date 2017年12月29日
 */
public class MethodUtils {
	
	/**
	 * 获取Field对应的Getter方法
	 * @param field 目标Field
	 * @param clazz 目标Class
	 * @return Getter方法
	 */
	public static Method getGetterMethod(Field field, Class<?> clazz){
		Method method = null;
		try {
			if(Boolean.class.isAssignableFrom(field.getType()) || field.getType().equals(boolean.class)){
				method = clazz.getDeclaredMethod("is" + upperFirstLetter(field.getName()));	
			}else{
				method = clazz.getDeclaredMethod("get" + upperFirstLetter(field.getName()));
			}
		} catch (NoSuchMethodException | SecurityException e) {
			if(clazz.getSuperclass() != null){
				method = getGetterMethod(field, clazz.getSuperclass());
			}else{
				e.printStackTrace();
			}
		}
		return method;
	}
	
	/**
	 * 获取Field对应的Setter方法
	 * @param field 目标Field
	 * @param clazz 目标Class
	 * @return Setter方法
	 */
	public static Method getSetterMethod(Field field, Class<?> clazz) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod("set" + upperFirstLetter(field.getName()), field.getType());
		}catch (NoSuchMethodException | SecurityException e) {
			if(clazz.getSuperclass() != null){
				method = getSetterMethod(field, clazz.getSuperclass());
			}else{
				e.printStackTrace();
			}
		}
		return method;
	}
	
	/**
	 * 执行方法
	 * @param method 要执行的方法
	 * @param obj 被执行的方法所在的对象
	 * @param params 方法参数
	 * @return 执行结果
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invoke(Method method, Object obj, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return method.invoke(obj, params);
	}
	
	/**
	 * 字段首字母大写
	 * @param fieldName 字段名字
	 * @return 结果
	 */
	private static String upperFirstLetter(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
