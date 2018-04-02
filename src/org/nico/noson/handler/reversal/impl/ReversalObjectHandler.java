package org.nico.noson.handler.reversal.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月27日 下午11:03:03
 */

public class ReversalObjectHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		return handleObject(obj, new ReversalRecorder());
	}

}
