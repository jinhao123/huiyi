package com.hy.platform.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils
{
	private static Log logger = LogFactory.getLog(HttpUtils.class);

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		NameValuePair nv = new BasicNameValuePair("keyword", "苏州");
		NameValuePair nv1 = new BasicNameValuePair("appkey", "db92e823d5c54727b4475ad7ede32c9a");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(nv);
		paramList.add(nv1);
		String baseUrl = "http://api.qixin007.com/APIService/enterprise/searchList";
		get(baseUrl, paramList);

	}

	/**
	 * 发送 get请求
	 */
	public static String get(String baseUrl, List<NameValuePair> paramList)
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try
		{
			String fullUrl = baseUrl + "?" + URLEncodedUtils.format(paramList, "utf-8");
			// 创建httpget.
			HttpGet httpget = new HttpGet(fullUrl);
			logger.debug("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try
			{
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				logger.debug(response.getStatusLine());
				if (entity != null)
				{
					// 打印响应内容
					String result = EntityUtils.toString(entity);
					logger.debug("Response content: " + result);
					return result;
				}
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 关闭连接,释放资源
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * post方式提交body体
	 */
	public static void postBody(String url, String body)
	{
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列

		// UrlEncodedFormEntity uefEntity;
		HttpEntity reqEntity = new StringEntity(body, "UTF-8");
		try
		{
			// uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(reqEntity);
			logger.debug("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try
			{
				HttpEntity entity = response.getEntity();
				if (entity != null)
				{
					logger.debug("--------------------------------------");
					logger.debug("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					logger.debug("--------------------------------------");
				}
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 关闭连接,释放资源
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * post方式提交表单（模拟用户登录请求）
	 */
	public static void postForm()
	{
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost("url");
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("username", "admin"));
		formparams.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity uefEntity;
		try
		{
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			logger.info("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try
			{
				HttpEntity entity = response.getEntity();
				if (entity != null)
				{
					logger.debug("--------------------------------------");
					logger.debug("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					logger.debug("--------------------------------------");
				}
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 关闭连接,释放资源
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public void post()
	{
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost("url");
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("type", "house"));
		UrlEncodedFormEntity uefEntity;
		try
		{
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			logger.debug("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try
			{
				HttpEntity entity = response.getEntity();
				if (entity != null)
				{
					logger.debug("--------------------------------------");
					logger.debug("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					logger.debug("--------------------------------------");
				}
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 关闭连接,释放资源
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
