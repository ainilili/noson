package org.nico.noson.scanner.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.scanner.NoScanner;
import org.nico.noson.util.type.TypeUtils;
import org.nico.noson.verify.SymbolVerify;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月27日 下午11:15:52
 */

public class FastScanner implements NoScanner{

	public Object scan(String json){
		//结构栈，用来保存非基本类型数据结构
		Stack<Object> structStack = new Stack<Object>();
		StringBuffer keyBuffer = new StringBuffer();
		StringBuffer valueBuffer = new StringBuffer();
		ScanState state = ScanState.NORMAL;
		SymbolVerify verify = new SymbolVerify();
		Object result = null;
		char[] chars = json.toCharArray();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			verify.verify(c);
			if(verify.safetyQuote()){
				if((c == '[' || c == '{')){
					Object newStruct = null;
					if(c == '['){
						newStruct = NosonConfig.CONVERT_FACTORY.getDefaultCollection();
						state = ScanState.VALUE;	
					}else{
						newStruct = new Noson();
						state = ScanState.KEY;
					}
					if(newStruct != null){
						if(structStack.size() != 0){
							Object obj = structStack.peek();
							if(obj instanceof Collection){
								((Collection<Object>) obj).add(newStruct);
							}else if(obj instanceof Noson){
								((Noson) obj).put(TypeUtils.typeAllotKey(keyBuffer.toString()), newStruct);
								keyBuffer.setLength(0);
							}
						}
						structStack.push(newStruct);
						if(result == null){
							result = newStruct;
						}
						continue;
					}
				}else if(c == ']' || c == '}'){
					if(valueBuffer.length() != 0 || keyBuffer.length() != 0){
						Object obj = structStack.peek();
						if(obj instanceof Collection){
							((Collection<Object>) obj).add(TypeUtils.typeAllotValue(valueBuffer.toString()));
							valueBuffer.setLength(0);
						}else if(obj instanceof Noson){
							((Noson) obj).put(TypeUtils.typeAllotKey(keyBuffer.toString()), 
									TypeUtils.typeAllotValue(valueBuffer.toString()));
							keyBuffer.setLength(0);
							valueBuffer.setLength(0);
						}
					}
					structStack.pop();
					if(! structStack.isEmpty()){
						if(structStack.peek() instanceof Collection){
							state = ScanState.VALUE;
						}else{
							state = ScanState.KEY;
						}
					}
					continue;
				}
			}
			switch(state){
			case KEY:
				if(verify.safetyQuote()){
					if(c == ':'){
						state = ScanState.VALUE;
					}else if(c == ','){
						continue;
					}else{
						keyBuffer.append(c);
					}
				}else{
					keyBuffer.append(c);
				}
				break;
			case VALUE:
				if(verify.safetyQuote() && c == ','){
					if(c == ','){
						if(valueBuffer.length() != 0 || keyBuffer.length() != 0){
							Object obj = structStack.peek();
							if(obj instanceof Collection){
								((Collection<Object>) obj).add(TypeUtils.typeAllotValue(valueBuffer.toString()));
								valueBuffer.setLength(0);
							}else if(obj instanceof Noson){
								((Noson) obj).put(TypeUtils.typeAllotKey(keyBuffer.toString()), 
										TypeUtils.typeAllotValue(valueBuffer.toString()));
								keyBuffer.setLength(0);
								valueBuffer.setLength(0);
							}
						}
						if(! structStack.isEmpty()){
							if(structStack.peek() instanceof Collection){
								state = ScanState.VALUE;
							}else{
								state = ScanState.KEY;
							}
						}
					}
				}else{
					valueBuffer.append(c);
				}
				break;
			default:
				break;
			}
		}
		return result;
	}

	@Override
	public Noson scanSingle(String json) {
		return (Noson)scan(json);
	}

	@Override
	public Collection<Object> scanArray(String json) {
		return (Collection<Object>)scan(json);
	}

	static enum ScanState{

		KEY,

		VALUE,

		NORMAL,
		;

	}

}
