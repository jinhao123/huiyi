package com.ss.platform.base.vo;

import com.ss.platform.base.pojo.Identifiable;

public class MbsTenant implements Identifiable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String code;
	private String status;
	private String dbUrl;
	private String netIp;
	private String netPort;
	private String dbUser;
	private String dbPwd;
	private String tenantType;
	private Integer index;
	private String activeFlag;
	/** 付费企业 */
	public static final String TYPE_PAY = "0";
	public static final String ACTIVE_YES = "1";

	public String toShortString()
	{
		return index + "_" + id;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDbUrl()
	{
		return dbUrl;
	}

	public void setDbUrl(String dbUrl)
	{
		this.dbUrl = dbUrl;
	}

	public String getDbUser()
	{
		return dbUser;
	}

	public void setDbUser(String dbUser)
	{
		this.dbUser = dbUser;
	}

	public String getDbPwd()
	{
		return dbPwd;
	}

	public void setDbPwd(String dbPwd)
	{
		this.dbPwd = dbPwd;
	}

	public Integer getIndex()
	{
		return index;
	}

	public void setIndex(Integer index)
	{
		this.index = index;
	}

	public String getNetIp()
	{
		return netIp;
	}

	public void setNetIp(String netIp)
	{
		this.netIp = netIp;
	}

	public String getNetPort()
	{
		return netPort;
	}

	public void setNetPort(String netPort)
	{
		this.netPort = netPort;
	}

	public String getTenantType()
	{
		return tenantType;
	}

	public void setTenantType(String tenantType)
	{
		this.tenantType = tenantType;
	}

	public String getActiveFlag()
	{
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag)
	{
		this.activeFlag = activeFlag;
	}

}
