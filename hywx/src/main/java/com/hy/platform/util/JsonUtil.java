package com.hy.platform.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

public final class JsonUtil
{
	public static final String CODE_SUCCESS = "1";
	public static final String CODE_FAIL = "0";
	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

	public static String jsonMsgStr(String message)
	{
		return toJSONString(jsonMsg(message));
	}

	public static String jsonErrorStr(String message)
	{
		return toJSONString(jsonError(message));
	}

	public static Map<String, Object> jsonMsg(String message)
	{
		return jsonMsg(CODE_SUCCESS, message);
	}

	public static Map<String, Object> jsonError(String message)
	{
		return jsonMsg(CODE_FAIL, message);
	}

	public static Map<String, Object> jsonMsg(String code, Object message)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message);
		return map;
	}

	public static final String toJSONString(Object object)
	{
		return toJSONString(object, DATE_FORMAT_FULL);
	}

	public static final String toJSONString(Object object, String dateFormat)
	{
		SerializeWriter out = new SerializeWriter();
		try
		{
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
			// 日期
			serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
			if (dateFormat != null)
			{
				serializer.setDateFormat(dateFormat);
			}
			// 将null显示为空 不丢弃
			serializer.getValueFilters().add(new ValueFilter()
			{
				public Object process(Object obj, String s, Object value)
				{
					return value != null ? value : "";
				}
			});

			serializer.write(object);

			return out.toString();
		}
		finally
		{
			out.close();
		}
	}
}
