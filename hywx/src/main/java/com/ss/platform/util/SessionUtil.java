package com.ss.platform.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtil
{
	private static final Logger logger = LoggerFactory.getLogger(SessionUtil.class);
	private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<HttpServletResponse>();
	public static String USER_INFO = "user_info";
	public static String TENANT_ID = "tenant_id";

	public static void prepareContext(HttpServletRequest request, HttpServletResponse response)
	{
		requestHolder.set(request);
		responseHolder.set(response);
	}

	public static HttpServletRequest getRequest()
	{
		return requestHolder.get();
	}

	public static HttpServletResponse getResponse()
	{
		return responseHolder.get();
	}

	/*
	 * public static SmUser getLoginUser() { SmUser userInfo = null; HttpSession
	 * session = requestHolder.get().getSession(false); if (session != null) {
	 * userInfo = (SmUser) session.getAttribute(USER_INFO); } return userInfo; }
	 * 
	 * public static void setLoginUser(SmUser userInfo) {
	 * setAttribute(USER_INFO, userInfo); }
	 */

	public static void setAttribute(String name, Object value)
	{
		HttpSession session = requestHolder.get().getSession(true);
		session.setAttribute(name, value);
	}

	public static Object getAttribute(String name)
	{
		HttpServletRequest req = requestHolder.get();
		if (req != null)
		{
			HttpSession session = req.getSession(false);
			if (session != null)
			{
				return session.getAttribute(name);
			}
		}
		return null;
	}
}
