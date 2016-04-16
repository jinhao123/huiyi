package com.ss.weixin.ap.controller;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ss.platform.util.StringUtil;
import com.ss.platform.util.ZkUtil;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.service.WeixinGzhService;
import com.ss.weixin.ap.service.WeixinService;
import com.ss.weixin.ap.util.MenuUtil;
import com.ss.weixin.ap.util.SignUtil;

@Controller
@RequestMapping("/weixin")
public class WeixinController
{
	private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);

	@Autowired
	private WeixinService weixinService;
	@Autowired
	private WeixinGzhService weixinGzhService;

	@ResponseBody
	@RequestMapping(value = "/ap", method = RequestMethod.GET)
	public void validSvr(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");

			logger.info("微信接入验证,signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
			PrintWriter out = response.getWriter();
			if (SignUtil.checkSignature(signature, timestamp, nonce))
			{
				out.print(echostr);
			}
			else
			{
				logger.info("微信接入验证失败");
			}
			out.close();
			out = null;
		}
		catch (Exception e)
		{
			logger.error("微信入口出错:", e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ap", method = RequestMethod.POST)
	public void handleMsg(HttpServletRequest request, HttpServletResponse response)
	{
		String result = weixinService.handleMsg(request);
		ZkUtil.responseJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "/ap/createMenu", method = RequestMethod.GET)
	public void createMenu(String gzhId)
	{
		Object result = null;
		try
		{
			WeixinGzh gzh = weixinGzhService.getGzhByOpenId(gzhId);
			MenuUtil.createMenu(gzh);
			result = "已完成";
		}
		catch (Exception e)
		{
			logger.error("微信创建菜单出错:", e);
		}
		ZkUtil.responseJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "/ap/menuRoute")
	public ModelAndView menuRoute(String gzhId, String code, String state, HttpServletRequest req)
	{
		logger.info("menuRoute,gzhId:{},code:{},state:{}", gzhId, code, state);
		logger.info("menuRoute,url:{},uri:{}", req.getRequestURL(), req.getRequestURI());
		System.out.println(req.getPathInfo());
		try
		{
			if (!StringUtil.isEmpty(code))
			{

			}
			else
			{
				// 其他入口来的，如初始短信的连接
			}
			// 根据gzhId获取appid和appSecret
			// WeixinGzh gzh = weixinGzhService.getGzhByOpenId(gzhId);
			WeixinGzh gzh = weixinGzhService.defaultGzh;
			return weixinService.getProcessMv(gzh, code, state, req);
		}
		catch (Exception e)
		{
			logger.error("菜单入口出错:", e);
		}
		// ZkUtil.responseJson(result);
		return null;
	}

	private static void getUserAcessToken(String code)
	{

	}

	private static String getAllHeads(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();
		Enumeration<String> eh = request.getHeaderNames();
		while (eh.hasMoreElements())
		{
			String headerName = (String) eh.nextElement();
			sb.append(headerName).append(":").append(request.getHeader(headerName)).append("\r\n");
		}
		return sb.toString();
	}

	private static String getAllParams(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();
		Enumeration<String> eh = request.getParameterNames();
		while (eh.hasMoreElements())
		{
			String headerName = (String) eh.nextElement();
			sb.append(headerName).append(":").append(request.getParameter(headerName)).append("\r\n");
		}
		return sb.toString();
	}
}
