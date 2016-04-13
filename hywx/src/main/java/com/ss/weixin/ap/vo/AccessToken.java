package com.ss.weixin.ap.vo;

/**
 * 微信通用接口凭证
 * 
 */
public class AccessToken
{
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	private Long expiresTime;// 超时时间

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

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
		return "AccessToken [token=" + token + ", expiresIn=" + expiresIn + "]";
	}

	public Long getExpiresTime()
	{
		return expiresTime;
	}

	public void setExpiresTime(Long expiresTime)
	{
		this.expiresTime = expiresTime;
	}

}