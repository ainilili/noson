package org.nico.noson.exception;

/** 
 * json格式错误
 * @author nico
 * @version 创建时间：2017年11月24日 下午11:21:36
 */

public class NosonFormatException extends Exception{

	private static final long serialVersionUID = 5658156169010157968L;

	public NosonFormatException() {
		super();
	}

	public NosonFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NosonFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NosonFormatException(String message) {
		super(message);
	}

	public NosonFormatException(Throwable cause) {
		super(cause);
	}

}
