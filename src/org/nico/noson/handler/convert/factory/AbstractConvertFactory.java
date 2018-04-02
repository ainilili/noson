package org.nico.noson.handler.convert.factory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Construct factory methods for some default types (such as Collection, Map) to get instances.
 * 
 * @author nico
 * @date 2018年3月27日
 */
public abstract class AbstractConvertFactory {

	public abstract Collection<Object> getDefaultCollection();
	
	public abstract List<Object> getDefaultList();
	
	public abstract Set<Object> getDefaultSet();
	
	public abstract Map<Object, Object> getDefaultMap();
	
}
