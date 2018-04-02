package org.nico.noson.handler.convert.impl;

import org.nico.noson.entity.NoType;
import org.nico.noson.entity.TypeBean;
import org.nico.noson.exception.NosonException;
import org.nico.noson.handler.convert.ConvertHandler;
import org.nico.noson.util.type.TypeUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午10:35:46
 */

public class ConvertNoTypeHandler extends ConvertHandler{

	@Override
	public <T> T handle(Object obj, Class<T> clazz) throws NosonException {
 		if(NoType.class.isAssignableFrom(clazz)){
			TypeBean<?> typeBean = TypeUtils.getGenericityType(clazz);
			if(typeBean != null){
				return (T) allocation(obj, typeBean.getGenericityBeans()[0]);
			}else{
				throw new NullPointerException();
			}
		}
		return nextHandler.handle(obj, clazz);
	}
	
	
	
	
	
}
