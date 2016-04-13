package com.ss.weixin.ap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.weixin.ap.dao.WeixinGzhDao;
import com.ss.weixin.ap.pojo.WeixinGzh;

@Service
public class WeixinGzhService
{
	@Autowired
	private WeixinGzhDao weixinGzhDao;

	private static final Logger logger = LoggerFactory.getLogger(WeixinGzhService.class);
	public static final String defaultGzhOpenId = "gh_f9bce124d8e1";
	public static final WeixinGzh defaultGzh = new WeixinGzh();
	{
		// 旺店365公众号信息
		defaultGzh.setOpenid(defaultGzhOpenId);
		defaultGzh.setAppId("wx76837087b219b992");
		defaultGzh.setAppSecret("d80b919c9a50133c18af97ed94094782");
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
		if (gzh == null && defaultGzhOpenId.equals(gzhOpenId))
		{
			return defaultGzh;
		}
		return gzh;
	}
}
