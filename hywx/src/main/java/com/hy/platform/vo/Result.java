package com.hy.platform.vo;

public class Result
{
	public static final String CODE_SUCCESS = "1";
	public static final String CODE_FAIL = "0";

	private String code = CODE_FAIL;
	private String message;

	public boolean isSuccess()
	{
		return CODE_SUCCESS.equals(code);
	}

	public static Result ok(String message)
	{
		Result rst = new Result();
		rst.addOK(message);
		return rst;
	}

	public static Result error(String message)
	{
		Result rst = new Result();
		rst.addError(message);
		return rst;
	}

	public void addOK(String message)
	{
		this.message = message;
		this.code = CODE_SUCCESS;
	}

	/**
	 * 添加错误消息
	 * 
	 * @param message
	 */
	public void addError(String message)
	{
		this.message = message;
		this.code = CODE_FAIL;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
