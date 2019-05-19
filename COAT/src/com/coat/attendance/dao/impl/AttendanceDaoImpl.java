package com.coat.attendance.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.ReadExcel;
import util.Util;

import com.coat.attendance.dao.AttendanceDao;

import dao.common.BaseDao;
import entity.Econsreport;

public class AttendanceDaoImpl extends BaseDao implements AttendanceDao {
Logger logger=Logger.getLogger(AttendanceDaoImpl.class);

	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	public int saveResign(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from e_resignlist";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert e_resignlist(staffcode,staffname,recruiter,position,position_E,alias,addname,adddate) values(?,?,?,?,?,?,?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[8];
					for(int j=0;j<list.get(i).size();j++){
						object[j]=list.get(i).get(j);
					}
					object[object.length-2]=username;
					object[object.length-1]=nowdate;
					 list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	
	public int saveBorrow(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from e_borrow";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert e_borrow(staffcode,borrowDate,lc,addname,adddate) values(?,?,?,?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[5];
					for(int j=0;j<list.get(i).size();j++){
						if(j==1){
							object[j]=list.get(i).get(j).toString().replace(" 00:00:00", "");
						}else{
							object[j]=list.get(i).get(j);
						}
					}
					object[object.length-2]=username;
					object[object.length-1]=nowdate;
					 list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	
	public int savecclub(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from e_club";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert e_club(staffcode,updname,upddate) values(?,?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[3];
					for(int j=0;j<list.get(i).size();j++){
						object[j]=list.get(i).get(j);
					}
					object[object.length-2]=username;
					object[object.length-1]=nowdate;
					 list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	public int saveEexceptionDate(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from e_exceptdate";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert e_exceptdate(edate,ecode,events,updname,upddate,remark1) values(?,?,?,?,?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[6];
					for(int j=0;j<list.get(i).size();j++){
						if(j==0){
							object[j]=list.get(i).get(j).toString().replace(" 00:00:00", "");
						}else{
							object[j]=list.get(i).get(j);
						}
					}
					object[object.length-3]=username;
					object[object.length-2]=nowdate;
					object[object.length-1]="Y";
					list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	public int saveleaveRecord(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from e_leave";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert e_leave(staffcode,leavedate,nature,num,codeadd,rdate,remark,rdate2,addname,adddate) values(?,?,?,'',?,'','','',?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[6];
					for(int j=0;j<list.get(i).size();j++){
						
						if(j==1){
							object[j]=list.get(i).get(j).toString().replace(" 00:00:00","");
						}else{
							object[j]=list.get(i).get(j);
						}
					}
					object[object.length-3]=object[0]+""+object[2];
					object[object.length-2]=username;
					object[object.length-1]=nowdate;
					list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	public int saveEmap(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from emap";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert emap(staffcode,meetingdate,updname,upddate) values(?,?,?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[4];
					for(int j=0;j<list.get(i).size();j++){
						if(j==1){
							object[j]=list.get(i).get(j).toString().replace(" 00:00:00", "");
						}else{
							object[j]=list.get(i).get(j);
						}
					}
					object[object.length-2]=username;
					object[object.length-1]=nowdate;
					list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	public int saveTrainingList(List<List<Object>> list, String username) throws Exception {
		int num=-2;
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="update e_training set sfyx='N' where sfyx='Y'";
			update(sql, null);
			if(!Util.objIsNULL(list)){
				sql="insert e_training(staffcode,trainingDate,creator,createDate,sfyx) values(?,?,?,?,'Y');";
				List<Object[]> list2=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i++){
					Object[] object=new Object[4];
					for(int j=0;j<list.get(i).size();j++){
						object[j]=list.get(i).get(j);
					}
					object[object.length-2]=username;
					object[object.length-1]=nowdate;
					list2.add(object);
				}
				num=calculateNum(batchInsert(sql, list2));
				if(num!=list.size()){
					num=-2;
				}
				
			}
			list=null;
			sumbitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction();
		}finally{
			closeConnection();
		}
		return num;
	}
	/**
	 * 上传Attendance数据
	 * @author kingxu
	 * @date 2015-10-16
	 * @param list
	 * @param username
	 * @return
	 * @throws SQLException
	 * @return String
	 */
	public String uploadAttendance(List<List<Object>> list, String username) throws SQLException{
		String result="";
		try{
			String nowdate=DateUtils.getNowDateTime();
			openTransaction();
			String sql="delete from e_attendence";
			update(sql, null);
			int num=0;
			if(!Util.objIsNULL(list)){
				sql="insert e_attendence(cardno,staffcode,staffname,entryDate,entryTime,entryMethod,occur,in_out,entrystatus,updname,upddate) values(?,?,?,?,?,?,?,?,?,?,?);";
				List<Object[]> list2=new ArrayList<Object[]>();
				Object[] object=null;
				for(int i=0;i<list.size();i++){
					object=new Object[11];
					object[0]=list.get(i).get(0);
					if(!Util.objIsNULL(list.get(i).get(1))){
						object[1]=list.get(i).get(1);
						object[2]=list.get(i).get(4);
						object[3]=list.get(i).get(6);
						object[4]=list.get(i).get(7);
						object[5]=list.get(i).get(10);
						object[6]=list.get(i).get(12);
						object[7]=list.get(i).get(13);
						object[8]=list.get(i).get(14);
						object[object.length-2]=username;
						object[object.length-1]=nowdate;
						list2.add(object);
					}
					if(i%2000==0){
						num+=calculateNum(batchInsert(sql,list2));
						list2=new ArrayList<Object[]>();
					}else if(i==list.size()-1){
						num+=calculateNum(batchInsert(sql,list2));
						list2=new ArrayList<Object[]>();
					}
				}
				
				if(num!=list.size()){
					result=Util.getMsgJosnObject("success", "上传数据"+list.size()+"条,成功保存"+num+"条.");
				}else{
					result=Util.getMsgJosnObject("success", "上传数据成功,保存"+num+"条.");
				}
				list=null;
				list2=null;
			}
			sumbitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			closeConnection();
		}
		return result;
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
	public String findLeaveType(List<Object> list,String codeDate){
		String entryTime="";
		for(int i=0;i<list.size();i++ ){
			if(list.get(i).toString().indexOf(codeDate)==0){
				entryTime=list.get(i).toString().replace(codeDate,"");
				break;
			}
		}
		return entryTime;
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
	public String findentryTime(List<Object> list,String codeDate){
		String entryTime="";
		for(int i=0;i<list.size();i++ ){
			if(list.get(i).toString().indexOf(codeDate)==0){
				entryTime=list.get(i).toString().replace(codeDate,"");
				break;
			}
		}
		return entryTime;
	}
	
	
	
	/**
	 * 计算Consultant 整月数据
	 * 逻辑顺序【是否离职-->是否new Comer-->是否是假期-->是否请假-->是否有培训-->是否有会客-->是否有借卡-->是否试用期-->是否迟到或缺勤-->C-Club】
	 * @author kingxu
	 * @date 2015-10-16
	 * @param filename
	 * @param username
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String cacularAttendance(String filename,String username) throws Exception{
		String result;
		Econsreport es=new Econsreport();
		int num = 0;
		int y=0;//年
		int m=0;//月
		String y_month = "";
		long before=0;
		long after=0;
		//基础数据准备

		try {
			before=System.currentTimeMillis(); // 标记开始时
			String sql="select staffcode from e_club";
			List<Object> clubList=findDate2(sql);//C-Club 人员列表
			sql="select edate from e_exceptdate";
			List<Object> holidayList=findDate2(sql);//假期列表
			sql="select staffcode from e_resignlist";
			List<Object> ResignList=findDate2(sql);//离职人员列表
			sql="select concat(staffcode,meetingdate)as codeDate from emap";
			List<Object> MapList=findDate2(sql);//离职人员列表
			sql="select concat(staffcode,borrowdate) as codeDate from e_borrow";
			List<Object> BorrowList=findDate2(sql);//离职人员列表
			sql="select concat(staffcode,DATE_FORMAT(trainingDate,'%Y-%m-%d')) as codeDate from e_training  where sfyx='Y'";
			List<Object> trainingList=findDate2(sql);//离职人员列表
			sql="select * from (select DATE_FORMAT(entryDate,'%Y') as years ,DATE_FORMAT(entryDate,'%m') as months from  e_attendence  order by entryDate desc limit 0,1)a ";
			Map<String,Object> YearMonthMap=findMap(sql);//通过查询Attendance表获取本次就算年月
			if(Util.objIsNULL(YearMonthMap)||YearMonthMap.size()<=0){
				throw new RuntimeException("未上传Attendance数据");
			}
			y_month=YearMonthMap.get("years")+"-"+YearMonthMap.get("months");
			y=Integer.parseInt(YearMonthMap.get("years")+"");
			m=Integer.parseInt(YearMonthMap.get("months")+"");
			int maxmonth=DateUtils.getMonthLastDay(y, m);//获取打卡日期 最大的天数
			openTransaction();
			
			sql="delete from e_conslist";
			update2(sql);
			sql="delete from e_consrepot where mapdate like '"+y_month+"%'";
			update2(sql);
		
			Statement stmt = getConnction().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			stmt.clearBatch();
			List<List<Object>> conslist=ReadExcel.readExcel(new File(filename), 0, 5, 1);
			List<String> holiday=null;
			List<Object> l=null;
			List<Object> entryList=null;//个人当月打卡列表
			List<Object> leaveList=null;//个人请假打卡列表
			for(int i=0;i<conslist.size();i++){
				l=conslist.get(i);
				/** 给数据库里面的字段赋值* */
				String leadercode =l.get(0)+"";
				String staffcode  = l.get(1)+"";
				String position  = l.get(2)+"";
				String name = l.get(3)+"";
				String grade=l.get(4)+"";
				String joindate=(l.get(5)+"").replace(" 00:00:00", "");
				
				
				if(!Util.objIsNULL(staffcode)&&!Util.objIsNULL(joindate)){
					stmt.addBatch("insert e_conslist(recruiter,staffcode,potion,name,grade,joindate,updname,upddate) values('"+leadercode+"','"+staffcode+"','"+position+"','"+name+"','"+grade+"','"+joindate+"','"+username+"','"+DateUtils.getNowDateTime()+"');");
				
					es.setStaffcode(staffcode); 
					es.setRecruiter(leadercode);
					es.setPosition(position);
					es.setStaffname(name);
					if(!ResignList.contains(staffcode)){//不在 离职表中
						holiday=new ArrayList<String>();	
						int workday=0;//正常工作日
						int wday=-1;//仅为打卡第二天做判断
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
					
						if (!Util.objIsNULL(y_month)) {
							String sql2="select concat(staffcode,entryDate,entryTime) as codeDate from e_attendence where staffcode='"+staffcode+"'";
							entryList=findDate2(sql2);//个人当月打卡列表
							sql2="select concat(staffcode,leavedate,nature)as nature from e_leave where staffcode='"+staffcode+"'";
							leaveList=findDate2(sql2);//个人请假打卡列表
	
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
										if(DateUtils.getTwoDay(ym, joindate)>0){
											holiday.add("NA");//入职前
										}else if(DateUtils.getTwoDay(ym, joindate)==0){
											holiday.add(Constant.NC);//入职当天
											workday++;
											wday=workday;
										}else if(DateUtils.getTwoDay(ym, joindate)<0){
											if(wday>0){
												holiday.add("Second Day");//入职第二天(工作日)
												workday++;
												wday=0;
											}
										}
										if(wday<0 && DateUtils.getTwoDay(ym, joindate)<0 ){
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
															if(!Util.objIsNULL(entryTime)){//有打卡记录
																holiday.add(entryTime);
																if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
																	normal++;
																}else{
																	late++;  //迟到--记录
																	if(!grade.toUpperCase().equals(Constant.GRADE)){//员工不在试用期内，扣款十元
																		total= total+10; //扣10块钱
																	}
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
									}else{
										if(wday==0){
											wday=-1;
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
						int clubnum = clubList.contains(staffcode)?1:0;	//查询是否为C-CLUB成员
						es.setCclub(clubnum+"");	
						if(clubnum>0){
							clubList.remove(staffcode);
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
						if(workday==0){
							es.setE_lateness("0%");
							es.setE_noshow("0%");
							es.setE_noTime("100%");
						}else{
							es.setE_lateness(df1.format((late/(double)workday)));
							es.setE_noshow(df1.format((noshow/(double)workday)));
							es.setE_noTime(df1.format((1)-((late/(double)workday)+(noshow/(double)workday))));
						}
					
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



			}
			logger.info(username+"-e_consultantReport-success");
			after = System.currentTimeMillis(); // 标记结束时
			
			DecimalFormat df1 = new DecimalFormat("0");
			String nums = df1.format((after-before)/1000);
			result=Util.getMsgJosnObject("success", nums+"秒内成功计算数据"+conslist.size()+"条.");
			stmt.executeBatch();

			entryList=null;//个人当月打卡列表
			leaveList=null;//个人请假打卡列表
			l=null;
			holiday=null;
			clubList=null;
			holidayList=null;
			ResignList=null;
			MapList=null;
			BorrowList=null;
			trainingList=null;
			conslist=null;
			
			
			sumbitTransaction();
		} catch (SQLException e) {
			rollbackTransaction();
			result=Util.joinException(e);
		} catch (ClassNotFoundException e) {
			rollbackTransaction();
			result=Util.joinException(e);
		} catch (FileNotFoundException e) {
			rollbackTransaction();
			//result=Util.getMsgJosnObject("exception", "Consultant List -文件不存在.");
			result=Util.joinException(e);
		}catch (IOException e) {
			rollbackTransaction();
			result=Util.joinException(e);
		}catch (Exception e) {
			rollbackTransaction();
			result=Util.joinException(e);
		}finally{
			closeConnection();
		}
		return result;
	
	}

 
	
	public static void main(String[] args) {
		
	}
}
