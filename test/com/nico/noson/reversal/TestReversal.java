package com.nico.noson.reversal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月28日 下午11:01:05
 */

public class TestReversal {
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "小明");
		map.put("age", 10);
		
		List<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add("test");
		list.add(new Date());
		
		map.put("list", list);
		
		String json = Noson.reversal(map);
		//System.out.println(json);
		
		Noson noson = new Noson();
		noson.put("name", "nico");
		noson.put("age", 21);
		noson.put("birthday", new Date());
		noson.put("map", map);
		noson.put("list", list);
		json = Noson.reversal(noson);
		System.out.println(json);
	}
}
