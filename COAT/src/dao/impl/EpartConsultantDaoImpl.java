package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Util;
import dao.EpartConsultantDao;
import entity.Econsreport;

/**
 * 其他顾问离职、紧急等情况，计算打卡信息，上传consultant list后处理方法，计算后存入E-partconslist
 * @author Wilson
 *
 */
public class EpartConsultantDaoImpl implements EpartConsultantDao {
		Logger logger = Logger.getLogger(EpartConsultantDaoImpl.class);
		PreparedStatement ps = null;
		String sql = "";
		Connection con = null;
		int y = 0;
		int m = 0;
	
	@SuppressWarnings("unchecked")
	public int saveConsultant(String filename, InputStream os, String username) {
		int acti=1;
		Econsreport es=new Econsreport();
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数(第一行算作0)
		int totalRows = 0;// 总行数
		String y_month = "";
		try {
			con = DBManager.getCon();
			Util.deltables("e_conslist");
			y_month = getYMonth(); 		//查询打卡记录表中的 年-月
			Util.deltables("e_partconsrepot");//计算后存入e_partconsrepot  不删除
			
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行
				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell leadercodecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell positioncell = row.getCell(2);
				HSSFCell namecell = row.getCell(3);
				/** 给数据库里面的字段赋值* */
				String leadercode = Util.cellToString(leadercodecell);
				String staffcode  = Util.cellToString(staffcodecell);
				String name = Util.cellToString(namecell);
				String position  = Util.cellToString(positioncell);
		 if(isResignList(staffcode)!=1){//不在 离职表中
			 
			 es.setStaffcode(staffcode);
			 es.setRecruiter(leadercode);
			 es.setPosition(position);
			 es.setStaffname(name);
				sql = "insert e_conslist(recruiter,staffcode,potion,name,grade,joindate,updname,upddate) values('"+leadercode+"','"+staffcode+"','"+position+"','"+name+"','','"+username+"','"+DateUtils.getDateToday()+"')";
				ps = con.prepareStatement(sql);
				int rsNum = ps.executeUpdate();
				if (rsNum > 0) {
					
					List holiday=new ArrayList();	
					int workday=0;//正常工作日
					int leaveAL=0;
					int leaveSL=0;
					int leaveother=0;
					int borrownum=0;
					int MAPnum=0;
					int normal=0;
					int noshow=0;
					int late=0;
					int total = 0;  // 支付总数
					//String y_month = getYMonth(); 		//查询打卡记录表中的 年-月
						
					es.setMapdate(y_month+"-"+DateUtils.getDay());  //打卡年月 + 导入的当天-天
					 
					int maxmonth=DateUtils.getMonthLastDay(y, m);//获取打卡日期 最大的天数
					
					if (!Util.objIsNULL(y_month)) {
						for(int day=1;day<=maxmonth;day++){//循环遍历最大天数
							String ym="";
							if(day<=9){
								ym=y_month+"-0"+day;
							}else{
								ym=y_month+"-"+day;
							}
							if(isholiday(ym)==1 || DateUtils.idWeek(ym).equals(Constant.IS_WEEK)){//指定日期是否是假期
								holiday.add(Constant.H_CM);
							}else{//當天沒有假期
								workday++;
									String type=isLeave(staffcode, ym);//请假类型
									if(type!=null){//指定日期是否請假
										holiday.add(type);
										if(type.equals(Constant.LEAVE_TYPE_AL)){
											leaveAL++;
										}else if(type.equals(Constant.LEAVE_TYPE_SL)){
											leaveSL++;
										}else{
											leaveother++;
										}
									}else{//指定日期沒有請假
										if(isMap(staffcode, ym)==1){//指定staffcode以及指定日期内是否有会客
											holiday.add(Constant.MAP_TYPE);
											MAPnum++;
										}else{//指定staffcode以及指定日期内没有会客
											String entryTime=queryEntryTime(staffcode, ym);
									/*		if(entryTime!=null){//有打卡记录
												holiday.add(entryTime);
												if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
													normal++;
												}else{
													late++;  //迟到--记录
													total= total+10; //扣10块钱
												}
												
											}else{//没有打卡记录
												if(isBorrow(staffcode, ym)==1){//指定staffcode以及指定日期内是否有借卡记录
													holiday.add(Constant.LC);
													borrownum++;
												}else{//指定staffcode以及指定日期内没有借卡记录
													holiday.add(Constant.NO_SHOW);
													noshow++;
													total= total+10; //扣10块钱
												}
											}*/
									if(isBorrow(staffcode, ym)==1){//指定staffcode以及指定日期内是否有借卡记录
											holiday.add(Constant.LC);
												borrownum++;
									}else{
											if(entryTime!=null){//有打卡记录
												holiday.add(entryTime);
											   if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
												   normal++;
												}else{
													late++;  //迟到--记录
													total= total+10; //扣10块钱
												}
											}else{
											//指定staffcode以及指定日期内没有打卡记录
												holiday.add(Constant.NO_SHOW);
												noshow++;
												total= total+10; //扣10块钱
											}
										}
											 
									}
								}
							}
						}
					}else {
						logger.info(username+"没有处理打卡记录表中数据！");
					}
					 es.setDay1(holiday.get(0).toString());
					 es.setDay2(holiday.get(1).toString());
					 es.setDay3(holiday.get(2).toString());
					 es.setDay4(holiday.get(3).toString());
					 es.setDay5(holiday.get(4).toString());
					 es.setDay6(holiday.get(5).toString());
					 es.setDay7(holiday.get(6).toString());
					 es.setDay8(holiday.get(7).toString());
					 es.setDay9(holiday.get(8).toString());
					 es.setDay10(holiday.get(9).toString());
					 es.setDay11(holiday.get(10).toString());
					 es.setDay12(holiday.get(11).toString());
					 es.setDay13(holiday.get(12).toString());
					 es.setDay14(holiday.get(13).toString());
					 es.setDay15(holiday.get(14).toString());
					 es.setDay16(holiday.get(15).toString());
					 es.setDay17(holiday.get(16).toString());
					 es.setDay18(holiday.get(17).toString());
					 es.setDay19(holiday.get(18).toString());
					 es.setDay20(holiday.get(19).toString());
					 es.setDay21(holiday.get(20).toString());
					 es.setDay22(holiday.get(21).toString());
					 es.setDay23(holiday.get(22).toString());
					 es.setDay24(holiday.get(23).toString());
					 es.setDay25(holiday.get(24).toString());
					 es.setDay26(holiday.get(25).toString());
					 es.setDay27(holiday.get(26).toString());
					 es.setDay28(holiday.get(27).toString());
					 
					   if(maxmonth > 28){
						 es.setDay29(holiday.get(28).toString());
						 if(maxmonth>29){
							 es.setDay30(holiday.get(29).toString());
							 if(maxmonth>30){
								 es.setDay31(holiday.get(30).toString());
							 }
						 }
					 } 
						es.setTime("1");
						String etotal = total>Constant.hundred?Constant.hundred+"":total+"";
						es.setTotal(etotal);
						int clubnum = isClub(staffcode);	//查询是否为C-CLUB成员
						
						es.setCclub(clubnum+"");	
						if(clubnum>0){
							es.setPenalty("0");
						}else{
							es.setPenalty(etotal);
						}
						es.setWorking(workday+"");
						es.setAl_leave(leaveAL+"");
						es.setSl_leave(leaveSL+"");
						es.setOl_leave(leaveother+"");
						es.setBorrow(borrownum+"");
						es.setNoofmap(MAPnum+"");
						es.setEmap(MAPnum+"");
						es.setLateness(late+"");
						es.setNoshow(noshow+"");
						es.setOntimr(normal+"");
						DecimalFormat df1 = new DecimalFormat("##0.00%");
						es.setE_lateness(df1.format((late/(double)workday)));
						es.setE_noshow(df1.format((noshow/(double)workday)));
						es.setE_noTime(df1.format((normal/(double)workday)));
						
						if(Util.objIsNULL(staffcode)){
							return num;
						}
						es.setConvoy(Util.objIsNULL(staffcode)?"":staffcode.substring(2));
					try{
					 sql="insert into e_partconsrepot values('"+es.getMapdate()+"','"+es.getRecruiter()+"','"+es.getStaffcode()+
					 "','"+es.getPosition()+"','"+es.getStaffname()+"','"+es.getConvoy()+"','"+es.getTime()+
					 "','"+es.getDay1()+"','"+es.getDay2()+"','"+es.getDay3()+"','"+es.getDay4()+"','"+es.getDay5()+
					 "','"+es.getDay6()+"','"+es.getDay7()+"','"+es.getDay8()+"','"+es.getDay9()+"','"+es.getDay10()+
					 "','"+es.getDay11()+"','"+es.getDay12()+"','"+es.getDay13()+"','"+es.getDay14()+"','"+es.getDay15()+
					 "','"+es.getDay16()+"','"+es.getDay17()+"','"+es.getDay18()+"','"+es.getDay19()+"','"+es.getDay20()+
					 "','"+es.getDay21()+"','"+es.getDay22()+"','"+es.getDay23()+"','"+es.getDay24()+"','"+es.getDay25()+
					 "','"+es.getDay26()+"','"+es.getDay27()+"','"+es.getDay28()+"','"+es.getDay29()+"','"+es.getDay30()+
					 "','"+es.getDay31()+
					 "','"+es.getTotal()+"','"+es.getCclub()+"','"+es.getPenalty()+"','"+es.getNoofmap()+
					 "','"+es.getWorking()+"','"+es.getAl_leave()+"','"+es.getSl_leave()+"','"+es.getOl_leave()+
					 "','"+es.getBorrow()+"','"+es.getEmap()+"','"+es.getLateness()+"','"+es.getNoshow()+"','"+es.getOntimr()+
					 "','"+es.getE_lateness()+"','"+es.getE_noshow()+"','"+es.getE_noTime()+"','"+username+"','"+es.getMapdate()+"')";
					 ps = con.prepareStatement(sql);
						int reportnum = ps.executeUpdate();
						if (reportnum > 0) {
							logger.info(username+"-e_partCons-success");
							acti++;
						}else{
							logger.info(username+"-e_partCons-error");
						}
					 
					}catch(Exception e){
						e.printStackTrace();
						logger.error(username+"在EpartConsultantDaoImpl.saveConsultant()中"+"插入e_partconsReport异常！" + e);
					}
					num++;
				} else {
					logger.info(username+"-----cons list -error");
				}
					
					 
			}else{//在 离职表中
				
			}
		 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"在EpartConsultantDaoImpl.saveConsultant()中"+"插入E_consList异常！" + e);
			return num;
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/***
	 * 获取计算考勤的 年-月
	 */
	public String getYMonth() {
		String nums="";
		String months="";
		try{
			sql = "select DATE_FORMAT(entryDate,'%Y') as years ,DATE_FORMAT(entryDate,'%m') as months from  e_attendence  order by entryDate desc limit 0,1 ";
			ps = con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				nums=rs.getString("years");
				months=rs.getString("months");
			}else{
				nums=null;
				months=null;
			}
			y= new Integer(nums);
			m= new Integer(months);
		}catch(Exception e){
			logger.error("查询e_attendence表异常");
			nums=null;
			months=null;
		}finally{
		}
		return nums+"-"+months;
	}
	/***
	 * 是否为EClub的成员
	 */
	public int isClub(String staffcode) {
		int nums=-1;
		 try{
			 sql = "select * from e_club where staffcode='"+staffcode+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=1;
				}else{
					nums=0;
				}
				
		 }catch(Exception e){
			 logger.error("查询e_club表异常");
		 }finally{
		 }
			return nums;
	}
