package com.ss.weixin.ap.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ss.platform.util.JsonUtil;
import com.ss.weixin.ap.dao.WeixinUserDao;
import com.ss.weixin.ap.dao.WeixinUserLogDao;
import com.ss.weixin.ap.pojo.WeixinGzh;
import com.ss.weixin.ap.pojo.WeixinUser;
import com.ss.weixin.ap.pojo.WeixinUserLog;

@Service
public class WeixinUserService
{
	private static final Logger logger = LoggerFactory.getLogger(WeixinUserService.class);

	@Autowired
	private WeixinUserDao weixinUserDao;

	@Autowired
	private WeixinUserLogDao weixinUserLogDao;

	@Autowired
	private WeixinGzhService weixinGzhService;


	/**
	 * 记录关注的用户
	 * 
	 * @param keyword
	 * @return
	 */
	public String recordSubscribedUser(String userOpenId, String gzhOpenId)
	{
		WeixinUser wu = new WeixinUser();
		WeixinUserLog wul = new WeixinUserLog();
		String resp;
		// 按格式处理返回数据
		Map<String, Object> jobj = null;
		try
		{
			WeixinGzh wxGzh = weixinGzhService.getGzhByOpenId(gzhOpenId);
			// 查找下是否已经存在，防止重复插入
			wu.setOpenid(userOpenId);
			wu.setKeyid("keyid");
			wu.setGzhOpenid(gzhOpenId);
			wu.setGzhId(wxGzh.getId());
			// 不存在才插入,否则直接返回插入成功
			Integer count = weixinUserDao.countByUserOpenIdAndGzhOpenId(userOpenId, gzhOpenId);
			WeixinUser user = null;
			if (count == null || count == 0)
			{
				user = weixinUserDao.insertSelective(wu);
			}
			else
			{
				jobj = JsonUtil.jsonMsg(JsonUtil.CODE_SUCCESS, "关注成功");
				resp = JSON.toJSONString(jobj);
				return resp;
			}

			wul.setGzhId(wxGzh.getId());
			wul.setWeixinId(user.getId());
			wul.setOpenid(userOpenId);
			wul.setOpType(WeixinUserLog.OPTYPE_SUBSCRIBE);
			weixinUserLogDao.insertSelective(wul);
			jobj = JsonUtil.jsonMsg(JsonUtil.CODE_SUCCESS, "关注成功");
		}
		catch (Exception e)
		{
			jobj = JsonUtil.jsonMsg(JsonUtil.CODE_FAIL, "查询出错");
			e.printStackTrace();
		}
		resp = JSON.toJSONString(jobj);
		return resp;
	}

	/**
	 * 记录取消关注的用户
	 * 
	 * @param keyword
	 * @return
	 */
	public String recordDeSubscribedUser(String userOpenId, String gzhOpenId)
	{
		WeixinUserLog wul = new WeixinUserLog();
		String resp;
		// 按格式处理返回数据
		Map<String, Object> jobj = null;
		try
		{
			WeixinUser user = weixinUserDao.getByUserOpenIdAndGzhOpenId(userOpenId, gzhOpenId);
			if (user == null)
			{
				jobj = JsonUtil.jsonMsg(JsonUtil.CODE_SUCCESS, "取消关注成功");
				return JSON.toJSONString(jobj);
			}
			// 删除已有的关注
			weixinUserDao.deleteByUserOpenIdAndGzhOpenId(userOpenId, gzhOpenId);
			// TODO 已绑定联系人的 删除绑定关系
			// 插入取消关注日志
			wul.setOpenid(userOpenId);
			wul.setWeixinId(user.getId());
			wul.setOpType(WeixinUserLog.OPTYPE_DESUBSCRIBE);
			weixinUserLogDao.insertSelective(wul);
			jobj = JsonUtil.jsonMsg(JsonUtil.CODE_SUCCESS, "取消关注成功");
		}
		catch (Exception e)
		{
			jobj = JsonUtil.jsonMsg(JsonUtil.CODE_FAIL, "取消关注出错");
			e.printStackTrace();
		}
		resp = JSON.toJSONString(jobj);
		return resp;
	}

	/**
	 * 根据公众号openId和用户openId
	 * 
	 * @param gzhOpenId
	 * @param userOpenId
	 * @return
	 */
	public WeixinUser getByOpenId(String gzhOpenId, String userOpenId)
	{
		WeixinUser user = null;
		try
		{
			// 删除已有的关注
			user = weixinUserDao.getByUserOpenIdAndGzhOpenId(userOpenId, gzhOpenId);
		}
		catch (Exception e)
		{
			logger.error("根据公众号openId和用户openId出错", e);
		}
		return user;

	}

}
