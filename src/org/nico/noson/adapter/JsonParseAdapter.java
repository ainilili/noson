package org.nico.noson.adapter;

import org.nico.noson.NosonConfig;
import org.nico.noson.exception.NosonFormatException;
import org.nico.noson.scanner.NoScanner;
import org.nico.noson.scanner.impl.FastScanner;
import org.nico.noson.util.string.FormatUtils;
import org.nico.noson.util.string.StringUtils;
import org.nico.noson.util.type.TypeUtils;
import org.nico.noson.verify.SymbolVerify;

/** 
 * Noson解析分配中心
 * 
 * @author nico
 * @version 创建时间：2017年11月24日 下午11:15:22
 */

public class JsonParseAdapter {
	
	/**
	 * 验证Json是否合法，并分配扫描器
	 * @param json 要验证的json
	 * @return 返回扫描对象
	 * @throws NosonFormatException
	 */
	public static Object policy(String json) throws NosonFormatException{
		NoScanner scanner = NosonConfig.DEFAULT_SCANNER;
		Object result = null;
		SymbolVerify verify = new SymbolVerify();
		if(StringUtils.isNotBlank(json)){
			json = FormatUtils.formatJson(json);
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
