package com.nico.noson.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;
import com.nico.noson.entity.NoType;
import com.nico.noson.entity.User;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午2:52:17
 */

public class TestConvertEntity {
	
	public static void main(String[] args) {
		
		String json = "[{list:[1,2,3]},{list:[2,3,4]},{list:[3,4,5]}]";
		List<User> users = Noson.convert(json, new NoType<List<User>>(){});
		System.out.println(users);
		
		System.out.println("------------我是分割线------------");
		
		System.out.println(Noson.convert(json, new ArrayList<User>(){}.getClass()));
		
		System.out.println("------------我是分割线------------");
		
		json = "{user:{username:nico,password:niconico,userInfo:{address:中国},list:[6, 7, 8]}}";
		Map<String, User> map = Noson.convert(json, new NoType<Map<String, User>>(){});
		System.out.println(map);
		
		System.out.println("------------我是分割线------------");
		
		System.out.println(Noson.convert(json, Map.class));
		
	}
}
