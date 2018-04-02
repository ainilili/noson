package org.nico.noson.test.entity;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午6:24:05
 */

public class Cycle {

	private String id = null;
	
	private Cycle cycle = this;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "CycleEntity [id=" + id + ", cycle=" + cycle + "]";
	}
}
