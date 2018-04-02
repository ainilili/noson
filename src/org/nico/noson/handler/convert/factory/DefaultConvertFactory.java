package org.nico.noson.handler.convert.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Default conversion factory
 * 
 * @author nico
 * @date 2018年3月27日
 */
public class DefaultConvertFactory extends AbstractConvertFactory{

	@Override
	public List<Object> getDefaultList() {
		return new LinkedList<Object>();
	}

	@Override
	public Set<Object> getDefaultSet() {
		return new HashSet<Object>();
	}

	@Override
	public Map<Object, Object> getDefaultMap() {
		return new LinkedHashMap<Object, Object>();
	}

	@Override
	public Collection<Object> getDefaultCollection() {
		return new LinkedList<Object>();
	}

}
