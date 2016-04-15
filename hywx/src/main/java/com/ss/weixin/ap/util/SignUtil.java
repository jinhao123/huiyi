package com.ss.weixin.ap.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ss.platform.util.PropertiesUtil;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.service.WeixinGzhService;
import com.ss.weixin.ap.vo.AccessToken;
import com.ss.weixin.ap.vo.JsapiTicket;

/**
 * 请求校验工具类
 */
public class SignUtil
{
	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
	// 与接口配置信息中的Token要一致
	private static String token = PropertiesUtil.getSysProp("gzh.token", "mytoken123");
	private static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	private static Map<Long, AccessToken> accessTokenMap = new ConcurrentHashMap<Long, AccessToken>();
	private static Map<Long, JsapiTicket> jsapiTicketMap = new ConcurrentHashMap<Long, JsapiTicket>();

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce)
	{
		String[] arr = new String[]
		{
			token, timestamp, nonce
		};
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
		{
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try
		{
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 获取js签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static Map<String, String> getSignature(WeixinGzh gzh, String url)
	{
		// 获取JsapiTicket
		JsapiTicket jsapiTicket = jsapiTicketMap.get(gzh.getId());
		logger.debug(jsapiTicketMap.toString());
		if (jsapiTicket == null || jsapiTicket.getExpiresTime() < new Date().getTime())
		{
			jsapiTicket = getJsapiTicket(gzh);
			jsapiTicketMap.put(gzh.getId(), jsapiTicket);
		}
		Map<String, String> map = sign(jsapiTicket.getTicket(), url, WeixinGzhService.defaultGzh.getAppId());
		return map;
	}

	/**
	 * 生成签名
	 * 
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url, String appId)
	{
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try
		{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", appId);
		System.out.println(ret);
		return ret;
	}

	private static String byteToHex(final byte[] hash)
	{
		Formatter formatter = new Formatter();
		for (byte b : hash)
		{
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}

	private static String create_timestamp()
	{
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取JsapiTicket
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static JsapiTicket getJsapiTicket(WeixinGzh gzh)
	{
		// 读取缓存accessToken
		AccessToken accessToken = accessTokenMap.get(gzh.getId());
		logger.debug(accessTokenMap.toString());
		if (accessToken == null || accessToken.getExpiresTime() < new Date().getTime())
		{
			accessToken = WeixinUtil.getAccessToken(gzh.getAppId(), gzh.getAppSecret());
			accessTokenMap.put(gzh.getId(), accessToken);
		}
		JsapiTicket jsapiTicket = new JsapiTicket();
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken.getToken());
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject && jsonObject.getInteger("errcode") == 0)
		{
			try
			{
				String ticket = jsonObject.getString("ticket");
				Integer expiresIn = jsonObject.getInteger("expires_in");
				jsapiTicket.setExpiresIn(expiresIn);
				jsapiTicket.setTicket(ticket);
				jsapiTicket.setExpiresTime(new Date().getTime() + jsonObject.getLong("expires_in") * 900L);
			}
			catch (JSONException e)
			{
				jsapiTicket = null;
				logger.error("获取ticket失败:{}", jsonObject);
			}
		}
		return jsapiTicket;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray)
	{
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++)
		{
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte)
	{
		char[] Digit =
		{
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
		};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

}