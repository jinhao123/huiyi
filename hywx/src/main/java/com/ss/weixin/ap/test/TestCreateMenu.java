package com.ss.weixin.ap.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ss.weixin.ap.service.WeixinGzhService;

public class TestCreateMenu
{
	private static final Logger logger = LoggerFactory.getLogger(TestCreateMenu.class);

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		logger.info("aaaa:{}", WeixinGzhService.defaultGzh.getAppId());
	}

}
