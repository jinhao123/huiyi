package com.ss.weixin.ap.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.pojo.WeixinMenu;
import com.ss.weixin.ap.vo.AccessToken;
import com.ss.weixin.ap.vo.MenuButton;

public class MenuUtil
{
	private static final Logger logger = LoggerFactory.getLogger(MenuUtil.class);
	//
	public static final String MENU_HELP = "sys_help";
	public static final String MENU_SETTING = "sys_setting";
	public static final String MENU_GONG_HUO_SHANG = "sys_ghs";
	public static final String MENU_DUI_ZHANG_DAN = "esss_dzd";
	public static final String MENU_YI_JIAN_DING_HUO = "sys_yjdh";
	public static final List<WeixinMenu> menuList = new ArrayList<WeixinMenu>();

	public static void createMenu(WeixinGzh gzh)
	{

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(gzh.getAppId(), gzh.getAppSecret());
		// AccessToken at = new AccessToken();
		// at.setToken("ZDUQsoHa_pJwxWOHjaxoY5DHWPLaOVhZlwnSGPMJ3ZFPL3bHLwEiDXdCt_XbhsDgOQNsekt2ABS7Urcvfkrm1SajP8yjSUBnlZfb1I2cgk4xwZ7FP_Wklw26Ql7JAqTeGYGiADAKCK");
		logger.info("获取到微信access_token:{}", at);
		if (null != at)
		{
			// 调用接口创建菜单
			String menuJson = buildMenu(gzh);
			logger.info("创建菜单:{}", menuJson);
			int result = WeixinUtil.createMenu(menuJson, at.getToken());
		}
	}

	private static String buildMenu(WeixinGzh gzh)
	{

		MenuButton root = new MenuButton();
		List<MenuButton> menus1 = new ArrayList<MenuButton>();
		root.setButton(menus1);
		menus1.add(MenuButton.newUrlMenu("一键订货", buildMenuUrl(gzh, MENU_YI_JIAN_DING_HUO)));
		menus1.add(MenuButton.newUrlMenu("供货商", buildMenuUrl(gzh, MENU_GONG_HUO_SHANG)));
		menus1.add(MenuButton.newUrlMenu("帮助", buildMenuUrl(gzh, MENU_HELP)));

		/*
		 * MenuButton submenu = MenuButton.newSubMenu("个人中心");
		 * menus1.add(submenu);
		 * submenu.getSub_button().add(MenuButton.newUrlMenu("设置",
		 * buildMenuUrl(gzh, MENU_SETTING)));
		 * submenu.getSub_button().add(MenuButton.newUrlMenu("验证",
		 * buildMenuUrl(gzh, MENU_HELP)));
		 * submenu.getSub_button().add(MenuButton.newClickMenu("赞一下",
		 * "give_me_five"));
		 */

		String json = JSONObject.toJSONString(root);
		return json;
	}

	public static final String menu_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s";
	public static final String menu_router_url = "http://wx.waiqin365.com/ci/weixin/ap/menuRoute.action";

	private static String buildMenuUrl(WeixinGzh gzh, String menuKey)
	{
		try
		{
			String redirect_url = URLEncoder.encode(menu_router_url + "?gzhId=" + gzh.getOpenid(), "utf8");
			String url = String.format(menu_url, gzh.getAppId(), redirect_url, menuKey + "#wechat_redirect");
			return url;
		}
		catch (Exception e)
		{
			logger.error("菜单url生成出错", e);
		}
		return null;
	}

}
