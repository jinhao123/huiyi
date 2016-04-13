package com.ss.weixin.ap.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ss.platform.base.dao.BaseDao;
import com.ss.platform.exception.DaoException;
import com.ss.weixin.ap.pojo.WeixinGzh;

@SuppressWarnings("unused")
@Repository
public class WeixinGzhDao extends BaseDao<WeixinGzh>
{
	/**
	 * 保存
	 * 
	 * @param entity
	 */
	public WeixinGzh insertSelective(WeixinGzh entity)
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
	public Integer updateByPrimaryKeySelective(WeixinGzh entity)
	{
		Assert.notNull(entity);
		try
		{
			entity.setModifyTime(new Date());
			// weixinUser.setStatus("1");
			String sql = getSqlName("updateByPrimaryKeySelective");
			Integer count = sqlSessionTemplate.update(sql, entity);
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
	public Integer deleteByOpenId(String openid)
	{
		Assert.notNull(openid);
		try
		{
			String sql = getSqlName("deleteByUserOpenId");
			Integer count = sqlSessionTemplate.delete(sql, openid);
			return count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("按用户openId删除！语句：%s", getSqlName("deleteByUserOpenId")), e);
		}
	}

	/**
	 * 通过openId获取公众号信息
	 * 
	 * @param gzhOpenId
	 * @return
	 */
	public WeixinGzh getGzhByOpenId(String gzhOpenId)
	{
		try
		{
			String sql = getSqlName("getGzhByOpenId");
			WeixinGzh gzh = sqlSessionTemplate.selectOne(sql, gzhOpenId);
			return gzh;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format("通过openId获取公众号信息出错！语句：%s", getSqlName("getGzhByOpenId")), e);
		}
	}

	/**
	 * 通过id获取公众号
	 * 
	 * @param gzhId
	 * @return
	 */
	public WeixinGzh getById(Long id)
	{
		try
		{
			String sql = getSqlName("selectByPrimaryKey");
			WeixinGzh gzh = sqlSessionTemplate.selectOne(sql, id);
			return gzh;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DaoException(String.format(" 通过id获取公众号出错！语句：%s", getSqlName("selectByPrimaryKey")), e);
		}
	}
}
