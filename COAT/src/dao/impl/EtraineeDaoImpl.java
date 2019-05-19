package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import dao.EtraineeDao;
import entity.EtraineeList;
import entity.TraineeList;

/**
 * EtraineeDaoImpl
 * 
 * @author Wilson
 * 
 */
public class EtraineeDaoImpl implements EtraineeDao {

	Logger logger = Logger.getLogger(EtraineeDaoImpl.class);
	Connection con = null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String sql = "";
	int y = 0;
	int m = 0;
	int d = 0;
	int fistday = 1;
	/**
	 * 保存trainee表
	 */
	public int saveTrainee(String filename, InputStream os) {
		PreparedStatement ps = null;
		String sql = "";
		EtraineeList es = new EtraineeList();
		int num = 0;
		int beginRowIndex = 11;// 开始读取数据的行数
		int totalRows = 0;// 总行数
	
		Util.deltables("e_taineelist");
		// 移至下方删除 Util.deltables("e_traineerepot where mapdate like '"+DateUtils.delMonthafter(DateUtils.getDateToday())+"%'");
		/** 删除trainee表 * */
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet(Constant.TRAINEE_SHEET);// 读取Sheet 页码：TRAINEE_SHEET
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行

				/** 获取Excel里面的指定单元格数据 下标从0开始* */
				HSSFCell cardcodecell = row.getCell(0);
				HSSFCell staffcodecell = row.getCell(1);
				HSSFCell nameCell = row.getCell(2);
				HSSFCell joindateCell = row.getCell(3);
				HSSFCell locationCell = row.getCell(4);
				HSSFCell pomotionCell = row.getCell(5); //第六列为空
				HSSFCell leaderCell = row.getCell(7);
				HSSFCell statusCell = row.getCell(8);
				HSSFCell aliasCell = row.getCell(9);
				HSSFCell groupCell = row.getCell(10);
				HSSFCell lnameCell = row.getCell(11);
				HSSFCell stage1Cell = row.getCell(12);
				HSSFCell groupjoinAd6Cell = row.getCell(13);
				HSSFCell promitionAd3Cell = row.getCell(14);
				HSSFCell rdateCell = row.getCell(15);

				/** 给数据库里面的字段赋值* */
				String cardcode = Util.cellToString(cardcodecell);
				String staffcode  = Util.cellToString(staffcodecell);
				String name = Util.cellToString(nameCell);
				String joindate = Util.cellToString(joindateCell);
				String location = Util.cellToString(locationCell);
				String pomotion  = Util.cellToString(pomotionCell);
				String leader  = Util.cellToString(leaderCell);
				String status  = Util.cellToString(statusCell);
				String alias  = Util.cellToString(aliasCell);
				String group  = Util.cellToString(groupCell);
				String lname  = Util.cellToString(lnameCell);
				String stage1 = Util.cellToString(stage1Cell);
				String groupjoinAd6  = Util.cellToString(groupjoinAd6Cell);
				String promitionAd3   = Util.cellToString(promitionAd3Cell);
				String rdate   = Util.cellToString(rdateCell);
				//if(isResignList(staffcode)!=1){//不在 离职表中
				if (!Util.objIsNULL(staffcode)) { 
					sql = "insert e_taineelist values('"+cardcode+"','"+staffcode+"','"+name+"','"+joindate+"','"+location+"','"+pomotion+"','"+leader+"','"+status+"','"+alias+"','"+group+"','"+lname+"','"+stage1+"','"+groupjoinAd6+"','"+promitionAd3+"','"+rdate+"','admin','"+DateUtils.getDateToday()+"','Y')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入e_taineelist成功！");
						//查询打卡记录表中的 年-月
						String y_month = getYMonth(); 
						//获取最小天
						getAttendDay();
						//执行一次 清除历史计算数据 
						//2013年4月22日15:24:57 wilson 修改为根据attendence表中的打卡月份删除
						if (i == beginRowIndex) {
							Util.deltables("e_traineerepot where mapdate like '"+y_month+"%'");
							
						}
						
						String dayString = "";
						//取getMonthLastDay  非getMaxDay
						/**
						 * int maxmonth=DateUtils.getMonthLastDay(y, m);//获取当前日期的本月最大天数  
						 * 2013年4月22日15:24:57 wilson 修改为打卡记录表中的 最大天数
						 * 2013-07-02 改回获取当月最大天数
						 */
						int maxmonth=DateUtils.getMonthLastDay(y, m);//获取当前日期的本月最大天数  
						//stage2 为空时取 stage1（joindate+3个月） ， stage2不为空时，取promitionAd3 （为标准的stage2 date） ----超过则不计
						String stageDate ="";
						String endDate ="";
						if (Util.objIsNULL(groupjoinAd6)) {
							stageDate = DateUtils.addTWODate(joindate);//DateUtils.addOneDate(joindate);
							endDate = DateUtils.afterDate(DateUtils.addMonth(joindate)); //joindate + 3Month -1day
						}else{
						}
						int fivenum =0;  //累计超过5天没打卡  记录
						int weekNumber=DateUtils.isWeekOfnumber(y_month+"-01");
						if(DateUtils.getDay()<=9){
							dayString = "-0"+DateUtils.getDay();
						}else {
							dayString = "-"+DateUtils.getDay();
						}
						es.setMapdate(y_month+dayString);  //打卡年月 + 导入的当天-天
						if (!Util.objIsNULL(y_month) && fistday >= 1) {
							// 2013年4月22日15:24:57 wilson 修改为打卡记录表中的 最大天数 d = maxmonth
							for(int day = fistday ; day <= maxmonth; day++){//循环遍历最大天数
								String ym="";
								if(day<=9){
									ym=y_month+"-0"+day;
								}else{
									ym=y_month+"-"+day;
								}
								if(!Util.objIsNULL(groupjoinAd6)){
									if(DateUtils.compare_date(stage1,ym)==1 ){
										stageDate = DateUtils.addTWODate(joindate);//DateUtils.addOneDate(joindate);
										endDate = DateUtils.afterDate(DateUtils.addMonth(joindate)); //joindate + 3Month -1day
									}
									if(DateUtils.compare_date(ym,stage1)==1 ){ //S1 date超过了打卡日期间， 取S1 至 S2
 										if(DateUtils.compare_date(stage1,pomotion)==1 ){
											stageDate = stage1;
										}else {
											stageDate = pomotion;
										}
										if(DateUtils.compare_date(promitionAd3, groupjoinAd6)==1 ){
											endDate = promitionAd3;
										}else {
											endDate = groupjoinAd6;
										}
									}
								}
								int rntOne= DateUtils.compare_date(stageDate, endDate, ym);
								String isweek = DateUtils.idWeek(ym);
								String holiday = isholiday(ym);
								//System.out.println("===========ym=============="+ym);
								String entryTime=queryEntryTime(staffcode, ym);//获取打卡记录
								if(rntOne == 0){//打卡日期不在 joindate <= ym <=stage1  or  stage1 <= ym  <= stage2
									 //打卡日期不在内--不处理
									 fivenum = 0;
								}else{
								 	//月份的第一天 在星期几 并返回 1-5 的天數
									if(weekNumber>1&&weekNumber<=5){//判断是否在星期二到星期五之内（包括星期五）
										if(ym.equals(y_month+"-0"+(5-weekNumber+2)) && fivenum==(5-weekNumber+1)){//判断当月一号所在的星期数到星期五之内是否都未打卡
											for(int h=1;h<=weekNumber-1;h++){//循环上月在本星期内的所有天数
												if(isShoot(staffcode, DateUtils.betweenDate(y_month+"-01", -h))>0){//判断上月所在本星期内的所有天数中是否未打卡
													fivenum++;
												}else{//有一天打卡，退出循环
													h=weekNumber;
												}
											}
										}
									} 
									if(ym.equals(y_month+"-01")&&(weekNumber==6 || weekNumber==7)){//月份的第一天是星期六或星期日
										int k=1;//默认只减一天
										if(weekNumber==7){//当当前月份的第一天是星期天
											k=2;//多减一天
										}
										for(int h=k;h<=weekNumber-1;h++){//循环上月在本星期内的所有天数
											if(isShoot(staffcode, DateUtils.betweenDate(y_month+"-01", -h))>0){//判断上月所在本星期内的所有天数中是否未打卡
												fivenum++;
											}else{//有一天打卡，退出循环
												h=weekNumber;
											}
										}
									}
									//循環 獲取前一個月的，同一週的有效工作日
									if(isweek.equals(Constant.IS_WEEK)){//周末
									//	System.out.println("-----fivenum------------"+fivenum);
										if(fivenum >= Constant.FIVE && fivenum <= Constant.SEVEN){//当未打卡次数大于5天也就是星期一到星期五都未打卡
											//System.out.println("-----记录连续5天不打卡------------"+fivenum);
											//记录连续5天不打卡
											es.setCode(staffcode.substring(2));
											es.setStaffcode(staffcode);
											es.setLeave_type("");  
											es.setLdatestart(ym);
											es.setLdateend(ym);
											es.setDaynum("1");
											es.setD("5天没打卡");
											es.setLnum("1");
											es.setSfyx("Y");
											saveTraineeReport(es);
											num++;
											if(fivenum >= Constant.SEVEN){ 
												//大于等于7-1次， 清零
												fivenum = 0;
												//修改本周 N H 数据 将标志 置为Y 
												updTraineeReport(staffcode);
											}else {
												fivenum++;  //累计5次不打卡
											}
										}else{//当天已是周末，而未打卡记录次数（fivenum）没有大于5
											//删除本周 H N 的数据
											delTraineeReport(staffcode);
											fivenum = 0;
										}
									}else if(!Util.objIsNULL(holiday) ){//为节假日时， 判断是否为 "CM" 记录
										if(fivenum >= Constant.SEVEN){ 
											//大于等于7-1次， 清零
											fivenum = 0;
										}
										if(holiday.equals("CM")){
											if(!Util.objIsNULL(entryTime)){
												//---不为空，有打卡，不处理
												es.setStaffcode("");
											}else{
												//没有打卡——记录
												logger.info("===================为‘CM’ date,没有打卡==处理！");
												//节假日-  
												es.setCode(staffcode.substring(2));
												es.setStaffcode(staffcode);
												es.setLeave_type("");  
												es.setLdatestart(ym);
												es.setLdateend(ym);
												es.setDaynum("1");
												es.setD("CM - 未打卡！");
												es.setLnum("1");
												es.setSfyx("Y");
												saveTraineeReport(es);
												num++;
												fivenum++; //累计5次不打卡
											}
										}else{
											//假期为其他类型 -- 没打卡-累加
											if(Util.objIsNULL(entryTime)){
												//-----holiday,没有打卡==处理 标记为 N待定------------
												es.setCode(staffcode.substring(2));
												es.setStaffcode(staffcode);
												es.setLeave_type("");  
												es.setLdatestart(ym);
												es.setLdateend(ym);
												es.setDaynum("1");
												es.setD("H");
												es.setLnum("1");
												es.setSfyx("H");
												saveTraineeReport(es);
												//没有打卡——holiday--记录
												logger.info("===================holiday,没有打卡==处理！");
												//节假日-  
												fivenum++;
											}
										}
									}else{//非节假日
											int isemap= isMap(staffcode, ym);
											//System.out.println("-----------isemap----------"+isemap);
											if(isemap==1){//有会客
												//有会客 --不处理
												es.setStaffcode("");
											}else{//没有会客
												String type=isLeave(staffcode, ym);//获取请假类型
												//System.out.println("-----------type----------"+type);
												if(!Util.objIsNULL(type)){//请假
													// if(type.equals(Constant.LEAVE_TYPE_STL)){ 
														 //STL请假---不处理
													// }else{
														logger.info("==========请假========处理！");
														//记录请假为 “NPL” 类型的记录
														es.setCode(staffcode.substring(2));
														es.setStaffcode(staffcode);
														es.setLeave_type(type);  //请假类型
														es.setLdatestart(ym);
														es.setLdateend(ym);
														es.setDaynum("1");
														es.setD("D");
														es.setLnum("1");
														es.setSfyx("Y");
														saveTraineeReport(es);
														fivenum++;  //累计5次不打卡
														num++;
													// }
													//}else{
														 //其他类型的请假---不处理
													//	es.setStaffcode("");
													//}
												}else{//指定日期沒有請假
													int isLC = isBorrow(staffcode, ym);
												//	System.out.println("-----------isBorrow----------"+isLC);
													if(isLC==1){//是否有借卡记录
														//借卡记录 --- 不处理
														es.setStaffcode("");
													}else{//没有借卡记录
														//String entryTime=queryEntryTime(staffcode, ym);//获取打卡记录
													//	System.out.println("-----------entryTime----------"+entryTime);
														if(!Util.objIsNULL(entryTime)){
															/*//有打卡记录
															if(!DateUtils.compTime(entryTime, Constant.ON_TIME)){
																//没迟到---不处理
																es.setStaffcode("");
															}else{
																//迟到做记录
																es.setStaffcode("");
															}*/
															//---不处理
															es.setStaffcode("");
														}else{//没有打卡记录
															logger.info("===================非请假,没有会客,没有借卡,没有打卡==处理！");
														//	System.out.println("-----没打卡！--------"+fivenum);
															es.setCode(staffcode.substring(2));
															es.setStaffcode(staffcode);
															es.setLeave_type("");  
															es.setLdatestart(ym);
															es.setLdateend(ym);
															es.setDaynum("1");
															es.setD("D");
															es.setLnum("1");
															es.setSfyx("Y");
															saveTraineeReport(es);
															num++;
															fivenum++;  //累计5次不打卡
														}
													}
												}
											}
										}
								   }
								}
						}else{
							logger.info("打卡记录表数据问题！请联系管理员！");
						}
					} else {
						logger.info("e__traineerepot-error");
					}
					//num++;
				}else {
					logger.info("插入e_taineelist code is null！");
				}
				/*}else{//在 离职表中
					System.out.println("*****************************************"+staffcode);
					return num;
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入e_taineelist异常！" + e);
			return num;
		} finally {
			// 关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 保存report
	 * @param es
	 * @return
	 */
	public String saveTraineeReport(EtraineeList es) {
		
		try{
			if(!Util.objIsNULL(es.getStaffcode())){
					sql="insert into e_traineerepot values('"+es.getMapdate()+"','"+es.getCode()+"','"+es.getStaffcode()+"','"+es.getLdatestart()+"','"+es.getLdateend()+"','"+es.getDaynum()+"','"+es.getD()+"','"+es.getLnum()+"','"+DateUtils.getYear()+"','"+es.getLeave_type()+"','admin','"+DateUtils.getDateToday()+"','"+es.getSfyx()+"')";
					//System.out.println("=========sql=========="+sql);
					ps = con.prepareStatement(sql);
					int reportnum = ps.executeUpdate();
					if (reportnum > 0) {
						logger.info("-e_traineeReport-success");
					}else{
						logger.info("-e_traineeReport-error");
					}
			}else {
				logger.info("e__traineerepot ___staffcode="+es.getStaffcode());
			}
		 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在EtraineeDaoImpl.savetrainee()中"+"插入e_traineerepot异常！" + e);
		}
		return "ok";
	}
	/**
	 * 修改report
	 * @param es
	 * @return
	 */
	public String updTraineeReport(String staffcode) {
		
		try{
			if(!Util.objIsNULL(staffcode) ){//&& !Util.objIsNULL(upddate)
				sql="update e_traineerepot set sfyx='Y' where staffcode='"+staffcode+"' and sfyx='H' and D='H' ";//and DATE_FORMAT(upddate,'%Y-%m-%d')  <= DATE_FORMAT('"+upddate+"','%Y-%m-%d') 
			//	System.out.println("=========sql=========="+sql);
				ps = con.prepareStatement(sql);
				int reportnum = ps.executeUpdate();
				if (reportnum >= 0) {
					logger.info("-e_traineeReport-success");
				}else{
					logger.info("-e_traineeReport-error");
				}
			}else {
				logger.info("e__traineerepot ___staffcode="+staffcode);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在EtraineeDaoImpl.savetrainee()中"+"修改e_traineerepot异常！" + e);
		}
		return "ok";
	}
	/**
	 * 删除report
	 * @param es
	 * @return
	 */
	public String delTraineeReport(String staffcode) {
		
		try{
			if(!Util.objIsNULL(staffcode) ){
				sql="delete from e_traineerepot  where staffcode='"+staffcode+"' and sfyx='H' and D='H' ";
			//	System.out.println("=========sql=========="+sql);
				ps = con.prepareStatement(sql);
				int reportnum = ps.executeUpdate();
				if (reportnum >= 0) {
					logger.info("-e_traineeReport-success");
				}else{
					logger.info("-e_traineeReport-error");
				}
			}else {
				logger.info("e__traineerepot ___staffcode="+staffcode);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("在EtraineeDaoImpl.savetrainee()中"+"删除e_traineerepot异常！" + e);
		}
		return "ok";
	}
	/**
	 * 查询trainee
	 */
	public List<TraineeList> queryTraineeList(String startDate,
			String endDate, String code) {

		List<TraineeList> list=new ArrayList<TraineeList>();
		try{
			StringBuffer sql= new StringBuffer("SELECT code,staffcode,staffname,joindate,location,promotion,leader,status,alias,groupdate,leadername,stage1,groupjoinAd6,promitionAd3,rdate FROM e_taineelist ");
			if(!Util.objIsNULL(startDate) && !Util.objIsNULL(endDate)){
				sql.append(" WHERE DATE_FORMAT(upddate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
				sql.append(" and DATE_FORMAT(upddate,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			sql.append(" and sfyx ='Y' order by upddate asc ");
			
			con = DBManager.getCon();
			logger.info("导出TraineeList信息表 EtraineeDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				TraineeList t=new TraineeList();
				t.setCode(rs.getString("code"));
				t.setStaffcode(rs.getString("staffcode"));
				t.setStaffname(rs.getString("staffname"));
				t.setJoindate(rs.getString("joindate"));
				t.setLocation(rs.getString("location"));
				t.setPromotion(rs.getString("promotion"));
				t.setLeader(rs.getString("leader"));
				t.setStatus(rs.getString("status"));
				t.setAlias(rs.getString("alias"));
				t.setGroupdate(rs.getString("groupdate"));
				t.setLeadername(rs.getString("leadername"));
				t.setStage1(rs.getString("stage1"));
				t.setGroupjoinAd6(rs.getString("groupjoinAd6"));
				t.setPromitionAd3(rs.getString("promitionAd3"));
				t.setRdate(rs.getString("rdate"));
				
				list.add(t);
			}rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	
	}
	/**
	 * 总数
	 */
	public int getCount(String staffcode, String startDate, String endDate) {
		int num=-1;
		try{
			StringBuffer sal=new StringBuffer("select count(*) as num from e_traineerepot where  sfyx='Y' and staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}

			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				num=Integer.parseInt(rs.getString("num"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Tranee 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	}
	/**
	 * 分页查询
	 */
	public List<EtraineeList> querybystaff(String staffcode, String startDate,
			String endDate, int pageSize, int currentPage) {

		List<EtraineeList> list=new ArrayList<EtraineeList>();
		try{
			StringBuffer sal=new StringBuffer("select * from e_traineerepot where sfyx='Y' and staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			logger.info("查询Trainee   sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				EtraineeList es =new EtraineeList(rs.getString("mapdate"), rs.getString("code"), rs.getString("staffcode") ,
						rs.getString("ldatestart"), rs.getString("ldateend") , rs.getString("daynum"), rs.getString("D") ,
						rs.getString("lnum") , rs.getString("lyear"), rs.getString("leave_type") , rs.getString("updname"),
						rs.getString("upddate") , rs.getString("sfyx"));
				list.add(es);
			}rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询Trainee时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	
	}

	/**
	 * 页面查询
	 */
	public List<EtraineeList> queryTraineeReport(String startDate,
			String endDate, String code) {

		List<EtraineeList> list=new ArrayList<EtraineeList>();
		try{
			StringBuffer sql= new StringBuffer("SELECT code,staffcode,ldatestart,ldateend,daynum,D,lnum,lyear,leave_type FROM e_taineelist ");
			if(!Util.objIsNULL(startDate) && !Util.objIsNULL(endDate)){
				sql.append(" WHERE DATE_FORMAT(upddate,'%Y-%m-%d') >=DATE_FORMAT('"+startDate+"','%Y-%m-%d') ");
				sql.append(" and DATE_FORMAT(upddate,'%Y-%m-%d') <= DATE_FORMAT('"+endDate+"','%Y-%m-%d') ");
			}
			if (!Util.objIsNULL(code)) {
				sql.append(" and staffcode like '%"+code+"%' ");
			}
			sql.append(" and sfyx ='Y' order by upddate asc ");
			
			con = DBManager.getCon();
			logger.info("导出TraineeList信息表 EtraineeDaoImpl SQL:"+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				EtraineeList t=new EtraineeList();
				t.setCode(rs.getString("code"));
				t.setStaffcode(rs.getString("staffcode"));
				t.setLdatestart(rs.getString("ldatestart"));
				t.setLdateend(rs.getString("ldateend"));
				t.setDaynum(rs.getString("daynum"));
				t.setD(rs.getString("D"));
				t.setLnum(rs.getString("lnum"));
				t.setLyear(rs.getString("lyear"));
				t.setLeave_type(rs.getString("leave_type"));
				
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询Medical信息表异常！"+e);
		}finally
		{
			//关闭连接
			DBManager.closeCon(con);
		}
		return list;
	
	}
	
	/**
	 * 导出
	 */
	public ResultSet queryrsbystaff(String staffcode, String startDate,
			String endDate, int pageSize, int currentPage) {
		ResultSet rs= null;
		try{
			//StringBuffer sal=new StringBuffer("select code,staffcode,staffcode,ldatestart,ldateend,daynum,D,lnum,lyear,leave_type from e_traineerepot where staffcode like '%"+staffcode+"%'");
			StringBuffer sal=new StringBuffer("select code,a.staffcode,a.staffcode,ldatestart,ldateend,daynum,D,lnum,lyear,leave_type ,b.resignDate,b.jointitle "+
						" from e_traineerepot a left join promotion_c_report b on a.staffcode = b.staffcode  where  a.sfyx='Y' and a.staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}
			 
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	
/*	public List<String[]> queryrsbystaff(String staffcode, String startDate,String endDate, int pageSize, int currentPage) {
		ResultSet rs= null;
		List<String []>list=new ArrayList<String[]>();
		try{
			StringBuffer sal=new StringBuffer("select code,a.staffcode,a.staffcode,ldatestart,ldateend,daynum,D,lnum,lyear,leave_type ,b.resignDate,b.jointitle "+
						" from e_traineerepot a left join promotion_c_report b on a.staffcode = b.staffcode  where  a.sfyx='Y' and a.staffcode like '%"+staffcode+"%'");
			if(!Util.objIsNULL(startDate)){
				sal.append(" and mapdate >='"+startDate+"'");
			}if(!Util.objIsNULL(endDate)){
				sal.append(" and mapdate <='"+endDate+"'");
			}
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					list.add(new String []{
							rs.getString("code"),
							rs.getString("staffcode"),
							rs.getString("staffcode"),
							rs.getString("ldatestart"),
							rs.getString("ldateend"),
							rs.getString("daynum"),
							rs.getString("D"),
							rs.getString("lnum"),
							rs.getString("lyear"),
							rs.getString("leave_type"),
							rs.getString("resignDate"),
							rs.getString("jointitle")
					});
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con); 
		}
		return list;
	}
	*/
	
	/***
	 * 获取计算考勤的 年-月
	 */
	public String getYMonth() {
		String nums="";
		try{
			sql = "select DATE_FORMAT(entryDate,'%Y') as years ,DATE_FORMAT(entryDate,'%m') as months,DATE_FORMAT(entryDate,'%d') as days, DATE_FORMAT(entryDate,'%Y-%m') as date from  e_attendence order by entryDate desc limit 0,1 ";
			ps = con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				nums=rs.getString("date");
				y= new Integer(rs.getString("years"));
				m= new Integer(rs.getString("months"));
				d= new Integer(rs.getString("days"));
			}else{
				nums=null;
			}
			
		}catch(Exception e){
			logger.error("查询e_attendence表异常");
			nums=null;
		}finally{
		}
		return nums;
	}
	/***
	 * 获取计算考勤的 天
	 */
	public int getAttendDay() {
		try{
			sql = "select DATE_FORMAT(entryDate,'%d') as days from  e_attendence order by entryDate asc limit 0,1 ";
			ps = con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				fistday = new Integer(rs.getString("days"));
			}else{
				fistday =1;
			}
			
		}catch(Exception e){
			logger.error("查询e_attendence表异常");
			fistday=1;
		}finally{
		}
		return fistday;
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
	/***
	 * 是否为EClub的成员
	 */
	public int isShoot(String staffcode,String dateTime) {
		int nums=-1;
		try{
			sql = "select * from e_traineerepot where staffcode='"+staffcode+"' and ldatestart='"+dateTime+"' and (leave_type='' or leave_type=null)";
			ps = con.prepareStatement(sql);
			ResultSet rs= null;
			rs=ps.executeQuery();
			if(rs.next()){
				nums=1;
			}else{
				nums=0;
			}
			
		}catch(Exception e){
			logger.error("查询E_traineerepot表异常");
		}finally{
		}
		return nums;
	}
/**
 * 是否在指定时间为假期
 */
	public String isholiday(String date) {
 		String nums="";
		 try{
			 sql = "select ecode from e_exceptdate where edate='"+date+"'";
			 
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("ecode");
				}else{
					nums="";
				}
				 rs.close();
		 }catch(Exception e){
			 logger.error("查询e_exceptdate表异常"+date);
			 e.printStackTrace();
			 nums="";
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
				System.out.println("**************************在离职表*****************************"+staffcode);
			}else{
				nums=0;
				//System.out.println("**************************不在离职表*****************************");
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

	
	public String isholidayForUtil(String date) {
 		String nums="";
		 try{
			 sql = "select ecode from e_exceptdate where edate='"+date+"'";
			 	con = DBManager.getCon();
				ps = con.prepareStatement(sql);
				ResultSet rs= null;
				rs=ps.executeQuery();
				if(rs.next()){
					nums=rs.getString("ecode");
				}else{
					nums="";
				}
				 rs.close();
		 }catch(Exception e){
			 logger.error("查询e_exceptdate表异常"+date);
			 e.printStackTrace();
			 nums="";
		 }finally{
			 try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		 return nums;
	}

}
