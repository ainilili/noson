package org.nico.noson.util.string;

import org.nico.noson.verify.SymbolVerify;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午7:39:35
 */

public class FormatUtils {

	public static String formatJson(String json){
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

	public static String enEscape(String obj){
		return obj.replaceAll("'", "\\\\\'").replaceAll("\"", "\\\\\"");
	}

	public static String deEscape(String obj){
		return obj.replaceAll("\\\\\"", "\"").replaceAll("\\\\\'", "\'");
	}


}