/**
 * 是否在指定时间为假期
 */
	public int isholiday(String date) {
		int nums=-1;
		 try{
			 sql = "select * from e_exceptdate where edate='"+date+"'";
			 
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=1;
				}else{
					nums=0;
				}
				 rs.close();
		 }catch(Exception e){
			 logger.error("查询e_exceptdate表异常"+date);
			 e.printStackTrace();
		 }finally{
		
		 }
			return nums;
	}
	/**
	 * 该staffcode在指定日期是否请假
	 */
	public String isLeave(String staffcode, String date) {
		String nums="";
		 try{
			 sql = "select nature from e_leave where staffcode='"+staffcode+"' and leavedate='"+date+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("nature");
				}else{
					nums=null;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询e_leave表异常");
		 }finally{
		 }
			return nums;
	}
/**
 * 该staffcode在指定日期内是否有meeting
 */
	public int isMap(String staffcode, String date) {
		int nums=-1;
		 try{
			 sql = "select * from emap where staffcode='"+staffcode+"' and meetingdate='"+date+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=1;
				}else{
					nums=0;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询emap表异常");
		 }finally{
		 }
			return nums;
	}
/**
 * 查看该staffcode是否离职
 */
	public int isResignList(String staffcode) {
		int nums=-1;
	 try{
		 sql = "select * from e_resignlist where staffcode='"+staffcode+"'";
			ps = con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				nums=1;
			}else{
				nums=0;
			}
			
	 }catch(Exception e){
		 e.printStackTrace();
		 logger.error("查询resign表异常"+e.toString());
		 return nums;
		 
	 }finally{
	 }
		return nums;
	}
/**
 * 查询该staffcode在指定日期内是否打卡
 */
	public String queryEntryTime(String staffcode, String date) {
		String nums="";
		 try{
			 sql = "select * from e_attendence where staffcode='"+staffcode+"' and entryDate='"+date+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("entryTime");
				}else{
					nums=null;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询entryTime表异常");
			 
		 }finally{
			 
		 }
			return nums;
		}
	/**
	 * 查询该staffcode在指定日期内是否有借卡记录
	 */
	public int isBorrow(String staffcode, String date) {
		int nums=-1;
		 try{
			 sql = "select * from e_borrow where staffcode='"+staffcode+"' and borrowdate='"+date+"'";
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=1;
				}else{
					nums=0;
				}
				rs.close();
		 }catch(Exception e){
			 logger.error("查询resign表异常");
			 
		 }finally{
		 }
			return nums;
		}

	
	
	
}
