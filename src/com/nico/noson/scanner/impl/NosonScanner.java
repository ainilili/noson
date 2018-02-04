package com.nico.noson.scanner.impl;

import java.util.ArrayList;
import java.util.List;

import com.nico.noson.Noson;
import com.nico.noson.scanner.NoScanner;
import com.nico.noson.scanner.verify.SymbolVerify;
import com.nico.noson.util.string.StringUtils;
import com.nico.noson.util.type.TypeUtils;

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
				return TypeUtils.typeAllot(subson);
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
		StringBuffer keyCache = new StringBuffer();
		StringBuffer valueCache = new StringBuffer();
		StringBuffer noQuote = new StringBuffer();
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
	public List<Object> scanArray(String json) {
		List<Object> list = new ArrayList<Object>();
		SymbolVerify verify = new SymbolVerify();
		char[] chars = json.toCharArray();
		StringBuffer nosonCache = new StringBuffer();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			if(verify.verify(c) && verify.getBracket() == 0){
				list.add(transfer(nosonCache.toString()));
				continue;
			}
			if(verify.getBracket() == 1){
				if(verify.getBrace() == 0 && c == ','){
					list.add(transfer(nosonCache.toString()));
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

}
