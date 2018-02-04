package com.nico.noson.scanner;

import java.util.List;

import com.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午7:41:27
 */

public class TestArray {
	
	public static void main(String[] args) {
		
		String json = "[{name:'nico','list':[{'test':'this is a test'},2,3,4]},{'name':'lili',list:[4,5,6]}]";
		
		List<Object> objs = Noson.parseArray(json);
		for(Object obj: objs){
			Noson noson = (Noson)obj;
			System.out.println("name:" + noson.get("name"));
			System.out.println("list:" + noson.get("list"));
		}
	}
}
