package com.hy.platform.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.hy.platform.base.dao.BaseDao;
import com.hy.platform.base.pojo.Identifiable;
import com.hy.platform.base.vo.PageData;
import com.hy.platform.base.vo.QueryInfo;

public abstract class BaseService<T extends Identifiable>
{

	/**
	 * 获取基础数据库操作类
	 * 
	 * @return
	 */
	protected BaseDao<T> getBaseDao()
	{
		return null;
	};

	public T queryOne(QueryInfo query)
	{
		return getBaseDao().selectOne(query);
	}

	public T queryById(Long id)
	{
		return getBaseDao().selectById(id);
	}

	public <V extends T> List<V> queryList(QueryInfo query)
	{
		return getBaseDao().selectList(query);
	}

	public <K, V extends T> Map<K, V> queryMap(QueryInfo query, String mapKey)
	{
		return getBaseDao().selectMap(query, mapKey);
	}

	public int queryCount()
	{
		return getBaseDao().selectCount();
	}

	public int queryCount(QueryInfo query)
	{
		return getBaseDao().selectCount(query);
	}

	public PageData queryPageList(QueryInfo query)
	{
		return getBaseDao().selectPageList(query);
	}

	public void insert(T entity)
	{
		getBaseDao().insert(entity);
	}

	public int delete(QueryInfo query)
	{
		return getBaseDao().delete(query);
	}

	public int deleteById(Long id)
	{
		return getBaseDao().deleteById(id);
	}

	public int deleteAll()
	{
		return getBaseDao().deleteAll();
	}

	public int updateById(T entity)
	{
		return getBaseDao().updateById(entity);
	}

	public int updateByIdSelective(T entity)
	{
		return getBaseDao().updateByIdSelective(entity);
	}

	@Transactional
	public void deleteByIdInBatch(List<Long> idList)
	{
		getBaseDao().deleteByIdInBatch(idList);
	}

	@Transactional
	public void insertInBatch(List<T> entityList)
	{
		getBaseDao().insertInBatch(entityList);
	}

	@Transactional
	public void updateInBatch(List<T> entityList)
	{
		getBaseDao().updateInBatch(entityList);
	}

}
