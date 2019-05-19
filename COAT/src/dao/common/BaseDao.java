package dao.common;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.DBManager;
import util.DateUtils;
import util.Help;
import util.Pager;
import util.Util;



public abstract class BaseDao{
	Logger logger=Logger.getLogger(BaseDao.class);
	Connection con = null;  
	PreparedStatement ps = null;  
	ResultSet rs = null;  
	public BaseDao() {
		try{
			openConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openConnection() throws SQLException, ClassNotFoundException{
		if(Util.objIsNULL(con) || con.isClosed()){
			con=DBManager.getCon();
			logger.info("  Get DataBase Connection==>"+DateUtils.getNowDateTime()+"==>"+getClass().getSimpleName());
			//System.out.print("  Get DataBase Connection==>"+DateUtils.getNowDateTime()+"==>"+getClass().getSimpleName());

		}
	}
	/**
	 * 将List<String>转成sql参数
	 * @param list
	 * @return
	 */
	public static String ListConvertToSQLParam(List<String> list){
		return Arrays.toString(list.toArray()).replace("[", "'").replace("]","'").replace(",","','").replace(" ", "");
	}
	/**
	 * 获取连接
	 * @author kingxu
	 * @date 2015-10-16
	 * @return
	 * @throws SQLException
	 * @return Connection
	 */
	protected Connection getConnction() throws SQLException{
		return con;
	}
	/**
	 * 自定义事务管理开关
	 * @throws SQLException
	 */
	public void openTransaction() throws SQLException{
		if(!Util.objIsNULL(con)){
			con.setAutoCommit(false);
		}
	}
	/**
	 * 回滚事务
	 * @throws SQLException
	 */
	public void rollbackTransaction() throws SQLException{
		if(!Util.objIsNULL(con)){
			con.rollback();
		}
	}
	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public void sumbitTransaction() throws SQLException{
		if(!Util.objIsNULL(con)){
			con.commit();
		}
	}



	public void closeConnection() throws SQLException{
		if(!Util.objIsNULL(rs)){
			rs.close();
		}
		if(!Util.objIsNULL(ps)){
			ps.close();
		}
		if(!Util.objIsNULL(con)&& !con.isClosed()){
			DBManager.closeCon(con);
			logger.info("Close DataBase Connection==>"+DateUtils.getNowDateTime()+"==>"+getClass().getSimpleName());
			//System.out.println("Close DataBase Connection==>"+DateUtils.getNowDateTime()+"==>"+getClass().getSimpleName());
		}
	}



	/**
	 * 通过传入数量生成参数问号
	 * @author kingxu
	 * @date 2016-5-20
	 * @param num
	 * @return
	 * @return String
	 */
	public  String buildValue(int num){
		String v="";
		for(int i=0;i<num;i++){
			if(i==0){
				v+="?";
			}else{
				v+=",?";
			}
		}
		return v;
	}
	/**
	 * 保存实体类到数据库
	 * @author kingxu
	 * @date 2016-5-20
	 * @param obj
	 * @return
	 * @return int
	 */
	public  int saveObject(Object obj){
		int num=0;
		try{
			if(!Util.objIsNULL(obj)){
				JSONObject json=JSONObject.fromObject(obj);
				json.remove("id");
				json.remove("serialVersionUID");
				Set set=json.keySet();
				String sql="insert into "+(obj.getClass().getSimpleName())+" ";
				sql+=set.toString().replace("[","(").replace("]",")")+"values(";
				sql+=buildValue(set.size())+");";
				num=saveEntity(sql,json.values().toArray());
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	
	public int saveMapList(List<Map<String,Object>> list,String tableName)throws Exception{
		int num=0;
		try{
			if(!Util.objIsNULL(list)&&list.size()>0){
				String sql="";
				int index=1;
				for(int i=0;i<list.size();i++){
					Map<String,Object> obj=list.get(i);
					obj.remove("id");
					obj.remove("serialVersionUID");
					Set set=obj.keySet();
					if(i==0){
						 sql="insert into "+(tableName)+" ";
						sql+=set.toString().replace("[","(").replace("]",")")+"values(";
						sql+=buildValue(set.size())+");";
						ps=con.prepareStatement(sql);
					}
					
					 for(Object o:set){
						 ps.setObject(index, obj.get(o));
						 index++;
					 }
					 ps.addBatch();
					 index=1;
				}
			}
 
			num=calculateNum(ps.executeBatch());
		}catch (Exception e) {
			throw e;
		}
		return num;
	}
	/**
	 * 批量保存实体类对象
	 * @author kingxu
	 * @date 2016-5-20
	 * @param parms
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public int saveObjectList(List<?> parms) throws Exception{
		int num=0;
		try{
			if(!Util.objIsNULL(parms)&&parms.size()>0){
				String sql="";
				int index=1;
				for(int i=0;i<parms.size();i++){
					Object obj=parms.get(i);
					JSONObject json=JSONObject.fromObject(obj);
					json.remove("id");
					json.remove("serialVersionUID");
					Set set=json.keySet();
					if(i==0){
						 sql="insert into "+(obj.getClass().getSimpleName())+" ";
						sql+=set.toString().replace("[","(").replace("]",")")+"values(";
						sql+=buildValue(set.size())+");";
						ps=con.prepareStatement(sql);
						
					}
					
					 for(Object o:set){
						 ps.setObject(index, json.get(o));
						 index++;
					 }
					 ps.addBatch();
					 index=1;
				}
			}
 
			num=calculateNum(ps.executeBatch());
		}catch (Exception e) {
			throw e;
		}
		return num;
	}


	/**
	 * 保存单个实体类并返回主键ＩＤ
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public int saveEntity(String sql,Object...args) throws Exception{
		int id=-1;
		try{ 
			ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//获取自动增加的id号
			ParameterMetaData pmd=ps.getParameterMetaData();
			if(!Util.objIsNULL(args)){
				if(args.length<pmd.getParameterCount()){
					throw new SQLException("参数错误:"+pmd.getParameterCount());
				}
				for(int i=0;i<args.length;i++){
					ps.setObject(i+1, args[i]);
				}
			}
			int num=ps.executeUpdate();

			//System.out.println(num+"----"+con+"-------------"+ps);
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				id=rs.getInt(1);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return id;
	}
	
	
	/**
	 * 保存单个实体类并返回主键ＩＤ
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public int saveEntityNoKeys(String sql,Object...args) throws Exception{
		int num=-1;
			try {  
				ps = con.prepareStatement(sql);  
				if(!Util.objIsNULL(args)){
					for (int i = 0; i < args.length; i++){
						ps.setObject(i + 1, args[i]);  
					}
				}
				num= ps.executeUpdate();
			} catch (SQLException e) {  
				e.printStackTrace();
				throw e;
			}
		return num;
	}
	
	
	/**
	 * 保存实体类并返回主键
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public int saveEntitys(String sql,Object[] args) throws Exception{
		int id=-1;
		try{
			ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ParameterMetaData pmd=ps.getParameterMetaData();
			if(!Util.objIsNULL(args)){
				if(args.length<pmd.getParameterCount()){
					throw new RuntimeException("参数错误:"+pmd.getParameterCount());
				}
				for(int i=0;i<args.length;i++){
					ps.setObject(i+1, args[i]);
				}
			}
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				id=rs.getInt(1);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return id;
	}


	/** 
	 * 支持单条增，删，改方法 
	 * @param sql 
	 * @param args sql参数 
	 * @return 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */  
	public int update(String sql, Object[] args) throws SQLException, ClassNotFoundException {  
		int num=-1;
		try {  
			ps = con.prepareStatement(sql);  
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){
					ps.setObject(i + 1, args[i]);  
				}
			}
			num= ps.executeUpdate();
		} catch (SQLException e) {  
			e.printStackTrace();
			throw e;
		}
		return num;  
	}  
	/**
	 * 
	 * @author kingxu
	 * @date 2015-10-16
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @return int
	 */
	public int update2(String sql, Object... args) throws SQLException, ClassNotFoundException {  
		int num=-1;
		try {  
			ps = con.prepareStatement(sql);  
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){
					ps.setObject(i + 1, args[i]);  
				}
			}
			num= ps.executeUpdate();
		} catch (SQLException e) {  
			e.printStackTrace();
			throw e;
		}
		return num;  
	}

	public int updates(String sql, Object... args) throws SQLException, ClassNotFoundException {  
		int num=-1;
		try {  

			ps = con.prepareStatement(sql);  
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){
					ps.setObject(i + 1, args[i]);  
				}
			}
			num= ps.executeUpdate();
		} catch (SQLException e) {  
			e.printStackTrace();
			throw e;
		}
		return num;  
	}




	/**
	 * 批量插入语句 仅适用于操作同一个表
	 * @param sql
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsert(String sql,List<Object[]> parms) throws Exception{
		int[] num=null;
		try{
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(parms)){
				for(int i=0;i<parms.size();i++){
					for(int j=0;j<parms.get(i).length;j++){
						ps.setObject(j+1, parms.get(i)[j]);
					}
					ps.addBatch();
				}
			}
			num=ps.executeBatch();
		}catch (Exception e) {
			throw e;
		}
		return num;
	}
	
	/**
	 * 批量插入语句 仅适用于操作同一个表
	 * @param sql
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsertforListArray(String sql,List<List<Object>> parms) throws Exception{
		int[] num=null;
		try{
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(parms)){
				for(int i=0;i<parms.size();i++){
					for(int j=0;j<parms.get(i).size();j++){
						ps.setObject(j+1, parms.get(i).get(j));
					}
					ps.addBatch();
				}
			}
			num=ps.executeBatch();
		}catch (Exception e) {
			throw e;
		}
		return num;
	}
	
	/**
	 * 批量插入语句 仅适用于操作同一个表
	 * @param sql
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsertforListString(String sql,List<String> parms) throws Exception{
		int[] num=null;
		try{
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(parms)){
				for(int i=0;i<parms.size();i++){
					ps.setObject(1, parms.get(i));
					ps.addBatch();
				}
			}
			num=ps.executeBatch();
		}catch (Exception e) {
			throw e;
		}
		return num;
	}
	/**
	 * 批量执行sql语句,已添加事物控制
	 * @param sql
	 * @return
	 * @throws Exception 
	 */
	public int[] batchUpdate(String[] sql) throws Exception{
		int[]result=new int[sql.length];
		Statement st=null;
		try{
			st=con.createStatement();
			for(int i=0;i<sql.length;i++){
				st.addBatch(sql[i]);
			}
			result=st.executeBatch();
			st.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}




	/**
	 * 批量执行SQL语句，并返回影响行数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int batchExcute(String[] sql) throws Exception{
		int num=-1;
		Statement st=null;
		try{
			st=con.createStatement();
			for(int i=0;i<sql.length;i++){
				st.addBatch(sql[i]);
			}
			num=calculateNum(st.executeBatch());
			st.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return num;
	}
	/**
	 * 计算批量执行语句返回影响行数
	 * @param n
	 * @return
	 */
	protected int calculateNum(int[] n){
		int num=0;
		try{
			for(int i=0;i<n.length;i++){
				num+=n[i];
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num; 
	}


	public int modifyEntity(Object clas,Object clas2,String table,String id) throws SQLException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException{
		int num=-1;
		// 获取需要修改项
		String compare = Help.compareEntity(clas, clas2);
		String sql = "update "+table+" set " + compare.replaceAll("\\,", "=?,")
				+ "=? where "+id+"=?";
		ps = con.prepareStatement(sql);
		String[] param = (compare + ","+id).split(",");
		for (int i = 0; i < param.length; i++) {
			ps.setObject((i + 1), clas2.getClass().getMethod(
					"get" + param[i].substring(0, 1).toUpperCase()
					+ param[i].substring(1)).invoke(clas2));
		}

		num = ps.executeUpdate();
		return num;

	}

	/** 
	 * 根据条件查询实体类
	 * @param <T> 
	 * @param sql 
	 * @param args 
	 * @return 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */  
	public <T> T find(String sql, Object... args) throws ClassNotFoundException, SQLException {  
		T t = null;  
		try {  
			ps = con.prepareStatement(sql);  
			for (int i = 0; i < args.length; i++){  
				ps.setObject(i + 1, args[i]);  
			}
			rs = ps.executeQuery();  
			if (rs.next()) {  
				t = rowMapper(rs);  
			}  
			rs.close();
		} catch (SQLException e) {  
			e.printStackTrace();
			throw e;
		} 
		return t;  
	}  

	/** 
	 * 根据条件查询数据实体类集合 
	 * @param <T> 
	 * @param sql 
	 * @param args 
	 * @return 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */  
	public <T> List<T> list(String sql, Object[] args) throws SQLException, ClassNotFoundException {  
		List<T> list = new ArrayList<T>();  
		try {  
			ps = con.prepareStatement(sql); 
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);  
				}
			}
			rs = ps.executeQuery();  
			while (rs.next()) {  
				T  t = rowMapper(rs);  
				list.add(t);  
			}  
			rs.close();
		} catch (SQLException e) {  
			e.printStackTrace();
			throw e;
		}  
		return list;  
	}  

	/**
	 * 根据条件查询数据实体类集合 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public <T> List<T> Objectlist(String sql, Object... args) throws SQLException, ClassNotFoundException {  
		List<T> list = new ArrayList<T>();  
		try {  
			ps = con.prepareStatement(sql); 
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);  
				}
			}
			rs = ps.executeQuery();  
			// System.out.println(rs.getRow());
			while(rs.next()){
				//	System.out.println(rs.getRow());
				T  t = rowMapper(rs);  
				//  System.out.println(rs.getRow());
				list.add(t);  
			}  
			rs.close();
		} catch (SQLException e) {  
			e.printStackTrace();
			throw e;
		}  
		return list;  
	}  

	/**
	 * 根据条件查询Map对象结果
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> findMap(String sql,Object...args) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>(); 
		try{
			ps = con.prepareStatement(sql);  
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			while(rs.next()){
				for(int i=1;i<=md.getColumnCount();i++){
					map.put(md.getColumnName(i), rs.getObject(i));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return map;
	}

	public List<Map<String,Object>> findListMap(String sql,Object...args) throws Exception{
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null; 
		try{

			ps = con.prepareStatement(sql);  
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			while(rs.next()){
				map=new HashMap<String, Object>(); 
				for(int i=1;i<=md.getColumnCount();i++){
					map.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}


	/**
	 * 无参数查询,返回集合数组
	 * @param sql 
	 * return 
	 */
	public List<Object[]> findDate(String sql,Object...args)throws Exception{
		List<Object[]> list=new ArrayList<Object[]>();
		Object[] object=null;
		try {
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			while(rs.next()){
				object=new Object[md.getColumnCount()];
				for(int i=1;i<=md.getColumnCount();i++){
					object[i-1]= rs.getObject(i);
				}
				//object=new Object[]{rs.getInt(1),rs.getString(2)};
				list.add(object);
			}
		} catch (Exception e) {
			list=null;
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 只查一个字段的集合(仅适用于一个查询一个字段的集合)
	 * @author kingxu
	 * @date 2015-10-16
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @return List<Object>
	 */
	public List<Object> findDate2(String sql,Object...args)throws Exception{
		List<Object> list=new ArrayList<Object>();
		try {
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getObject(1));
			}
		} catch (Exception e) {
			list=null;
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 只查一个字段的集合(仅适用于一个查询一个字段的集合)
	 * @author kingxu
	 * @date 2015-12-24
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * @return List<String>
	 */
	public List<String> findStringDate(String sql,Object...args)throws Exception{
		List<String> list=new ArrayList<String>();
		try {
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);
				}
			}
			rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			list=null;
			e.printStackTrace();
			throw e;
		}
		return list;
	}




	/**
	 * 根据条件查询Map集合
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String,Object>> listMap(String sql,Object...args) throws Exception{
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null; 
		try{
			ps = con.prepareStatement(sql);  
			if(!Util.objIsNULL(args)){
				for (int i = 0; i < args.length; i++){  
					ps.setObject(i + 1, args[i]);  
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			while(rs.next()){
				map=new HashMap<String, Object>(); 
				for(int i=1;i<=md.getColumnCount();i++){
					map.put(md.getColumnName(i), rs.getObject(i));
				}
				listMap.add(map);
			}

		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listMap;
	}
	/**
	 * 根据条件查询条数
	 * @param sql  
	 * @return
	 * @throws Exception
	 */
	public int findCount(String sql) throws Exception{
		int num=-1;
		try{
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return num;
	}
	public int findCount1(String sql) throws Exception{
		int num=-1;
		try{
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return num;
	}
	/**
	 * 根据条件查询分页条数
	 * @param sql 
	 * @param objects 查询条件
	 * @return
	 * @throws Exception
	 */
	public int findCount(String sql,Object...objects) throws Exception{
		int num=-1;
		try{
			ps=con.prepareStatement(sql);
			if(!Util.objIsNULL(objects)){
				for(int i=0;i<objects.length;i++){
					ps.setObject(i+1,objects[i]);
				}
			}
			rs=ps.executeQuery();
			if(rs.next()){
				num=rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return num;
	}
	/**
	 * 根据条件查询分页数据
	 * @param fields 需要显示的列名
	 * @param sql 
	 * @param limit 分页语句 
	 * @param page 分页参数
	 * @param objects 查询条件
	 * @return List<Map>
	 * @throws Exception
	 */
	public Pager findPager(String[] fields,String sql,String limit,Pager page,Object...objects) throws Exception{
		try{
			page.setTotal(findCount("select count(*) "+sql, objects));
			//con=DBManager.getCon();
			String field="";
			if(Util.objIsNULL(fields)){
				field="select * ";
			}else{
				field="select "+Arrays.asList(fields).toString().replace("[","").replace("]","")+" ";
			}
			ps=con.prepareStatement(field+sql+" "+limit);
			for(int i=0;i<objects.length;i++){
				ps.setObject(i+1,objects[i]);
			}
			ps.setInt(objects.length+1, (page.getPagenow()-1)*page.getPagesize());
			ps.setInt(objects.length+2, page.getPagesize());
			logger.info(ps);
			page.setList(rsMapper(ps.executeQuery()));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return page;
	}

	/**
	 * 将Rs映射成Map对象
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> rsMapper(ResultSet rs)  throws Exception{
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		try{
			ResultSetMetaData md = rs.getMetaData();
			Map<String,Object> map=null; 
			while(rs.next()){
				map=new HashMap<String, Object>(); 
				for(int i=1;i<=md.getColumnCount();i++){
					if(md.getColumnTypeName(i).indexOf("BLOB")>-1){
						map.put(md.getColumnName(i), BLOB2String(rs.getBlob(i)));
					}else{
						map.put(md.getColumnName(i), rs.getObject(i));
					}
				}
				listMap.add(map);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listMap;

	}
	public static String BLOB2String(Object o) throws SQLException, UnsupportedEncodingException{  
		String str = null;  
		byte[] inbyte=null;  
		if(o instanceof Blob){  
			Blob blob = (Blob) o;  
			if (blob != null) {  
				inbyte = blob.getBytes(1, (int) blob.length());  
			}  
			str =new String(inbyte,"utf-8");  
		}  
		return str;  
	}

	/**
	 * 将RS转成具体实体类对象
	 * @param <T>
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	abstract protected <T> T rowMapper(ResultSet rs) throws SQLException;  






	/** 
	 * 为bean自动注入数据 
	 *  
	 * @author kingxu 
	 * @param object 
	 * @param beanProperty 
	 */  
	private void beanRegister(Object object, String beanProperty, String value) {  
		Object[] beanObject = beanMatch(object.getClass(), beanProperty);  
		Object[] cache = new Object[1];  
		Method getter = (Method) beanObject[0];  
		Method setter = (Method) beanObject[1];  
		try {  

			// 通过get获得方法类型  
			String methodType = getter.getReturnType().getName();
			//System.out.println("********"+methodType+"--->"+value+"--->"+cache[0]);
			if (methodType.equalsIgnoreCase("long")) {  
				cache[0] = new Long(value);  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("int")  
					|| methodType.equalsIgnoreCase("Integer")|| methodType.equalsIgnoreCase("java.lang.Integer")) {  
				cache[0] = new Integer(value);  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("short")) {  
				cache[0] = new Short(value);  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("float")) {  
				cache[0] = new Float(value);  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("double")||methodType.equalsIgnoreCase("java.lang.Double")) {  
				cache[0] = new Double(value);  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("boolean")) {  
				cache[0] = new Boolean(value);  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("java.lang.String")) {  
				cache[0] = value;  
				setter.invoke(object, cache);  
			} else if (methodType.equalsIgnoreCase("java.io.InputStream")) {  
			} else if (methodType.equalsIgnoreCase("char")) {  
				cache[0] = (Character.valueOf(value.charAt(0)));  
				setter.invoke(object, cache);  
			}  


		} catch (Exception e) {  
			e.printStackTrace();  
		}  
	} 



	/** 
	 * 匹配指定class中数据,并返回包含get和set方法的object 
	 *  
	 * @author kingxu 
	 * @param clazz 
	 * @param beanProperty 
	 * @return 
	 */  
	private Object[] beanMatch(Class clazz, String beanProperty) {  
		Object[] result = new Object[2];  
		char beanPropertyChars[] = beanProperty.toCharArray();  
		beanPropertyChars[0] = Character.toUpperCase(beanPropertyChars[0]);  
		String s = new String(beanPropertyChars);  
		String names[] = { ("set" + s).intern(), ("get" + s).intern(),  
				("is" + s).intern(), ("write" + s).intern(),  
				("read" + s).intern() };  
		Method getter = null;  
		Method setter = null;  
		Method methods[] = clazz.getMethods();  
		for (int i = 0; i < methods.length; i++) {  
			Method method = methods[i];  
			// 只取公共字段  
			if (!Modifier.isPublic(method.getModifiers()))  
				continue;  
			String methodName = method.getName().intern();  
			for (int j = 0; j < names.length; j++) {  
				String name = names[j];  
				if (!name.equals(methodName))  
					continue;  
				if (methodName.startsWith("set")  
						|| methodName.startsWith("read"))  
					setter = method;  
				else  
					getter = method;  
			}  
		}  
		result[0] = getter;  
		result[1] = setter;  
		return result;  
	} 



	public Object zhuanhuan(ResultSet result,Class clazz){
		Object object=null;
		try{
			ResultSetMetaData rsmd = result.getMetaData();  
			// 获得数据列数  
			int cols = rsmd.getColumnCount();  
			// 创建等同数据列数的arraylist类型collection实例  
			//result.beforeFirst();//光标回滚
			// 遍历结果集  
			//if (result.next()) {
			if(result.relative(0)){
				// 创建对象  
				object = clazz.newInstance();  
				// 循环每条记录  
				for (int i = 1; i <= cols; i++) {  
					//System.out.println(rsmd.getColumnName(i)+"------>"+result.getString(i));
					beanRegister(object, rsmd.getColumnName(i), result.getString(i));  
				} 
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return object;

	}


}
