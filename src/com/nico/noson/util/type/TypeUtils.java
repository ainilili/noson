package com.nico.noson.util.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nico.noson.config.NoConfig;

/** 
 * Noson类型转换工具
 * @author nico
 * @version 创建时间：2017年11月25日 下午4:55:08
 */
public class TypeUtils {
	
	/**
	 * JSON属性转类型
	 * @param param
	 * @return
	 */
	public static Object typeAllot(String param){
		if(param.startsWith("'") || param.startsWith("\"")){
			param = param.substring(1, param.length() - 1);
			try{
				return NoConfig.defaultDateFormat.parse(param);
			}catch(ParseException e){
				return param;
			}
		}else{
			try{
				return Integer.parseInt(param);
			}catch(NumberFormatException e1){
				try{
					return Long.parseLong(param);
				}catch(NumberFormatException e2){
					try{
						return Double.parseDouble(param);
					}catch(NumberFormatException e3){
						try{
							return Double.parseDouble(param);
						}catch(NumberFormatException e4){
							try{
								return Boolean.parseBoolean(param);
							}catch(Exception e){
								return null;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 判断是否是基本类型
	 * @param clazz 被判断的class
	 * @return 验证结果
	 */
	public static boolean isInseparable(Class<?> clazz){
		boolean inseparable = false;
		if(clazz.isPrimitive()){
			inseparable = true;
		}else if(Number.class.isAssignableFrom(clazz)){
			inseparable = true;
		}else if(clazz.isAssignableFrom(String.class)){
			inseparable = true;
		}else if(clazz.isAssignableFrom(Boolean.class) || clazz.equals(double.class)){
			inseparable = true;
		}else if(Date.class.isAssignableFrom(clazz)){
			inseparable = true;
		}else if(Enum.class.isAssignableFrom(clazz)){
			inseparable = true;
		}
		return inseparable;
	}
	
	/**
	 * 对被转换的对象进行包装
	 * @param obj 被转换的对象
	 * @return 包装后的字符串
	 */
	public static String typeWrap(Object obj){
		if(obj == null) return "\"\"";
		if(isInseparable(obj.getClass())){
			if(obj instanceof String){
				return "\"" + obj + "\"";
			}else if(obj instanceof Date){
				return "\"" + NoConfig.defaultDateFormat.format((Date)obj) + "\"";
			}else if(obj instanceof Enum){
				return "\"" + obj.toString() + "\"";
			}else{
				return obj.toString();
			}
		}
		return "\"" + obj.getClass().getName() + "\"";
	}
	
	public static Class<?>[] getGenericityType(Class<?> clazz){
		ParameterizedType superclass =(ParameterizedType) clazz.getGenericSuperclass();
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		return (Class<?>[]) actualTypeArguments;
	}
	
}
