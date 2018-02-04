package com.nico.noson.policy;

import com.nico.noson.exception.NosonFormatException;
import com.nico.noson.scanner.NoScanner;
import com.nico.noson.scanner.impl.NosonScanner;
import com.nico.noson.scanner.verify.SymbolVerify;
import com.nico.noson.util.string.FormatUtils;
import com.nico.noson.util.string.StringUtils;

/** 
 * Noson中央指挥部
 * @author nico
 * @version 创建时间：2017年11月24日 下午11:15:22
 */

public class NoPolicy {
	
	/**
	 * 验证Json是否合法，并分配扫描器
	 * @param json 要验证的json
	 * @return 返回扫描对象
	 * @throws NosonFormatException
	 */
	public static Object policy(String json) throws NosonFormatException{
		NoScanner scanner = new NosonScanner();
		Object result = null;
		SymbolVerify verify = new SymbolVerify();
		if(StringUtils.isNotBlank(json)){
			json = FormatUtils.trimBlank(json);
			if(verify.check(json)){
				if(json.startsWith("{") && json.endsWith("}")){
					result = scanner.scanSingle(json);
				}else if(json.startsWith("[") && json.endsWith("]")){
					result = scanner.scanArray(json);
				}else{
					throw new NosonFormatException("json str format error !!");
				}
			}else{
				throw new NosonFormatException("json str is not closed !!");
			}
		}else{
			return null;
		}
		return result;
	}
}
