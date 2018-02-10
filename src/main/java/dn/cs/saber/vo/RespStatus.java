package dn.cs.saber.vo;

/**
 * Represent response data object. Would be converted to JSON by Spring.
 * Contains:
 * <br> 1. code, status represent success or fail. Code use http response code.
 * 		code represented info, it's no need, just be conventions.
 * <br> 2. data, data to front-end.
 * 
 * @author hewen.deng
 * @param <T>
 */
public final class RespStatus<T> {

	private int code;

	private T data;

	protected RespStatus() {}

	protected RespStatus(int code, T data) {
		this.code = code;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String toString() {
		return "{code: " + code + ", data: " + data + "}";
	}

}