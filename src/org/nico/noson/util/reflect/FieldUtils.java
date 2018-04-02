package org.nico.noson.util.reflect;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.nico.noson.NosonConfig;
import org.nico.noson.exception.NosonException;
import org.nico.noson.exception.VerifyException;
import org.nico.noson.util.string.StringUtils;
import org.nico.noson.util.type.TypeUtils;
import org.nico.noson.verify.FieldVerify;


/**
 * FieldUtils
 * @author nico
 * @date 2017年12月29日
 */
public class FieldUtils {
	
	/**
	 * Field Set操作
	 * 
	 * @param field 字段
	 * @param obj 对象
	 * @param clazz class
	 * @param value 参数
	 * @throws VerifyException 
	 */
	public static void set(Field field, Object obj, Class<?> clazz, Object value) throws NosonException{
		Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
		if(currentMethod == null) return;
		try {
			value = TypeUtils.convertType(field.getType(), value);
			String message = null;
			if((message = FieldVerify.verify(field, value)) == null){
				MethodUtils.invoke(currentMethod, obj, value);
			}else{
				throw new VerifyException("Validation fails at：" + message);
			}
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
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
		if(Modifier.isStatic(field.getModifiers())){
			field.setAccessible(true);
			try {
				return field.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}finally{
				field.setAccessible(false);
			}
		}
		Method currentMethod = MethodUtils.getGetterMethod(field, clazz);
		if(currentMethod == null) return null;
		try {
			return MethodUtils.invoke(currentMethod, obj);
		} catch (Exception e) {
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
	
}
