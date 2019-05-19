package com.coat.ivr.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.DBManager_sqlservler;
import util.DateUtils;
import util.ExcelTools;
import util.FileUtil;
import util.ReadExcel;
import util.Util;

import com.coat.ivr.dao.IVROptOutListDao;

import dao.common.BaseDao;
import entity.Excel;

public class IVROptOutListDaoImpl extends BaseDao implements IVROptOutListDao{
	

	Logger logger =Logger.getLogger(IVROptOutListDaoImpl.class);

	public String timeTaskGetDailyOptoutList(){
		String result = "";
		boolean flag = false;
		Long befor = null;
		Long after = null;
		try {
			befor = System.currentTimeMillis(); 
			//将数据从O盘拷贝至服务器本地
			flag = copyFileFromOdiscToLocal();
			if(!flag){
				throw new Exception("将数据从O盘拷贝至服务器本地失败!");
			}
			System.out.println("数据拷贝至服务器本地结束------>");
			//获取数据源A
			result = readAndWriteOptOutList();
			JSONObject json=JSONObject.fromObject(result);
			if(!"success".equals(json.get("state"))){
				return result;
			}
			System.out.println("获取数据源A结束------>");
			//获取数据源C
			result = timeTaskGetVSMPhoneNumberAndSave();
			System.out.println("---->"+json.get("state"));
			
			if(!"success".equals(json.get("state"))){
				return result;
			}
			System.out.println("获取数据源C结束------>");
			//获取report
			result = GetDailyOptoutList();
			if(!"success".equals(json.get("state"))){
				return result;
			}
			System.out.println("获取report结束------>");
			
		} catch (Exception e) {
			e.printStackTrace();
			result = Util.joinException(e);
		} finally {
			after= System.currentTimeMillis(); // 标记结束时
			System.out.println("生产ivr_report 耗时："+(after-befor)+" ms");
			try {
				super.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * 获取 Daily Opt-out list 即 report
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public String GetDailyOptoutList() throws SQLException{
		String result = "";
		String sql = "";
		Long befor = null;
		Long after = null;
		Excel excel=new Excel();
		try {
			super.openConnection();
			befor = System.currentTimeMillis(); 
			sql = "select phonenumber from ( select DISTINCT phonenumber from ivr_optout_list where flag='B' "+
					"union "+
					"select DISTINCT phonenumber  from ivr_vsmoptout_list) a where LENGTH(phonenumber) = '8' and phonenumber not in ( select phonenumber from ivr_optout_list where flag='A')";

//TODO LENGTH(phonenumber) = '8' and			
			//把数据交给Excel
		    excel.setExcelContentList(super.findListMap(sql));	
		    //属性字段名称
		    excel.setColumns(new String[]{"phonenumber"});
		    //设置Excel列头
		    excel.setHeaderNames(new String[]{"Telephone No."}); 
			//sheet名称
		    excel.setSheetname("Daily Opt-out List_"+DateUtils.Ordercode());
		    //文件名称
			excel.setFilename("Daily Opt-out List_"+DateUtils.Ordercode()+".csv");
			//表单生成
			excel.setFilepath(Util.getProValue("public.write.Opt.out.report.IVR.folder"));
			result = ExcelTools.createCSVToPath(excel, "");
		} catch (Exception e) {
			result = Util.joinException(e);
		} finally {
			after= System.currentTimeMillis(); // 标记结束时
			System.out.println("获取 Daily Opt-out list 即 report 耗时："+(after-befor)+" ms");
			super.closeConnection();
		}
		return result;
	}
	
	
	/**
	 * 定时从vsmart中获取香港电话号码并保存至本地数据库
	 * @return
	 */
	public String timeTaskGetVSMPhoneNumberAndSave(){
		String result = "";
		try{
			Util.printLogger(logger,"开始执行指定任务-从vsmart中获取新增的香港电话号码并保存至本地数据库");    
			List<String> list=readAndWriteVSMPhoneNumList();
			if(!Util.objIsNULL(list)&&list.size()>0){
				System.out.println("------> 进入这个方法 list 不为空");
				result=saveList(list);
				list=null;
			}else{
				result=Util.getMsgJosnObject("success", "vsmart中暂无新增的香港电话号码!");
				Util.printLogger(logger,"指定任务-->vsmart中暂无新增的香港电话号码!");		
			} 
		}catch(Exception e){
			result=Util.joinException(e);
			Util.printLogger(logger,"指定任务-->从vsmart中获取香港电话号码并保存至本地数据库失败："+e.getMessage());
		}
		return result;
		
	}

	/**
	 * 保存新增的香港电话号码至ivr_vsmoptout_list
	 * @param list
	 * @return
	 * @throws SQLException 
	 */
	public String saveList(List<String> list) throws SQLException{
		String result = "";
		Long befor = null;
		Long after = null;
		String sql1 = "";
		String sql2 = "";
		try {
			super.openConnection();
			super.openTransaction();
			befor = System.currentTimeMillis();
			sql1 = "delete from ivr_vsmoptout_list";
			int num=super.update2(sql1);
			if(num < 1){
				logger.info("清空ivr_vsmoptout_list数据条数为0条");
			}
			System.out.println("------>运行到这里了！<------");
			String nowTime = DateUtils.getNowDateTime();
			sql2 = "insert into ivr_vsmoptout_list (phonenumber,creater,createdate) VALUES (?,'system','"+nowTime+"')";
			num=calculateNum(batchInsertforListString(sql2, list));
			System.out.println("------>执行条数num："+num);
			if( num < 1){
				logger.info("插入ivr_vsmoptout_list数据条数为0条");
			}
			super.sumbitTransaction();
			result = Util.getMsgJosnObject("success", "保存数据至ivr_vsmoptout_list表成功");
			logger.info("保存数据至ivr_vsmoptout_list表成功");
		} catch (Exception e) {
			logger.error("保存数据至ivr_vsmoptout_list表失败");
			result=Util.joinException(e);
			try {
				super.rollbackTransaction();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			after= System.currentTimeMillis(); // 标记结束时
			System.out.println("保存数据至ivr_vsmoptout_list表耗时："+(after-befor)+" ms");
			super.closeConnection();
		}	
		return result;
	}
	
	
	public List<String> readAndWriteVSMPhoneNumList() throws SQLException{
		Connection con = null;
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list=null;
		Long befor = null;
		Long after = null;
		try{
			befor = System.currentTimeMillis(); 
			con=DBManager_sqlservler.getCon();
			  sql = "select homePhone from [SZO_system].[dbo].[vSZO_SOS_ClientContact] where (homePhone like '852%') union " +
			  		"select mobilePhone from [SZO_system].[dbo].[vSZO_SOS_ClientContact] where (mobilePhone like '852%') union " +
			  		"select officePhone from [SZO_system].[dbo].[vSZO_SOS_ClientContact] where (officePhone like '852%') union " +
			  		"select otherPhone from [SZO_system].[dbo].[vSZO_SOS_ClientContact] where (otherPhone like '852%')";			  
			  ps=con.prepareStatement(sql);
			  rs=ps.executeQuery();
			  list=new ArrayList<String>();
				while(rs.next()){
					list.add(rs.getString(1).substring(3).replace(" ", ""));
					/*list.add(rs.getString(1).substring(3));*/
				}
				rs.close();
				logger.info("从VSmart获取香港电话号码成功");
		}catch(Exception e){
			e.printStackTrace();
			Util.printLogger(logger,"指定任务-->从VSmart获取香港电话号码失败："+e.getMessage());
			logger.error("从VSmart获取香港电话号码失败");
			throw new RuntimeException(e);
		}finally{
			after= System.currentTimeMillis(); // 标记结束时
			System.out.println("ivr 从vsmart读取电话号码耗时："+(after-befor)+" ms");
			con.close();
		    DBManager_sqlservler.closeCon(con);
		}
		return list;
	}
	
	
	/**
	 * 将 SZOAdm同事 从PCCW的系统中下载一些档案1.csv, 2.csv,3.csv,4.csv,5.csv 存入数据库
	 * 将 Opt out list by request 存入数据库
	 * @return
	 * @throws SQLException
	 */
	public String readAndWriteOptOutList() throws SQLException{
		String result = "";
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String nowdate = "";
		int numToTemp = -1;
		Long befor = null;
		Long after = null;
		List<List<Object>> list1 = null;
		List<List<Object>> list2 = null;
		try {
			super.openTransaction();
			befor = System.currentTimeMillis(); 
			nowdate = DateUtils.getNowDateTime();
			sql="delete from ivr_optout_list";
			super.update2(sql);
			String[] filelist=Util.getProValue("public.servicesdisc.Opt.out.list.IVR.file").split(",");/*字符串转数组*/
			for(int i=0; (!Util.objIsNULL(filelist)) && i < filelist.length; i++ ){		
				System.out.println("------>"+filelist[i].substring(filelist[i].lastIndexOf(".")+1));
				if("xlsx".equals(filelist[i].substring(filelist[i].lastIndexOf(".")+1))){
					list1 = ReadExcel.readExcel(new File(filelist[i]), "Sheet1", 0, 1);
					if(Util.objIsNULL(list1)){
						continue;					
					}
					sql1 = "insert into ivr_optout_list (phonenumber,flag,creater,createdate) values (REPLACE(?,'.00',''),'B','system','"+nowdate+"')";
					numToTemp=calculateNum(batchInsertforListArray(sql1, list1));
					if(numToTemp != list1.size()){
						throw new Exception("保存数据至ivr_optout_list表异常："+filelist[i]);
					}
					logger.info("保存数据至ivr_optout_list表条数："+numToTemp);
					System.out.println("保存数据至ivr_optout_list表条数："+numToTemp);	
				}else{
					list2 = ReadExcel.readExcel(new File(filelist[i]), 0, 1, 1);
					if(Util.objIsNULL(list2)){
						continue;					
					}
					sql2 = "insert into ivr_optout_list (phonenumber,flag,creater,createdate) values (?,'A','system','"+nowdate+"')";
					numToTemp=calculateNum(batchInsertforListArray(sql2, list2));
					if(numToTemp != list2.size()){
						throw new Exception("保存数据至ivr_optout_list表异常："+filelist[i]);
					}
					logger.info("保存数据至ivr_optout_list表条数："+numToTemp);
					System.out.println("保存数据至ivr_optout_list表条数："+numToTemp);	
				}
			}
			list1=null;
			list2=null;
			super.sumbitTransaction();
			result = Util.getMsgJosnObject_success();
		} catch (Exception e) {
			super.rollbackTransaction();
			e.printStackTrace();
			result = Util.joinException(e);
		} finally {
			after= System.currentTimeMillis(); // 标记结束时
			System.out.println("读取并保存ivr_optout_list耗时："+(after-befor)+" ms");
			super.closeConnection();
		}
		return result;
	}


	/**
	 * 将O盘数据拷贝至本地盘符
	 * @return
	 */
	public boolean copyFileFromOdiscToLocal() throws Exception{
		boolean flag = true;
		String[] filelist = null;
		try {
			filelist=Util.getProValue("public.Odisc.Opt.out.list.IVR.file").split(",");/*字符串转数组*/
			for(int i=0; (!Util.objIsNULL(filelist)) && i < filelist.length; i++ ){
				String pathName=filelist[i];
				String[] pathNameTab = pathName.split("\\\\");
				String filename = pathNameTab[pathNameTab.length - 1];
				flag = FileUtil.copyFile3(pathName, Util.getProValue("file.handle.temp.downpath")+filename);
				if(!flag){
					throw new Exception("O盘拷贝数据至服务器本地失败，失败的文件名为："+filename);
				}
			}
		} catch (Exception e) {
			flag = false;
			Util.joinException(e);
		}
		return flag;
	}
	
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}

}
