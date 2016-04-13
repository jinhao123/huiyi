package com.ss.platform.base.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面请求的查询信息，分页相关字段名目前和前台框架easyui一致
 * 
 * @author jinhao
 * 
 */
public class QueryInfo
{
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_ROWS = 10;
	// 第几页
	private int page;
	// 每页行数
	private int rows;
	// 排序字段名
	private String sort;
	// desc asc
	private String order;
	private Map<String, Object> query = new HashMap<String, Object>();

	public void put(String key, Object value)
	{
		query.put(key, value);
	}

	public static Map<String, Object> toSqlParams(QueryInfo query)
	{
		if (query != null)
		{
			return query.toSqlParams();
		}
		return new HashMap<String, Object>();
	}

	public Map<String, Object> toSqlParams()
	{
		Map<String, Object> params = new HashMap<String, Object>();
		if (query != null)
		{
			params.putAll(query);
		}
		// 分页信息
		if (page >= 1 && rows >= 0)
		{
			params.put("offset", rows * (page - 1));
			params.put("limit", rows);
		}
		// 排序信息
		if (sort != null && !sort.isEmpty())
		{
			params.put("sort", toUnderlineStyle(sort));
			params.put("order", order);
		}
		return params;
	}

	public static String toUnderlineStyle(String s)
	{
		if (s == null)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (Character.isUpperCase(c))
			{
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	public Map<String, Object> getQuery()
	{
		return query;
	}

	public void setQuery(Map<String, Object> query)
	{
		this.query = query;
	}
}
