package com.ss.weixin.ap.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ss.platform.base.dao.BaseDao;
import com.ss.platform.exception.DaoException;
import com.ss.weixin.ap.pojo.WeixinUserLog;

@SuppressWarnings("unused")
@Repository
public class WeixinUserLogDao extends BaseDao<WeixinUserLog>
{
	/**
	 * 保存微信用户日志
	 * 
	 * @param entity
	 */
	public WeixinUserLog insertSelective(WeixinUserLog entity)
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

}
