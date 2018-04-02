package org.nico.noson.test;

import org.nico.noson.Noson;
import org.nico.noson.test.entity.Order;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月2日 下午10:32:34
 */

public class AnnotationTest {

	public static void main(String[] args) {
		String json = "{name:订,price:23.5,count:3,address:China,books:[{}]}";
		Order order = Noson.convert(json, Order.class);
		System.out.println(order);
	}
	
}
