package org.nico.noson.util.string;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午7:44:39
 */

public class CharUtils {
	
	public static boolean checkBlankChar(char c){
		if(c == '﻿' || c == ' ' || c == '\t' || c == '\n' || c == '\r'){
			return true;
		}
		return false;
	}
}
