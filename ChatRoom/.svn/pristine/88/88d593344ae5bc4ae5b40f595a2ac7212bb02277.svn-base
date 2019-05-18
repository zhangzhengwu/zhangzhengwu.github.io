package com.orlando.mysql.superdao.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.orlando.mysql.superdao.SuperDao;
import com.orlando.mysql.superdao.util.DbUtil;

public class SuperDaoImpl<T> implements SuperDao<T> {
	  private DbUtil dbUtil;
	  private Class<T> clz;
	  private Properties properties;

	  public SuperDaoImpl(Class<T> clz) {
	    this(clz, new DbUtil());
	  }

	  public SuperDaoImpl(Class<T> clz, DbUtil dbUtil) {
	    this.clz = clz;
	    this.setDbUtil(dbUtil);
	    this.properties = new Properties();
	    try {
	      this.properties.load(clz.getResourceAsStream(clz.getSimpleName() + ".orm"));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  public void setDbUtil(DbUtil dbUtil) {
	    this.dbUtil = dbUtil;
	  }

	  public DbUtil getDbUtil() {
	    return this.dbUtil;
	  }

	  @Override
	  public boolean add(T entity) {
	    String pkCol = this.properties.getProperty("pkId");// 主键列
	    List<Object> params = new ArrayList<Object>();// 匿名参数
	    int idx = 0;// 匿名参数
	    try {
	      StringBuffer sb = new StringBuffer();
	      sb.append("insert into ");
	      sb.append(this.properties.getProperty(this.clz.getSimpleName()));
	      sb.append("(");
	      for (Field field : this.clz.getDeclaredFields()) {
	    	  if(!("serialVersionUID".equals(field.getName()))){
			        String fieldCol = this.properties.getProperty(field.getName());// 字段列
			        field.setAccessible(true);
			        if (!fieldCol.equals(pkCol)) {
			          sb.append(fieldCol);
			          sb.append(",");
			          idx++;
			          params.add(field.get(entity));
			        }
	    	  }      
	      }
	      sb.deleteCharAt(sb.length() - 1);
	      // sb.append("u_name,u_pwd,u_nick,u_img,u_email,u_phone,u_card_id,u_register_time");
	      sb.append(") values (");
	      for (int i = 0; i < idx; i++) {
	        sb.append("?,");
	      }
	      sb.deleteCharAt(sb.length() - 1);
	      sb.append(")");
	      sb.append(";");
	      if (this.dbUtil.doUpdate(sb.toString(), params.toArray()) > 0) {
	        return true;
	      }
	    } catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
	      e.printStackTrace();
	    }
	    return false;
	  }

	  @Override
	  public boolean remove(T entity) {
	    String pkCol = this.properties.getProperty("pkId");// 主键列
	    try {
	      for (Field field : this.clz.getDeclaredFields()) {
	    	  if(!("serialVersionUID".equals(field.getName()))){
			        String fieldCol = this.properties.getProperty(field.getName());// 字段列
			        field.setAccessible(true);
			        if (fieldCol.equals(pkCol)) {
			          return this.remove((Integer) field.get(entity));
			        }
	    	  } 
	      }
	    } catch (IllegalArgumentException | IllegalAccessException e) {
	      e.printStackTrace();
	    }
	    return false;
	  }

	  @Override
	  public boolean remove(int pkId) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("delete from ");
	    sb.append(this.properties.getProperty(this.clz.getSimpleName()));
	    sb.append(" where ");
	    sb.append(this.properties.getProperty("pkId"));
	    sb.append(" = ?");// 匿名参数的SQL语句
	    sb.append(";");

	    try {
	      if (this.dbUtil.doUpdate(sb.toString(), pkId) > 0) {
	        return true;
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return false;
	  }

	  @Override
	  public boolean merge(T entity) {
	    String pkCol = this.properties.getProperty("pkId");// 主键列
	    List<Object> params = new ArrayList<Object>();// 匿名参数
	    try {
	      StringBuffer sb = new StringBuffer();
	      sb.append("update ");
	      sb.append(this.properties.getProperty(this.clz.getSimpleName()));
	      sb.append(" set ");
	      for (Field field : this.clz.getDeclaredFields()) {
	    	if(!("serialVersionUID".equals(field.getName()))){
	    		String fieldCol = this.properties.getProperty(field.getName());// 字段列
	    		field.setAccessible(true);
	    		if (!fieldCol.equals(pkCol)) {
	    			sb.append(fieldCol);
	    			sb.append(" = ?,");
	    			params.add(field.get(entity));
	    		}
	    	}  
	      }
	      sb.deleteCharAt(sb.length() - 1);
	      sb.append(" where ");
	      sb.append(pkCol);
	      sb.append(" = ?");// 匿名参数的SQL语句
	      for (Field field : this.clz.getDeclaredFields()) {
	    	  if(!("serialVersionUID".equals(field.getName()))){
			        String fieldCol = this.properties.getProperty(field.getName());// 字段列
			        field.setAccessible(true);
			        if (fieldCol.equals(pkCol)) {
			          params.add(field.get(entity));
			        }
	    	  }
	      }
	      sb.append(";");

	      if (this.dbUtil.doUpdate(sb.toString(), params.toArray()) > 0) {
	        return true;
	      }
	    } catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
	      e.printStackTrace();
	    }
	    return false;
	  }

	  @Override
	  public T findById(int pkId) {
	    try {
	      StringBuffer sb = new StringBuffer();
	      sb.append("where ");
	      sb.append(this.properties.getProperty("pkId"));
	      sb.append(" = ?");
	      ResultSet rs = this.dbUtil.doQuery(this.buildFindSql(sb.toString()), pkId);
	      if (rs.next()) {
	        return this.buildObject(rs);
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  @Override
	  public List<T> findAll() {
	    try {
	      ResultSet rs = this.dbUtil.doQuery(this.buildFindSql(null));
	      return this.buildList(rs);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return null;
	  }

	  @Override
	  public List<T> findByExample(boolean isFuzzyQuery, T example) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("where 1 = 1");
	    // List<Object> params = new ArrayList<Object>();// 匿名参数
	    Map<String, Object> params = new HashMap<String, Object>();// 命名参数
	    String pkCol = this.properties.getProperty("pkId");// 主键列
	    try {
	      for (Field field : this.clz.getDeclaredFields()) {
	    	  if(!("serialVersionUID".equals(field.getName()))){
		        String fieldCol = this.properties.getProperty(field.getName());// 字段列
		        field.setAccessible(true);
		        if (!fieldCol.equals(pkCol) && field.get(example) != null) {
		          sb.append(" and ");
		          sb.append(fieldCol);
		          if (isFuzzyQuery) {
		            // 模糊查询
		            // sb.append(" like ?");// 匿名参数
		            // params.add("%" + field.get(example) + "%");// 匿名参数
		            sb.append(" like :");// 命名参数
		            sb.append(fieldCol);
		            params.put(fieldCol, "%" + field.get(example) + "%");// 命名参数
		          } else {
		            // 精确查询
		            // sb.append(" = ?");// 匿名参数
		            // params.add(field.get(example));// 匿名参数
		            sb.append(" = :");// 命名参数
		            sb.append(fieldCol);
		            params.put(fieldCol, field.get(example));
		          }
		        }
	    	  }
	      }
	      // return this.buildList(this.dbUtil.doQuery(this.buildFindSql(sb.toString()), params.toArray()));// 匿名参数
	      return this.buildList(this.dbUtil.doQuery(this.buildFindSql(sb.toString()), params));// 命名参数
	    } catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  @Override
	  public List<T> findBySQL(String sql, Object... params) {
	    try {
	      ResultSet rs = this.dbUtil.doQuery(sql, params);
	      return this.buildList(rs);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  @Override
	  public List<T> findBySQL(String sql, Map<String, Object> params) {
	    try {
	      ResultSet rs = this.dbUtil.doQuery(sql, params);
	      return this.buildList(rs);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  @Override
	  public T buildObject(ResultSet rs) throws SQLException {
	    // 将一行数据封装成一个实体对象
	    try {
	      T entity = this.clz.newInstance();
	      for (Field field : this.clz.getDeclaredFields()) {
	    	  if(!("serialVersionUID".equals(field.getName()))){
		        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
		          if (rs.getMetaData().getColumnName(i).equals(this.properties.getProperty(field.getName()))) {
		            field.setAccessible(true);
		            field.set(entity, rs.getObject(this.properties.getProperty(field.getName())));
		          }
		        }
	    	  }
	      }
	      return entity;
	    } catch (InstantiationException | IllegalAccessException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  @Override
	  public List<T> buildList(ResultSet rs) throws SQLException {
	    List<T> list = new ArrayList<T>();
	    rs.beforeFirst();
	    while (rs.next()) {// 读取一条记录
	      list.add(this.buildObject(rs));
	    }
	    return list;
	  }

	  private String buildFindSql(String condition) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("select ");
	    for (Field field : this.clz.getDeclaredFields()) {
	    	if(!("serialVersionUID".equals(field.getName()))){
	    		sb.append(this.properties.getProperty(field.getName()) + ",");
	    	}
	    }
	    sb.deleteCharAt(sb.length() - 1);
	    sb.append(" from ");
	    sb.append(this.properties.getProperty(this.clz.getSimpleName()));
	    if (condition != null) {
	      sb.append(" ");
	      sb.append(condition);
	    }
	    sb.append(";");
	    return sb.toString();
	  }

	@Override
	public void closeConnection() {
		this.dbUtil.close();
	}

}
