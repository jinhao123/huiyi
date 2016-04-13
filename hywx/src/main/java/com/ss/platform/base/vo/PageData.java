package com.ss.platform.base.vo;

import java.util.List;

import com.ss.platform.util.JsonUtil;

/***
 * 返回给前端的一页数据
 * 
 * @author jinhao
 * 
 */
public class PageData
{

	private int total;
	private List rows;
	private List footer;

	public String toJsonString()
	{
		return JsonUtil.toJSONString(this);
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public List getRows()
	{
		return rows;
	}

	public void setRows(List rows)
	{
		this.rows = rows;
	}

	public List getFooter()
	{
		return footer;
	}

	public void setFooter(List footer)
	{
		this.footer = footer;
	}
}
