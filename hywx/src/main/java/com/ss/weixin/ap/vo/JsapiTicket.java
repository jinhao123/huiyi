package com.ss.weixin.ap.vo;

/**
 * 公众号用于调用微信JS接口的临时票据
 * 
 */
public class JsapiTicket
{
	// 获取到的凭证
	private String ticket;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	private Long expiresTime;// 超时时间

	public int getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString()
	{
		return "AccessToken [token=" + ticket + ", expiresIn=" + expiresIn + "]";
	}

	public Long getExpiresTime()
	{
		return expiresTime;
	}

	public void setExpiresTime(Long expiresTime)
	{
		this.expiresTime = expiresTime;
	}

	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}

}