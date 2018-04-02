package org.nico.noson.verify;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import org.nico.noson.annotation.ParamVerify;
import org.nico.noson.exception.VerifyException;
import org.nico.noson.util.type.TypeUtils;

/** 
 * 该类用于字段验证</br>
 * 验证{@link NoVerfiy}的字段
 * 
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午4:02:02
 */
public class FieldVerify {

	public static final String UNIFIED = "(.*)";

	public static final String REGEX_FOR_UNIFIED = "[*]+";
	
	/**
	 * Verify that field can pass the test.
	 * 
	 * @param field Be verfiy field
	 * @param value The value whill be inject
	 * @return access
	 * @throws VerifyException Verify arguments format error
	 */
	public static String verify(Field field, Object value) throws VerifyException{
		String message = null;
		String header = field.getDeclaringClass().getName() + "[" + field.getName() + "]";
		Class<?> type = field.getType();
		ParamVerify v = null;
		if((v = field.getDeclaredAnnotation(ParamVerify.class)) != null){
			if(! v.accessNull()){
				if(value == null){
					message = header + " not access null";
				}
			}
			if(value != null){
				if(String.class.isAssignableFrom(type)){
					String target = (String) value;
					if(target.length() < v.minLength()){
						message = header + " length not access < " + v.minLength();
					}else if(target.length() > v.maxLength()){
						message = header + " length not access > " + v.maxLength();
					}else if(v.textFormat() != null && ! target.matches(v.textFormat().replaceAll(REGEX_FOR_UNIFIED, UNIFIED))){
						message = header + " format not matching " + v.textFormat();
					}
				}else if(Number.class.isAssignableFrom(type) || TypeUtils.NUM_CLASS_SET.contains(type)){
					double target = ((Number) value).doubleValue();
					if(v.range() == null){
						throw new VerifyException("Range can't be null");
					}else if(v.range().length != 2){
						throw new VerifyException("Range length must be 2, but it is " + v.range().length + "!!");
					}
					if(target < v.range()[0]){
						message = header + " value not access < " + v.range()[0];
					}else if(target > v.range()[1]){
						message = header + " value not access > " + v.range()[1];
					}
				}
			}
		}
		return message;
	}
}
