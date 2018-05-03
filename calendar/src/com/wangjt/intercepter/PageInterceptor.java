package com.wangjt.intercepter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.wangjt.calendar.util.StringUtile;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class PageInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {

		if (null != PageContextHolder.getPageInfo()) {

			MappedStatement mappedStatement = (MappedStatement) invocation
					.getArgs()[0];
			Object parameter = invocation.getArgs()[1];
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			String originalSql = boundSql.getSql().trim();
			Object parameterObject = boundSql.getParameterObject();

			if ("true".equals(PageContextHolder.getPageInfo().getRefreshAll())) {

				int totpage = 0;
				Connection connection = null;
				PreparedStatement countStmt = null;
				ResultSet rs = null;

				// 计算总的数据量
				try {

					String countSql = getCountSql(originalSql);

					connection = mappedStatement.getConfiguration()
							.getEnvironment().getDataSource().getConnection();

					countStmt = connection.prepareStatement(countSql);

					BoundSql countBS = copyFromBoundSql(mappedStatement,
							boundSql, countSql);

					DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
							mappedStatement, parameterObject, countBS);

					parameterHandler.setParameters(countStmt);

					rs = countStmt.executeQuery();

					if (rs.next()) {
						totpage = rs.getInt(1);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != rs) {
						try {
							rs.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (null != countStmt) {
						try {
							countStmt.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (null != connection) {
						try {
							connection.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				// 保存总数据量
				PageContextHolder.getPageInfo().setTotalRows(totpage + "");
			}

			// 计算所选页面的数据
			if (!StringUtile.isNull(PageContextHolder.getPageInfo().getPageNo())
					&& !StringUtile.isNull(PageContextHolder.getPageInfo()
							.getPageSize())) {
				BoundSql newBoundSql = copyFromBoundSql(mappedStatement,
						boundSql, getPageSql(originalSql));
				MappedStatement newMs = copyFromMappedStatement(
						mappedStatement, new BoundSqlSqlSource(newBoundSql));
				invocation.getArgs()[0] = newMs;
			}

		}
		return invocation.proceed();
	}

	/**
	 * 复制MappedStatement对象
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource) {

		Builder builder = new Builder(ms.getConfiguration(), ms.getId(),
				newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(ms.getKeyProperty());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	/**
	 * 复制BoundSql对象
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
			String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
				boundSql.getParameterMappings(), boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop,
						boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	private String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	private String getPageSql(String sql) {

		int startRow = (Integer.parseInt(PageContextHolder.getPageInfo()
				.getPageNo()) - 1)
				* Integer.parseInt(PageContextHolder.getPageInfo()
						.getPageSize());

//		int endRow = Integer.parseInt(PageContextHolder.getPageInfo()
//				.getPageNo())
//				* Integer.parseInt(PageContextHolder.getPageInfo()
//						.getPageSize());

		return sql + " limit " + startRow + "," +PageContextHolder.getPageInfo().getPageSize()+ ""; 
//		oracle
//		return "select * from (select pageRow.* ,rownum as r_num from (" + sql
//				+ ") pageRow )  where r_num > " + startRow + " and r_num <= "
//				+ endRow;
	}

	public class BoundSqlSqlSource implements SqlSource {

		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties arg0) {
	}
}