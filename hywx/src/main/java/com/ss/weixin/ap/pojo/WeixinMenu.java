package com.ss.weixin.ap.pojo;

import com.ss.platform.base.pojo.BasePojo;

public class WeixinMenu extends BasePojo
{
	private String menuKey;
	private String name;
	private String btnType;// MenuButton.BTN_TYPE_CLICK = "click";
							// MenuButton.BTN_TYPE_VIEW = "view"
	private int horizontalIndex;// 水平位置，从左到右，从0开始
	private int verticalIndex;// 纵向位置，从下到上，从0开始
	private String gzhOpenId;// 公众号OpenId

	/**
	 * 
	 * @param menuKey
	 *            菜单唯一标识
	 * @param name
	 *            菜单名称
	 * @param gzhOpenId
	 *            公众号OpenId
	 * @param btnType
	 *            按钮类型
	 * @param horizontalIndex
	 *            水平位置，从左到右，从0开始
	 * @param verticalIndex
	 *            纵向位置，从下到上，从0开始
	 */
	public WeixinMenu(String menuKey, String name, String gzhOpenId, String btnType, int horizontalIndex,
			int verticalIndex)
	{
		this.menuKey = menuKey;
		this.name = name;
		this.gzhOpenId = gzhOpenId;
		this.horizontalIndex = horizontalIndex;
		this.verticalIndex = verticalIndex;
	}

	public String getMenuKey()
	{
		return menuKey;
	}

	public String getName()
	{
		return name;
	}

	public int getHorizontalIndex()
	{
		return horizontalIndex;
	}

	public int getVerticalIndex()
	{
		return verticalIndex;
	}

	public String getGzhOpenId()
	{
		return gzhOpenId;
	}

	public void setMenuKey(String menuKey)
	{
		this.menuKey = menuKey;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setHorizontalIndex(int horizontalIndex)
	{
		this.horizontalIndex = horizontalIndex;
	}

	public void setVerticalIndex(int verticalIndex)
	{
		this.verticalIndex = verticalIndex;
	}

	public void setGzhOpenId(String gzhOpenId)
	{
		this.gzhOpenId = gzhOpenId;
	}

	public String getBtnType()
	{
		return btnType;
	}

	public void setBtnType(String btnType)
	{
		this.btnType = btnType;
	}

}