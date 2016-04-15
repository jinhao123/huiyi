package com.ss.weixin.ap.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.vo.AccessToken;
import com.ss.weixin.ap.vo.UserAccessToken;

/**
 * 公众平台通用接口工具类
 * 
 */
public class WeixinUtil
{
	// 推送客户消息地址
	public final static String send_customer_message = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr)
	{
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try
		{
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm =
			{
				new MyX509TrustManager()
			};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr)
			{
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null)
			{
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		}
		catch (ConnectException ce)
		{
			// log.error("Weixin server connection timed out.");
		}
		catch (Exception e)
		{
			// log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret)
	{
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject)
		{
			try
			{
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
				accessToken.setExpiresTime(new Date().getTime() + jsonObject.getLong("expires_in") * 900L);
			}
			catch (JSONException e)
			{
				accessToken = null;
				logger.error("获取token失败:{}", jsonObject);
			}
		}
		return accessToken;
	}

	public static final String user_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	public static UserAccessToken accessTokenInCache = null;

	/***
	 * 网页授权access_token
	 * 
	 * @param gzh
	 * @param code
	 * @return
	 */
	// TODO 对access_token做缓存
	public static UserAccessToken getUserAccessToken(WeixinGzh gzh, String code)
	{
		/*
		 * if (WeixinUtil.accessTokenInCache != null)
		 * logger.debug(accessTokenInCache.toString()); // 如果缓存中已经有，而且没有过期则直接 if
		 * (accessTokenInCache != null && accessTokenInCache.getExpiresTime() !=
		 * null) { Long nowTime = new Date().getTime(); if (nowTime <
		 * accessTokenInCache.getExpiresTime()) { return accessTokenInCache; } }
		 */

		UserAccessToken uat = null;
		String requestUrl = String.format(user_token_url, gzh.getAppId(), gzh.getAppSecret(), code);
		JSONObject json = httpRequest(requestUrl, "GET", null);
		logger.debug(json.toJSONString());
		// 如果请求成功
		if (null != json)
		{
			try
			{
				uat = new UserAccessToken();

				uat.setToken(json.getString("access_token"));
				uat.setExpiresIn(json.getInteger("expires_in"));
				uat.setRefreshToken(json.getString("refresh_token"));
				uat.setOpenid(json.getString("openid"));
				uat.setScope(json.getString("scope"));

				WeixinUtil.accessTokenInCache = uat;
				logger.debug("--------" + WeixinUtil.accessTokenInCache.toString());
			}
			catch (JSONException e)
			{
				uat = null;
				logger.error("获取用户token失败:{}", json);
			}
		}
		return uat;
	}

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(String jsonMenu, String accessToken)
	{
		int result = 0;
		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject)
		{
			result = jsonObject.getIntValue("errcode");
		}
		// 判断菜单创建结果
		if (0 == result)
		{
			logger.info("菜单创建成功！");
		}
		else
		{
			logger.info("菜单创建失败，错误码：" + result);
		}
		return result;
	}

	/**
	 * 发送客户端消息
	 * 
	 * @return 0表示成功，其他值表示失败
	 */
	public static int sendCustomerMessage(String jsonReply, WeixinGzh gzh)
	{
		int result = 0;
		// 拼装创建菜单的url
		String url = String
				.format(send_customer_message, getAccessToken(gzh.getAppId(), gzh.getAppSecret()).getToken());
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonReply);

		if (null != jsonObject)
		{
			result = jsonObject.getIntValue("errcode");
		}
		// 判断菜单创建结果
		if (0 == result)
		{
			logger.info("发送客户消息成功！");
		}
		else
		{
			logger.info("发送客户消息失败，错误码：" + result);
		}
		return result;
	}

	static final String HTTP_POST = "POST";
	static final String HTTP_GET = "GET";

	private static String doHttpRequest(String url, String method, String postData)
	{
		String result = "";
		//
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try
		{
			HttpUriRequest request = null;
			CloseableHttpResponse response = null;
			if (HTTP_GET.equals(method))
			{
				request = new HttpGet(url);
			}
			else
			{
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new StringEntity(postData, "utf-8"));
				request = httpPost;
			}
			try
			{
				response = httpclient.execute(request);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null)
				{
					result = EntityUtils.toString(resEntity, "utf-8");
				}
			}
			finally
			{
				response.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
}