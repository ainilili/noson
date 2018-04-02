package org.nico.noson;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.nico.noson.handler.convert.factory.AbstractConvertFactory;
import org.nico.noson.handler.convert.factory.DefaultConvertFactory;
import org.nico.noson.scanner.NoScanner;
import org.nico.noson.scanner.impl.FastScanner;
import org.nico.noson.scanner.impl.NosonScanner;

/** 
 * The config of noson working guide
 * 
 * @author nico
 * @version createTime：2018年3月24日 下午9:49:17
 */

public class NosonConfig {

	/**
	 * Allows null values to be displayed.
	 * If this field is false, NOSON reveral will not print params who is null
	 */
	public static boolean ALLOW_EMPTY = true;
	
	/**
	 * Allows empty values to be null
	 * </br>
	 * <b>Example：</b>
	 * </br>
	 * {"name":""} -> {"name":null}
	 */
	public static boolean ALLOW_EMPTY_TO_NULL = true;
	
	/**
	 * Allows the parsing of object circular references.
	 */
	public static int ALLOW_CYCLE_MAX_COUNT = 0;
	
	/**
	 * Convert factory
	 */
	public static AbstractConvertFactory CONVERT_FACTORY = new DefaultConvertFactory();
	
	/**
	 * Default scanner
	 */
	public static NoScanner DEFAULT_SCANNER = new FastScanner();
//	public static NoScanner DEFAULT_SCANNER = new NosonScanner();
	
	/**
	 * Cache delay
	 */
	public static Long DEFAULT_CACHE_DELAY = 100L;
	
	/**
	 * Allows modify of Field in Object</br>
	 * using:</br>
	 * add("private");</br>
	 * add("public");</br>
	 * add("private static");</br>
	 * add("public static");</br>
	 */
	public static final Set<String> ALLOW_MODIFY;
	
	/**
	 * Date Format
	 */
	public static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("YYY-MM-dd HH:mm:ss");
	
	static{
		ALLOW_MODIFY = new HashSet<String>();
		ALLOW_MODIFY.add("private");
		ALLOW_MODIFY.add("public");
		ALLOW_MODIFY.add("private static");
		ALLOW_MODIFY.add("public static");
	}
}
