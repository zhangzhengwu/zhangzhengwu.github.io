package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.SendMail;
import util.Util;
import dao.PIBAStudyNoteDao;
import entity.CPibaBook;
import entity.CPibaOrder;
import entity.CPibaOrderDetial;
import entity.CPibaSigndetial;
import entity.C_Access;

public class PIBAStudyNoteDaoImpl implements PIBAStudyNoteDao {
	Logger logger = Logger.getLogger(PIBAStudyNoteDaoImpl.class);
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	private CPibaOrderDetial CPibaOrderDetial;
	
	public int saveAccessCard(C_Access ca) {
		int num=-1;
		try{
			synchronized (this) {
				String refno=findref();
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
				else
					ca.setRefno(refno);
			}
			conn=DBManager.getCon();
			String sql="insert c_access(refno,staffcode,staffname,userType,location,staffCard,photoSticker,reason,creator,createDate,status,sfyx)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,ca.getRefno());
			ps.setString(2,ca.getStaffcode());
			ps.setString(3, ca.getStaffname());
			ps.setString(4,ca.getUserType());
			ps.setString(5,ca.getLocation());
			ps.setString(6, ca.getStaffCard());
			ps.setString(7, ca.getPhotoSticker());
			ps.setString(8, ca.getReason());
			ps.setString(9, ca.getCreator());
			ps.setString(10, ca.getCreateDate());
			ps.setString(11,ca.getStatus());
			ps.setString(12, ca.getSfyx());
			num=ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			num=0;
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
		
		
	}

	
	 /**
	  * 获取订单编号
	  * @return
	  */
public String findref(){
	String num=null;
	try{
		conn=DBManager.getCon();
		String sql="select count(*) from c_piba_order";
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next()){
			num=rs.getString(1);
			if(rs.getInt(1)<9){
				num="000"+(rs.getInt(1)+1);
			}else if(rs.getInt(1)<99){
				num="00"+(rs.getInt(1)+1);
			}else if(rs.getInt(1)<999){
				num="0"+(rs.getInt(1)+1);
			}else{
				num=""+(rs.getInt(1)+1);
			}
			num=Constant.PIBACARD+DateUtils.Ordercode()+num;
		}rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}
	
/**
 * 查询总条数
 */
public int getRow(String staffcode,String staffname, String refno, String status,String start_date,String end_date) {
	int num=-1;
	try{
		conn=DBManager.getCon();
		String sql="select count(*)as num from c_piba_order where sfyx='Y'" +
				" and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and " +
				" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') " +
				" and staffcode like ? and staffname like ? and refno like ? and status like ?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, Util.objIsNULL(start_date)?"1999-01-01":start_date);
		ps.setString(2, Util.objIsNULL(end_date)?"2999-12-31":end_date);
		ps.setString(3, "%"+staffcode+"%");
		ps.setString(4, "%"+staffname+"%");
		ps.setString(5, "%"+refno+"%");
		ps.setString(6, "%"+status+"%");
		rs=ps.executeQuery();
		if(rs.next()){
			num=rs.getInt("num");
		}
		rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

/**
 * 分页查询
 */
public List<CPibaOrder> findAccessList(String staffcode, String staffname, String refno,String status, Page page,String start_date,String end_date) {
	List<CPibaOrder> c_AccessList=new ArrayList<CPibaOrder>();
	try{
		conn=DBManager.getCon();
		String sql="select *from c_piba_order where sfyx='Y'" +
				" and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and " +
				" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') " +
				" and staffcode like ? and staffname like ? and refno like ? and status like ? " +
				" order by createDate desc limit ?,?";
		ps=conn.prepareStatement(sql);
		ps.setString(1, Util.objIsNULL(start_date)?"1999-01-01":start_date);
		ps.setString(2, Util.objIsNULL(end_date)?"2999-12-31":end_date);
		ps.setString(3, "%"+staffcode+"%");
		ps.setString(4, "%"+staffname+"%");
		ps.setString(5, "%"+refno+"%");
		ps.setString(6, "%"+status+"%");
		ps.setInt(7, (page.getCurPage()-1)*page.getPageSize());
		ps.setInt(8, page.getPageSize());
		rs=ps.executeQuery();

		//System.out.println("sql-->"+sql);
		while(rs.next()){
			c_AccessList.add(new CPibaOrder(
					rs.getString("refno"),
					rs.getString("staffcode"),
					rs.getString("staffname"),
					rs.getString("userType"),
					rs.getString("location"),
					rs.getString("creator"),
					rs.getString("createDate"),
					rs.getString("status"),
					rs.getString("sfyx"),
					rs.getString("remark"),
					rs.getString("remark2"),
					rs.getString("remark3"),
					rs.getString("remark4")));
		}
		rs.close();
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return c_AccessList;
}
	/**
	 * 查看详细
	 * @param refno
	 * @return
	 */
	public List<CPibaOrderDetial> findAdminserviceByRef(String refno) {
		List<CPibaOrderDetial> cpiba=new ArrayList<CPibaOrderDetial>();
		try{
			conn=DBManager.getCon();
			String sql="select * from c_piba_order_detial where refno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			while(rs.next()){
				cpiba.add(new CPibaOrderDetial(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("type"),
						rs.getString("bookCName"),
						rs.getString("bookEName"),
						rs.getString("bookNum"),
						rs.getString("language"),
						rs.getString("remark"),
						rs.getString("creator"),
						rs.getString("createDate"))
				);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return cpiba;
	}
	
	/**
	 * 查看详细
	 * @param refno
	 * @return
	 */
	public List<CPibaSigndetial> findCPibaSigndetial(String refno) {
		List<CPibaSigndetial> cpiba=new ArrayList<CPibaSigndetial>();
		try{
			conn=DBManager.getCon();
			String sql="select * from c_piba_signdetial where refno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			while(rs.next()){
				cpiba.add(new CPibaSigndetial(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("signcode"),
						rs.getString("signname"),
						rs.getString("creator"),
						rs.getString("createdate"),
						rs.getString("remark")
				));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return cpiba;
	}

/**
 * 操作PIBACard Ready
 */
public int Ready(String refno, String type,String staffcode,String staffnames,String location) {
	int num=0;
	String email="";
	try{
		conn=DBManager.getCon();
		conn.setAutoCommit(false);
		String sql="update c_piba_order set status='"+type+"' where status='Submitted' and sfyx='Y' and refno='"+refno+"'";
		ps=conn.prepareStatement(sql); 
		ps.execute();
		
		sql="insert c_piba_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
		ps=conn.prepareStatement(sql);
		ps.execute();
		
		
		sql="select Email from cons_list where EmployeeId='"+staffcode+"'";
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		if(rs.next()){
			email=rs.getString("Email");
		}
		rs.close();
		/**
		 * 发送邮件
		 */
		//subject,content,to,attr,ftl,sign  标题   ，内容，收件人邮箱，附件，“email.ftl”,签名( Administration Department )
		String a="";
		if(location.equalsIgnoreCase("@Convoy")){
			a="40/F Mailing Room";
		}
		if(location.equalsIgnoreCase("CP3")){
			a="17/F Mailing Room";
		}
		String emailBody="Dear "+staffnames+",<br/>" +
		" &nbsp;&nbsp; Please collect your requested IIQAS study notes at "+location+" "+a+".<br/>" +
		" &nbsp;&nbsp;Should you have any enquiries, please contact ADM hotline at ext 3667. " ;
		
		String result=SendMail.send("PIBA Apply", email, null, emailBody, null, "email.ftl", "Administration Department");
		
		JSONObject json=new JSONObject(result);
		if(json.get("state")=="error"){
			throw new RuntimeException((String)json.get("msg"));
		}
		//System.out.println(json.get("state"));
		//System.out.println(json.get("msg"));
		//exception throw new RuntimeException(msg);
		conn.commit();
		num=1;
	}catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
			throw new RuntimeException(e);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}finally{
		DBManager.closeCon(conn);
	}
	return num;
}

	/**
	 * 删除 PIBA
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int Deleted(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);	//禁止提交事物
			String sql="update c_piba_order set status='"+type+"',sfyx='D' where sfyx!='D' and ( status='Submitted' or status='Ready') and refno='"+refno+"'";
			 ps=conn.prepareStatement(sql);
			 ps.execute();
			
			 sql="insert c_piba_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			 conn.commit(); //提交事物
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback(); //事物回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);//关闭连接
		}
		return num;
	}

	public int DeletedCon(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);	//禁止提交事物
			String sql="update c_piba_order set status='"+type+"',sfyx='D' where sfyx!='D' and  status='Submitted' and refno='"+refno+"'";
			 ps=conn.prepareStatement(sql);
			 ps.execute();
			
			 sql="insert c_piba_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			 conn.commit(); //提交事物
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback(); //事物回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);//关闭连接
		}
		return num;
	}
	
	/**
	 * 取消订单
	 * @param refno
	 * @param type
	 * @param staffcode
	 * @return
	 */
	public int VOID(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);	//禁止提交事物
			String sql="update c_piba_order set status='"+type+"' ,sfyx='D'  where (status='Submitted' or status='Ready') and refno='"+refno+"'";
			ps=conn.prepareStatement(sql);
			//logger.info(ps);
			ps.execute();
			sql="insert c_piba_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			conn.commit(); //提交事物
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback(); //事物回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);//关闭连接
		}
		return num;
	}

	/**
	 * 操作PIBA Completed
	 */
	public int complete(String refno, String type,String staffcode) {
		int num=0;
		try{
			conn=DBManager.getCon();
			conn.setAutoCommit(false);
			String sql="update c_piba_order set status='"+type+"' where status='Ready' and sfyx='Y' and refno='"+refno+"'";
			ps=conn.prepareStatement(sql); 
			ps.execute();
			
			sql="insert c_piba_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=conn.prepareStatement(sql);
			ps.execute();
			
			conn.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}


/**
 * 导出PIBA
 */
public Result downPIBAList(String startDate, String endDate,String staffcode, String staffname, String refno, String status) {
		Result rss=null;
		try{
			conn=DBManager.getCon();
			String sql="SELECT ca.refno,ca.staffcode,ca.staffname,ca.location,ca.status," +
					"cp.type,cp.bookCName,cp.bookEName,cp.bookNum,ca.createDate"+ 
			" from c_piba_order ca"+
			" left join c_piba_order_detial cp on(ca.refno=cp.refno)" +
			" where  ca.sfyx='Y'  and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" ca.staffcode like ? and ca.staffname like ? and ca.refno like ? and status like ?";
			
			//System.out.println(sql+"-----------");
			ps=conn.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出PIBA 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return rss;
}


public Result downReportingForAccessCard(String startDate, String endDate) {
	Result rss=null;
	try{
		conn=DBManager.getCon();
		String sql="select * from(SELECT 'Month',ca.createDate,ca.refno,staffcode,ca.staffname,ca.userType,"+
					" if(cp.paymentMethod='Cash',cp.paymentAount,'0.00') as Cash,"+
					" if(cp.paymentMethod='Octopus',cp.paymentAount,'0.00') as Octopus,"+
					" if(cp.paymentMethod='EPS',cp.paymentAount,'0.00') as EPS,"+
					" if(staffcard='Y',1,'')as staffcard,"+
					" if(photosticker='Y',1,'') as photosticker,reason "+
					" from c_access ca"+
					" left join c_payment cp on(ca.refno=cp.refno) "+
					" where  ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
		
		ps=conn.prepareStatement(sql);
		ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
		ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);

		rs=ps.executeQuery();
		rss=ResultSupport.toResult(rs);
		 rs.close();
	}catch (Exception e) {
		logger.error("导出PIBA 异常=="+e.getMessage());
		e.printStackTrace();
	}finally{
		DBManager.closeCon(conn);
	}
	return rss;
}


	public List<CPibaBook> queryBook() {
		List<CPibaBook> cpiba=new ArrayList<CPibaBook>();
		try{
			conn=DBManager.getCon();
			String sql="select * from c_piba_book ";
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				cpiba.add(new CPibaBook(
						rs.getInt("bookNo"),
						rs.getString("type"),
						rs.getString("bookCName"),
						rs.getString("bookEName"),
						rs.getString("language"),
						rs.getInt("num"),
						rs.getString("creator"),
						rs.getString("createDate"))
				);
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return cpiba;
	}


	public int submitOrder(String staffcode, String staffname, String location,String type,String username,String userType,String signcodes,String signnames,String bookTypes,String bookNames,String bookNumber) {
		int num=-1;
		CPibaOrder cPibaOrder=new CPibaOrder();
		try{
			String refno="";
			//生成流水号
			synchronized (this) {
				refno=findref();
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
				else
					cPibaOrder.setRefno(refno);
			}
			conn=DBManager.getCon();
			conn.setAutoCommit(false);//禁止提交事物
			//-->保存订单
			String sql="insert c_piba_order(refno,staffcode,staffname,userType,location,creator,createDate,status,sfyx)" +
					"values(?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1,cPibaOrder.getRefno());
			ps.setString(2,staffcode);
			ps.setString(3,staffname);
			ps.setString(4,userType);
			ps.setString(5,location);
			ps.setString(6,username);
			ps.setString(7,DateUtils.getDateToday());
			ps.setString(8,type);
			ps.setString(9,"Y");
			//logger.info(ps);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}

			String signName[]=signnames.split("~,~");
			String signcode[]=signcodes.split("~,~");
			String bookType[]=bookTypes.split("~,~");
			String bookName[]=bookNames.split("~,~");
			String number[]=bookNumber.split("~,~");
			
			//System.out.println(Arrays.toString(bookType));
			//System.out.println(Arrays.toString(bookName));
			//System.out.println(Arrays.toString(number));
			//-->保存订单详细
			for (int j = 0; j < bookType.length; j++) {
				sql="insert c_piba_order_detial(refno,staffcode,type,bookCName,bookEName,bookNum,language,remark,creator,createDate)" +
						"values('"+refno+"','"+staffcode+"','"+bookType[j]+"','"+bookName[j]+"','','"+number[j]+"','English&Chinese','--'," +
								"'"+username+"','"+DateUtils.getNowDateTime()+"');";
				ps=conn.prepareStatement(sql);
				num=ps.executeUpdate();
				if(num<1){
					throw new RuntimeException();
				}
				
			}
			//-->保存拿单人信息
			for (int f = 0; f < signName.length; f++) {
				sql="insert c_piba_signdetial(refno,staffcode,signcode,signname,creator,createDate,remark)" +
						"values('"+refno+"','"+staffcode+"','"+signcode[f]+"','"+signName[f]+"'," +
						"'"+username+"','"+DateUtils.getNowDateTime()+"','');";
				ps=conn.prepareStatement(sql);
				num=ps.executeUpdate();
				if(num<1){
					throw new RuntimeException();
				}
				
			}
			conn.commit();//提交事物
			num=1;
				
		}catch(Exception e){
			num=0;
			e.printStackTrace();
			try {
				conn.rollback();//事物回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(conn);//关闭链接
		}
		return num;
	}




}
