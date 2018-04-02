package org.nico.noson.util.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.nico.noson.NosonConfig;
import org.nico.noson.cache.Cache;
import org.nico.noson.cache.SimpleCache;
import org.nico.noson.entity.TypeBean;
import org.nico.noson.exception.TypeParseException;
import org.nico.noson.util.string.FormatUtils;
import org.nico.noson.util.string.StringUtils;

/** 
 * Noson类型转换工具
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午4:55:08
 */
public class TypeUtils {

	public static final String BOOLEAN_TRUE = "TRUE";

	public static final String BOOLEAN_FALSE = "FALSE";
	
	public static Cache keyCache = new SimpleCache(NosonConfig.DEFAULT_CACHE_DELAY);
	
	public static Cache valueCache = new SimpleCache(NosonConfig.DEFAULT_CACHE_DELAY);
	
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
	 * 转换Json字符串中的Value
	 * 
	 * @param param
	 * @return
	 */
	public static Object typeAllotValue(String param){
		Object result = param;
		if((result = valueCache.getCache(param)) != null){
			return result;
		}else if((param.startsWith("'") && param.endsWith("'")) || (param.startsWith("\"") && param.endsWith("\""))){
			String tailor = param.substring(1, param.length() - 1);
			try{
				result = NosonConfig.DEFAULT_DATE_FORMAT.parse(tailor);
			}catch(ParseException e){
				result = tailor;
			}
		}else{
			try{
				result = Integer.parseInt(param);
			}catch(NumberFormatException e1){
				try{
					result = Long.parseLong(param);
				}catch(NumberFormatException e2){
					try{
						result = Double.parseDouble(param);
					}catch(NumberFormatException e3){
						if(BOOLEAN_TRUE.equalsIgnoreCase(param) || BOOLEAN_TRUE.equalsIgnoreCase(param)){
							result = Boolean.parseBoolean(param);
						}else{
							if(param.equals("null")){
								result = null;
							}else{
								result = param;
							}
						}
					}
				}
			}
		}
		result = result instanceof String ? FormatUtils.deEscape((String) result) : result;
		valueCache.putCache(param, result);
		return result;
	}

	/**
	 * 转换Json中的key
	 * 
	 * @param param
	 * @return
	 */
	public static String typeAllotKey(String param){
		String result = param;
		if(keyCache.containsCache(param)){
			return String.valueOf(keyCache.getCache(param));
		}else if((param.startsWith("'") && param.endsWith("'")) || (param.startsWith("\"") && param.endsWith("\""))){
			result = param.substring(1, param.length() - 1);
		}
		result = result instanceof String ? FormatUtils.deEscape((String) result) : result;
		keyCache.putCache(param, result);
		return result;
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
	public static String typeWrap(Class<?> type, Object obj){
		if(obj == null) return emptyWrap(type, NosonConfig.ALLOW_EMPTY_TO_NULL);
		if(isInseparable(obj.getClass())){
			if(obj instanceof String){
				return "\"" + FormatUtils.enEscape((String) obj) + "\"";
			}else if(obj instanceof Date){
				return "\"" + NosonConfig.DEFAULT_DATE_FORMAT.format((Date)obj) + "\"";
			}else if(obj instanceof Enum){
				return "\"" + obj.toString() + "\"";
			}else{
				return obj.toString();
			}
		}
		return "\"" + obj.getClass().getName() + "\"";
	}

	/**
	 * If obj is null. this method can help noson finished convert
	 * 
	 * @param type	Obj's type
	 * @param toNull Whether object is set to null.
	 * @return result
	 */
	public static String emptyWrap(Class<?> type, boolean toNull){
		String result = null;
		if(Object.class.isAssignableFrom(type)){
			if(! toNull){
				if(Number.class.isAssignableFrom(type)){
					result = "0";
				}else if(String.class.isAssignableFrom(type)){
					result = "\"\"";
				}
			}
		}else{
			if(type.isAssignableFrom(int.class)){
				result = "0";
			}else if(type.isAssignableFrom(float.class)){
				result = "0.000";
			}else if(type.isAssignableFrom(double.class)){
				result = "0.000000";
			}else if(type.isAssignableFrom(long.class)){
				result = "0";
			}else if(type.isAssignableFrom(short.class)){
				result = "0";
			}else if(type.isAssignableFrom(byte.class)){
				result = "0";
			}else if(type.isAssignableFrom(boolean.class)){
				result = "false";
			}
		}
		return result;
	}

	/**
	 * 获取Class的泛型类型数组
	 * 
	 * @param clazz class
	 * @return
	 */
	public static TypeBean<?> getGenericityType(Class<?> clazz){
		TypeBean<?> typeBean = new TypeBean(clazz);
		if(clazz == null)
			throw new NullPointerException("Class is null");
		Type superclass = clazz.getGenericSuperclass();
		if(superclass != null){
			if(superclass instanceof Class) {
				//				throw new RuntimeException("Missing type parameter.");
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
				typeBean = new TypeBean((Class<?>)((ParameterizedType) type).getRawType());
				typeBean.setGenericityBeans(getGenericityTypes(((ParameterizedType) type).getActualTypeArguments()));
			}else{
				typeBean = new TypeBean((Class<?>)type);
			}
			typeBeans[index ++] = typeBean;
		}
		return typeBeans;
	}

	/**
	 * 将target转为clazz的类型对象
	 * @param clazz 要转换的目标对象类型
	 * @param target 待转对象
	 * @return 转换后的对象
	 * @throws ParseException 
	 * @throws TypeParseException 
	 */
	public static Object convertType(Class<?> clazz, Object target) throws TypeParseException{
		if(clazz.getName().equals(Object.class.getName())) return target;
		try{
			Object obj = null;
			if(Number.class.isAssignableFrom(clazz) || NUM_CLASS_SET.contains(clazz)){
				Number value = StringUtils.isBlank(target) ? 0 : (Number)Double.parseDouble(String.valueOf(target));
				if(clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)){
					obj = value.intValue();
				}else if(clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)){
					obj = value.doubleValue();
				}else if(clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class)){
					obj = value.floatValue();
				}else if(clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)){
					obj = value.longValue();
				}else if(clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class)){
					obj = value.shortValue();
				}else if(clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(byte.class)){
					obj = value.byteValue();
				}else{
					obj = value;
				}
			}else if(String.class.isAssignableFrom(clazz)){
				obj = String.valueOf(target);
			}else if(Date.class.isAssignableFrom(clazz)){
				if(target instanceof Date){
					obj = target;
				}else{
					obj = NosonConfig.DEFAULT_DATE_FORMAT.parse(String.valueOf(target));
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
		}catch(ParseException e){
			throw new TypeParseException(e.getMessage(), e);
		}
	}
	
}
