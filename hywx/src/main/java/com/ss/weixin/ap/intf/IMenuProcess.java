package com.ss.weixin.ap.intf;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.ss.weixin.ap.pojo.WeixinGzh;

public interface IMenuProcess
{
	public ModelAndView getProcessMv(WeixinGzh gzh, String code, HttpServletRequest req);
}
