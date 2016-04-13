package com.ss.platform.base.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class BasePojo implements Identifiable
{

	protected static final long serialVersionUID = 1L;
	protected Long id;
	protected String keyid;
	protected String status = STATUS_NEW;
	public static final String STATUS_DELETE = "0";
	public static final String STATUS_NEW = "1";
	protected Long creatorId;
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	protected Date createTime;
	protected Long modifyierId;
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	protected Date modifyTime;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
		this.keyid = id != null ? id.toString() : "";

	}

	public String getKeyid()
	{
		return keyid;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Long getCreatorId()
	{
		return creatorId;
	}

	public void setCreatorId(Long creatorId)
	{
		this.creatorId = creatorId;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Long getModifyierId()
	{
		return modifyierId;
	}

	public void setModifyierId(Long modifyierId)
	{
		this.modifyierId = modifyierId;
	}

	public Date getModifyTime()
	{
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	public void setKeyid(String keyid)
	{
		this.keyid = keyid;
	}

}
