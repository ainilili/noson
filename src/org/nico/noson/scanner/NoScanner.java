package org.nico.noson.scanner;

import java.util.Collection;
import java.util.List;

import org.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月24日 下午9:52:48
 */

public interface NoScanner {

	/**
	 * Scanning the json as single Object & convert to Noson
	 * @param json The json being scanned
	 * @return noson object
	 */
	public Noson scanSingle(String json);
	
	/**
	 * Scanning the json as array Object & convert to List<Object>
	 * @param json json being scanned
	 * @return array Object
	 */
	public Collection<Object> scanArray(String json);
}
