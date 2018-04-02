package org.nico.noson.exception;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月4日 下午12:02:32
 */

public class TypeNotMatchException extends NosonException{

	private static final long serialVersionUID = -1697964020618241811L;

	public TypeNotMatchException() {
		super();
	}
	
	public TypeNotMatchException(Class<?> c1, Class<?> c2) {
		super("Type not match：" + c1.getName() + " can't convert to " + c2.getName());
	}


	public TypeNotMatchException(String message) {
		super(message);
	}

	public TypeNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
