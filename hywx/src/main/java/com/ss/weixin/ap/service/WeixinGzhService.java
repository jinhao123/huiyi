package com.ss.weixin.ap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.platform.util.PropertiesUtil;
import com.ss.weixin.ap.dao.WeixinGzhDao;
import com.ss.weixin.ap.pojo.WeixinGzh;

@Service
public class WeixinGzhService
{
	@Autowired
	private WeixinGzhDao weixinGzhDao;

	private static final Logger logger = LoggerFactory.getLogger(WeixinGzhService.class);
	public static final WeixinGzh defaultGzh = new WeixinGzh();
	static
	{
		defaultGzh.setOpenid(PropertiesUtil.getSysProp("gzh.openid"));
		defaultGzh.setAppId(PropertiesUtil.getSysProp("gzh.appid"));
		defaultGzh.setAppSecret(PropertiesUtil.getSysProp("gzh.secret"));
	}

	/**
	 * 通过openId获取公众号信息
	 * 
	 * @param gzhOpenId
	 * @return
	 */
	public WeixinGzh getGzhByOpenId(String gzhOpenId)
	{

		// T从库中获取公众号信息
		WeixinGzh gzh = weixinGzhDao.getGzhByOpenId(gzhOpenId);
		if (gzh == null && defaultGzh.getOpenid().equals(gzhOpenId))
		{
			return defaultGzh;
		}
		return gzh;
	}
}
