package com.hy.platform.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadContext
{
	private static final ThreadLocal<Map<String, Object>> ctxMap = new ThreadLocal<Map<String, Object>>();

	private static final String HTTP_REQUEST = "HTTP_REQUEST";
	private static final String HTTP_RESPONSE = "HTTP_RESPONSE";
	private static final String TENANT_ID = "TENANT_ID";

	private static Map<String, Object> getMap()
	{
		Map<String, Object> map = ctxMap.get();
		if (map == null)
		{
			map = new HashMap<String, Object>();
			ctxMap.set(map);
		}
		return map;
	}

	public static void setTenantId(Long tenantId)
	{
		getMap().put(TENANT_ID, tenantId);
	}

	public static Long getTenantId()
	{
		Long tenantId = (Long) getMap().get(TENANT_ID);
		return tenantId;
	}

	/***
	 * 客户端请求的相关对象
	 * 
	 * @param request
	 * @param response
	 */
	public static void setRequest(HttpServletRequest request, HttpServletResponse response)
	{
		getMap().put(HTTP_REQUEST, request);
		getMap().put(HTTP_RESPONSE, response);
	}

	public static HttpServletRequest getRequest()
	{
		return (HttpServletRequest) getMap().get(HTTP_REQUEST);
	}

	public static HttpServletResponse getResponse()
	{
		return (HttpServletResponse) getMap().get(HTTP_RESPONSE);
	}
}
