package org.nico.noson.test;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.entity.NoType;
import org.nico.noson.test.entity.Nico;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月1日 下午10:52:38
 */

public class SimpleTest {

	public static void main(String[] args) {
		
		//Single Json Object Str
		String json = "{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}";
		
		//Json Object Array
		String jsonArray = "[" + json + "," + json + "]";

		//Noson parse
		Noson noson = Noson.parseNoson(json);
		List<Object> nosonArray = Noson.parseArray(jsonArray);

		//Noson to Json
		System.out.println("==========>> Noson to Json：");
		System.out.println(json = noson.toString());
		System.out.println(jsonArray = nosonArray.toString());
		System.out.println();

		//Get parameter from noson
		System.out.println("==========>> Get parameter from noson：");
		System.out.println("name\t" + noson.get("name"));
		System.out.println("age\t" + noson.get("age"));
		System.out.println("skill\t" + noson.get("skill"));
		System.out.println("deposit\t" + noson.get("deposit"));
		System.out.println("info\t" + noson.get("info"));
		System.out.println();

		//Convert to Map
		Map<String, Object> testMap = Noson.convert(json, Map.class);
		System.out.println("==========>> Convert to Map：");
		System.out.println(testMap);
		System.out.println();

		//Convert to List
		List<Object> testList = Noson.convert(jsonArray, List.class);
		System.out.println("==========>> Convert to List：");
		System.out.println(testList);
		System.out.println();

		//Convert to Set
		Set<Object> testSet = Noson.convert(jsonArray, Set.class);
		System.out.println("==========>> Convert to Set：");
		System.out.println(testSet);
		System.out.println();

		//Convert to Java Object
		System.out.println("==========>> Convert to Java Object：");
		Nico nico = Noson.convert(json, Nico.class);
		System.out.println(nico);
		System.out.println();
		
		//Convert to Complex Types
		System.out.println("==========>> Convert to Complex Types：");
		List<Nico> nicos = Noson.convert(jsonArray, new NoType<List<Nico>>(){});
		System.out.println(nicos);
		System.out.println();
		
		//Reversal Object to Json
		System.out.println("==========>> Reversal Object to Json：");
		System.out.println(Noson.reversal(testMap));
		System.out.println(Noson.reversal(testList));
		System.out.println(Noson.reversal(testSet));
		System.out.println(Noson.reversal(nico));
		System.out.println(Noson.reversal(nicos));
		System.out.println();

	}
}
