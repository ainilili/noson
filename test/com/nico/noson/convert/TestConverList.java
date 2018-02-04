package com.nico.noson.convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.List;
import java.util.Map;

import com.nico.noson.Noson;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 上午10:53:24
 */

public class TestConverList {
	
	public static void main(String[] args) throws IOException {
		/*String json = "[{name:'nico','list':[{'test':'this is a test'},2,3,4]},{'name':'lili',list:[4,5,6]}]";
		List<Noson> list = Noson.convert(json, List.class);
		System.out.println(list);*/
		File file = new File("E:\\test\\json.txt");
		FileInputStream fi = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fi));
		StringBuffer buff = new StringBuffer();
		String temp = null;
		while((temp = reader.readLine()) != null){
			buff.append(new String(temp.getBytes(),"utf-8") + "\n");
		}
		fi.close();
		reader.close();
		Noson noson = Noson.parseNoson(buff.toString());
		System.out.println(noson);
		System.out.println(noson.get("message"));
		System.out.println(noson.get("code"));
	}
}
