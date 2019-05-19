package dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;

import util.DBManager;
import util.DateUtils;
import util.SendMail;
import util.Util;
import dao.MissingPaymentDao;
import entity.MissingSendMail;
import entity.Missingpayment;
import entity.Missingreport;
import entity.Principal;

public class MissingPaymentDaoImpl implements MissingPaymentDao{
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(MissingPaymentDaoImpl.class);
	public MissingPaymentDaoImpl() {
	}
	/**
	 * 上传
	 */
	public int uploadmis(String filename, InputStream os, String username) {
		int num=0;
		int beginRowIndex =1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		//无须删除 其他数据
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheetAt(0);// 獲取頁數
			
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行

				HSSFCell staffcodecell=row.getCell(1);
				HSSFCell receivecell = row.getCell(8);

				String staffcode =Util.cellToString(staffcodecell);
				String receive =Util.cellToString(receivecell);
				//如果不为空
				if (!Util.objIsNULL(receive) || !Util.objIsNULL(staffcode) ) {
					if (DateUtils.checkdate(receive)!=1) {
						return -1;
					}
				}
			}
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				
				HSSFCell principalcell=row.getCell(0);
				HSSFCell staffcodecell=row.getCell(1);
				HSSFCell staffcode2cell=row.getCell(2);
				HSSFCell clientCell=row.getCell(3);
				HSSFCell polocynocell=row.getCell(4);
				HSSFCell missingsumCell=row.getCell(5);
				HSSFCell premiumdateCell=row.getCell(6);
				HSSFCell reasonCell=row.getCell(7);
				HSSFCell receivecell = row.getCell(8);
				//HSSFCell remarkCell=row.getCell(10); 
				HSSFCell dateformcell=row.getCell(9);
				/**给数据库里面的字段赋值**/
				String receive =Util.cellToString(receivecell);
				String principal =Util.cellToString(principalcell);
				String staffcode =Util.cellToString(staffcodecell);
				String staffcode2 =Util.cellToString(staffcode2cell);//入職日期
				String polocyno =Util.cellToString(polocynocell);
				String client =Util.cellToString(clientCell);
				String missingsum =Util.cellToString(missingsumCell);
				String premiumdate =Util.cellToString(premiumdateCell);
				String reason =Util.cellToString(reasonCell);
				String dateform  =Util.cellToString(dateformcell);
				String remark ="";//Util.cellToString(remarkCell); 
				//if(staffcode.length()<7){
				//如果不为空
				if (!Util.objIsNULL(receive) || !Util.objIsNULL(staffcode) ) {
					if (DateUtils.checkdate(receive)==1) {
						 
						sql = "insert missingpayment (receivedDate,principal,staffcode,staffcode2,policyno,clientname," +
								"missingsum,premiumDate,reason,datafrom,remark,adddate,addname,note,sfyx) values('"+
								DateUtils.strChToCH(receive)+"','"+principal+"','"+staffcode+"','"+staffcode2+"','"+
								polocyno+"','"+client+"','"+missingsum+"','"+premiumdate+"','"+reason+"','"+dateform+"','"+
								remark+"','"+DateUtils.getNowDateTime()+"','"+username+"','','Y')";
						System.out.println(sql);
						ps = con.prepareStatement(sql);
						int rsNum = ps.executeUpdate();
						if (rsNum > 0) {
							
							num++;
						} else {
							logger.info("插入missingpayment失敗"+username);
						} 
					 }
				}
			}
			logger.info(username+" 插入missingpayment成功！ 总共："+num);
		} catch (Exception e) {
			e.printStackTrace();
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	
	}
	public int delMissing(String id, String username) {
		int r = 0;
		try {
			con=DBManager.getCon();
			sql="update missingpayment set sfyx='N', note='"+username+DateUtils.getNowDateTime()+"' where id="+id.trim()+"  ";
			logger.info(username+ " delete missingpayment SQL:"+sql);
			ps=con.prepareStatement(sql);
			
			r=ps.executeUpdate();
			if(r<1){
				System.out.println(username+ " delete missingpayment失败!");
				//logger.info(username+ " delete missingpayment失败!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(username+ DateUtils.getNowDateTime()+" delete missingpayment表异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(username+ DateUtils.getNowDateTime()+" delete missingpayment表异常！"+e);
		}
		finally{
			//关闭连接
			DBManager.closeCon(con);
		}
		return r;
	}
	public List<Missingpayment> selectMissList(String staffcode,
			String principal, String startdate, String enddate,String clientname, String policyno,String ctype ,String lastday ,int pageSize, int currentPage) {
		List<Missingpayment> list=new ArrayList<Missingpayment>();
		try{
			StringBuffer sal=new StringBuffer("select * from missingpayment a" +
					" where a.sfyx='Y' ");
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and (a.staffcode = '"+staffcode+"' or a.staffcode2 = '"+staffcode+"')");
			}
			if(!Util.objIsNULL(principal)){
				sal.append(" and principal ='"+principal+"' ");
			}
			if(!Util.objIsNULL(clientname)){
				sal.append(" and clientname  like '%"+clientname+"%' ");
			}
			if(!Util.objIsNULL(policyno)){
				sal.append(" and policyno like '%"+policyno+"%' ");
			}
			if(ctype.trim().equals("1") && !Util.objIsNULL(lastday) ){
				
				sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=  DATE_ADD(DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d'),INTERVAL -"+lastday.trim()+" DAY) ");
			}else {
				if(!Util.objIsNULL(startdate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startdate+"','%Y-%m-%d') ");
				}if(!Util.objIsNULL(enddate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  <=DATE_FORMAT('"+enddate+"','%Y-%m-%d') ");
				}
			}
			sal.append(" order by principal,policyno asc ");
			sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  missingpayment  sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				  
				Missingpayment es =new Missingpayment(
						rs.getInt("Id"), 
						rs.getString("receivedDate"), 
						rs.getString("principal"), 
						rs.getString("staffcode") ,
						rs.getString("staffcode2") ,
						rs.getString("policyno"), 
						rs.getString("clientname") , 
						rs.getString("missingsum"), 
						rs.getString("premiumDate"), 
						rs.getString("reason") ,
						rs.getString("datafrom") ,
						rs.getString("remark") ,
						rs.getString("adddate") , 
						rs.getString("addname") , 
						rs.getString("note") , 
						rs.getString("sfyx")  );
				 
				list.add(es);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Missingpayment 时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public List<Missingpayment> selectMissListFcons(String staffcode,
			String principal, String startdate, String enddate,String clientname, String policyno,String ctype ,String lastday ) {
		List<Missingpayment> list=new ArrayList<Missingpayment>();
		try{
			StringBuffer sal=new StringBuffer("select * from missingpayment a" +
			" where a.sfyx='Y' ");
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and (a.staffcode = '"+staffcode+"' or a.staffcode2 = '"+staffcode+"')");
			}
			if(!Util.objIsNULL(principal)){
				sal.append(" and principal ='"+principal+"' ");
			}
			if(!Util.objIsNULL(clientname)){
				sal.append(" and clientname  like '%"+clientname+"%' ");
			}
			if(!Util.objIsNULL(policyno)){
				sal.append(" and policyno like '%"+policyno+"%' ");
			}
			if(ctype.trim().equals("1") && !Util.objIsNULL(lastday) ){
				
				sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=  DATE_ADD(DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d'),INTERVAL -"+lastday.trim()+" DAY) ");
			}else {
				if(!Util.objIsNULL(startdate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startdate+"','%Y-%m-%d') ");
				}if(!Util.objIsNULL(enddate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  <=DATE_FORMAT('"+enddate+"','%Y-%m-%d') ");
				}
			}
			sal.append(" order by principal,policyno asc ");
			//sal.append(" limit "+(currentPage-1)*pageSize+","+pageSize);
			
			logger.info("查询  missingpayment For Cons sql:===="+sal.toString());
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			ResultSet rs= null;
			rs=ps.executeQuery();
			while(rs.next()){
				
				Missingpayment es =new Missingpayment(
						rs.getInt("Id"), 
						rs.getString("receivedDate"), 
						rs.getString("principal"), 
						rs.getString("staffcode") ,
						rs.getString("staffcode2") ,
						rs.getString("policyno"), 
						rs.getString("clientname") , 
						rs.getString("missingsum"), 
						rs.getString("premiumDate"), 
						rs.getString("reason") ,
						rs.getString("datafrom") ,
						rs.getString("remark") ,
						rs.getString("adddate") , 
						rs.getString("addname") , 
						rs.getString("note") , 
						rs.getString("sfyx")  );
				
				list.add(es);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"查询 Missingpayment 时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	public int selectMissAcount(String staffcode, String principal,
			String startdate, String enddate,String clientname, String policyno,String ctype,String lastday) {
		int num =0;
		try{
			StringBuffer sal=new StringBuffer("select count(1) as num from missingpayment a" +
					" where a.sfyx='Y' ");
			if(!Util.objIsNULL(staffcode)){
					sal.append(" and (a.staffcode = '"+staffcode+"' or a.staffcode2 = '"+staffcode+"')");
			}
			if(!Util.objIsNULL(principal)){
				sal.append(" and principal ='"+principal+"' ");
			}
			if(!Util.objIsNULL(clientname)){
				sal.append(" and clientname  like '%"+clientname+"%' ");
			}
			if(!Util.objIsNULL(policyno)){
				sal.append(" and policyno like '%"+policyno+"%' ");
			}
			if(ctype.trim().equals("1") && !Util.objIsNULL(lastday) ){
				
				sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=  DATE_ADD(DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d'),INTERVAL -"+lastday.trim()+" DAY) ");
			}else {
				if(!Util.objIsNULL(startdate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startdate+"','%Y-%m-%d') ");
				}if(!Util.objIsNULL(enddate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  <=DATE_FORMAT('"+enddate+"','%Y-%m-%d') ");
				}
			}
			sal.append(" order by principal,policyno asc ");
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
			logger.error(DateUtils.getNowDateTime()+"查询 missingpayment 条数 时出现： "+e);
			num=0;
		}finally{
			DBManager.closeCon(con);
		}

		return num;
	}
	public List<Principal> queryPrincipal() {
		
		List<Principal> list = new ArrayList<Principal>();
		try {
			con = DBManager.getCon();
			String sqlString ="SELECT * FROM missingprincipal order by principal ";
			logger.info("querymissingprincipal SQL:"+sqlString);
			ps = con.prepareStatement(sqlString);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Principal peoBean = new Principal();
				peoBean.setId(rs.getInt(1));
				peoBean.setPrincipal(rs.getString(2));
				peoBean.setRemark(rs.getString(5));
				list.add(peoBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("missingprincipal异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("missingprincipal异常！"+e);
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	/*public ResultSet selectMissForReport(String staffcode, String principal,
			String startdate, String enddate, String clientname, String policyno,String ctype ,String lastday ) {
		//List<Missingpayment> list=new ArrayList<Missingpayment>();
		ResultSet rs= null;
		try{
			StringBuffer sal=new StringBuffer("select if(principal='Zurich','Zurich International Life Ltd',principal) as principal,clientname,policyno,missingsum,premiumDate,reason,receivedDate,datafrom from missingpayment a" +
					" where a.sfyx='Y' ");//,datafrom
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and (a.staffcode = '"+staffcode+"' or a.staffcode2 = '"+staffcode+"')");
			}
			if(!Util.objIsNULL(principal)){
				sal.append(" and principal ='"+principal+"' ");
			}
			if(!Util.objIsNULL(clientname)){
				sal.append(" and clientname  like '%"+clientname+"%' ");
			}
			if(!Util.objIsNULL(policyno)){
				sal.append(" and policyno like '%"+policyno+"%' ");
			}
			if(ctype.trim().equals("1") && !Util.objIsNULL(lastday) ){
				
				sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=  DATE_ADD(DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d'),INTERVAL -"+lastday.trim()+" DAY) ");
			}else {
				if(!Util.objIsNULL(startdate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startdate+"','%Y-%m-%d') ");
				}if(!Util.objIsNULL(enddate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  <=DATE_FORMAT('"+enddate+"','%Y-%m-%d') ");
				}
			}
			sal.append(" order by principal,policyno asc ");
			
			//System.out.println("导出  missingpayment  sql:===="+sal.toString());//logger.info
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			
			rs=ps.executeQuery();
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"导出 Missingpayment 时出现 ："+e.toString());
		}finally{
			//DBManager.closeCon(con);
		}
		return rs;
	}*/
	
	public List<String []> selectMissForReport(String staffcode, String principal,String startdate, String enddate, String clientname, String policyno,String ctype ,String lastday ) {
		List<String []> list=new ArrayList<String []>();
		ResultSet rs= null;
		try{
			StringBuffer sal=new StringBuffer("select if(principal='Zurich','Zurich International Life Ltd',principal) as principal,clientname,policyno,missingsum,premiumDate,reason,receivedDate,datafrom,staffcode,staffcode2 from missingpayment a" +
					" where a.sfyx='Y' ");//,datafrom
			if(!Util.objIsNULL(staffcode)){
				sal.append(" and (a.staffcode = '"+staffcode+"' or a.staffcode2 = '"+staffcode+"')");
			}
			if(!Util.objIsNULL(principal)){
				sal.append(" and principal ='"+principal+"' ");
			}
			if(!Util.objIsNULL(clientname)){
				sal.append(" and clientname  like '%"+clientname+"%' ");
			}
			if(!Util.objIsNULL(policyno)){
				sal.append(" and policyno like '%"+policyno+"%' ");
			}
			if(ctype.trim().equals("1") && !Util.objIsNULL(lastday) ){
				
				sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=  DATE_ADD(DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d'),INTERVAL -"+lastday.trim()+" DAY) ");
			}else {
				if(!Util.objIsNULL(startdate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  >=DATE_FORMAT('"+startdate+"','%Y-%m-%d') ");
				}if(!Util.objIsNULL(enddate)){
					sal.append(" and DATE_FORMAT(receiveddate,'%Y-%m-%d')  <=DATE_FORMAT('"+enddate+"','%Y-%m-%d') ");
				}
			}
			sal.append(" order by principal,policyno asc ");
			
			
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
			if(rs!=null){
				while(rs.next()){
					list.add(new String []{
						rs.getString("principal"),	
						rs.getString("clientname"),	
						rs.getString("policyno"),	
						rs.getString("missingsum"),	
						rs.getString("premiumDate"),	
						rs.getString("reason"),	
						rs.getString("receivedDate"),	
						rs.getString("datafrom"),	
						rs.getString("staffcode"),	
						rs.getString("staffcode2")	
					});
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(DateUtils.getNowDateTime()+"导出 Missingpayment 时出现 ："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return list;
	}
	
	
	public int saveMissing(Missingpayment ms, String username) {
		int num=0;
		try {
			con = DBManager.getCon();
			 
			sql = "insert missingpayment (receivedDate,principal,staffcode,staffcode2,policyno,clientname," +
					"missingsum,premiumDate,reason,datafrom,remark,adddate,addname,note,sfyx) values('"+
					ms.getReceivedDate()+"','"+ms.getPrincipal()+"','"+ms.getStaffcode()+"','"+ms.getStaffcode2()+"','"+
					ms.getPolicyno()+"','"+ms.getClientname()+"','"+ms.getMissingsum()+"','"+ms.getPremiumDate()+"','"+ms.getReason()+"','"+ms.getDatafrom()+"','"+
					ms.getRemark()+"','"+DateUtils.getDateToday()+"','"+username+"','','Y')";
 
			ps = con.prepareStatement(sql);
			int rsNum = ps.executeUpdate();
			if (rsNum > 0) {
				//System.out.println("---save ok !===========");
				num++;
			} else {
				logger.info("插入missingpayment失敗"+username);
			} 
			logger.info(username+" 插入missingpayment成功！ 总共："+num);
		} catch (Exception e) {
			e.printStackTrace();
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	public int saveMissingReport(Missingreport ms) {
		int num=0;
		try {
			con = DBManager.getCon();
			 
			sql = "insert missingreport (upddate,updname,type,remark) values('"+
			ms.getUpddate()+"','"+ms.getUpdname()+"','"+ms.getType()+"','"+ms.getRemark()+"')";
 
			ps = con.prepareStatement(sql);
			int rsNum = ps.executeUpdate();
			if (rsNum > 0) {
				//System.out.println("---save ok !===========");
				num++;
			} else {
				logger.info("插入 missingreport 失敗"+ms.getUpdname());
			} 
			logger.info(ms.getUpdname()+" 插入 missingreport 成功！ 总共："+num);
		} catch (Exception e) {
			e.printStackTrace();
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	public String searchnum() {
		String a ="";
		String b ="";
		try {
			con = DBManager.getCon();
			String sql ="select sum(search),sum(exp) from ( "+
			" select count(1) as search,0 as exp from missingreport where  updname !='' AND TYPE ='S' "+
			" union "+
			" select 0 as search,count(1) as exp from missingreport where  updname !='' AND TYPE ='E' "+
			" ) a ";
			//System.out.println("获取Missing payment num SQL："+sql);
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				a = rs.getString(1);
				b = rs.getString(2);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取ConsMarquee Message 英文名称异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("获取ConsMarquee Message英文名称异常！"+e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return "顾问访问查询功能总数为："+a+" ， 操作导出总数为："+b+" 。 ";
	}

	
	/**
	 * 定时查询missingpayment并发送通知邮件
	 */
	public String timeTaskSearchMissingPayment(){
		String result="";
		int num=-1;
		Util.printLogger(logger,"开始执行指定任务20点-Missingpayment 查询");    
		try{	
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			num=findByTime(sdf.format(new Date()));//"2014-10-20"
			if(num>=0){
				result="success";
				Util.printLogger(logger,"开始执行指定任务20点-Missingpayment查询 并插入数据成功");    
			}else{
				Util.printLogger(logger,"开始执行指定任务20点-Missingpayment查询 并插入数据失败"); 
				throw new Exception();
			}
		}catch (Exception e) {
			result=e.getMessage();
			Util.printLogger(logger,"执行指定任务20点-Missingpayment 查询 失败"+e.getMessage());
		}
		
		return result;
	}
	
	
	/**
	 * 根据当前时间查询STAFFCODE、ADDDATE
	 * @param adddate
	 * @return  List<Missingpayment>
	 * @throws Exception 
	 */
	public int  findByTime(String date) throws Exception{
		PreparedStatement psd = null;
	     int num=0;
		try{
			con=DBManager.getCon();
			//String sql="select DATE_FORMAT(a.ADDDATE,'%Y-%m-%d') as adddate,a.STAFFCODE staffcode,b.email from missingpayment a left join cons_list b on(a.staffcode=b.employeeid) " +
			//		" WHERE DATE_FORMAT(a.ADDDATE,'%Y-%m-%d')=DATE_FORMAT(?,'%Y-%m-%d') GROUP BY DATE_FORMAT(a.ADDDATE,'%Y-%m-%d'),STAFFCODE,b.email ORDER BY STAFFCODE";
			String sql ="select staffcode,email from ( "+
						" select DATE_FORMAT(a.ADDDATE,'%Y-%m-%d') as adddate,a.STAFFCODE staffcode,email from missingpayment a left join cons_list b "+
						" on(a.staffcode=b.employeeid)  WHERE DATE_FORMAT(a.ADDDATE,'%Y-%m-%d')=DATE_FORMAT('"+date+"','%Y-%m-%d') and a.STAFFCODE!=''  "+
						" GROUP BY DATE_FORMAT(a.ADDDATE,'%Y-%m-%d'), STAFFCODE,b.email  union"+
						" select DATE_FORMAT(a.ADDDATE,'%Y-%m-%d') as adddate,a.STAFFCODE2 as staffcodeb,b.email from missingpayment a left join cons_list b"+
						" on(a.staffcode2=b.employeeid)  WHERE DATE_FORMAT(a.ADDDATE,'%Y-%m-%d')=DATE_FORMAT('"+date+"','%Y-%m-%d') and a.STAFFCODE2!='' "+
						" GROUP BY DATE_FORMAT(a.ADDDATE,'%Y-%m-%d'), STAFFCODE,b.email ) a group by  STAFFCODE,email ";
			//ps=con.prepareStatement(sql);
			
			psd = con.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,  
                    ResultSet.CONCUR_READ_ONLY);  
			psd.setFetchSize(Integer.MIN_VALUE);  
			psd.setFetchDirection(ResultSet.FETCH_REVERSE);  
			//ps.setString(1, date);
			//ps.setString(2, date);
			ResultSet rs=psd.executeQuery();
			String stuts ="";
			//System.out.println(rs.getFetchSize()+"----sql---"+sql);
			while(rs.next()){
				
				String staffEmail = rs.getString("email");

				String staffcode = rs.getString("staffcode"); 
				//System.out.println(staffEmail+"============="+staffcode);
				//HttpURLConnection htp=null;
				String body="Dear Consultant,<br/><br/>"//("+staffcode+"_"+staffEmail+")
				+"         Friendly Reminder: Record shows that your client(s) have missing premium payments.<br/><br/>"
				+"         Your action is needed to remind them to settle the payments due on time to avoid any loss. <br/><br/>"
				+"         To check the details,  please go to eConvoy > Sales Knowledge Management > Missing Premium Report or click the below link: <br/><br/>"
				+"<a href='http://www.econvoy.com/group/convoy/missing-premium-report'>http://www.econvoy.com/group/convoy/missing-premium-report</a><br/><br/>"
				+"         For further enquiries, you are welcome to call our Consultant Hotline at 3601-3603."
				+"<br/><br/>Regards,<br/>"
				+"Shared Service Centre<br/>"
				+"Convoy Financial Services Limited";
				/**
				 * wilson 2015年9月7日16:22:39 upd
				 * 
				 * String parmString="to="+staffEmail+"&" +
				//"cc=adminfo@convoy.com.hk&" +
				"bc=Wilson.Shen@convoy.com.hk&" +
				"subject=Missing Payment Alert&" +
				"webapp=MPR&" +
				"body="+body;
				
				
				htp=(HttpURLConnection)new URL("http://localhost:8888/ExchangeMail/SendMailServlet").openConnection();
				htp.setDoOutput(true);
				htp.setRequestMethod("POST");
				htp.setUseCaches(false);
				htp.setInstanceFollowRedirects(true);
				htp.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				OutputStream tarparm=htp.getOutputStream();
			 
			
				//System.out.println("Client----第"+(i+1)+"次调用Servlet");
				tarparm.write((parmString).getBytes());//传递参数
				tarparm.flush();
				tarparm.close();
				
			      InputStreamReader isr=new InputStreamReader(htp.getInputStream());
		            BufferedReader br=new BufferedReader(isr);
		            
		           if(br.ready()) {
		        	    stuts="Y";
						System.out.println("Client-----获取返回结果：==="+br.readLine());
						System.out.println(staffcode+"-"+br.readLine()+"\r\n");
					}
		           htp.disconnect(); 
				*/
				
				/*stuts = SendMail.send("Missing Payment Alert", staffEmail, null,
						null, "", body, "MPR", "", "");
				JSONObject json=new JSONObject(stuts);
				
				if ("success".equalsIgnoreCase(json.getString("state"))) {
					stuts = "Y";
				}else {
					stuts = "N";
				}*/
				
				// 2018-08-06 用户需求，不在接收邮件，故屏蔽邮件发送，记录所有发送邮件状态为N  --orlando
				stuts = "N";
				
				 MissingSendMail mail=new MissingSendMail();
		           mail.setEmail(staffEmail);
		           mail.setStaffcode(staffcode);
		           mail.setStuts(stuts);
		           mail.setUpdata(DateUtils.getNowDateTime());
				//插入数据
				num=addMissingSendMail(mail);
			}
			rs.close();
		}catch (Exception e) {
			num=-1;
			logger.error("根据当前时间查询STAFFCODE、ADDDATE数据 异常 "+e.getMessage());
			throw new Exception(e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 插入数据 MIssingSendMail
	 * @param mail
	 * @return
	 */
	public int addMissingSendMail(MissingSendMail mail){
		int num=-1;
		PreparedStatement psds = null;
		Connection connection=null;
		try{
			connection=DBManager.getCon();
			 String SQL="insert into missingsendmail(staffcode,email,updata,stuts) values  (?,?,?,?)";
			 psds=connection.prepareStatement(SQL); 
			 psds.setString(1, mail.getStaffcode());
			 psds.setString(2, mail.getEmail());
			 psds.setString(3, mail.getUpdata());
			 psds.setString(4, mail.getStuts());
			    num=psds.executeUpdate();
			    logger.info("向表missingsendmail 插入数据成功");
		}catch (Exception e) {
			logger.error("根据当前时间查询STAFFCODE、ADDDATE插入数据 异常 "+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(connection);
		}
		return num;
		
	}
}
