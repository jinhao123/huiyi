package com.ss.weixin.ap.pojo;

import com.ss.platform.base.pojo.BasePojo;

public class WeixinUser extends BasePojo
{

	private String weixinAccount;

	private String openid;

	private String unionid;

	private String mobile;

	private Long gzhId;

	private String gzhOpenid;

	public String getWeixinAccount()
	{
		return weixinAccount;
	}

	public void setWeixinAccount(String weixinAccount)
	{
		this.weixinAccount = weixinAccount;
	}

	public String getOpenid()
	{
		return openid;
	}

	public void setOpenid(String openid)
	{
		this.openid = openid;
	}

	public String getUnionid()
	{
		return unionid;
	}

	public void setUnionid(String unionid)
	{
		this.unionid = unionid;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public Long getGzhId()
	{
		return gzhId;
	}

	public void setGzhId(Long gzhId)
	{
		this.gzhId = gzhId;
	}

	public String getGzhOpenid()
	{
		return gzhOpenid;
	}

	public void setGzhOpenid(String gzhOpenid)
	{
		this.gzhOpenid = gzhOpenid;
	}
}