package com.ss.weixin.ap.vo;

import java.util.ArrayList;
import java.util.List;

public class MenuButton
{
	public final static String BTN_TYPE_CLICK = "click";
	public final static String BTN_TYPE_VIEW = "view";
	private List<MenuButton> button;
	private List<MenuButton> sub_button;
	private String type;
	private String name;
	private String key;
	private String url;
	private String media_id;

	public static MenuButton newClickMenu(String name, String key)
	{
		MenuButton btn = new MenuButton();
		btn.setType("click");
		btn.setName(name);
		btn.setKey(key);
		return btn;
	}

	public static MenuButton newUrlMenu(String name, String url)
	{
		MenuButton btn = new MenuButton();
		btn.setType("view");
		btn.setName(name);
		btn.setUrl(url);
		return btn;
	}

	public static MenuButton newSubMenu(String name)
	{
		MenuButton btn = new MenuButton();
		btn.setName(name);
		List<MenuButton> menus2 = new ArrayList<MenuButton>();
		btn.setSub_button(menus2);
		return btn;
	}

	public List<MenuButton> getButton()
	{
		return button;
	}

	public void setButton(List<MenuButton> button)
	{
		this.button = button;
	}

	public List<MenuButton> getSub_button()
	{
		return sub_button;
	}

	public void setSub_button(List<MenuButton> sub_button)
	{
		this.sub_button = sub_button;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getMedia_id()
	{
		return media_id;
	}

	public void setMedia_id(String media_id)
	{
		this.media_id = media_id;
	}
}
