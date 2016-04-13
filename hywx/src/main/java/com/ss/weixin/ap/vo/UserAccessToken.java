package com.ss.weixin.ap.vo;


/**
 * 微信用户授权的接口凭证
 * 
 */
public class UserAccessToken
{
	private String token;
	private int expiresIn;
	private String refreshToken;
	private String openid;
	private String scope;
	private Long expiresTime;// 超时时间

	// 获取到的凭证

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

	public String getRefreshToken()
	{
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public String getScope()
	{
		return scope;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
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