package com.nico.noson.util.string;

import com.nico.noson.scanner.verify.SymbolVerify;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午7:39:35
 */

public class FormatUtils {
	
	public static String trimBlank(String json){
		StringBuilder builder = new StringBuilder();
		if(StringUtils.isNotBlank(json)){
			SymbolVerify verify = new SymbolVerify();
			char[] chars = json.toCharArray();
			for(int index = 0; index < chars.length; index ++){
				char c = chars[index];
				verify.verify(c);
				if(verify.safetyQuote()){
					if(CharUtils.checkBlankChar(c)){
						continue;
					}
				}
				builder.append(c);
			}
		}
		return builder.toString();
	}
	
	
}
