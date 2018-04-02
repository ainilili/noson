package org.nico.noson.test.entity;

import java.util.List;

import org.nico.noson.annotation.NoIgnore;
import org.nico.noson.annotation.ParamVerify;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月2日 下午10:32:48
 */

public class Order {

	@ParamVerify(minLength = 2, maxLength = 6)
	private String name;
	
	@ParamVerify(range = {10, 88.8})
	private double price;
	
	@ParamVerify(range = {0, 5})
	private int count;
	
	@ParamVerify(textFormat = "China*")
	private String address;
	
	@NoIgnore
	private List<Nico> books;
	
	public List<Nico> getBooks() {
		return books;
	}

	public void setBooks(List<Nico> books) {
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", price=" + price + ", count=" + count + ", address=" + address + ", books="
				+ books + "]";
	}
	
}
