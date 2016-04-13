package com.hy.platform.util;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkUtil
{
	/** 日志. */
	protected static Logger logger = LoggerFactory.getLogger(ZkUtil.class);
	/** plain类型 */
	private static final String CONTENT_TYPE_PLAIN = "text/plain;charset=UTF-8";
	/** html类型 */
	private static final String CONTENT_TYPE_HTML = "text/html;charset=UTF-8";
	/** xml类型 */
	private static final String CONTENT_TYPE_XML = "text/xml;charset=UTF-8";
	/** json类型 */
	private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
	/** 字符集 */
	private static final Charset Charset_UTF8 = Charset.forName("UTF-8");

	public static void responseJson(Object data)
	{
		String jsonStr = JsonUtil.toJSONString(data);
		responseJson(jsonStr);
	}

	public static void responseJson(String content)
	{
		responseText(content, CONTENT_TYPE_JSON);
	}

	public static void responsePlainText(String content)
	{
		responseText(content, CONTENT_TYPE_PLAIN);
	}

	public static void responseHtml(String content)
	{
		responseText(content, CONTENT_TYPE_HTML);
	}

	public static void responseXml(String content)
	{
		responseText(content, CONTENT_TYPE_XML);
	}

	/**
	 * 向客户端返回指定类型的内容
	 * 
	 * @param content
	 * @param contentType
	 *            响应类型
	 */
	public static void responseText(String content, String contentType)
	{
		HttpServletResponse rsp = SessionUtil.getResponse();
		rsp.setCharacterEncoding("UTF-8");
		rsp.setContentType(contentType);
		rsp.setHeader("Cache-Control", "no-cache");
		ServletOutputStream os = null;
		try
		{
			byte[] b = content.getBytes(Charset_UTF8);
			rsp.setContentLength(b.length);
			// rsp.setHeader("wq-real-length", String.valueOf(b.length));
			os = rsp.getOutputStream();
			os.write(b);
		}
		catch (IOException e)
		{
			logger.error("返回浏览器结果失败", e);
		}
		finally
		{
			IOUtils.closeQuietly(os);
		}
	}
}
