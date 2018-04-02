package org.nico.noson.handler.reversal.impl;


import java.util.ArrayList;
import java.util.HashSet;

import org.nico.noson.Noson;
import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月27日 下午11:03:03
 */

public class ReversalNosonHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj instanceof Noson){
			return handleNoson((Noson)obj, new ReversalRecorder());
		}
		return nextHandler.handle(obj);
	}

}
