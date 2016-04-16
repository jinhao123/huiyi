package com.ss.weixin.ap.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ss.platform.util.SpringUtil;
import com.ss.weixin.ap.intf.IMenuProcess;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.vo.AccessToken;
import com.ss.weixin.ap.vo.MenuButton;

public class MenuUtil
{
	private static final Logger logger = LoggerFactory.getLogger(MenuUtil.class);
	//
	public static final String MENU_HELP = "sys_help";
	public static final String MENU_USER_INFO = "sys_user_info";
	public static final String MENU_NOTICE = "sys_notice";
	public static final String MENU_YUYUE = "sys_yuyue";
	public static final String menu_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s";
	// 菜单ModelView Map
	public static Map<String, IMenuProcess> menuConfig = new ConcurrentHashMap<String, IMenuProcess>();
	public static Map<String, String> menuBeanConfig = new ConcurrentHashMap<String, String>();
	static
	{
		menuBeanConfig.put(MenuUtil.MENU_USER_INFO, "userInfoController");
		menuBeanConfig.put(MenuUtil.MENU_HELP, "menuHelp");
		menuBeanConfig.put(MenuUtil.MENU_YUYUE, "yuyueController");
	}

	public static IMenuProcess getMenuProcesser(String menuTag)
	{
		IMenuProcess mp = menuConfig.get(menuTag);
		if (mp == null)
		{
			String menuBeanName = menuBeanConfig.get(menuTag);
			if (menuBeanName != null)
			{
				mp = (IMenuProcess) SpringUtil.getBean(menuBeanName);
				if (mp != null)
				{
					menuConfig.put(menuTag, mp);
				}
			}
		}
		return mp;
	}

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
		menus1.add(MenuButton.newUrlMenu("在线预约", buildMenuUrl(gzh, MENU_YUYUE)));
		{
			MenuButton submenu = MenuButton.newSubMenu("报表");
			menus1.add(submenu);

			submenu.getSub_button().add(MenuButton.newUrlMenu("每日收入", buildMenuUrl(gzh, MENU_HELP)));
			submenu.getSub_button().add(MenuButton.newUrlMenu("绩效查询", buildMenuUrl(gzh, MENU_HELP)));
		}
		{
			MenuButton submenu = MenuButton.newSubMenu("我的");
			menus1.add(submenu);
			submenu.getSub_button().add(MenuButton.newUrlMenu("个人信息", buildMenuUrl(gzh, MENU_USER_INFO)));
			submenu.getSub_button().add(MenuButton.newClickMenu("帮助", "give_me_five"));
		}

		String json = JSONObject.toJSONString(root);
		return json;
	}

	public static final String menu_router_url = "http://%s/weixin/ap/menuRoute.action?gzhId=";

	private static String buildMenuUrl(WeixinGzh gzh, String menuKey)
	{
		try
		{
			String menuRoteUrl = String.format(menu_router_url, gzh.getHost(), gzh.getOpenid());
			String redirect_url = URLEncoder.encode(menuRoteUrl, "utf-8");
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
