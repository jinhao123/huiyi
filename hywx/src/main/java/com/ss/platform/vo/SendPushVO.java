package com.ss.platform.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送短信和推送消息对象
 * 
 * @package: com.fh.iasp.sysapp.message.vo
 * @class: SendPushVO
 * @date: May 10, 2013
 * @author: SUNFG
 */
/**
 * @描述：
 * @包名：com.fh.iasp.sysapp.message.vo
 * @类名：SendPushVO
 * @日期：2013-5-14
 * @版权：Copyright ® 烽火星空. All right reserved.
 * @作者：Administrator
 */
public class SendPushVO
{
	/**
	 * 企业ID
	 */
	private Long tenantid;
	/**
	 * 应用ID 不带版本号
	 */
	private Long appid;
	/**
	 * 消息对应模块名称，作为消息分类使用,新版本5.2.0以后废弃
	 * 
	 * @deprecated since 5.2.0 使用 moudleId替代
	 */
	@Deprecated
	private String moudle;
	/**
	 * 模块ID 5.2.0中推送消息分类中的子分类，如待办任务中有：客户拜访，审批等 messageType=1 moudleId: 1 审批 2
	 * 日报点评 3 任务 4 订单 5 销量 6 拜访 messageType=2 moudleId: 1 审批 2 日报点评 3 任务 4 订单 5
	 * 销量
	 */
	private String moudleId;
	/**
	 * 小秘书分类
	 */
	public static final String MOUDLE_XMS_SP = "1";
	public static final String MOUDLE_XMS_RB = "2";
	public static final String MOUDLE_XMS_RW = "3";
	public static final String MOUDLE_XMS_DD = "4";
	public static final String MOUDLE_XMS_XL = "5";
	public static final String MOUDLE_XMS_BF = "6";
	/**
	 * 待办任务分类
	 */
	public static final String MOUDLE_DBRW_SP = "1";
	public static final String MOUDLE_DBRW_RB = "2";
	public static final String MOUDLE_DBRW_RW = "3";
	public static final String MOUDLE_DBRW_DD = "4";
	public static final String MOUDLE_DBRW_XL = "5";
	/**
	 * 消息类型
	 */
	public static final String MESSAGE_TYPE_SYS = "0";
	public static final String MESSAGE_TYPE_XMS = "1";
	public static final String MESSAGE_TYPE_DBRW = "2";
	public static final String MESSAGE_TYPE_TZGG = "3";
	public static final String MESSAGE_TYPE_LJDW = "4";

	private String messageType;
	/**
	 * 发送人ID
	 */
	private Long sendid;
	/**
	 * 接收人
	 */
	private List<Long> recvid;
	/**
	 * PUSH消息标题
	 */
	private String title;
	/**
	 * Push Android URL
	 */
	private String androidurl;
	/**
	 * Push iPhone URL
	 */
	private String iosurl;
	/**
	 * SMS smscontent 短信内容
	 */
	private String smscontent;
	/**
	 * SMS smssign 短信签名
	 */
	private String smssign;
	/**
	 * 消息对应审批业务ID，用于审批回调
	 */
	private Long workId;

	/**
	 * 发送时间，不指定时立即发送
	 */
	private String sendTime;

	private boolean isNotPush;
	/**
	 * 消息自定义内容
	 */
	private Map<String, String> custom = new HashMap<String, String>();

	public void addCustom(String key, String value)
	{
		custom.put(key, value);
	}

	public String getCustom(String key)
	{
		return custom.get(key);
	}

	/**
	 * 消息直接打开地址，设置后客户端直接在手机打开，不需要请求Token信息
	 * 
	 * @param openUrl
	 *            打开地址
	 */
	public void setOpenUrl(String openUrl)
	{
		custom.put("push_url", openUrl);
	}

	@Override
	public String toString()
	{
		return String.format("appId:%s,from:%s,to:%s,androidurl:%s,iosurl:%s,smscontent:%s,smssign:%s", new Object[]
		{
			appid, sendid, recvid, androidurl, iosurl, smscontent, smssign
		});
	}

	public Long getAppid()
	{
		return appid;
	}

	public void setAppid(Long appid)
	{
		this.appid = appid;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public String getMoudle()
	{
		return moudle;
	}

	/**
	 * @deprecated
	 * @param moudle
	 *            消息模块
	 */
	@Deprecated
	public void setMoudle(String moudle)
	{
		this.moudle = moudle;
	}

	public Long getSendid()
	{
		return sendid;
	}

	public void setSendid(Long sendid)
	{
		this.sendid = sendid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getIosurl()
	{
		return iosurl;
	}

	public void setIosurl(String iosurl)
	{
		this.iosurl = iosurl;
	}

	public String getAndroidurl()
	{
		return androidurl;
	}

	public void setAndroidurl(String androidurl)
	{
		this.androidurl = androidurl;
	}

	public String getSmscontent()
	{
		return smscontent;
	}

	public void setSmscontent(String smscontent)
	{
		this.smscontent = smscontent;
	}

	public String getSmssign()
	{
		return smssign;
	}

	public void setSmssign(String smssign)
	{
		this.smssign = smssign;
	}

	public List<Long> getRecvid()
	{
		return recvid;
	}

	public void setRecvid(List<Long> recvid)
	{
		this.recvid = recvid;
	}

	public Long getTenantid()
	{
		return tenantid;
	}

	public void setTenantid(Long tenantid)
	{
		this.tenantid = tenantid;
	}

	public String getMoudleId()
	{
		return moudleId;
	}

	public void setMoudleId(String moudleId)
	{
		this.moudleId = moudleId;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}

	public Long getWorkId()
	{
		return workId;
	}

	public void setWorkId(Long workId)
	{
		this.workId = workId;
	}

	public Map<String, String> getCustom()
	{
		return custom;
	}

	public void setCustom(Map<String, String> custom)
	{
		this.custom = custom;
	}

	public String getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(String sendTime)
	{
		this.sendTime = sendTime;
	}

	public boolean isNotPush()
	{
		return isNotPush;
	}

	public void setNotPush(boolean isNotPush)
	{
		this.isNotPush = isNotPush;
	}

}
