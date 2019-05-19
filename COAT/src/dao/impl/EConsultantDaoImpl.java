package dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import util.ReadExcel;
import util.Util;
import dao.EConsultantDao;
import entity.Econsreport;

public class EConsultantDaoImpl implements EConsultantDao {
	Logger logger = Logger.getLogger(EConsultantDaoImpl.class);
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	int y = 0;
	int m = 0;


	public int saveConsultants(String filename, InputStream os, String username) {
		Econsreport es=new Econsreport();
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数(第一行算作0)
		int totalRows = 0;// 总行数
		String y_month = "";
		//查询打卡记录表中的 年-月
		List<String> ClubList=getClub();//C-Club 人员列表
		List<String> holidayList=getholiday();//假期列表
		List<String> ResignList=getResignList();//离职人员列表
		List<String> MapList=getMap();//Map 人员列表
		List<String> BorrowList=getBorrow();//借卡人员列表

		List<String> trainingList=getTraining();// Training 会议人员列表


		try {
			con = DBManager.getCon();
			//System.out.println(DateUtils.getNowDateTime());
			Util.deltables("e_conslist");
			y_month = getYMonth(); 	
			Util.deltables("e_consrepot where mapdate like '"+y_month+"%'");/** 根据e_attendence表，删除consreport表 * */
			con.setAutoCommit(false);











			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			stmt.clearBatch();




			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行
				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell leadercodecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell positioncell = row.getCell(2);
				HSSFCell namecell = row.getCell(3);
				HSSFCell gradecell = row.getCell(4);//2014-5-8 14:52:41 King 新增Grade

				/** 给数据库里面的字段赋值* */
				String leadercode = Util.cellToString(leadercodecell);
				String staffcode  = Util.cellToString(staffcodecell);
				String name = Util.cellToString(namecell);
				String position  = Util.cellToString(positioncell);
				String grade=Util.cellToString(gradecell);

				es.setStaffcode(staffcode); 
				es.setRecruiter(leadercode);
				es.setPosition(position);
				es.setStaffname(name);




				if(!Util.objIsNULL(staffcode)){
				//	System.out.println(i+"====staffcode"+staffcode);
					stmt.addBatch("insert e_conslist(recruiter,staffcode,potion,name,grade,joindate,updname,upddate) values('"+leadercode+"','"+staffcode+"','"+position+"','"+name+"','"+grade+"','','"+username+"','"+DateUtils.getDateToday()+"')");
					if(!ResignList.contains(staffcode)){//不在 离职表中
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
						String dayString = "";
						if(DateUtils.getDay()<=9){
							dayString = "-0"+DateUtils.getDay();
						}else {
							dayString = "-"+DateUtils.getDay();
						}
						es.setMapdate(y_month+dayString);  //打卡年月 + 导入的当天-天
						int maxmonth=DateUtils.getMonthLastDay(y, m);//获取打卡日期 最大的天数
						if (!Util.objIsNULL(y_month)) {
							List<String> entryList=querstaffyEntryTime(staffcode);//个人当月打卡列表
							List<String> leaveList=findLeavebystaffcode(staffcode);//个人请假打卡列表

							for(int day=1;day<=maxmonth;day++){//循环遍历最大天数
								String ym="";
								if(day<=9){
									ym=y_month+"-0"+day;
								}else{
									ym=y_month+"-"+day;
								}
								if(holidayList.contains(ym)|| DateUtils.idWeek(ym).equals(Constant.IS_WEEK)){//指定日期是否是假期
									holiday.add(Constant.H_CM);
								}else{//當天沒有假期
									workday++;
									//String type=isLeave(staffcode, ym);//请假类型
									String type=findLeaveType(leaveList,staffcode+ym);//请假类型
									if(!Util.objIsNULL(type)){//指定日期是否請假
										holiday.add(type);
										if(type.equals(Constant.LEAVE_TYPE_AL)){
											leaveAL++;
										}else if(type.equals(Constant.LEAVE_TYPE_SL)){
											leaveSL++;
										}else{
											leaveother++;
										}
									}else{//指定日期沒有請假



										if(MapList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有会客
											MapList.remove(staffcode+""+ym);
											holiday.add(Constant.MAP_TYPE);
											MAPnum++;
										}else{//指定staffcode以及指定日期内没有会客

											if(BorrowList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有借卡记录
												BorrowList.remove(staffcode+""+ym);	
												holiday.add(Constant.LC);
												borrownum++;
											}else{
												//String entryTime=queryEntryTime(staffcode, ym);
												String entryTime=findentryTime(entryList,staffcode+ym);
												if(!Util.objIsNULL(entryTime)){//有打卡记录
													holiday.add(entryTime);
													if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
														normal++;
													}else{
														late++;  //迟到--记录
														if(!grade.toUpperCase().equals(Constant.GRADE))//员工不在试用期内，扣款十元
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
						int clubnum = ClubList.contains(staffcode)?1:0;	//查询是否为C-CLUB成员
						es.setCclub(clubnum+"");	
						if(clubnum>0){
							ClubList.remove(staffcode);
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
						es.setE_noTime(df1.format((1)-((late/(double)workday)+(noshow/(double)workday))));
						es.setConvoy(staffcode.substring(2));
						stmt.addBatch("insert into e_consrepot values('"+es.getMapdate()+"','"+es.getRecruiter()+"','"+es.getStaffcode()+
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
								"','"+es.getE_lateness()+"','"+es.getE_noshow()+"','"+es.getE_noTime()+"','"+username+"','"+es.getMapdate()+"');");
						if(i%300==0){
							stmt.executeBatch();
						}
						num++;
					}else{//在 离职表中
						ResignList.remove(staffcode);
						logger.info(staffcode+"在离职表中--------------");
					}
				}

			}
			stmt.executeBatch();
			logger.info(username+"-e_consultantReport-success");
			con.commit();
			stmt.close();

			System.out.println(DateUtils.getNowDateTime());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"在EConsultantDaoImpl.saveConsultant()中"+"插入E_consList异常！" + e);
			return num;
		} finally {
			ClubList=holidayList=ResignList=MapList=BorrowList=null;
			// 关闭连接
			DBManager.closeCon(con);
			System.out.println(DateUtils.getNowDateTime());
		}

		return num;
	}
	/**
	 * 计算顾问个人打卡数据汇总
	 * @author kingxu
	 * @date 2015-10-15
	 * @param filename
	 * @param os
	 * @param username
	 * @return
	 * @return int
	 */
	public int cacularConsultantAttendance(String filename,String username){
		Econsreport es=new Econsreport();
		int num = 0;
		String y_month = "";
		//查询打卡记录表中的 年-月
		List<String> ClubList=getClub();//C-Club 人员列表
		List<String> holidayList=getholiday();//假期列表
		List<String> ResignList=getResignList();//离职人员列表
		List<String> MapList=getMap();//Map 人员列表
		List<String> BorrowList=getBorrow();//借卡人员列表
		List<String> trainingList=getTraining();// Training 会议人员列表

		try {
			con = DBManager.getCon();
			Util.deltables("e_conslist");
			y_month = getYMonth(); 	
			Util.deltables("e_consrepot where mapdate like '"+y_month+"%'");/** 根据e_attendence表，删除consreport表 * */
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			stmt.clearBatch();
			List<List<Object>> list=ReadExcel.readExcel(new File("O:\\SZO\\CSD\\SZO Programm\\SZO Admin\\Attendence\\test\\consultant list.xls"), 0, 5, 1);
			for(int i=0;i<list.size();i++){
				List<Object> l=list.get(i);
				/** 给数据库里面的字段赋值* */
				String leadercode =l.get(0)+"";
				String staffcode  = l.get(1)+"";
				String name = l.get(2)+"";
				String position  = l.get(3)+"";
				String grade=l.get(4)+"";

				if(!Util.objIsNULL(staffcode)){
					stmt.addBatch("insert e_conslist(recruiter,staffcode,potion,name,grade,joindate,updname,upddate) values('"+leadercode+"','"+staffcode+"','"+position+"','"+name+"','"+grade+"','','"+username+"','"+DateUtils.getDateToday()+"');");
				}
				stmt.executeBatch();
				
				List<String> holiday=null;
				staffcode=l.get(1)+"";
				grade=l.get(4)+"";
				es.setStaffcode(staffcode); 
				es.setRecruiter(l.get(0)+"");
				es.setPosition(l.get(3)+"");
				es.setStaffname(l.get(2)+"");

				if(!ResignList.contains(staffcode)){//不在 离职表中
					holiday=new ArrayList<String>();	
					int workday=0;//正常工作日
					int leaveAL=0;
					int leaveSL=0;
					int leaveother=0;
					int borrownum=0;
					int TraningNum=0;
					int MAPnum=0;
					int normal=0;
					int noshow=0;
					int late=0;
					int total = 0;  // 支付总数
					String dayString = "";
					if(DateUtils.getDay()<=9){
						dayString = "-0"+DateUtils.getDay();
					}else {
						dayString = "-"+DateUtils.getDay();
					}
					es.setMapdate(y_month+dayString);  //打卡年月 + 导入的当天-天
					int maxmonth=DateUtils.getMonthLastDay(y, m);//获取打卡日期 最大的天数
					if (!Util.objIsNULL(y_month)) {
						List<String> entryList=querstaffyEntryTime(staffcode);//个人当月打卡列表
						List<String> leaveList=findLeavebystaffcode(staffcode);//个人请假打卡列表

						//System.out.println((i+1)+"--"+staffcode+"----"+y+"---"+ m+"--------"+maxmonth);
						for(int day=1;day<=maxmonth;day++){//循环遍历最大天数
							String ym="";
							if(day<=9){
								ym=y_month+"-0"+day;
							}else{
								ym=y_month+"-"+day;
							}
							if(holidayList.contains(ym)|| DateUtils.idWeek(ym).equals(Constant.IS_WEEK)){//指定日期是否是假期
								holiday.add(Constant.H_CM);
							}else{//當天沒有假期
								workday++;
								//String type=isLeave(staffcode, ym);//请假类型
								String type=findLeaveType(leaveList,staffcode+ym);//请假类型
								if(!Util.objIsNULL(type)){//指定日期是否請假
									holiday.add(type);
									if(type.equals(Constant.LEAVE_TYPE_AL)){
										leaveAL++;
									}else if(type.equals(Constant.LEAVE_TYPE_SL)){
										leaveSL++;
									}else{
										leaveother++;
									}
								}else{//指定日期沒有請假
									if(trainingList.contains(staffcode+""+ym)){//制定staffcode当日是否有培训
										trainingList.remove(staffcode+""+ym);
										holiday.add(Constant.Training_TYPE);

										TraningNum++;
									}else{


										if(MapList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有会客
											MapList.remove(staffcode+""+ym);
											holiday.add(Constant.MAP_TYPE);
											MAPnum++;
										}else{//指定staffcode以及指定日期内没有会客

											if(BorrowList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有借卡记录
												BorrowList.remove(staffcode+""+ym);	
												holiday.add(Constant.LC);
												borrownum++;
											}else{
												//String entryTime=queryEntryTime(staffcode, ym);
												String entryTime=findentryTime(entryList,staffcode+ym);
												if(entryTime!=null){//有打卡记录
													holiday.add(entryTime);
													if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
														normal++;
													}else{
														late++;  //迟到--记录
														if(!grade.toUpperCase().equals(Constant.GRADE))//员工不在试用期内，扣款十元
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
					int clubnum = ClubList.contains(staffcode)?1:0;	//查询是否为C-CLUB成员
					es.setCclub(clubnum+"");	
					if(clubnum>0){
						ClubList.remove(staffcode);
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
					es.setE_noTime(df1.format((1)-((late/(double)workday)+(noshow/(double)workday))));
					es.setConvoy(staffcode.substring(2));
					stmt.addBatch("insert into e_consrepot values('"+es.getMapdate()+"','"+es.getRecruiter()+"','"+es.getStaffcode()+
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
							"','"+es.getE_lateness()+"','"+es.getE_noshow()+"','"+es.getE_noTime()+"','"+username+"','"+es.getMapdate()+"')");
					if(num%300==0){
						stmt.executeBatch();
					}
					num++;
				}else{//在 离职表中
					ResignList.remove(staffcode);
					logger.info(staffcode+"在离职表中--------------");
				}



			}
			stmt.executeBatch();
			con.commit();
			logger.info(username+"-e_consultantReport-success");
			//System.out.println(DateUtils.getNowDateTime());
		} catch (SQLException e) {
			e.printStackTrace();
		}   catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}












	public int cacular(String filename,InputStream os,String username){
		Econsreport es=new Econsreport();
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数(第一行算作0)
		int totalRows = 0;// 总行数
		String y_month = "";
		//查询打卡记录表中的 年-月
		List<String> ClubList=getClub();//C-Club 人员列表
		List<String> holidayList=getholiday();//假期列表
		List<String> ResignList=getResignList();//离职人员列表
		List<String> MapList=getMap();//Map 人员列表
		List<String> BorrowList=getBorrow();//借卡人员列表
		List<String> trainingList=getTraining();// Training 会议人员列表

		try {
			con = DBManager.getCon();
			Util.deltables("e_conslist");
			y_month = getYMonth(); 	
			Util.deltables("e_consrepot where mapdate like '"+y_month+"%'");/** 根据e_attendence表，删除consreport表 * */
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			stmt.clearBatch();
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行
				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell leadercodecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell positioncell = row.getCell(2);
				HSSFCell namecell = row.getCell(3);
				HSSFCell gradecell = row.getCell(4);//2014-5-8 14:52:41 King 新增Grade

				/** 给数据库里面的字段赋值* */
				String leadercode = Util.cellToString(leadercodecell);
				String staffcode  = Util.cellToString(staffcodecell);
				String name = Util.cellToString(namecell);
				String position  = Util.cellToString(positioncell);
				String grade=Util.cellToString(gradecell);



				if(!Util.objIsNULL(staffcode)){
					stmt.addBatch("insert e_conslist(recruiter,staffcode,potion,name,grade,joindate,updname,upddate) values('"+leadercode+"','"+staffcode+"','"+position+"','"+name+"','"+grade+"','','"+username+"','"+DateUtils.getDateToday()+"');");
				}
			}	
			stmt.executeBatch();
			con.commit();
			logger.info(username+"-e_consultantReport-success");

			String sqls="select recruiter,staffcode,grade,potion,name from e_conslist ";
			ps=con.prepareStatement(sqls);
			ResultSet rs=ps.executeQuery();
			String staffcode="";
			String grade="";
			List holiday=null;
			while(rs.next()){
				staffcode=rs.getString("staffcode");
				grade=rs.getString("grade");
				//System.out.println(staffcode+"----"+y+"---"+ m);

				es.setStaffcode(staffcode); 
				es.setRecruiter(rs.getString("recruiter"));
				es.setPosition(rs.getString("potion"));
				es.setStaffname(rs.getString("name"));

				if(!ResignList.contains(staffcode)){//不在 离职表中
					holiday=new ArrayList();	
					int workday=0;//正常工作日
					int leaveAL=0;
					int leaveSL=0;
					int leaveother=0;
					int borrownum=0;
					int TraningNum=0;
					int MAPnum=0;
					int normal=0;
					int noshow=0;
					int late=0;
					int total = 0;  // 支付总数
					String dayString = "";
					if(DateUtils.getDay()<=9){
						dayString = "-0"+DateUtils.getDay();
					}else {
						dayString = "-"+DateUtils.getDay();
					}
					es.setMapdate(y_month+dayString);  //打卡年月 + 导入的当天-天
					int maxmonth=DateUtils.getMonthLastDay(y, m);//获取打卡日期 最大的天数
					if (!Util.objIsNULL(y_month)) {
						for(int day=1;day<=maxmonth;day++){//循环遍历最大天数
							String ym="";
							if(day<=9){
								ym=y_month+"-0"+day;
							}else{
								ym=y_month+"-"+day;
							}
							if(holidayList.contains(ym)|| DateUtils.idWeek(ym).equals(Constant.IS_WEEK)){//指定日期是否是假期
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
									if(trainingList.contains(staffcode+""+ym)){//制定staffcode当日是否有培训
										trainingList.remove(staffcode+""+ym);
										holiday.add(Constant.Training_TYPE);

										TraningNum++;
									}else{


										if(MapList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有会客
											MapList.remove(staffcode+""+ym);
											holiday.add(Constant.MAP_TYPE);
											MAPnum++;
										}else{//指定staffcode以及指定日期内没有会客

											if(BorrowList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有借卡记录
												BorrowList.remove(staffcode+""+ym);	
												holiday.add(Constant.LC);
												borrownum++;
											}else{
												String entryTime=queryEntryTime(staffcode, ym);
												if(entryTime!=null){//有打卡记录
													holiday.add(entryTime);
													if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
														normal++;
													}else{
														late++;  //迟到--记录
														if(!grade.toUpperCase().equals(Constant.GRADE))//员工不在试用期内，扣款十元
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
					int clubnum = ClubList.contains(staffcode)?1:0;	//查询是否为C-CLUB成员
					es.setCclub(clubnum+"");	
					if(clubnum>0){
						ClubList.remove(staffcode);
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
					es.setE_noTime(df1.format((1)-((late/(double)workday)+(noshow/(double)workday))));
					es.setConvoy(staffcode.substring(2));
					stmt.addBatch("insert into e_consrepot values('"+es.getMapdate()+"','"+es.getRecruiter()+"','"+es.getStaffcode()+
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
							"','"+es.getE_lateness()+"','"+es.getE_noshow()+"','"+es.getE_noTime()+"','"+username+"','"+es.getMapdate()+"')");
					if(num%300==0){
						stmt.executeBatch();
					}
					num++;
				}else{//在 离职表中
					ResignList.remove(staffcode);
					logger.info(staffcode+"在离职表中--------------");
				}
			}







			stmt.executeBatch();
			con.commit();





		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}





		return num;

	}


	@SuppressWarnings("unchecked")
	public int saveConsultant(String filename, InputStream os, String username) {
		Econsreport es=new Econsreport();
		int num = 0;
		int beginRowIndex = 1;// 开始读取数据的行数(第一行算作0)
		int totalRows = 0;// 总行数
		String y_month = "";
		//查询打卡记录表中的 年-月
		List<String> ClubList=getClub();//C-Club 人员列表
		List<String> holidayList=getholiday();//假期列表
		List<String> ResignList=getResignList();//离职人员列表
		List<String> MapList=getMap();//Map 人员列表
		List<String> BorrowList=getBorrow();//借卡人员列表
		List<String> trainingList=getTraining();// Training 会议人员列表


		try {
			con = DBManager.getCon();
			Util.deltables("e_conslist");
			y_month = getYMonth(); 	
			Util.deltables("e_consrepot where mapdate like '"+y_month+"%'");/** 根据e_attendence表，删除consreport表 * */
			con.setAutoCommit(false);









			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
			totalRows = sheet.getLastRowNum();// 获取总行数
			stmt.clearBatch();




			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行
				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell leadercodecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell positioncell = row.getCell(2);
				HSSFCell namecell = row.getCell(3);
				HSSFCell gradecell = row.getCell(4);//2014-5-8 14:52:41 King 新增Grade

				/** 给数据库里面的字段赋值* */
				String leadercode = Util.cellToString(leadercodecell);
				String staffcode  = Util.cellToString(staffcodecell);
				String name = Util.cellToString(namecell);
				String position  = Util.cellToString(positioncell);
				String grade=Util.cellToString(gradecell);

				es.setStaffcode(staffcode); 
				es.setRecruiter(leadercode);
				es.setPosition(position);
				es.setStaffname(name);




				if(!Util.objIsNULL(staffcode)){
					//System.out.println(i+"====staffcode"+staffcode);
					stmt.addBatch("insert e_conslist(recruiter,staffcode,potion,name,grade,joindate,updname,upddate) values('"+leadercode+"','"+staffcode+"','"+position+"','"+name+"','"+grade+"','','"+username+"','"+DateUtils.getDateToday()+"')");
					if(!ResignList.contains(staffcode)){//不在 离职表中
						List holiday=new ArrayList();	
						int workday=0;//正常工作日
						int leaveAL=0;
						int leaveSL=0;
						int leaveother=0;
						int borrownum=0;
						int TraningNum=0;
						int MAPnum=0;
						int normal=0;
						int noshow=0;
						int late=0;
						int total = 0;  // 支付总数
						String dayString = "";
						if(DateUtils.getDay()<=9){
							dayString = "-0"+DateUtils.getDay();
						}else {
							dayString = "-"+DateUtils.getDay();
						}
						es.setMapdate(y_month+dayString);  //打卡年月 + 导入的当天-天
						int maxmonth=DateUtils.getMonthLastDay(y, m);//获取打卡日期 最大的天数
						if (!Util.objIsNULL(y_month)) {
							for(int day=1;day<=maxmonth;day++){//循环遍历最大天数
								String ym="";
								if(day<=9){
									ym=y_month+"-0"+day;
								}else{
									ym=y_month+"-"+day;
								}
								if(holidayList.contains(ym)|| DateUtils.idWeek(ym).equals(Constant.IS_WEEK)){//指定日期是否是假期
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
										if(trainingList.contains(staffcode+""+ym)){
											trainingList.remove(staffcode+""+ym);
											holiday.add(Constant.Training_TYPE);
											TraningNum++;
										}else{


											if(MapList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有会客
												MapList.remove(staffcode+""+ym);
												holiday.add(Constant.MAP_TYPE);
												MAPnum++;
											}else{//指定staffcode以及指定日期内没有会客

												if(BorrowList.contains(staffcode+""+ym)){//指定staffcode以及指定日期内是否有借卡记录
													BorrowList.remove(staffcode+""+ym);	
													holiday.add(Constant.LC);
													borrownum++;
												}else{
													String entryTime=queryEntryTime(staffcode, ym);
													if(entryTime!=null){//有打卡记录
														holiday.add(entryTime);
														if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
															normal++;
														}else{
															late++;  //迟到--记录
															if(!grade.toUpperCase().equals(Constant.GRADE))//员工不在试用期内，扣款十元
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
						int clubnum = ClubList.contains(staffcode)?1:0;	//查询是否为C-CLUB成员
						es.setCclub(clubnum+"");	
						if(clubnum>0){
							ClubList.remove(staffcode);
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
						es.setE_noTime(df1.format((1)-((late/(double)workday)+(noshow/(double)workday))));
						es.setConvoy(staffcode.substring(2));
						stmt.addBatch("insert into e_consrepot values('"+es.getMapdate()+"','"+es.getRecruiter()+"','"+es.getStaffcode()+
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
								"','"+es.getE_lateness()+"','"+es.getE_noshow()+"','"+es.getE_noTime()+"','"+username+"','"+es.getMapdate()+"')");
						if(i%300==0){
							stmt.executeBatch();
						}
						num++;
					}else{//在 离职表中
						ResignList.remove(staffcode);
						logger.info(staffcode+"在离职表中--------------");
					}
				}

			}
			stmt.executeBatch();
			logger.info(username+"-e_consultantReport-success");
			con.commit();
			stmt.close();

			//System.out.println(DateUtils.getNowDateTime());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(username+"在EConsultantDaoImpl.saveConsultant()中"+"插入E_consList异常！" + e);
			return num;
		} finally {
			ClubList=holidayList=ResignList=MapList=BorrowList=null;
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
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			sql = "select DATE_FORMAT(entryDate,'%Y') as years ,DATE_FORMAT(entryDate,'%m') as months from  e_attendence  order by entryDate desc limit 0,1 ";
			PreparedStatement	pss  = conn.prepareStatement(sql);
			ResultSet rs= null;
			rs=pss.executeQuery();
			if(rs.next()){
				nums=rs.getString("years");
				months=rs.getString("months");
			}else{
				nums=null;
				months=null;
			}
			y= new Integer(nums);
			m= new Integer(months);
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询e_attendence表异常");
			nums=null;
			months=null;
		}finally{

			DBManager.closeCon(conn);
		}
		return nums+"-"+months;
	}

	/***
	 * 是否为EClub的成员
	 */
	public List<String> getClub() {
		List<String> staffcodeList=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			sql = "select staffcode from e_club ";
			PreparedStatement	pss = conn.prepareStatement(sql);
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				staffcodeList.add(rs.getString("staffcode"));
			} 
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询e_club表异常");
		}finally{
			DBManager.closeCon(conn);
		}
		return staffcodeList;
	}

	/**
	 * 是否在指定时间为假期
	 */
	public List<String> getholiday() {
		List<String> edateList=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			pss = conn.prepareStatement("select edate from e_exceptdate ");
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				edateList.add(rs.getString("edate"));
			} 
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询e_exceptdate表异常"+e);
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return edateList;
	}
	/**
	 * 该staffcode在指定日期是否请假
	 */
	public String isLeave(String staffcode, String date) {
		String nums="";
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			sql = "select nature from e_leave where staffcode='"+staffcode+"' and leavedate='"+date+"'";
			pss = conn.prepareStatement(sql);
			ResultSet rs= null;
			rs=pss.executeQuery();
			if(rs.next()){
				nums=rs.getString("nature");
			}else{
				nums=null;
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询e_leave表异常");
		}finally{
			DBManager.closeCon(conn); 
		}
		return nums;
	}
	/**
	 * 根据staffcode查询当月请假记录
	 * @author kingxu
	 * @date 2015-10-14
	 * @param staffcode
	 * @return
	 * @return String
	 */
	public List<String> findLeavebystaffcode(String staffcode) {
		List<String> list=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			String sql2 = "select concat(staffcode,leavedate,nature)as nature from e_leave where staffcode='"+staffcode+"' ";
			pss = conn.prepareStatement(sql2);
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				list.add(rs.getString("nature"));
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询e_leave表异常");
		}finally{
			DBManager.closeCon(conn); 
		}
		return list;
	}
	/**
	 * 该staffcode在指定日期内是否有meeting
	 */
	public List<String> getMap() {
		List<String> MapList=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			//pss = conn.prepareStatement( "select concat(staffcode,meetingdate)as codeDate from emap where  DATE_FORMAT(meetingdate,'%Y-%m')=DATE_FORMAT('"+y_month+"','%Y-%m')");
			pss = conn.prepareStatement( "select concat(staffcode,meetingdate)as codeDate from emap ");
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				MapList.add(rs.getString("codeDate"));
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询emap表异常");
		}finally{
			DBManager.closeCon(conn);
		}
		return MapList;
	}
	/**
	 * 查看该staffcode是否离职
	 */


	/**
	 * 查看该staffcode是否离职
	 */
	public List<String> getResignList() {
		List<String> ResignList=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			pss = conn.prepareStatement("select staffcode from e_resignlist ");
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				ResignList.add(rs.getString("staffcode"));
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询resign表异常"+e.toString());
			return ResignList;

		}finally{
			DBManager.closeCon(conn);
		}
		return ResignList;
	}

	/**
	 * 查询该staffcode在指定日期内是否打卡
	 */
	public String queryEntryTime(String staffcode, String date) {
		String nums="";
		Connection conn=null;
		try{
			PreparedStatement pss=null;
			conn=DBManager.getCon();
			sql = "select * from e_attendence where staffcode='"+staffcode+"' and entryDate='"+date+"'";
			pss = conn.prepareStatement(sql);
			ResultSet rs= null;
			rs=pss.executeQuery();
			if(rs.next()){
				nums=rs.getString("entryTime");
			}else{
				nums=null;
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询entryTime表异常");
		}finally{
			DBManager.closeCon(conn); 
		}
		return nums;
	}
	/**
	 * 根据staff获取当月打卡数据
	 * @author kingxu
	 * @date 2015-10-14
	 * @param staffcode
	 * @return
	 * @return List<String>
	 */
	public List<String> querstaffyEntryTime(String staffcode) {
		List<String> list=new ArrayList<String>();
		Connection conn=null;
		try{
			PreparedStatement pss=null;
			conn=DBManager.getCon();
			String sql2 = "select concat(staffcode,entryDate,entryTime) as codeDate from e_attendence where staffcode='"+staffcode+"' ";
			pss = conn.prepareStatement(sql2);
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				//nums=rs.getString("entryTime");
				list.add(rs.getString("codeDate").replace("/", "").replace("/", ""));
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询entryTime表异常");
		}finally{
			DBManager.closeCon(conn); 
		}
		return list;
	}
	/**
	 * 根据staffcode 日期获取打卡时间
	 * @author kingxu
	 * @date 2015-10-14
	 * @param list
	 * @param codeDate
	 * @return
	 * @return String
	 */
	public String findentryTime(List<String> list,String codeDate){
		String entryTime="";
		for(int i=0;i<list.size();i++ ){
			if(list.get(i).indexOf(codeDate)==0){
				entryTime=list.get(i).replace(codeDate,"");
				break;
			}
		}
		return entryTime;
	}
	/**
	 * 根据staffcode 日期获取请假类型
	 * @author kingxu
	 * @date 2015-10-14
	 * @param list
	 * @param codeDate
	 * @return
	 * @return String
	 */
	public String findLeaveType(List<String> list,String codeDate){
		String entryTime="";
		for(int i=0;i<list.size();i++ ){
			if(list.get(i).indexOf(codeDate)==0){
				entryTime=list.get(i).replace(codeDate,"");
				break;
			}
		}
		return entryTime;
	}

	/**
	 * 查询该staffcode在指定日期内是否有借卡记录
	 */

	/**
	 * 查询该staffcode在指定日期内是否有借卡记录
	 */
	public List<String> getBorrow() {
		List<String> BorrowList=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			//	pss = conn.prepareStatement("select concat(staffcode,borrowdate) as codeDate from e_borrow  where DATE_FORMAT(meetingdate,'%Y-%m')=DATE_FORMAT('"+y_month+"','%Y-%m')");
			pss = conn.prepareStatement("select concat(staffcode,borrowdate) as codeDate from e_borrow  ");
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				BorrowList.add(rs.getString("codeDate"));
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询resign表异常");
		}finally{
			DBManager.closeCon(conn);
		}
		return BorrowList;
	}
	/**
	 * 查询该staffcode在指定日期内是否有借卡记录
	 */
	public List<String> getTraining() {
		List<String>TrainingList=new ArrayList<String>();
		Connection conn=null; 
		try{
			conn=DBManager.getCon();
			PreparedStatement pss=null;
			//	pss = conn.prepareStatement("select concat(staffcode,borrowdate) as codeDate from e_borrow  where DATE_FORMAT(meetingdate,'%Y-%m')=DATE_FORMAT('"+y_month+"','%Y-%m')");
			pss = conn.prepareStatement("select concat(staffcode,DATE_FORMAT(trainingDate,'%Y-%m-%d')) as codeDate from e_training  where sfyx='Y' ");
			ResultSet rs= null;
			rs=pss.executeQuery();
			while(rs.next()){
				TrainingList.add(rs.getString("codeDate"));
			}
			pss.close();
			rs.close();
		}catch(Exception e){
			logger.error("查询resign表异常");
		}finally{
			DBManager.closeCon(conn);
		}
		return TrainingList;
	}


}
