package cn.itcast.ssm.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.xml.sax.helpers.DefaultHandler;

import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.utils.Page;
import cn.itcast.utils.ReflectHelper;


@Intercepts(@Signature(type=StatementHandler.class,method="prepare",args={Connection.class}))
public class MyPageInterceptor implements Interceptor{
	private String dialect;
	private String pageSqlId;
    
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			System.out.println("RoutingStatementHandler");
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			StatementHandler delegate = (StatementHandler) ReflectHelper.getFieldValue(statementHandler, "delegate");
			BoundSql boundSql=  delegate.getBoundSql();
			Object obj = boundSql.getParameterObject();
			if(obj instanceof Page){
				Page page = (Page) obj;
				MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getFieldValue(delegate, "mappedStatement");
//				mappedStatement.getId().matches("");配置符合分页id
				Connection connection =  (Connection) invocation.getArgs()[0];
				String sql = boundSql.getSql();
				this.setTotalRecord(page,connection,mappedStatement);
				String pageSql = this.getPageSql(page,sql);
				ReflectHelper.setFieldValue(boundSql, "sql", pageSql);
			}
			System.out.println(boundSql);
			System.out.println(obj);
		}
		return invocation.proceed();
	}
	
	private String getPageSql(Page page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		int offset = 0;
		int pageCount = page.getTotalRecord()/page.getRows();
		pageCount = page.getTotalRecord()%page.getRows()==0?pageCount:pageCount+1;
		if(page.getPage()>=1)
			offset = (page.getPage() - 1) * page.getRows();  
		if(page.getPage()>pageCount)
			offset = (pageCount==0?0:(pageCount-1)) * page.getRows();  
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getRows());  
		return sqlBuffer.toString(); 
	}

	private void setTotalRecord(Page page, Connection connection,MappedStatement mappedStatement) {
		// TODO Auto-generated method stub
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), page);
		DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, page, totalBoundSql);
		try {
			PreparedStatement  statement = connection.prepareStatement(countSql);
			handler.setParameters(statement);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				int totalRecord = resultSet.getInt(1);
				page.setTotalRecord(totalRecord);
				System.out.println(totalRecord);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}

	private String getCountSql(String sql) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(1) from (");
		buffer.append(sql);
		buffer.append(") as totalrecord");
		return buffer.toString();
	}

	public Object plugin(Object target) {
		if(target instanceof StatementHandler){
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties properties) {
		
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getPageSqlId() {
		return pageSqlId;
	}

	public void setPageSqlId(String pageSqlId) {
		this.pageSqlId = pageSqlId;
	}

}
