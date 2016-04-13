package com.ss.platform.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ss.platform.base.pojo.IUser;
import com.ss.platform.util.SessionUtil;

/***
 * 暂不用
 * 
 * @author jinhao
 * 
 */
@Deprecated
public class SessionUserListener implements HttpSessionListener
{
	private static final Logger logger = LoggerFactory.getLogger(SessionUserListener.class);
	private static Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();

	public void sessionCreated(HttpSessionEvent se)
	{
		logger.info("sessionCreated:{}", se.getSession().getId());
		HttpSession session = se.getSession();
		sessionMap.put(session.getId(), session);
	}

	public void sessionDestroyed(HttpSessionEvent se)
	{
		logger.info("sessionDestroyed:{}", se.getSession().getId());
		sessionMap.remove(se.getSession().getId());
	}

	/**
	 * 得到在线用户会话集合
	 */
	public static List<HttpSession> getUserSessions()
	{
		List<HttpSession> list = new ArrayList<HttpSession>();
		Iterator<String> iterator = getSessionMapKeySetIt();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			HttpSession session = getSessionMap().get(key);
			list.add(session);
		}
		return list;
	}

	/**
	 * 得到用户对应会话map，key为用户ID,value为会话ID
	 */
	public static Map<Long, String> getUserSessionMap()
	{
		Map<Long, String> map = new HashMap<Long, String>();
		Iterator<String> iter = getSessionMapKeySetIt();
		while (iter.hasNext())
		{
			String sessionId = iter.next();
			HttpSession session = getSessionMap().get(sessionId);
			IUser user = (IUser) session.getAttribute(SessionUtil.USER_INFO);
			if (user != null)
			{
				map.put(user.getId(), sessionId);
			}
		}
		return map;
	}

	/**
	 * 移除用户Session
	 */
	public synchronized static void removeUserSession(String userId)
	{
		Map<Long, String> userSessionMap = getUserSessionMap();
		if (userSessionMap.containsKey(userId))
		{
			String sessionId = userSessionMap.get(userId);
			getSessionMap().get(sessionId).invalidate();
			getSessionMap().remove(sessionId);
		}
	}

	/**
	 * 增加用户到session集合中
	 */
	public static void addUserSession(HttpSession session)
	{
		getSessionMap().put(session.getId(), session);
	}

	/**
	 * 移除一个session
	 */
	public static void removeSession(String sessionID)
	{
		getSessionMap().remove(sessionID);
	}

	public static boolean containsKey(String key)
	{
		return getSessionMap().containsKey(key);
	}

	/**
	 * 判断该用户是否已重复登录，使用 同步方法，只允许一个线程进入，才好验证是否重复登录
	 * 
	 * @param user
	 * @return
	 */
	public synchronized static boolean checkIfHasLogin(IUser user)
	{
		Iterator<String> iter = getSessionMapKeySetIt();
		while (iter.hasNext())
		{
			String sessionId = iter.next();
			HttpSession session = getSessionMap().get(sessionId);
			IUser sessionuser = (IUser) session.getAttribute(SessionUtil.USER_INFO);
			if (sessionuser != null)
			{
				if (sessionuser.getId().equals(user.getId()))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取在线的sessionMap
	 */
	public static Map<String, HttpSession> getSessionMap()
	{
		return sessionMap;
	}

	/**
	 * 获取在线sessionMap中的SessionId
	 */
	public static Iterator<String> getSessionMapKeySetIt()
	{
		return getSessionMap().keySet().iterator();
	}

}
