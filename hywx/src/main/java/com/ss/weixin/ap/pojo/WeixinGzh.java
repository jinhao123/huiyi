package com.ss.weixin.ap.pojo;

import com.ss.platform.base.pojo.BasePojo;

public class WeixinGzh extends BasePojo
{
	private Long tenantId;
	private String name;
	private String account;
	private String openid;
	private String appId;
	private String appSecret;
	private String shMchId;
	private String shApiKey;

	public Long getTenantId()
	{
		return tenantId;
	}

	public void setTenantId(Long tenantId)
	{
		this.tenantId = tenantId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getAppSecret()
	{
		return appSecret;
	}

	public void setAppSecret(String appSecret)
	{
		this.appSecret = appSecret;
	}

	public String getShMchId()
	{
		return shMchId;
	}

	public String getShApiKey()
	{
		return shApiKey;
	}

	public void setShMchId(String shMchId)
	{
		this.shMchId = shMchId;
	}

	public void setShApiKey(String shApiKey)
	{
		this.shApiKey = shApiKey;
	}
}