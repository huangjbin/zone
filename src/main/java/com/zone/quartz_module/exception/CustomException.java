package com.zone.quartz_module.exception;

/**
 * 系统 自定义异常类，针对预期的异常，需要在程序中抛出此类的异常
 * @author huangjunbin
 *
 */
public class CustomException extends RuntimeException {
	
	//异常信息
	public String message;
	
	public int code;
	
	public CustomException(String message,int code){
		super(message);
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "CustomException [message=" + message + ", code=" + code + "]";
	}

}
