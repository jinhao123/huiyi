package com.ss.platform.base.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ss.platform.base.consts.SqlId;
import com.ss.platform.base.pojo.Identifiable;
import com.ss.platform.base.vo.PageData;
import com.ss.platform.base.vo.QueryInfo;
import com.ss.platform.exception.DaoException;
import com.ss.platform.util.BeanUtils;
import com.ss.platform.util.StringUtil;

/**
 * 基础Dao接口实现类，实现改类的子类必须设置泛型类型
 */
public abstract class BaseDao<T extends Identifiable>
{
	@Autowired(required = true)
	protected SqlSession sqlSessionTemplate;

	public static final String SQLNAME_SEPARATOR = ".";

	/**
	 * @fields sqlNamespace SqlMapping命名空间 默认为T的全类名
	 */
	private String sqlNamespace = getDefaultSqlNamespace();

	/**
	 * 获取泛型类型的实体对象类全名
	 * 
	 * @return
	 */
	private String getDefaultSqlNamespace()
	{
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}

	/**
	 * 获取SqlMapping命名空间
	 * 
	 */
	public String getSqlNamespace()
	{
		return sqlNamespace;
	}

	/**
	 * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
	 * 
	 */
	public void setSqlNamespace(String sqlNamespace)
	{
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName)
	{
		return getSqlNamespace() + SQLNAME_SEPARATOR + sqlName;
	}

	/**
	 * 生成主键值。
	 * 
	 * @param entity
	 *            要持久化的对象
	 */
	protected long generateId()
	{
		return StringUtil.getUUID2Long();
	}

	public T selectOne(QueryInfo query)
	{
		Assert.notNull(query);
		try
		{
			return (T) sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT), QueryInfo.toSqlParams(query));
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("查询一条记录出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	public T selectById(Long id)
	{
		Assert.notNull(id);
		try
		{
			return (T) sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_BY_ID), id);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("根据ID查询对象出错！语句：%s", getSqlName(SqlId.SQL_SELECT_BY_ID)), e);
		}
	}

	public <K, V extends T> Map<K, V> selectMap(QueryInfo query, String mapKey)
	{
		Assert.notNull(mapKey, "[mapKey] - must not be null!");
		try
		{
			return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), QueryInfo.toSqlParams(query), mapKey);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("查询对象Map时出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	public <V extends T> List<V> selectList(QueryInfo query)
	{
		try
		{
			Map<String, Object> params = QueryInfo.toSqlParams(query);
			return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), params);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	public PageData selectPageList(QueryInfo query)
	{
		try
		{
			PageData page = new PageData();
			int total = this.selectCount(query);
			page.setTotal(total);
			if (total > 0)
			{
				Map<String, Object> qInfo = QueryInfo.toSqlParams(query);
				List contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), qInfo);
				page.setRows(contentList);
			}
			else
			{
				page.setRows(new ArrayList<T>());
			}
			return page;
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	public int selectCount()
	{
		try
		{
			return (Integer) sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT));
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
		}
	}

	public int selectCount(QueryInfo query)
	{
		try
		{
			Map<String, Object> params = QueryInfo.toSqlParams(query);
			return (Integer) sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
		}
	}

	public void insert(T entity)
	{
		Assert.notNull(entity);
		try
		{
			if (entity.getId() == null)
			{
				entity.setId(generateId());
			}
			sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT), entity);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("添加对象出错！语句：%s", getSqlName(SqlId.SQL_INSERT)), e);
		}
	}

	public int delete(QueryInfo query)
	{
		Assert.notNull(query);
		try
		{
			return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE), QueryInfo.toSqlParams(query));
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("删除对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE)), e);
		}
	}

	public int deleteById(Long id)
	{
		Assert.notNull(id);
		try
		{
			return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE_BY_ID), id);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("根据ID删除对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE_BY_ID)), e);
		}
	}

	public int deleteAll()
	{
		try
		{
			return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE));
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("删除所有对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE)), e);
		}
	}

	public int updateById(T entity)
	{
		Assert.notNull(entity);
		try
		{
			return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID), entity);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s", getSqlName(SqlId.SQL_UPDATE_BY_ID)), e);
		}
	}

	public int updateByIdSelective(T entity)
	{
		Assert.notNull(entity);
		try
		{
			return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE), entity);
		}
		catch (Exception e)
		{
			throw new DaoException(String.format("根据ID更新对象某些属性出错！语句：%s", getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE)),
					e);
		}
	}

	@Transactional
	public void deleteByIdInBatch(List<Long> idList)
	{
		if (idList == null || idList.isEmpty())
			return;
		for (Long id : idList)
		{
			this.deleteById(id);
		}
	}

	@Transactional
	public void updateInBatch(List<T> entityList)
	{
		if (entityList == null || entityList.isEmpty())
			return;
		for (T entity : entityList)
		{
			this.updateByIdSelective(entity);
		}
	}

	@Transactional
	public void insertInBatch(List<T> entityList)
	{
		if (entityList == null || entityList.isEmpty())
			return;
		for (T entity : entityList)
		{
			this.insert(entity);
		}
	}

}
