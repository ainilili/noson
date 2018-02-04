package com.nico.noson.convert;

import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 上午10:53:24
 */

public class TestConverMap {
	
	public static void main(String[] args) {
		String json = "[{name:'nico','list':[{'test':'this is a test'},2,3,4]},{'name':'lili',list:[4,5,6]}]";
		Map<String, Object> map = Noson.convert(json, Map.class);
		System.out.println("convert json to map: \n" + map);
		List<Noson> list = Noson.convert(json, List.class);
		System.out.println("convert json to list: \n" + list);
	}
}
