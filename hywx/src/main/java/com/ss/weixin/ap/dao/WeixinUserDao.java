package com.ss.weixin.ap.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ss.platform.base.dao.BaseDao;
import com.ss.platform.exception.DaoException;
import com.ss.weixin.ap.pojo.WeixinUser;

@SuppressWarnings("unused")
@Repository
public class WeixinUserDao extends BaseDao<WeixinUser>
{
	/**
	 * 保存关系对象
	 * 
	 * @param entity
	 */
	public WeixinUser insertSelective(WeixinUser entity)
	{
		Assert.notNull(entity);
		entity.setStatus("1");
		entity.setCreateTime(new Date());
		try
		{
			if (entity.getId() == null)
			{
				entity.setId(generateId());
			}
			String sql = getSqlName("insertSelective");
			sqlSessionTemplate.insert(sql, entity);
			return entity;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("添加对象出错！语句：%s", getSqlName("insertSelective")), e);
		}
	}

	/**
	 * 按id更新
	 * 
	 * @param weixinUser
	 * @return
	 */
	public Integer updateByPrimaryKeySelective(WeixinUser weixinUser)
	{
		Assert.notNull(weixinUser);
		try
		{
			weixinUser.setModifyTime(new Date());
			// weixinUser.setStatus("1");
			String sql = getSqlName("updateByPrimaryKeySelective");
			Integer count = sqlSessionTemplate.update(sql, weixinUser);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("更新对象出错！语句：%s", getSqlName("updateByPrimaryKeySelective")), e);
		}
	}

	/**
	 * 按主键删除
	 * 
	 * @param regNo
	 * @return
	 */
	public int deleteById(Long id)
	{
		try
		{
			String sql = getSqlName("deleteByPrimaryKey");
			int i = sqlSessionTemplate.delete(sql, id);
			return i;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("按主键删除对象出错！语句：%s", getSqlName("deleteByPrimaryKey")), e);
		}
	}

	/**
	 * 按用户openId删除
	 * 
	 * @param fromUserName
	 * @return
	 */
	public Integer deleteByUserOpenIdAndGzhOpenId(String userOpenid, String gzhOpenId)
	{
		Assert.notNull(userOpenid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userOpenid", userOpenid);
		map.put("gzhOpenId", gzhOpenId);
		try
		{
			String sql = getSqlName("deleteByUserOpenIdAndGzhOpenId");
			Integer count = sqlSessionTemplate.delete(sql, map);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("按用户openId和公众号openId删除！语句：%s",
					getSqlName("deleteByUserOpenIdAndGzhOpenId")), e);
		}

	}

	/**
	 * 按用户openId删除
	 * 
	 * @param fromUserName
	 * @return
	 */
	public Integer countByUserOpenIdAndGzhOpenId(String userOpenid, String gzhOpenId)
	{
		Assert.notNull(userOpenid);
		Assert.notNull(gzhOpenId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userOpenid", userOpenid);
		map.put("gzhOpenId", gzhOpenId);
		try
		{
			String sql = getSqlName("countByUserOpenIdAndGzhOpenId");
			Integer count = sqlSessionTemplate.selectOne(sql, map);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("按用户openId和公众号openId查询数量！语句：%s", getSqlName("deleteByUserOpenId")), e);
		}

	}

	/**
	 * 通过userOpenId和 gzhOpenId更新新关注的用户
	 * 
	 * @param userOpenId
	 * @param gzhOpenId
	 */
	public int updateByOpenIdSelective(WeixinUser weixinUser)
	{

		Assert.notNull(weixinUser);
		try
		{
			weixinUser.setModifyTime(new Date());
			// weixinUser.setStatus("1");
			String sql = getSqlName("updateByOpenIdSelective");
			Integer count = sqlSessionTemplate.update(sql, weixinUser);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("更新对象出错！语句：%s", getSqlName("updateByOpenIdSelective")), e);
		}

	}

	/**
	 * 通过userOpenId和 gzhOpenId查询用户
	 * 
	 * @param userOpenId
	 * @param gzhOpenId
	 */
	public WeixinUser getByUserOpenIdAndGzhOpenId(String userOpenId, String gzhOpenId)
	{
		WeixinUser weixinUser = new WeixinUser();
		weixinUser.setOpenid(userOpenId);
		weixinUser.setGzhOpenid(gzhOpenId);
		try
		{
			String sql = getSqlName("getByUserOpenIdAndGzhOpenId");
			weixinUser = sqlSessionTemplate.selectOne(sql, weixinUser);
			return weixinUser;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("通过userOpenId和 gzhOpenId查询用户出错！语句：%s",
					getSqlName("getByUserOpenIdAndGzhOpenId")), e);
		}
	}

	/**
	 * 通过主键更新
	 * 
	 * @param weixinUser
	 * @return
	 */
	public int updateByPrimaryIdSelective(WeixinUser weixinUser)
	{
		Assert.notNull(weixinUser);
		try
		{
			weixinUser.setModifyTime(new Date());
			// weixinUser.setStatus("1");
			String sql = getSqlName("updateByPrimaryKeySelective");
			Integer count = sqlSessionTemplate.update(sql, weixinUser);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("更新对象出错！语句：%s", getSqlName("updateByPrimaryKeySelective")), e);
		}
	}

	/**
	 * 通过用户手机号获取用户列表
	 * 
	 * @param mobile
	 * @return
	 */
	public List<WeixinUser> getByUserMobile(String mobile)
	{
		WeixinUser weixinUser = new WeixinUser();
		weixinUser.setMobile(mobile);
		List<WeixinUser> list = null;
		try
		{
			String sql = getSqlName("getByUserMobile");
			list = sqlSessionTemplate.selectList(sql, weixinUser);
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format(" 通过用户手机号获取用户列表出错！语句：%s", getSqlName("getByUserMobile")), e);
		}
	}
}
