package org.nico.noson.handler.convert.impl;

import java.util.Map;

import org.nico.noson.exception.NosonException;
import org.nico.noson.handler.convert.ConvertHandler;
import org.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:35:46
 */

public class ConvertMapHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws NosonException {
		if(Map.class.isAssignableFrom(clazz)){
			if(null == obj) 
				throw new NullPointerException();
			return (T) allocation(obj, TypeUtils.getGenericityType(clazz));
		}
		return nextHandler.handle(obj, clazz);
	}
	
//	private Map<String, Object> handleArray2Map(List<Object> list){
//		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		for(int index = 0; index < list.size(); index++){
//			Object obj = list.get(index);
//			if(obj != null){
//				if(obj instanceof List){
//					map.put(String.valueOf(index), handleArray((List<Object>)obj));
//				}else if(obj instanceof Noson){
//					map.put(String.valueOf(index), handleNoson((Noson)obj));
//				}else{
//					map.put(String.valueOf(index), obj);
//				}
//			}
//		}
//		return map;
//	}
}
