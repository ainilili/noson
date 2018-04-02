package org.nico.noson.scanner.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.scanner.NoScanner;
import org.nico.noson.util.string.StringUtils;
import org.nico.noson.util.type.TypeUtils;
import org.nico.noson.verify.SymbolVerify;

/** 
 * NosonScanner
 * @author nico
 * @version 创建时间：2017年11月25日 下午6:19:11
 */
public class NosonScanner implements NoScanner{

	/**
	 * 对扫描结果提供一个中转站
	 * @param subson
	 * @return
	 */
	public Object transfer(String subson){
		if(StringUtils.isNotBlank(subson)){
			if(subson.startsWith("{")){
				return scanSingle(subson);
			}else if(subson.startsWith("[")){
				return scanArray(subson);
			}else{
				return TypeUtils.typeAllotValue(subson);
			}
		}else{
			return null;
		}
	}

	@Override
	public Noson scanSingle(String json) {
		SymbolVerify sv = new SymbolVerify();
		Noson noson = new Noson();
		char[] chars = json.toCharArray();
		StringBuilder keyCache = new StringBuilder();
		StringBuilder valueCache = new StringBuilder();
		StringBuilder noQuote = new StringBuilder();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			//扫描到敏感符号 -> { }[ ] ' "
			if(sv.verify(c) && !sv.turnValue() && index < chars.length){
				continue;
			}
			//确保扫描属于外层
			if(sv.getBrace() == 1){
				//当单双引号不孤立的时候遇到','要存入noMap中
				if(c == ',' && sv.safetyQuote() && sv.getBracket() == 0){ 
					sv.setColon(0);
					if(valueCache.length() == 0) valueCache.append(noQuote);
					noson.put(keyCache.toString(), transfer(valueCache.toString()));
					keyCache.setLength(0);
					valueCache.setLength(0);
				}else{
					//冒号之后就要存入value，否则存入key
					if(!sv.isSpecial(c)){
						if(sv.turnValue()){
							valueCache.append(c);
						}else{
							keyCache.append(c);
						}
					}
				}
			}
			//其他情况直接存入value
			if(index == chars.length - 1){
				if(valueCache.length() == 0) valueCache.append(noQuote);
				noson.put(keyCache.toString(), transfer(valueCache.toString()));
			}else{
				if(sv.getBrace() != 1) valueCache.append(c);
			}
		}
		return noson;
	}

	@Override
	public Collection<Object> scanArray(String json) {
		Collection<Object> list = NosonConfig.CONVERT_FACTORY.getDefaultCollection();
		SymbolVerify verify = new SymbolVerify();
		char[] chars = json.toCharArray();
		StringBuilder nosonCache = new StringBuilder();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			if(verify.verify(c) && verify.getBracket() == 0){
				if(nosonCache.length() > 0){
					list.add(transfer(nosonCache.toString()));
				}
				continue;
			}
			if(verify.getBracket() == 1){
				if(verify.getBrace() == 0 && c == ','){
					if(nosonCache.length() > 0){
						list.add(transfer(nosonCache.toString()));
					}
					nosonCache.setLength(0);
				}else{
					if(c != '['){
						nosonCache.append(c);
					}
				}
			}else{
				nosonCache.append(c);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		String str = "a,b,c//,d";
		System.out.println(Arrays.asList(str.split(",")));
	}
}
