package com.ss.weixin.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ss.platform.sms.SmsSendHandler;
import com.ss.platform.util.JsonUtil;
import com.ss.platform.util.SessionUtil;
import com.ss.platform.util.StringUtil;
import com.ss.weixin.Const;
import com.ss.weixin.ap.pojo.WeixinUser;
import com.ss.weixin.ap.service.WeixinGzhService;
import com.ss.weixin.ap.service.WeixinUserService;
import com.ss.weixin.ap.util.WeixinUtil;

@Controller
@RequestMapping("/weixin/reg")
public class ValidateMobileController
{
	private static final Logger logger = LoggerFactory.getLogger(ValidateMobileController.class);

	@Autowired
	WeixinUserService weixinUserService;
	@Autowired
	private WeixinGzhService weixinGzhService;

	@ResponseBody
	@RequestMapping(value = "/beginValidateMobile")
	public ModelAndView beginValidateMobile(String gzhOpenId, String userOpenId)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		try
		{
			logger.info("toid:{},fromid:{}", gzhOpenId, userOpenId);
			SessionUtil.setAttribute(Const.SESSION_GZH_OPENID, gzhOpenId);
			SessionUtil.setAttribute(Const.SESSION_USER_OPENID, userOpenId);
			WeixinUser weixinUser = weixinUserService.getByOpenId(gzhOpenId, userOpenId);
			if (weixinUser == null)
			{
				model.put("message", "请先关注微信号！");
				ModelAndView mv = new ModelAndView("ci/weixin/ap/error", model);
				return mv;
			}
			// 是否已经绑定了手机
			if (!StringUtil.isEmpty(weixinUser.getMobile()))
			{
				ModelAndView mv = new ModelAndView("ci/weixin/ap/hasbinded", model);
				return mv;
			}
			SessionUtil.setAttribute(Const.SESSION_CURRENT_USER, weixinUser);
			ModelAndView mv = new ModelAndView("ci/weixin/ap/validationmobile", model);
			return mv;
		}
		catch (Exception e)
		{
			logger.error("开始验证手机号出错:", e);
			model.put("message", "绑定错误，请重新关注微信号！");
			ModelAndView mv = new ModelAndView("ci/weixin/ap/error", model);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/beginChangeMobile")
	public ModelAndView beginChangeMobile(String gzhOpenId, String userOpenId)
	{
		try
		{
			Map<String, Object> model = new HashMap<String, Object>();
			logger.info("toid:{},fromid:{}", gzhOpenId, userOpenId);
			SessionUtil.setAttribute(Const.SESSION_GZH_OPENID, gzhOpenId);
			SessionUtil.setAttribute(Const.SESSION_USER_OPENID, userOpenId);
			WeixinUser weixinUser = weixinUserService.getByOpenId(gzhOpenId, userOpenId);
			if (weixinUser == null)
			{
				model.put("message", "请先关注微信号！");
				ModelAndView mv = new ModelAndView("ci/weixin/ap/error", model);
				return mv;
			}
			SessionUtil.setAttribute(Const.SESSION_CURRENT_USER, weixinUser);
			ModelAndView mv = new ModelAndView("ci/weixin/ap/changemobile", model);
			return mv;
		}
		catch (Exception e)
		{
			logger.error("开始修改手机号出错:", e);
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/sendValidateCode", produces =
	{
		"application/json;charset=UTF-8"
	})
	public String sendValidateCode(String mobile, HttpServletRequest request)
	{
		String result = null;
		WeixinUser wu = (WeixinUser) SessionUtil.getAttribute(Const.SESSION_CURRENT_USER);
		if (null == wu)
		{
			result = JsonUtil.jsonErrorStr("用户会话已超时");
			return result;
		}

		if (wu.getMobile() != null && wu.getMobile().equals(mobile))
		{
			result = JsonUtil.jsonErrorStr("手机号码与原有号码相同!");
			return result;
		}

		try
		{
			String validateCode = StringUtil.genRandomNum(4);
			SessionUtil.setAttribute("validateCode", validateCode);
			SessionUtil.setAttribute("mobile", mobile);
			logger.info("sendValidateCode,mobile:{},code:{} ", mobile, validateCode);
			String message = String.format("【ABC】 %s为您认证手机的验证码。", validateCode);
			SmsSendHandler.sendSms(mobile, message);
			result = JsonUtil.jsonMsgStr("发送成功!");
		}
		catch (Exception e)
		{
			logger.error("发送验证码出错:", e);
			result = JsonUtil.jsonErrorStr("发送失败");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/checkValidateCode", produces =
	{
		"application/json;charset=UTF-8"
	})
	public String checkValidateCode(String mobile, String code)
	{

		String result = null;
		Map<String, Object> jobj = null;
		try
		{
			logger.info("checkValidateCode,mobile:{},code:{} ", mobile, code);
			String sessionCode = (String) SessionUtil.getAttribute("validateCode");
			String currentMobileInSession = (String) SessionUtil.getAttribute("mobile");
			WeixinUser wu = (WeixinUser) SessionUtil.getAttribute(Const.SESSION_CURRENT_USER);
			if (null == wu || currentMobileInSession == null)
			{
				result = JsonUtil.jsonErrorStr("用户会话已超时");
				return result;
			}
			if (!currentMobileInSession.equals(mobile))
			{
				result = JsonUtil.jsonErrorStr("手机号码与先前输入不一致！");
				return result;
			}
			if (code != null && code.equals(sessionCode))
			{
				String okWithPro = null;
				String okWithoutPro = null;
				if (StringUtil.isEmpty(wu.getMobile()))
				{
					okWithPro = "恭喜您手机号验证成功，现在您可以尽享旺店365的功能了。点击下方的菜单快去体验吧！";
					okWithoutPro = "您的手机号验证成功，但没有匹配到供货商，请联系供货商业务员确认是否在相应的系统中没有录入您的店铺信息。";
				}
				else
				{
					okWithPro = "您的手机号更换成功，现在您可以尽享旺店365的功能了。点击下方的菜单快去体验吧！";
					okWithoutPro = "您的手机号更换成功，但没有匹配到供货商，请联系供货商业务员确认是否在相应的系统中没有录入您的店铺信息。";
				}
				wu.setMobile(mobile);
				// 绑定用户企业关系

				// 给用户发信息
				result = JsonUtil.jsonMsgStr("验证成功");
				String jsonReply = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
				// Integer bindedCount = (Integer) jobj.get("bindedCount");
				Integer bindedCount = 0;
				if (bindedCount != null && bindedCount > 0)
				{
					jsonReply = String.format(jsonReply, wu.getOpenid(), okWithPro);
				}
				else
				{
					jsonReply = String.format(jsonReply, wu.getOpenid(), okWithoutPro);
				}
				WeixinUtil.sendCustomerMessage(jsonReply, weixinGzhService.getGzhByOpenId(wu.getGzhOpenid()));
			}
			else
			{
				result = JsonUtil.jsonErrorStr("验证码不正确");
			}
		}
		catch (Exception e)
		{
			result = JsonUtil.jsonErrorStr("发送失败");
			logger.error("校验验证码出错:", e);
		}
		if (jobj != null)
		{
			return JsonUtil.toJSONString(jobj);
		}
		return result;
	}
}
