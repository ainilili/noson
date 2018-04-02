package org.nico.noson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Parameter Verfiy
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午1:31:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ParamVerify {

	/**
	 * @return Text max length
	 */
	public int maxLength() default Integer.MAX_VALUE;
	
	/**
	 * @return Text min length
	 */
	public int minLength() default 0;
	
	/**
	 * @return Field can be null
	 */
	public boolean accessNull() default true;
	
	/**
	 * @return Number must in range()
	 */
	public double[] range() default {Double.MIN_VALUE, Double.MAX_VALUE};
	
	/**
	 * @return Text format
	 */
	public String textFormat() default "*";
	
}
