package com.hy.platform.base.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.hy.platform.base.vo.MbsTenant;
import com.hy.platform.util.PropertiesUtil;
import com.hy.platform.util.SessionUtil;

public class DynamicDataSource extends DelegatingDataSource
{
	protected static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	/** 企业数据源缓存 */
	private static final Map<Long, DataSource> tenantDataSources = new ConcurrentHashMap<Long, DataSource>();
	/** 用于存放当前线程的企业信息 适用于没有request对象的后台程序切换数据源 */
	public static final ThreadLocal<Long> currentTenantId = new ThreadLocal<Long>();
	public static Long CIS_DB = 901L;
	public static Long MASTER_DB = 902L;

	@Override
	public void afterPropertiesSet()
	{
		// do nothing
	}

	public static void setCurTenant(Long tenantId)
	{
		currentTenantId.set(tenantId);
	}

	public DataSource getTargetDataSource()
	{
		DataSource ds = null;
		// 获取企业ID
		Long tenantId = (Long) SessionUtil.getAttribute(SessionUtil.TENANT_ID);
		if (tenantId == null)
		{
			tenantId = currentTenantId.get();
		}
		// 默认用本地库
		if (tenantId == null)
		{
			tenantId = CIS_DB;
		}
		logger.debug("切数据源,{},{}", Thread.currentThread().getId(), tenantId);
		// 根据企业ID取得数据源
		ds = tenantDataSources.get(tenantId);
		if (ds == null)
		{
			logger.info("动态数据源缓存中未查到数据源,企业{}", tenantId);
			ds = createDataSource(tenantId);
			if (ds != null)
			{
				logger.info("新建数据源,企业{}", tenantId);
				tenantDataSources.put(tenantId, ds);
			}
			else
			{
				logger.warn("创建数据源失败!{}", tenantId);
			}
		}
		return ds;
	}

	private static DataSource createDataSource(Long dbId)
	{

		DataSource ds = null;
		if (CIS_DB.equals(dbId))
		{
			ds = createCisDataSource();
		}
		else if (MASTER_DB.equals(dbId))
		{
			ds = createMasterDataSource();
		}
		else
		{
			// MbsTenantService mbsTenantService =
			// SpringUtil.getBean(MbsTenantService.class);
			// MbsTenant tenant = mbsTenantService.findOneTenat(dbId);
			// setCurTenant(dbId);
			// if (tenant != null)
			// {
			// ds = createTenantDataSource(tenant);
			// }
			// else
			// {
			// logger.error("未查到企业信息:{}", dbId);
			// }
		}
		return ds;
	}

	public static DataSource createTenantDataSource(MbsTenant tenant)
	{
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		ds.setUrl(String.format("jdbc:postgresql://%s/%s", tenant.getDbUrl(), tenant.getDbUser()));
		ds.setUsername(tenant.getDbUser());
		ds.setPassword(tenant.getDbPwd());
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setConnectionProperties("useUnicode=true;characterEncoding=UTF-8");
		ds.setMaxActive(10);
		ds.setInitialSize(1);
		ds.setMaxIdle(10);
		ds.setMinIdle(0);
		return ds;
	}

	public static DataSource createMasterDataSource()
	{
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		String propFile = PropertiesUtil.FILE_JDBC;
		ds.setUrl(PropertiesUtil.getProp(propFile, "master.url"));
		ds.setUsername(PropertiesUtil.getProp(propFile, "master.username"));
		ds.setPassword(PropertiesUtil.getProp(propFile, "master.password"));
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setConnectionProperties("useUnicode=true;characterEncoding=UTF-8");
		ds.setMaxActive(PropertiesUtil.getIntProp(propFile, "master.maxActive"));
		ds.setInitialSize(1);
		ds.setMaxIdle(PropertiesUtil.getIntProp(propFile, "master.maxIdle"));
		ds.setMinIdle(0);
		return ds;
	}

	public static DataSource createCisDataSource()
	{
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		String propFile = PropertiesUtil.FILE_JDBC;
		ds.setUrl(PropertiesUtil.getProp(propFile, "cis.url"));
		ds.setUsername(PropertiesUtil.getProp(propFile, "cis.username"));
		ds.setPassword(PropertiesUtil.getProp(propFile, "cis.password"));
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setConnectionProperties("useUnicode=true;characterEncoding=UTF-8");
		ds.setMaxActive(PropertiesUtil.getIntProp(propFile, "cis.maxActive"));
		ds.setInitialSize(1);
		ds.setMaxIdle(PropertiesUtil.getIntProp(propFile, "cis.maxIdle"));
		ds.setMinIdle(0);
		return ds;
	}

	private static DataSource creatDbcpMasterDataSource()
	{
		BasicDataSource ds = new BasicDataSource();
		String propFile = PropertiesUtil.FILE_JDBC;
		ds.setDriverClassName(PropertiesUtil.getProp(propFile, "master.driver"));
		ds.setUrl(PropertiesUtil.getProp(propFile, "master.url"));
		ds.setUsername(PropertiesUtil.getProp(propFile, "master.username"));
		ds.setPassword(PropertiesUtil.getProp(propFile, "master.password"));
		ds.setMaxActive(Integer.parseInt(PropertiesUtil.getProp(propFile, "master.maxIdle")));
		ds.setMinIdle(Integer.parseInt(PropertiesUtil.getProp(propFile, "master.minIdle")));
		ds.setTimeBetweenEvictionRunsMillis(1000 * 30);
		ds.setMinEvictableIdleTimeMillis(1000 * 60 * 10);
		return ds;
	}

	private static DataSource createDbcpCisDataSource()
	{
		BasicDataSource ds = new BasicDataSource();
		String propFile = PropertiesUtil.FILE_JDBC;
		ds.setDriverClassName(PropertiesUtil.getProp(propFile, "cis.driver"));
		ds.setUrl(PropertiesUtil.getProp(propFile, "cis.url"));
		ds.setUsername(PropertiesUtil.getProp(propFile, "cis.username"));
		ds.setPassword(PropertiesUtil.getProp(propFile, "cis.password"));
		ds.setMaxActive(Integer.parseInt(PropertiesUtil.getProp(propFile, "cis.maxIdle")));
		ds.setMinIdle(Integer.parseInt(PropertiesUtil.getProp(propFile, "cis.minIdle")));
		ds.setTimeBetweenEvictionRunsMillis(1000 * 30);
		ds.setMinEvictableIdleTimeMillis(1000 * 60 * 10);
		return ds;
	}

	private static DataSource createDbcpTenantDataSource(MbsTenant tenant)
	{
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl(String.format("jdbc:postgresql://%s/%s", tenant.getDbUrl(), tenant.getDbUser()));
		ds.setConnectionProperties("useUnicode=true;characterEncoding=UTF-8");
		ds.setUsername(tenant.getDbUser());
		ds.setPassword(tenant.getDbPwd());
		ds.setMaxActive(10);
		ds.setMinIdle(1);
		ds.setMinEvictableIdleTimeMillis(1000 * 30);
		return ds;
	}

	/***
	 * 创建不带池的数据源
	 * 
	 * @param tenant
	 * @return
	 * @deprecated
	 */
	@Deprecated
	private static DataSource createDriverManagerDataSource(MbsTenant tenant)
	{
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl(String.format("jdbc:postgresql://%s/%s", tenant.getDbUrl(), tenant.getDbUser()));
		ds.setUsername(tenant.getDbUser());
		ds.setPassword(tenant.getDbPwd());
		return ds;
	}

}
