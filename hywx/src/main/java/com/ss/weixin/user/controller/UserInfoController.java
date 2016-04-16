package com.ss.weixin.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.ss.platform.util.StringUtil;
import com.ss.weixin.ap.intf.IMenuProcess;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.pojo.WeixinUser;

@Repository(value = "userInfoController")
public class UserInfoController implements IMenuProcess
{
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	public ModelAndView getProcessMv(WeixinGzh gzh, String code, HttpServletRequest req)
	{
		return showUserInfo(gzh.getOpenid(), "");
	}

	public ModelAndView showUserInfo(String gzhOpenId, String userOpenId)
	{
		try
		{
			// 根据userOpenId查库
			WeixinUser user = new WeixinUser();// weixinUserService.getByOpenId(gzhOpenId,
			// userOpenId);
			Map<String, Object> model = new HashMap<String, Object>();
			if (user == null)
			{
				user = new WeixinUser();
				model.put("message", "关注公众号出错，请取消关注本公众号后重新关注!");
				ModelAndView mv = new ModelAndView("weixin/ap/error", model);
				return mv;
			}
			if (StringUtil.isEmpty(user.getMobile()))
			{
				user = new WeixinUser();
				model.put("user", user);
				user.setOpenid(userOpenId);
				user.setGzhOpenid(gzhOpenId);
				ModelAndView mv = new ModelAndView("weixin/ap/notbinded", model);
				return mv;
			}
			model.put("user", user);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			model.put("resultList", list);
			// 处理手机号
			String userMobile = user.getMobile();
			userMobile = userMobile.substring(0, 3) + "****" + userMobile.substring(7, 11);
			user.setMobile(userMobile);
			ModelAndView mv = new ModelAndView("weixin/ap/mysupply", model);
			return mv;
		}
		catch (Exception e)
		{
			logger.error("开始验证手机号出错:", e);
		}
		return null;
	}

}
