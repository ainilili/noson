package com.nico.noson.convert;

import com.nico.noson.Noson;
import com.nico.noson.entity.User;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午2:52:17
 */

public class TestConvertEntity {
	
	public static void main(String[] args) {
		
		String json = "{list:[1,2,3]}";
		User user = Noson.convert(json, User.class);
		System.out.println(user);
		
		/*Noson noson = Noson.parseNoson(json);
		System.out.println(noson);*/
	}
}
