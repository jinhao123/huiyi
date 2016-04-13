package com.ss.platform.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtil
{
	private static volatile WebApplicationContext wac;

	public static WebApplicationContext getWac()
	{
		if (wac == null)
		{
			synchronized (SpringUtil.class)
			{
				if (wac == null)
				{
					wac = ContextLoader.getCurrentWebApplicationContext();
				}
			}
		}
		return wac;
	}

	public static <T> T getBean(Class<T> requiredType)
	{
		return getWac().getBean(requiredType);
	}

	public static Object getBean(String name)
	{
		return getWac().getBean(name);
	}
}
