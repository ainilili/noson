package com.nico.noson.util.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nico.noson.config.NoConfig;
import com.nico.noson.entity.TypeBean;
import com.nico.noson.entity.User;

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

	/**
	 * 获取Class的泛型类型数组
	 * 
	 * @param clazz class
	 * @return
	 */
	public static TypeBean<?> getGenericityType(Class<?> clazz){
		TypeBean<?> typeBean = new TypeBean<>(clazz);
		if(clazz == null)
			throw new NullPointerException("Class is null");
		Type superclass = clazz.getGenericSuperclass();
		if(superclass != null){
			if(superclass instanceof Class) {
				throw new RuntimeException("Missing type parameter.");
			} else {
				ParameterizedType parameterized = (ParameterizedType)superclass;
				Type[] types = parameterized.getActualTypeArguments();
				typeBean.setGenericityBeans(getGenericityTypes(types));
			}
		}
		return typeBean;
	}

	public static TypeBean[] getGenericityTypes(Type[] types){
		if(types.length == 0)
			throw new ArrayIndexOutOfBoundsException(0);
		TypeBean<?>[] typeBeans = new TypeBean[types.length];
		int index = 0;
		for(Type type: types){
			TypeBean<?> typeBean = null;
			if(type instanceof ParameterizedType){
				typeBean = new TypeBean<>((Class<?>)((ParameterizedType) type).getRawType());
				typeBean.setGenericityBeans(getGenericityTypes(((ParameterizedType) type).getActualTypeArguments()));
			}else{
				typeBean = new TypeBean<>((Class<?>)type);
			}
			typeBeans[index ++] = typeBean;
		}
		return typeBeans;
	}

}
