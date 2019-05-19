package dao.impl;
/**
 * @author younggao
 */
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
import util.Page;
import util.Util;
import dao.RecruimentDao;
import entity.C_Recruitment_detail;
import entity.C_Recruitment_list;
import entity.C_Recruitment_order;
import entity.Cons_listBean;

public class RecuritmentDaoImpl implements RecruimentDao {
	
    Connection connection=null;
    PreparedStatement ps = null;
	ResultSet rs=null;
	Logger logger = Logger.getLogger(RecuritmentDaoImpl.class);
	
	public RecuritmentDaoImpl() {
	}
	/**
     * 查找所有產品
     * @return C_Recruitment_list
     */
	public List<C_Recruitment_list> findRecruitment() {
		List<C_Recruitment_list> Recruitment_list =new ArrayList<C_Recruitment_list>();
		String sql="select id,mediacode,mediatype,medianame,price  from c_recruitment_list where sfyx='Y'";
		try{
			connection = DBManager.getCon();
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				C_Recruitment_list list=new C_Recruitment_list();
				list.setId(rs.getInt("id"));
				list.setMediacode(rs.getString("mediacode"));
				list.setMediatype(rs.getString("mediatype"));
				list.setMedianame(rs.getString("medianame"));
				list.setPrice(rs.getString("price"));
				Recruitment_list.add(list);
			}
			return Recruitment_list;
		}catch (Exception e) {
			logger.error("Find C_Recruitment_list ERROR"+e.toString());
			e.printStackTrace();
		}finally{
			try{
				rs.close();
				ps.close();
				connection.close();
			}catch (Exception e) {
				logger.error("Close connection or PreparedStatement or  ResultSet ERROR");
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 根据当前登录用户的code，获取状态不为complete的订单信息
	 * @param code
	 * @return List
	 */
	 public List<C_Recruitment_order> findOrderByCode(String code){
		 List<C_Recruitment_order> orders=new ArrayList<C_Recruitment_order>();
		 try{
			   String sql="select * from c_recruitment_order o where  o.staffcode=? and o.sfyx='Y' and  o.status !='Completed' and  o.status !='Cancelled' and  o.status !='VOID'"; 
			   connection = DBManager.getCon();
			    ps=connection.prepareStatement(sql);
			    ps.setString(1, code);
				rs=ps.executeQuery();
				while(rs.next()){
					C_Recruitment_order order=new C_Recruitment_order();
					order.setRefno(rs.getString("refno"));
					order.setStaffcode(rs.getString("staffcode"));
					order.setStaffname(rs.getString("staffname"));
					order.setUsertype(rs.getString("usertype"));
					order.setPosition(rs.getString("position"));
					order.setContactemail(rs.getString("contactemail")); 
					order.setContactperson(rs.getString("contactperson"));
					order.setChargecode(rs.getString("chargecode"));
					order.setChargename(rs.getString("chargename"));
					order.setDate(rs.getString("date"));
					order.setCreatedate(rs.getString("createdate"));
					order.setCreater(rs.getString("creater"));
					orders.add(order);
				}
				  return orders;
		 }catch (Exception e) {
		   logger.error("Find C_Recruitment_order ERROR"+e.toString());
		   e.printStackTrace();
		}finally{
			try{
				rs.close();
				ps.close();
				connection.close();
			}catch (Exception e) {
				logger.error("Close connection or PreparedStatement or  ResultSet ERROR");
				e.printStackTrace();
			}
		}

		return null;
	 }
	 /**
      * 根据用户订单状况查询产品单号
      * @param List<C_Recruitment_order>
      * @return List<C_Recruitment_detail>
      */
	 public List<C_Recruitment_detail> findDetialByOrder(List<C_Recruitment_order> orders){
		 List<C_Recruitment_detail> details=new ArrayList<C_Recruitment_detail>();
		 try{
			 connection = DBManager.getCon();
			 for(int i=0;i<orders.size();i++){
			    String sql="select * from c_recruitment_detail  where refno=?";
			    ps=connection.prepareStatement(sql);
			    ps.setString(1,orders.get(i).getRefno());
				rs=ps.executeQuery();
                while(rs.next()){
                	C_Recruitment_detail detail=new C_Recruitment_detail();
                	detail.setMediacode(rs.getString("mediacode"));
                	detail.setMedianame(rs.getString("medianame"));
                	detail.setPrice(rs.getDouble("price"));
                	detail.setRefno(rs.getString("refno"));
                	detail.setQuantity(rs.getDouble("quantity"));
                	details.add(detail);
                	
                   }
			    }
			 return details;
		 }catch (Exception e) {
			 logger.error("Find C_Recruitment_detail ERROR"+e.toString());
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				DBManager.closeCon(connection);
			} catch (SQLException e) {
				logger.error("Close ResultSet Error");
				e.printStackTrace();
			}  
		}
		 return null;
	 }
	 /**
		 * 增加订单和订单详情信息
		 * @param String[] demo
		 * @return int 
		 */
		public boolean addOrder(C_Recruitment_order order,C_Recruitment_list list,String refno ){

			try{	
			connection = DBManager.getCon();
			connection.setAutoCommit(false);
			String sql="insert c_recruitment_order" +
					"  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			  ps=connection.prepareStatement(sql);
			  ps.setString(1, order.getRefno());
			  ps.setString(2, order.getStaffcode());
			  ps.setString(3, order.getStaffname());
			  ps.setString(4, order.getUsertype());
			  ps.setString(5, order.getPosition());
			  ps.setString(6, order.getContactperson());
			  ps.setString(7, order.getContactemail());
			  ps.setString(8, order.getChargecode());
			  ps.setString(9, order.getChargename());
			  ps.setString(10, order.getDate());
			  ps.setString(11,order.getCreatedate());
			  ps.setString(12,order.getCreater());
			  ps.setString(13,order.getStatus());
			  ps.setString(14,order.getSfyx());
			  ps.setString(15, order.getRemark());
			  ps.setString(16, order.getFilterdate());
			  ps.executeUpdate();
			  
				 // 增加产品详情记录
			   String SQL="insert into c_recruitment_detail(refno,mediacode,medianame,price)values(?,?,?,?)";
			   ps=connection.prepareStatement(SQL);
			   ps.setString(1, refno);
			   ps.setString(2, list.getMediacode());
			   ps.setString(3, list.getMedianame());
			   ps.setDouble(4,  Double.parseDouble(list.getPrice()));
			   ps.executeUpdate();
			   connection.commit();
			  logger.info("Create c_recruitment_order  c_recruitment_detail  success");
			  return true;
			}catch (Exception e) {
				 logger.error("Create c_recruitment_order failed"+e.toString());
				 try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				 e.printStackTrace();
			}finally{
                 DBManager.closeCon(connection);   
			}
			return false;
		}
		
		/**
		 * 生成流水号
		 */
		public String getNo(){
			String num=null;
			try{
				connection=DBManager.getCon();
				String sql="select count(*) from c_recruitment_order";
				ps=connection.prepareStatement(sql);
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
					num=Constant.RECRUITMENT+DateUtils.Ordercode()+num;
				}
				rs.close();
			}catch (Exception e) {
				logger.error("get refno error");
				e.printStackTrace();
			}finally{
				DBManager.closeCon(connection);
			}
			return num;
		}
		public int saveRecuritment(String filename, InputStream os) {
			
			int num = 0;
			int beginRowIndex = 2;// 开始读取数据的行数
			int totalRows = 0;// 总行数
			//此处不做删除
			try {
				
				HSSFWorkbook workbook = new HSSFWorkbook(os);
				HSSFSheet sheet = workbook.getSheetAt(0);// 读取Sheet 页码：第一页
				totalRows = sheet.getLastRowNum();// 获取总行数
				for (int i = beginRowIndex; i <= totalRows; i++) {
					HSSFRow row = sheet.getRow(i);// 读取Excel里面的i行
					connection = DBManager.getCon();
					/** 获取Excel里面的指定单元格数据* */
					HSSFCell codecell = row.getCell(0);
					HSSFCell ctypecell = row.getCell(1);
					HSSFCell cnamecell = row.getCell(2);
					HSSFCell ccnamecell = row.getCell(3);
					HSSFCell unitpricecell = row.getCell(4);
					HSSFCell isactivecell = row.getCell(5);
				 
					/** 给数据库里面的字段赋值* */
					String procode = Util.cellToString(codecell);
					String type = Util.cellToString(ctypecell);
					String cname = Util.cellToString(cnamecell);
					String ccname = Util.cellToString(ccnamecell);
					String unitprice  = Util.cellToString(unitpricecell);
					String isactive = Util.cellToString(isactivecell);
					 
					double unitpricenum = Util.objIsNULL(unitprice)?0d:Double.valueOf(unitprice);
					//code不为空 
					if (!Util.objIsNULL(procode) ) {
						String sql = "insert c_recruitment_stock(mediacode,mediatype,medianame,price,quantity,unit,adddate,addname,remark,sfyx" +
								" ) values('"+
								procode+"','"+type+"','" + 
								cname+"',"+
								unitpricenum+",'0','','"+ //quantity,unit
								DateUtils.getNowDateTime()+"','','','Y')";
						
						ps = connection.prepareStatement(sql);
						int rsNum = ps.executeUpdate();
						if (rsNum > 0) {
							num++;
							logger.info("保存 c_recruitment_stock OK ！");
							//更新标志为Y 则更新产品表中的数据
							 if (isactive.trim().equals(Constant.YXBZ_Y)) {
								
								 int acountnum = findAllByCode(procode);
								
								//有记录存在 则更新
									if (acountnum  > 0) { 
										int updnum =updProduct(procode, type, cname, unitpricenum, 0D, "", "", "", "Y");
										logger.info("更新 c_recruitment_list！"+updnum);
									
									}else {//无记录存在 则新增
										int addnum = saveProduct(procode, type, cname, unitpricenum, 0D, "", "", "", "Y");
										logger.info("新增 c_recruitment_list！"+addnum);
									}
								}else if (isactive.trim().equals(Constant.YXBZ_D)) {
									//状态为D 则删除
									
									int numa = delProduct(procode);
									if (numa > 0) {
										logger.info("删除 c_recruitment_list！"+numa);
									
								
								}
							}
							
						} else {
							logger.info("保存 c_Mar_stock 为空！");
						}
						
					} else {
						logger.info("c_Mar_stock procode is null！");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("c_Mark_stock！" + e);
			} finally {
				// 关闭连接
				DBManager.closeCon(connection);
			}
			return num;
		
		}
		public int findAllByCode(String procode) {
			int num=-1;
			try{
				connection=DBManager.getCon();
				StringBuffer stringBuffer=new StringBuffer("select * from c_recruitment_list where mediacode ='"+procode+"'");
			
				logger.info("Query c_recruitment_list SQL:"+stringBuffer.toString());
				ps = connection.prepareStatement(stringBuffer.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					num=rs.getRow();//获取总行数
				}rs.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("c_recruitment_list 中根据条件查询数据个数时出现异常："+e.toString());
				num=0;
			}finally{
				DBManager.closeCon(connection);
			}
			return num;
		}
		/**
		 * Wilson
		 * 修改产品，
		 * 累计数量
		 */
		public int saveProduct(String procode, String type, String cname,
				Double  unitpricenum, Double quantity,String unit,  String updname, String remark,   String sfyx) {
			int r = -1;
			try {
				 
				 connection=DBManager.getCon();
					String sql = "insert c_recruitment_list(mediacode,mediatype,medianame,price,quantity,unit,adddate,addname,remark,sfyx" +
						" ) values('"+
						procode+"','"+type+"','" + 
						cname+"',"+
						unitpricenum+",'"+quantity+"','"+unit+"','"+ //quantity,unit
						DateUtils.getNowDateTime()+"','','','Y')";
				ps = connection.prepareStatement(sql);
				r = ps.executeUpdate();
				if(r<0){
					logger.info("upd c_recruitment_list Product信息表失败");
				}
				 
					
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("upd c_recruitment_list Product信息表异常！"+e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("upd c_recruitment_list Product信息表异常！"+e);
			}
			finally{
				//关闭连接
				DBManager.closeCon(connection);
			}
			return r;
				
		}
		public int updProduct(String mediacode, String mediatype, String medianame,
				Double  price, Double quantity,String unit,  String updname, String remark,   String sfyx) {
			int r = -1;
			try {
				
				connection=DBManager.getCon();
				String sql="update c_recruitment_list set mediacode='"+mediacode+"',mediatype='"+mediatype+"',medianame='"+
				medianame+"',price="+price+",quantity='"+quantity+"',unit='"+
				unit+"', adddate='"+DateUtils.getNowDateTime()+"' , addname='"+updname+"' , remark='"+remark+"',sfyx='"+sfyx+"' where mediacode='"+
				mediacode+"' ";
				logger.info("upd c_recruitment_list Product信息表SQL:"+sql);
				
				ps=connection.prepareStatement(sql);
				r=ps.executeUpdate();
				if(r<0){
					logger.info("upd c_recruitment_list Product信息表失败");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("upd c_recruitment_list Product信息表异常！"+e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("upd c_recruitment_list Product信息表异常！"+e);
			}
			finally{
				//关闭连接
				DBManager.closeCon(connection);
			}
			return r;
			
		}

		public int delProduct(String procode) {
			Connection con = null;
			PreparedStatement ps = null;
			int num =0;
			String sql = "update c_recruitment_list set sfyx='N' where mediacode ='"+procode+"'";
			try {
				con = DBManager.getCon();
				ps = con.prepareStatement(sql);
				num = ps.executeUpdate();

				System.out.println("=====del========c_recruitment_list============" + num);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return num;
		}
		/**
		 * 根据产品code查询产品对象
		 * @param code
		 * @return C_Recruitment_list
		 */
		public C_Recruitment_list findListByCode(String code){
			C_Recruitment_list list=new C_Recruitment_list();
			try{
				connection=DBManager.getCon();
				String sql="select mediacode,mediatype,medianame,price from c_recruitment_list where  mediacode=?";
				ps=connection.prepareStatement(sql);
				ps.setString(1, code);
			    rs=ps.executeQuery();
				if(rs.next()){
					list.setMediacode(rs.getString("mediacode"));
					list.setMediatype(rs.getString("mediatype"));
					list.setMedianame(rs.getString("medianame"));
					list.setPrice(rs.getDouble("price")+"");
				}else{
					list=null;
				}
				rs.close();
			}catch (Exception e){
				logger.error("Find list by mediacode  Error"+e.toString());
				e.printStackTrace();  
			}finally{
				DBManager.closeCon(connection);
			}
			return list;
		}
		
		/**
		 * 根据Staff Code获取Consultant Name
		 */
		public String  findConsultantName(String code){
			String name="";
			try{
				connection=DBManager.getCon();
				String sql="select EmployeeName  from cons_list where EmployeeId=?";
				ps=connection.prepareStatement(sql);
				ps.setString(1, code);
				rs=ps.executeQuery();
				if(rs.next()){
				name=rs.getString(1);
				}
			}catch (Exception e) {
				logger.error("Find EmployeeName from cons_list Error "+e.toString());
				e.printStackTrace();
			}
			return name;
		}
		/**
		 * 根据Staffcode 获取Name 和Position
		 */
		public Cons_listBean getPosition(String code){
			Cons_listBean cl=null;
			try{
				connection=DBManager.getCon();
				String sql="select E_PositionName,EmployeeName from cons_list where  (EmployeeId=? and Grade='AD')  or (EmployeeId=?  and Grade='DD')";
				ps=connection.prepareStatement(sql);
				ps.setString(1, code);
				ps.setString(2, code);
				rs=ps.executeQuery();
				if(rs.next()){
					cl=new Cons_listBean();
					cl.setEmployeeName(rs.getString("EmployeeName"));
					cl.setE_PositionName(rs.getString("E_PositionName"));
				}
				
			}catch (Exception e) {
				logger.error("Find  Position ,E_PositionName from   Cons_list  ERROR"+e.getMessage());
				e.printStackTrace();
			}
			return cl;
		}

		/**
		 * 定时扫描recruitment并修改状态-17点
		 * @return
		 */
		public String timeTaskScanningRecruitmentAdversiting(){
			String result="";
			int num=-1;
			Util.printLogger(logger,"开始执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态");    
			try{	
				num=ScanningRecruitmentAdversiting();
				if(num>=0){
					result="success";
					Util.printLogger(logger,"执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态成功");    
				}else{
					Util.printLogger(logger,"执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态失败");
					throw new Exception();
				}
			}catch (Exception e) {
				result=e.getMessage();
				Util.printLogger(logger,"执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态 失败"+e.getMessage());
			}
			return result;
		}
		
		public int ScanningRecruitmentAdversiting() throws Exception {
			int num=0;
			try{	
				connection = DBManager.getCon();
				connection.setAutoCommit(false);
				String sql="select a.refno from (select refno,status from c_recruitment_order where status='Confirmation Request' and sfyx='Y' and  DATE_FORMAT(createdate,'%Y-%m-%d')<DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d') ) a"+ 
							" left join"+
							" (select * from c_recruitment_operation where DATE_FORMAT(operationDate,'%Y-%m-%d')<DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d') order by operationDate desc)b on(a.refno=b.refno) where DATE_FORMAT(operationDate,'%Y-%m-%d')<DATE_FORMAT('"+DateUtils.getDateToday()+"','%Y-%m-%d')";
				  ps=connection.prepareStatement(sql);
				  rs=ps.executeQuery();
				  while(rs.next()){ 
					 //修改状态 
					 sql="update c_recruitment_order set status='Submitted',date='' where refno='"+rs.getString("refno")+"'";
					 ps=connection.prepareStatement(sql);
					 ps.execute();
					 //插入操作记录
					 sql="insert into c_recruitment_operation(refno,operationType,operationName,operationDate) values ('"+rs.getString("refno")+"','Submitted','System','"+DateUtils.getNowDateTime()+"');";
					 ps=connection.prepareStatement(sql);
					 ps.execute();
					 num++;
				  }
				  connection.commit();
				  rs.close();
				  logger.info("扫描Recruitment Adversiting 状态，并修改状态成功!!!"+sql);
			}catch (Exception e) {
				num=-1;
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					logger.error("扫描Recruitment Adversiting 状态，并修改状态时出现异常,进行数据回滚异常...."+e.getMessage());
				}
				logger.error("扫描Recruitment Adversiting 状态，并修改状态时出现异常,进行数据回滚...."+e.getMessage());
				throw new Exception(e);
			}finally{
				DBManager.closeCon(connection);
			}
			return num;
		}
		/**
		 * 根据条件查找产品
		 */
		public List<C_Recruitment_list> findByConditions(String code,String name,String type,String date,String date1,Page page){
			    List<C_Recruitment_list> list=new ArrayList<C_Recruitment_list>();
			  try{
				  connection = DBManager.getCon();
				  StringBuffer sql= new StringBuffer("select * from c_recruitment_list  where sfyx='Y'");
				  if(!(Util.objIsNULL(code))){
					  sql.append("and mediacode like '%"+code+"%'");
				  }
				  if(!Util.objIsNULL(name)){
					  sql.append("and  medianame like '%"+name+"%'");
				  }
				  if(!(Util.objIsNULL(type))){
					  sql.append("and mediatype like '%"+type+"%'");
				  }
				  if(!(Util.objIsNULL(date))){
					  sql.append("and DATE_FORMAT(adddate,'%Y-%m-%d')>=DATE_FORMAT('"+date+"','%Y-%m-%d') ");
				  }
				  if(!(Util.objIsNULL(date1))){
					  sql.append("and DATE_FORMAT(adddate,'%Y-%m-%d')<=DATE_FORMAT('"+date1+"','%Y-%m-%d') ");
				  }
				  sql.append("LIMIT ?,?");
				  ps=connection.prepareStatement(sql.toString());
				  ps.setInt(1,(page.getCurPage()-1)*page.getPageSize());
				  ps.setInt(2, page.getPageSize());
				  rs=ps.executeQuery();
				  while(rs.next()){
					 C_Recruitment_list cl=new C_Recruitment_list(); 
				      cl.setId(rs.getInt("id"));   
				      cl.setMediacode(rs.getString("mediacode"));
				      cl.setMedianame(rs.getString("medianame"));
				      cl.setMediatype(rs.getString("mediatype"));
				      cl.setPrice(rs.getString("price")); 
				      cl.setQuantity((int)rs.getDouble("quantity"));
				      cl.setUnit(rs.getString("unit"));
				      cl.setAdddate(rs.getString("adddate"));
				      cl.setAddname(rs.getString("addname"));
				      cl.setRemark(rs.getString("remark"));
				      list.add(cl);
				  } 
				  rs.close();
			  }catch (Exception e) {
				  logger.error("条件查询所有产品失败"+e.getMessage());
				   e.printStackTrace();
			}
			  finally{
				  DBManager.closeCon(connection);
			  }
			  return list;
		}
		/**
		 *  商品总记录
		 */
		public int recordCount(String code,String name,String type,String date,String date1){
			int num=-1;
			try{
				 connection = DBManager.getCon();
				  StringBuffer sql= new StringBuffer("select count(*) from c_recruitment_list  where sfyx='Y'");
				  if(!(Util.objIsNULL(code))){
					  sql.append("and mediacode like '%"+code+"%'");
				  }
				  if(!Util.objIsNULL(name)){
					  sql.append("and  medianame like '%"+name+"%'");
				  }
				  if(!(Util.objIsNULL(type))){
					  sql.append("and mediatype like '%"+type+"%'");
				  }
				  if(!(Util.objIsNULL(date))){
					  sql.append("and DATE_FORMAT(adddate,'%Y-%m-%d')>=DATE_FORMAT('"+date+"','%Y-%m-%d')");
				  }
				  if(!(Util.objIsNULL(date1))){
					  sql.append("and DATE_FORMAT(adddate,'%Y-%m-%d')<=DATE_FORMAT('"+date1+"','%Y-%m-%d') ");
				  }
				  ps=connection.prepareStatement(sql.toString());
				  rs=ps.executeQuery();
				  if(rs.next()){
					  num=rs.getInt(1);
				  }
				  rs.close();
			}catch (Exception e) {
				  e.printStackTrace();
			}finally{
				 DBManager.closeCon(connection);
			}
			
			return num;
		}
		/**
		 * 读取上传文件并更新数据库表数据的信息(C_recruitment_list)
		 */
	    public int saveOrUplist(List<List<Object>> list,String name){
	    	int num=0;
	    	int h=0;
			try{
				connection=DBManager.getCon();
				connection.setAutoCommit(false);
	            //循环遍历二维集合
	            for(int i=0;i<list.size();i++){
	            	//拿到集合的第i行
	            	List<Object> list2=list.get(i);
	            	   //判断第0列 是否为空（产品编号）           
	            	if(!(Util.objIsNULL(list2.get(0)+""))){
	            		  //产品编号不为空，写入库存表  
	            		String sql=" insert into  c_recruitment_stock (mediacode,mediatype,medianame,price,adddate,addname,sfyx) values(?,?,?,?,?,?,?)";
	            		ps=connection.prepareStatement(sql);
	            		ps.setString(1, list2.get(0).toString());
	            		ps.setString(2, list2.get(1).toString());
	            		ps.setString(3, list2.get(2).toString());
	            		ps.setDouble(4, Double.parseDouble(list2.get(4).toString()));
	            		ps.setString(5, Util.getDate("yyyy-MM-dd HH:mm:ss"));
	            		ps.setString(6, name);
	            		ps.setString(7, list2.get(5).toString());
	            	    int n=ps.executeUpdate();
	            	    if(n>0){
	            	    	 num++;
	            	     }
	            	     //查询编号
	            	    String SQL="select mediacode  from c_recruitment_list  where mediacode=?";
            	    	ps=connection.prepareStatement(SQL); 
            	    	ps.setString(1, list2.get(0).toString());
            	    	rs=ps.executeQuery();
            	    	String modiacode="";
            	    	if(rs.next()){
            	    		modiacode=rs.getString("mediacode");
            	    	}
            	    	rs.close();
            	    	String Sql=""; 
            	    	  //判断sfyx的值
	            	    if(!(Util.objIsNULL(list2.get(5))) && list2.get(5).equals("Y")){
	            	    	//sfyx==Y, 查询code,存在——>更新  , 不存——>增加
	            	    	//如果能查询到 则更新
	            	    	if(!Util.objIsNULL(modiacode)){
	            	    		//开始更新
	            	    		Sql="update  c_recruitment_list set mediatype=?,medianame=?,price=?,adddate=?,addname=?,sfyx=? where mediacode=?";
	            	    	    ps=connection.prepareStatement(Sql);
	            	    		ps.setString(1, list2.get(1).toString());
	            	    		ps.setString(2, list2.get(2).toString());
	            	    		ps.setDouble(3, Double.parseDouble(list2.get(4).toString()));
	            	    		ps.setString(4, Util.getDate("yyyy-MM-dd HH:mm:ss"));
	    	            		ps.setString(5, name);
	    	            		ps.setString(6, list2.get(5).toString());
	    	            		ps.setString(7, list2.get(0).toString());
	    	            	    int b=ps.executeUpdate();
	    	            	    if(b>0){h++;}
	            	    	}else{
	            	    		//开始添加
	            	    	    Sql="insert into c_recruitment_list(mediacode,mediatype,medianame,price,quantity,adddate,addname,sfyx)values(?,?,?,?,?,?,?,?)";
	            	    		ps=connection.prepareStatement(Sql);
	    	            		ps.setString(1, list2.get(0).toString());
	    	            		ps.setString(2, list2.get(1).toString());
	    	            		ps.setString(3, list2.get(2).toString());
	    	            		ps.setDouble(4, Double.parseDouble(list2.get(4).toString()));
	    	            		ps.setDouble(5, 1d);
	    	            		ps.setString(6, Util.getDate("yyyy-MM-dd HH:mm:ss"));
	    	            		ps.setString(7, name);
	    	            		ps.setString(8, list2.get(5).toString());
	    	            	    ps.executeUpdate();
	            	    	 }
	            	    }else{
	            	    	if(!Util.objIsNULL(modiacode)){
	            	    	    Sql="update c_recruitment_list set sfyx='D' where mediacode=?";
	            	    		ps=connection.prepareStatement(Sql);
	            	    		ps.setString(1, list2.get(0).toString());
	            	    	    ps.executeUpdate();
	            	    	}
	            	    }
	            	    connection.commit();
	            	   
	            	}else{
	            		//产品编号为空,写入日志
	            		logger.info("产品编号为空");
	            	}
	            }	
	            System.out.println("库存表添加了"+num+"条数据");
	            System.out.println("产品表更新了"+h+"条数据");
				}catch (Exception e) {
					try {
						  num=0;
						 connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    e.printStackTrace();
				}
	    	return num;
	    }
}
