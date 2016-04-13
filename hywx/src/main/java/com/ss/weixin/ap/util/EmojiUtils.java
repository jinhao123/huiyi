package com.ss.weixin.ap.util;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ss.weixin.ap.msg.rsp.TextMessage;

/**
 * 核心服务类
 * 
 */
public class EmojiUtils
{
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request)
	{
		String respMessage = null;
		try
		{
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			textMessage.setContent("自行车" + emoji(0x1F6B2) + " 男性" + emoji(0x1F6B9) + " 钱袋" + emoji(0x1F4B0));
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return respMessage;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji)
	{
		return String.valueOf(Character.toChars(hexEmoji));
	}
}
