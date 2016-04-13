package com.ss.platform.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ss.platform.util.SessionUtil;


public class AuthFilter extends OncePerRequestFilter
{
	// BeanDefinitionParser ComponentScanBeanDefinitionParser
	// StandardServletEnvironment
	// ShiroFilter
	// BeanDefinitionParser
	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	private String[] notFilterUrls;

	protected void initFilterBean() throws ServletException
	{
		String notFilter = this.getFilterConfig().getInitParameter("notFilter");
		logger.info("notFilter:{}", notFilter);
		notFilter = trimString(notFilter);
		notFilterUrls = StringUtils.split(notFilter, ',');
	}

	private static String trimString(String str)
	{
		str = str.replace("\r", "");
		str = str.replace("\n", "");
		str = str.replace("\t", "");
		str = str.replace(" ", "");
		return str;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException
	{
		SessionUtil.prepareContext(request, response);

		// 请求的uri
		String uri = request.getRequestURI();
		// logger.info("AuthFilter:{},{}", uri, request);
		// 是否过滤
		boolean doFilter = uri.endsWith(".do");
		for (String s : notFilterUrls)
		{
			if (uri.indexOf(s) != -1)
			{
				doFilter = false;
				break;
			}
		}
		doFilter = false;
		if (doFilter)
		{
//			SmUser userInfo = SessionUtil.getLoginUser();
//			if (userInfo != null)
//			{
//				// 如果session中存在登录者实体，则继续
//				filterChain.doFilter(request, response);
//			}
//			else
//			{
//				boolean isAjaxRequest = isAjaxRequest(request);
//				if (isAjaxRequest)
//				{
//					response.setCharacterEncoding("UTF-8");
//					response.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");
//					return;
//				}
//				response.sendRedirect("/login/tologin");
//				return;
//			}
		}
		else
		{
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}
	}

	public static boolean isAjaxRequest(HttpServletRequest request)
	{
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

}
