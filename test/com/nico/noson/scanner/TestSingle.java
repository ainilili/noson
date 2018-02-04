package com.nico.noson.scanner;

import java.util.List;

import com.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月25日 下午3:25:46
 */

public class TestSingle {

	public static void main(String[] args) {
		
		String json = "{'user':{'name':'nico',age:18.2,'birthday':'2017-11-08 05:23:16'},'tea':[{lover:'lili'},2,3,4]}";
		
		Noson noson = Noson.parseNoson(json);
		System.out.println(noson.get("user"));
		Noson user = (Noson) noson.get("user");
		System.out.println(user.get("age") + "：" + user.get("age").getClass());
		System.out.println(user.get("name") + "：" + user.get("name").getClass());
		System.out.println(user.get("birthday") + "：" + user.get("birthday").getClass());
		List<Object> objs = (List<Object>) noson.get("tea");
		for(Object o: objs){
			if(o instanceof Noson){
				System.out.println(o + "：" + o.getClass() + ":" + ((Noson)o).get("lover"));
			}else{
				System.out.println(o + "：" + o.getClass());
			}
		}
	}
	
}	
