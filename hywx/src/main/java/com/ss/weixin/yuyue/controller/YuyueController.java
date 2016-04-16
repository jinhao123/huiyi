package com.ss.weixin.yuyue.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.ss.weixin.ap.intf.IMenuProcess;
import com.ss.weixin.ap.pojo.WeixinGzh;

@Repository(value = "yuyueController")
public class YuyueController implements IMenuProcess
{

	public ModelAndView getProcessMv(WeixinGzh gzh, String code, HttpServletRequest req)
	{
		return showDoctorList();
	}

	public ModelAndView showDoctorList()
	{
		Map<String, Object> model = new HashMap<String, Object>();
		// model.put("pTypeList", pTypeList);
		// model.put("storeList", storeList);
		ModelAndView mv = new ModelAndView("/ss/weixin/yuyue/ghList", model);
		return mv;
	}
}
