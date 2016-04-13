package com.ss.weixin.ap.pojo;

import com.ss.platform.base.pojo.BasePojo;

public class WeixinUserLog extends BasePojo
{
	private Long weixinId;

	private String weixinAccount;

	private String openid;

	private String unionid;

	private String opType;
	// 关注
	public static final String OPTYPE_SUBSCRIBE = "1";
	// 取消关注
	public static final String OPTYPE_DESUBSCRIBE = "0";

	private String gzhOpenid;

	private Long gzhId;

	public Long getWeixinId()
	{
		return weixinId;
	}

	public void setWeixinId(Long weixinId)
	{
		this.weixinId = weixinId;
	}

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

	public String getOpType()
	{
		return opType;
	}

	public void setOpType(String opType)
	{
		this.opType = opType;
	}

	public Long getGzhId()
	{
		return gzhId;
	}

	public void setGzhId(Long gzhId)
	{
		this.gzhId = gzhId;
	}

}