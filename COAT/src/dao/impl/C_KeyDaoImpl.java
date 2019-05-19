package dao.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Page;
import util.SendMail;
import util.Util;
import dao.C_KeyDao;
import entity.C_Keys;
import entity.C_KeysHistory;
import entity.C_KeysOperation;
import entity.C_Payment;

/**
 * C_KeyDaoImpl
 * @author Wilson
 * 2014-5-14 15:23:35
 * 
 */
public class C_KeyDaoImpl implements C_KeyDao {
	Logger logger = Logger.getLogger(C_KeyDaoImpl.class);
	/**
	 * 保存C_SearDaoImpl表
	 */
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	DecimalFormat dFormat= new DecimalFormat("##.##");
	public C_KeyDaoImpl() {
	}

	/**
	 * 定时上传keylist-6点
	 * @return
	 */
	public String timeTaskUploadKeyList(){
		String result="";
		int num=0;
		try{
			Util.printLogger(logger,"开始执行指定任务6点-同步更新Keys history");    
			InputStream os = new FileInputStream(Constant.Key_file);
			num=saveKeys(Constant.Key_file, os);
			if(num>0){
				result="success";
				Util.printLogger(logger,"指定任务-->6点-同步更新Keys history成功!");
			}else{
				Util.printLogger(logger,"指定任务-->6点-同步更新Keys history失败!");
				throw new Exception();
			}
		}catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger,"6 am同步更新Keys history失败："+e.getMessage());

		}		
		
		return result;
		
	}
	/**
	 * 保存上传信息
	 * @throws Exception 
	 */
	public int saveKeys(String filename, InputStream os) throws Exception {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("c_keys_history");/*           等待填写                      */
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet("Chicken Box Key");// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell StaffNocell = row.getCell(2);
				HSSFCell Staffnamecell = row.getCell(3);
				HSSFCell deskDrawernovell=row.getCell(4);
				HSSFCell lockernovell=row.getCell(5);
				HSSFCell pigenBoxnovell=row.getCell(6);
				/**给数据库里面的字段赋值**/
				String staffcode = Util.cellToString(StaffNocell);
				String staffname =Util.cellToString(Staffnamecell);
				String lockerno=Util.cellToString(lockernovell);
				String deskDrawerno=Util.cellToString(deskDrawernovell);
				String pigenBoxno=Util.cellToString(pigenBoxnovell);
				
				if(!Util.objIsNULL(staffcode)&& staffcode.length()==6){
					sql = "insert c_keys_history (staffcode,staffname,lockerno,deskDrawerno,pigenBoxno) values('"+staffcode+"','"+staffname+"','"+lockerno+"','"+deskDrawerno+"','"+pigenBoxno+"')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						//logger.info("插入keys表成功！");
						num++;
					} else {
						logger.info("插入keys表失敗");
					}
				}
			}
			os.close();
			logger.info("插入keys表完成");
		} catch (Exception e) {
			logger.error("插入keys表异常！"+e);
			throw new Exception(e);
		} finally {
			//关闭连接
			DBManager.closeCon(con);
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
			con=DBManager.getCon();
			String sql="select count(*) from c_keys";
			ps=con.prepareStatement(sql);
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
				num="K"+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public String complete(C_Keys cKeys ) {
		try{
			//订单号生成
			String refno=findref();
			synchronized (this) {
				if(Util.objIsNULL(refno))
					throw new Exception("流水号产生异常");
			}
			cKeys.setRefno(refno);
			
			int num =  saveKeysRecord(cKeys);
			if(num > 0){
				return "success";
			 }else{//保存消费记录表失败
				 logger.error("在保存Seat 订单详细时出现异常  ===REF.NO.："+refno);
				 return "Error";
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("save Seat Assignment出现异常  ===："+e);
		}finally{
			 
		}
		return "";
	}

	public int saveKeysRecord(C_Keys cKeys) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_keys values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
		logger.info("保存Additional_SQL:"+sql);
		
		try {
			con=(Connection) DBManager.getCon();
			pr=con.prepareStatement(sql);
			pr.setString(1, cKeys.getRefno());
			pr.setString(2, cKeys.getStaffcode() ); 
			pr.setString(3, cKeys.getStaffname()); 
			pr.setString(4, cKeys.getUserType());
			pr.setString(5, cKeys.getLocation());
			pr.setString(6, cKeys.getLocker());
			pr.setString(7, cKeys.getLockerno());
			pr.setString(8, cKeys.getDeskDrawer());
			pr.setString(9, cKeys.getDeskDrawerno());
			pr.setString(10,cKeys.getPigenBox());
			pr.setString(11,cKeys.getPigenBoxno());
			pr.setString(12, cKeys.getMobileDrawer());
			pr.setString(13, cKeys.getMobileno());
			pr.setString(14, cKeys.getRemarks());
			pr.setString(15, cKeys.getCreator());
			pr.setString(16, cKeys.getCreateDate());
			pr.setString(17, cKeys.getStatus());
			pr.setString(18, cKeys.getSfyx());
			
			logger.info(cKeys.getRefno()+"在Keys save成功！");
			num = pr.executeUpdate();
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存 Keys异常："+e);
			return 0;
		}finally{
		}
		return num;
	}
	/**
	 * add方法COAT Seat 操作记录
	 */
	public int saveKeysOpreation(C_KeysOperation seatOpreation) {
		PreparedStatement pr=null;
		int num =0;
		String	sql="insert c_keys_operation (refno,operationType,operationName,operationDate) values(?,?,?,? )";
		logger.info("保存Additional_SQL:"+sql);
		try {
			con=(Connection) DBManager.getCon();
			pr=con.prepareStatement(sql);
			pr.setString(1, seatOpreation.getRefno());
			pr.setString(2, seatOpreation.getOperationType() ); 
			pr.setString(3, seatOpreation.getOperationName()); 
			pr.setString(4, seatOpreation.getOperationDate());
			
			logger.info(seatOpreation.getRefno()+"在Keys Operation save成功！");
			num = pr.executeUpdate();
			pr.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存Keys Operation异常："+e);
			return 0;
		}finally{
		}
		return num;
	}
	/**
	 * down
	 */
	public Result downKeysList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status) {
			Result rss=null;
			 
		List<C_Keys> seatList=new ArrayList<C_Keys>();
		try{
			con=DBManager.getCon();
			String sql="select * from ( SELECT ca.refno,staffcode,ca.staffname,if(locker='N','',lockerno) as lockerno,if(deskDrawer='N','',deskDrawerno) as deskDrawerno,if(pigenBox='N','',pigenBoxno) as pigenBoxno,cp.paymentDate,cp.handleder,cp.paymentMethod,remarks,Status,ca.createDate,cp.paymentAount"+ 
			
			//String sql="SELECT ca.refno,staffcode,staffname,lockerno,deskDrawerno,pigenBoxno,cp.paymentDate,cp.handleder,cp.paymentMethod,remarks,Status,ca.createDate"+ 
			" from c_keys ca"+
			" left join c_payment cp on(ca.refno=cp.refno)" +
			" where ca.sfyx='Y' and  DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
			" staffcode like ? and ca.staffname like ? and ca.refno like ? and status like ? order by ca.createDate desc ) a";
			
			ps=con.prepareStatement(sql);
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
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
	
	}

	public List<C_Keys> findKeyList(String startDate,
			String endDate, String staffcode, String staffname, String refno,
			String status, Page page) {

		List<C_Keys> aList=new ArrayList<C_Keys>();
		try{
			con=DBManager.getCon();
			String sql="select refno,staffcode,staffname,userType,location,locker,lockerno,deskDrawer,deskDrawerno,pigenBox,pigenBoxno,mobileDrawer,mobileno,remarks,creator,createDate,status,sfyx from c_keys" +
					" where sfyx='Y' and  DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?" +
					" order by createDate desc limit ?,?";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
			ps.setString(3, "%"+staffcode+"%");
			ps.setString(4, "%"+staffname+"%");
			ps.setString(5, "%"+refno+"%");
			ps.setString(6, "%"+status+"%");
			ps.setInt(7, (page.getCurPage()-1)*page.getPageSize());
			ps.setInt(8, page.getPageSize());
			rs=ps.executeQuery();
			while(rs.next()){
				aList.add(new C_Keys(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"),
						rs.getString("locker"),
						rs.getString("lockerno"),
						rs.getString("deskDrawer"),
						rs.getString("deskDrawerno"),
						rs.getString("pigenBox"),
						rs.getString("pigenBoxno"),
						rs.getString("mobileDrawer"),
						rs.getString("mobileno"),
						rs.getString("remarks"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx")));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return aList;
	
	}

	public int getRow(String startDate, String endDate, String staffcode,
			String staffname, String refno, String status) {
		int num=-1;
		try{
			con=DBManager.getCon();
			String sql="select count(*)as num from c_keys" +
					" where sfyx='Y' and DATE_FORMAT(createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" DATE_FORMAT(createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d') and" +
					" staffcode like ? and staffname like ? and refno like ? and status like ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);
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
			DBManager.closeCon(con);
		}
		return num;
	}

	public int Ready(String refno, String type, String staffcode,String sfyx,String payment,String locaiton,String to) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update C_Keys set status='"+type+"',sfyx='"+sfyx+"' where status='Submitted' and sfyx='Y' and refno='"+refno+"'";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			//System.out.println("----"+num);
			if(num<1){
				throw  new RuntimeException("操作Ready失败");
			}
			sql="insert c_keys_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw  new RuntimeException();
			}
			//邮件内容
			String content="Dear Sir/Madam,<br/>"+
			"&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested key with ($"+payment+" / FOC) handling charge at (location: "+locaiton+" Mailing Room).";
			//邮件签名
			String signature="Best Regards,<br/>";
			signature+="Administration<br/>";
			signature+="Operations Department";
			
			String result=SendMail.send("COAT Request Notice", to, null, content, null, "email.ftl", signature);
			JSONObject json=new JSONObject(result);
			if(json.get("state")=="error"){
				throw new RuntimeException((String)json.get("msg"));
			}
			con.commit();
			num=1;
		}catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int modify(String refno, String type, String staffcode,String sfyx) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update C_Keys set status='"+type+"',sfyx='"+sfyx+"' where refno='"+refno+"'";
			 ps=con.prepareStatement(sql);
			 ps.execute();
			
			 sql="insert c_keys_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			ps.execute();
			
			 con.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int VOID(String refno, String type, String staffcode,String sfyx) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update C_Keys set status='"+type+"',sfyx='"+sfyx+"' where sfyx='Y' and status!='Completed' and refno='"+refno+"'";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_keys_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			con.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	
	public int detele(String refno, String type, String staffcode,String sfyx) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			String sql="update C_Keys set status='"+type+"',sfyx='"+sfyx+"' where status='Submitted' and sfyx='Y' and refno='"+refno+"'";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			sql="insert c_keys_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			num=ps.executeUpdate();
			if(num<1){
				throw new RuntimeException();
			}
			con.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}

	public C_Keys findKeyByRef(String refno) {

		C_Keys cKeys=null;
		try{
			con=DBManager.getCon();
			String sql="select * from c_keys where refno=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, refno);
			rs=ps.executeQuery();
			if(rs.next()){
				cKeys= new C_Keys(
						rs.getString("refno"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("userType"),
						rs.getString("location"),
						rs.getString("locker"),
						rs.getString("lockerno"),
						rs.getString("deskDrawer"),
						rs.getString("deskDrawerno"),
						rs.getString("pigenBox"),
						rs.getString("pigenBoxno"),
						rs.getString("mobileDrawer"),
						rs.getString("mobileno"),
						rs.getString("remarks"),
						rs.getString("creator"),
						rs.getString("createDate"),
						rs.getString("status"),
						rs.getString("sfyx"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return cKeys;
	
	}
	public C_KeysHistory findKeyBycode(String staffcode) {
		C_KeysHistory cKeys=null;
		try{
			con=DBManager.getCon();
			String sql="select Id,staffcode,staffname,lockerno,deskDrawerno,pigenBoxno,remark from c_keys_history where staffcode=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, staffcode);
			rs=ps.executeQuery();
			if(rs.next()){
				cKeys= new C_KeysHistory(
						rs.getInt("id"),
						rs.getString("staffcode"),
						rs.getString("staffname"),
						rs.getString("lockerno"),
						rs.getString("deskDrawerno"),
						rs.getString("pigenBoxno"),
						rs.getString("remark"));
			}
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return cKeys;
	}
	public int completed(String refno, String type,String staffcode,C_Payment payment) {
		int num=0;
		try{
			con=DBManager.getCon();
			con.setAutoCommit(false);
			sql="update c_keys set status=? where refno=?";
			 ps=con.prepareStatement(sql);
				ps.setString(1, type);
				ps.setString(2, refno);
			 ps.execute();
			
			 sql="insert c_keys_operation(refno,operationType,operationName,operationDate)values('"+refno+"','"+type+"','"+staffcode+"','"+DateUtils.getNowDateTime()+"');";
			ps=con.prepareStatement(sql);
			ps.execute();
			
			sql="insert c_payment(refno,type,paymentMethod,paymentAount,paymentDate,Handleder,creator,createDate,sfyx,staffname,location,saleno)values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			ps.setString(1,refno);
			ps.setString(2, payment.getType());
			ps.setString(3, payment.getPaymentMethod());
			ps.setDouble(4, payment.getPaymentAount());
			ps.setString(5, payment.getPaymentDate());
			ps.setString(6, payment.getHandleder());
			ps.setString(7, staffcode);
			ps.setString(8, DateUtils.getNowDateTime());
			ps.setString(9, payment.getSfyx());
			ps.setString(10, payment.getStaffname());
			ps.setString(11, payment.getLocation());
			ps.setString(12, payment.getSaleno());
			ps.execute();
			con.commit();
			num=1;
		}catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			DBManager.closeCon(con);
		}
		return num;
	}
	public Result downReportingForAccessCard(String startDate, String endDate) {
		Result rss=null;
		try{
			con=DBManager.getCon();
			String sql="select * from(SELECT 'Month',ca.createDate,ca.refno,staffcode,ca.staffname,ca.userType,"+
						" if(cp.paymentMethod='Cash',cp.paymentAount,'0.00') as Cash,"+
						" if(cp.paymentMethod='Octopus',cp.paymentAount,'0.00') as Octopus,"+
						" if(cp.paymentMethod='EPS',cp.paymentAount,'0.00') as EPS,"+
						" if(locker='Y',1,'')as locker,"+
						" if(deskDrawer='Y',1,'') as deskDrawer," +
						" if(pigenBox='Y',1,'') as pigenBox," +
						"remarks "+
						" from c_keys ca "+
						" left join c_payment cp on(ca.refno=cp.refno) "+
						" where ca.sfyx='Y' and   DATE_FORMAT(ca.createDate,'%Y-%m-%d')>=DATE_FORMAT(?,'%Y-%m-%d') and" +
						" DATE_FORMAT(ca.createDate,'%Y-%m-%d')<=DATE_FORMAT(?,'%Y-%m-%d'))a";
			
			ps=con.prepareStatement(sql);
			ps.setString(1, Util.objIsNULL(startDate)?"1999-01-01":startDate);
			ps.setString(2, Util.objIsNULL(endDate)?"2999-12-31":endDate);

			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			 rs.close();
		}catch (Exception e) {
			logger.error("导出AccessCard 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			DBManager.closeCon(con);
		}
		return rss;
	}
}
