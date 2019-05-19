package com.coat.pickuprecord.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.Pager;
import util.SendMail;
import util.Util;

import com.coat.pickuprecord.dao.PickUpRecordDao;
import com.coat.pickuprecord.entity.PRecordHkcode;
import com.coat.pickuprecord.entity.PRecordList;
import com.coat.pickuprecord.entity.PRecordOrder;

import dao.common.BaseDao;

public class PickUpRecordDaoImpl extends BaseDao implements PickUpRecordDao{
	ResultSet rs=null;	
	Connection conn=null;
	PreparedStatement ps=null;

	Logger logger=Logger.getLogger(this.getClass());
	@SuppressWarnings("unchecked")
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs,PRecordList.class);	
	}
	
	public Pager queryPickUpList(String[] fields, Pager page,Object... objects) throws Exception{
		String sql="FROM(select a.refno,a.documentId,a.staffcode,a.clientName,a.location,a.sender,a.documentType,a.scanDate,a.status," +
				" a.result,a.upd_date,a.sfyx,(case when b.staffcode is null then '' else b.staffcode end)as code," +
				"a.createdate FROM p_record_list a LEFT JOIN p_record_order b on (a.refno=b.listId) where a.sfyx='Y' and " +
				" a.staffcode like ? and a.clientName like ? " +
				" and  date_format(a.createdate,'%Y-%m-%d') >=date_format(?,'%Y-%m-%d') " +
				" and  date_format(a.createdate,'%Y-%m-%d') <=date_format(?,'%Y-%m-%d')" +
				" and  a.status like ? )xx ";
		String limit=" order by createdate asc limit ?,? ";
		
		//System.out.println("--->"+sql);
		
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}
	
	
	public String uploadPickUpList(List<List<Object>> list,String user) throws Exception{
		int num=0;
		int num3=0;
		int line=0;
		int num4=0;
		boolean s=true;
		String xx="";
		String scanDate="";
		String hkcode="";
		
		//List<String> listCode=new ArrayList<String>();
		PRecordHkcode p_hk=new PRecordHkcode();
		try{
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			openTransaction();
			String sql="";
			String documentId="";
			String documentType="";
		for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(3)+"")){
				line=i;
				//if(!Util.objIsNULL(list2.get(2)+"")){//-->clientName不能为空
					//-->判断staffcode是否存在
					/*code=findAllByCode(list2.get(0).toString());
					for (int j = 0; j < code.size(); j++) {
						if(code.get(j).toString().equalsIgnoreCase(list2.get(0).toString())){
							s=false;
						}else{
							s=true;
						}
					}*/
					/**
					 * 判断staffcode是否是澳门的或者国内的code Start
					 * 如果为澳门或者国内的code，则staffcode替换成香港code
					 * */
					p_hk=findAllByHKCode(list2.get(3).toString());
					if(!Util.objIsNULL(p_hk.getHkStaffcode())){
						hkcode=p_hk.getHkStaffcode();
					}else{
						hkcode=list2.get(3).toString();
					}
					
					if(s){
						documentId=list2.get(0)+"";
						
						documentType=list2.get(4)+"";
						if(list2.get(4).toString().indexOf("'")>0){
							documentType=list2.get(4).toString().replace("'", "‘");
						}
						//如果scanDate为空，则默认为当天日期
						scanDate=Util.objIsNULL(list2.get(1).toString())?DateUtils.getDateToday():DateUtils.strToYMD(list2.get(1).toString());
						sql="insert into p_record_list(documentId,staffcode,clientName,location,sender,documentType,scanDate,status,result,creator,createdate,sfyx,upd_name,upd_date)" +
								"values('"+documentId+"','"+hkcode+"','"+list2.get(2)+"','"+list2.get(5)+"','"+list2.get(8)+"','"+documentType+"','"+scanDate+"'," +
										" 'Submitted','"+list2.get(7)+"','"+user+"','"+DateUtils.getNowDateTime()+"','Y','','')";
						int refno=saveEntity(sql);
						if(refno<0){
							throw new RuntimeException("保存失败!");
						}
						/**--------------------------------保存  操作表--------------------------------------------*/
						String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate) values('"+refno+"','Submitted','"+user+"','"+DateUtils.getNowDateTime()+"');";
						num+=update(sql2, null);
						/*if(num<i){
							throw new RuntimeException();
						}*/
						/**--------------------------------保存  操作表--------------------------------------------*/
					}else{
						num3+=1;
					}
				/*}else{
					num4+=1;
				}*/
			}	
			
		}
			sumbitTransaction();//提交事物
			//connection.commit();
			xx=Util.getMsgJosnObject("success", "成功条数:"+num+"条,另外有"+num3+"条数据已存在拒绝上传");//,有"+num4+"条数据的ClientName为空
		}catch (Exception e) {
			xx=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				//connection.rollback();
				super.rollbackTransaction();//回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
				super.closeConnection();
		}
		return xx;
		
	}
	
	public String uploadPickUp_HKCode(List<List<Object>> list,String user) throws Exception{
		int num=0;
		int num3=0;
		int line=0;
		boolean s=true;
		String xx="";
		//List<String> code=new ArrayList<String>();
		PRecordHkcode p_hk=new PRecordHkcode();
		try{
			if(Util.objIsNULL(user)){
				throw new RuntimeException("Identity information is missing");
			}
			openTransaction();
			String sql="";
		for(int i=1;i<list.size();i++){
				List<Object> list2=list.get(i);
			if(!Util.objIsNULL(list2.get(0)+"")){
				line=i;
					/**
					 * 判断staffcode是否存在
					 */
					p_hk=findAllByHKCode(list2.get(0).toString());
					if(!Util.objIsNULL(p_hk.getStaffcode())){
						s=false;
					}else{
						s=true;
					}
					if(s){
						sql="insert into p_record_HKCode(staffcode,HK_staffcode,staffname,creator,createdate,sfyx)" +
								"values('"+list2.get(0)+"','"+list2.get(1)+"','"+list2.get(2)+"', '"+user+"','"+DateUtils.getNowDateTime()+"','Y')";
						num+=update(sql, null);
						if(num<(i+0)){
							throw new RuntimeException();
						}
					}else{
						num3+=1;
					}
			}	
		}
			sumbitTransaction();//提交事物
			xx=Util.getMsgJosnObject("success", "成功条数:"+num+"条,其中有"+num3+"条数据已存在拒绝上传");
		}catch (Exception e) {
			xx=Util.getMsgJosnObject("exception", "上传失败，第"+line+"行出错:"+e);
			e.printStackTrace();
			try {
				super.rollbackTransaction();//回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
				super.closeConnection();
		}
		return xx;
	}
	
	//判断staffcode是否存在
	public List<String> findAllByCode(String itemcode) throws Exception{
		List<String> code=new ArrayList<String>();
		Connection con=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select staffcode from p_record_list where staffcode ='"+itemcode+"'");
			logger.info("Query p_record_list SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				code.add(rs.getString(1));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("p_record_list 中根据条件查询数据个数时出现异常："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return code;
	}
	
	//判断staffcode是否存在
	public PRecordHkcode findAllByHKCode(String itemcode) throws Exception{
		PRecordHkcode code=new PRecordHkcode();
		Connection con=null;
		try{
			con=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select staffcode,HK_staffcode from p_record_HKCode where staffcode ='"+itemcode+"' and sfyx='Y' ");
			logger.info("Query p_record_list SQL:"+stringBuffer.toString());
			ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				code.setStaffcode(rs.getString("staffcode"));
				code.setHkStaffcode(rs.getString("HK_staffcode"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("p_record_list 中根据条件查询数据个数时出现异常："+e.toString());
		}finally{
			DBManager.closeCon(con);
		}
		return code;
	}
	
	
    
	public List<PRecordList> findList(String staffcrad) throws SQLException {
		List<PRecordList> p_record=new ArrayList<PRecordList>();
		List<Map<String,Object>> p_record2=new ArrayList<Map<String,Object>>();
		String extension="";
		try{
			conn=DBManager.getCon();
			StringBuffer stringBuffer=new StringBuffer("select * from p_record_list where sfyx='Y' and status='Ready' and staffcode ='"+staffcrad+"'");
			logger.info("Query p_record_list SQL:"+stringBuffer.toString());
			//p_record= Objectlist(stringBuffer.toString());
			p_record2= findListMap(stringBuffer.toString());
			
			String sql="select extension from c_adminservice_allextension where staffcode='"+staffcrad+"'   ";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				extension=rs.getString("extension");
			}
			rs.close();
			
			
			for (int i = 0; i < p_record2.size(); i++) {
				p_record.add(new PRecordList(
						(Integer)p_record2.get(i).get("refno"),
						(String)p_record2.get(i).get("staffcode"),
						(String)p_record2.get(i).get("clientName"),
						(String)p_record2.get(i).get("location"),
						(String)p_record2.get(i).get("sender"),
						(String)p_record2.get(i).get("documentId"),
						(String)p_record2.get(i).get("documentType"),
						(String)p_record2.get(i).get("scanDate"),
						(String)p_record2.get(i).get("status"),
						(String)p_record2.get(i).get("result"),
						(String)p_record2.get(i).get("creator"),
						(String)p_record2.get(i).get("createdate"),
						(String)p_record2.get(i).get("sfyx"),extension
				));
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("p_record_list 中根据条件查询数据个数时出现异常："+e.toString());
		}finally{
			super.closeConnection();
			DBManager.closeCon(conn);
		}
		return p_record;
	}

	public int saveOrder(String listIds,String staffcard,String otherCode,String exrension,
			String password,String adminUsername)throws SQLException {
			String listId[]=listIds.split("~,~");
			String sql="";
			String all_extension="";
			String all_pwd="";
			int num=0;
			String refno="";
		try {
			/**
			 * 生成流水号
			 */
			synchronized (this) {
				refno=findref();
				if(Util.objIsNULL(refno)){
					throw new Exception("流水号产生异常");
				}
			}
			openTransaction();
			/******************************************比对内线号和密码是否正确*************************************************************************/
			String sql3="select staffcode,extension,password from c_adminservice_allextension where staffcode='"+otherCode+"'  ";
			
			Map<String,Object> map=findMap(sql3);
			if(map.size()>0){
				all_extension=(String) map.get("extension");
				all_pwd=(String) map.get("password");
			}
			
			if(!all_extension.equalsIgnoreCase(exrension) || !all_pwd.equalsIgnoreCase(password)){
				return num=-2;
				//throw new RuntimeException("内线号或者密码匹配不符!");
			}
			/************************************************************************************************************************************/

			for (int i = 0; i < listId.length; i++) {
				String sql4="select * from p_record_order where sfyx='Y' and status='Open' and listId='"+listId[i]+"' ";
				List<Object[]> listOrder=findDate(sql4);
				if(listOrder.size()>0){
					//--<<有数据则不保存
					num+=1;
				}else{
					//--<<保存订单信息
					sql="insert p_record_order(refno,listId,staffcode,signcode,extension,password,creator,createdate,sfyx,status)values(" +
					" '"+refno+"','"+listId[i]+"','"+staffcard+"','"+otherCode+"','"+exrension+"','"+password+"','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','Y','Open')";
					
					String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate)" +
					" values('"+listId[i]+"','saveOrder','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
					num=batchExcute(new String[]{sql,sql2});
					logger.info("保存PickUpRecord p_record_order 时    ====SQL:"+sql);
					if(num<1){
						throw new RuntimeException("保存失败!");
					}
				}
				
			}
			
			sumbitTransaction();//提交事物
		} catch (Exception e) {
			num=-1;
			e.printStackTrace();
			super.rollbackTransaction();//回滚事务
			logger.error("保存PickUpRecord p_record_order信息异常！"+e);
		}finally{
			super.closeConnection();
		}
		return num;
	}
	
	public int saveReceive(String listIds,String adminUsername)throws SQLException {
			String listId[]=listIds.split("~,~");
			String sql="";
			String sql2="";
			int num=-1;
		try{
			openTransaction();
			for (int i = 0; i < listId.length; i++) {
				//--<<保存订单信息
				sql="update p_record_list set status='Received', upd_name='"+adminUsername+"',upd_date='"+DateUtils.getNowDateTime()+"'" +
						" where sfyx='Y' and status='Ready' and refno='"+listId[i]+"' ";
				sql2="update p_record_order set status='Close' where sfyx='Y' and status='Open' and listId='"+listId[i]+"' ";
				String sql3="insert into p_record_operation(refno,operationType,operationName,operationDate)" +
				" values('"+listId[i]+"','Received','"+adminUsername+"','"+DateUtils.getNowDateTime()+"')";
				num=batchExcute(new String[]{sql,sql2,sql3});
				logger.info("保存PickUpRecord p_record_order 时    ====SQL:"+sql);
				if(num<1){
					throw new RuntimeException("保存失败!");
				}
			}
			sumbitTransaction();//提交事物
		}catch (Exception e) {
			num=-1;
			e.printStackTrace();
			super.rollbackTransaction();//回滚事务
			logger.error("保存PickUpRecord p_record_order信息异常！"+e);
		}finally{
			super.closeConnection();
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
			String sql="select count(*) from p_record_order";
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
				num=Constant.PICKUPRECORD+DateUtils.Ordercode()+num;
			}rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeCon(conn);
		}
		return num;
	}
	
	public String HKOAdm_Ready(PRecordList rnb,String username,String refno) throws SQLException{
		int s=-1;
		String result="";
		String Email="";
		String staffname="";
		String location="";
		try{
			openTransaction();
			//--<<更新p_record_list
			String sql="update p_record_list set status='Ready', clientName='"+rnb.getClientName()+"', location='"+rnb.getLocation()+"', " +
					" sender='"+rnb.getSender()+"',documentId='"+rnb.getDocumentId()+"',documentType='"+rnb.getDocumentType()+"',scanDate='"+rnb.getScanDate()+"', " +
					" result='"+rnb.getResult()+"' where  status='Submitted' and sfyx='Y' and refno="+refno+" ";
			String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate)" +
					" values('"+refno+"','Ready','"+username+"','"+DateUtils.getNowDateTime()+"')";
			s=batchExcute(new String[]{sql,sql2});
			logger.info("PickUpRecord HKOAdm Approve Request  Convoy 时    ====SQL:"+sql+"\r\n"+sql+"\r\n"+sql);
		/****************************************发送邮件通知顾问********************************************************************/
			if("CP3".equalsIgnoreCase(rnb.getLocation())){
				location="17/F Mailing Room";
			}else{
				location="40/F Mailing Room";
			}
			//cons_list--<<获取邮箱地址
			String sql3="select * from (select staffcode,Email,staffname from staff_list a" +
					" union all select  EmployeeId ,Email,EmployeeName from cons_list b ) xx where staffcode='"+rnb.getStaffcode()+"' ";
			
			Map<String, Object> map =findMap(sql3);
			if(map.size()>0){
				Email=(String) map.get("Email");
				staffname=(String) map.get("staffname");
			}
			//System.out.println(Email+"---------------------");
			if(s>1){
				//String body="Dear "+staffname+",<br/>&nbsp;&nbsp;Your name card request has been processed and the name card will ready after 3 working days";
				String body="Dear "+rnb.getStaffcode()+" ,<br/><br/><br/>" +
				"	Please collect your "+rnb.getDocumentType()+"" ;
				if(!Util.objIsNULL(rnb.getClientName())){
					body+=" (Client Name："+rnb.getClientName()+" )" ;
				}
				if(!Util.objIsNULL(rnb.getSender())){
					body+=" from "+rnb.getSender();
				}
				body+=" at "+location+".<br/> " +
				"	Should you have any enquiries, please contact ADM hotline at ext 3667.<br/><br/><br/>" +
				"	Administration Department";
				
				if(Util.objIsNULL(Email)){
					throw new RuntimeException("The applicant email address is empty");
				}else{
					result=SendMail.send("Document Pick Up.",Email, body, "email.ftl", "");
					JSONObject json=new JSONObject(result);
					if(!"success".equalsIgnoreCase(json.get("state")+"")){
						throw new RuntimeException("HKOAdm_Ready"+json.getString("msg"));
					}
				}
			}else{
				throw new RuntimeException("HKOAdm_Ready Approval Error");
			}
		/****************************************发送邮件通知顾问********************************************************************/
			sumbitTransaction();//提交事物
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("插入HKOAdm_Ready信息保存异常！"+e.getMessage());
		}finally{
			super.closeConnection();
		}
		return result;
		
	}

	public String delList(String user,String refno) throws SQLException {
		int s=-1;
		String result="";
		try{
			openTransaction();
			//--<<更新p_record_list
			String sql="update p_record_list set status='Deleted'  where sfyx='Y' and status!='Receive' and refno="+refno+" ";
			String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate)" +
					" values('"+refno+"','Delete','"+user+"','"+DateUtils.getNowDateTime()+"')";
			s=batchExcute(new String[]{sql,sql2});
			logger.info("PickUpRecord HKOAdm Approve Request  Convoy 时    ====SQL:"+sql+"\r\n"+sql2);
			if(s<2){
				throw new RuntimeException("删除异常!");
			}else{
				result=Util.getMsgJosnObject("success", "Success");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("插入HKOAdm_Ready信息保存异常！"+e.getMessage());
		}finally{
			super.closeConnection();
		}
		return result;
		
	}
	/**
	 * 拒绝申请
	 * @param user
	 * @param refno
	 * @return
	 * @throws SQLException
	 */
	public String rejectList(String user,String refno) throws SQLException {
		int s=-1;
		String result="";
		String status="Return to Sender";
		try{
			openTransaction();
			//--<<更新p_record_list
			String sql="update p_record_list set status='"+status+"'  where sfyx='Y' and status!='Receive' and refno="+refno+" ";
			String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate)" +
					" values('"+refno+"','Return to Sender','"+user+"','"+DateUtils.getNowDateTime()+"')";
			s=batchExcute(new String[]{sql,sql2});
			logger.info("PickUpRecord HKOAdm Approve Request  Convoy 时    ====SQL:"+sql+"\r\n"+sql2);
			if(s<2){
				throw new RuntimeException("删除异常!");
			}else{
				result=Util.getMsgJosnObject("success", "Success");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("插入HKOAdm_Ready信息保存异常！"+e.getMessage());
		}finally{
			super.closeConnection();
		}
		return result;
		
	}
	
	 
	public List<PRecordOrder> findByOrderCode(String staffcrad) throws Exception{
		List<PRecordOrder> p_order2=new ArrayList<PRecordOrder>();
		List<Map<String,Object>> p_order=new ArrayList<Map<String,Object>>();
		try{
			StringBuffer stringBuffer=new StringBuffer("select * from p_record_order where sfyx='Y' and status='Open' and staffcode ='"+staffcrad+"'");
			logger.info("Query p_record_order SQL:"+stringBuffer.toString());
			p_order= findListMap(stringBuffer.toString());
			for (int i = 0; i <p_order.size(); i++) {
				String sql="select * from p_record_list where status='Ready' and refno='"+p_order.get(i).get("listId")+"'";
				Map<String,Object> map=findMap(sql);
				if(map.size()>0){
					p_order2.add(new PRecordOrder(
						(Integer)p_order.get(i).get("listId"),
						(String)map.get("staffcode"),
						(String)p_order.get(i).get("signcode"),
						(String)map.get("clientName"),
						(String)map.get("sender"),
						(String)map.get("documentId"),
						(String)map.get("documentType"),
						(String)map.get("scanDate")));
					
				}
				
			}
			/*ps = con.prepareStatement(stringBuffer.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String sql="select * from p_record_list where status='Ready' and refno='"+rs.getInt("listId")+"'";
				ps2 = con.prepareStatement(sql);
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()){
					p_order.add(new PRecordOrder(
						rs.getInt("listId"),
						rs2.getString("staffcode"),
						rs.getString("signcode"),
						rs2.getString("clientName"),
						rs2.getString("sender"),
						rs2.getString("documentType"),
						rs2.getString("scanDate")));
				}
				rs2.close();
			}
			rs.close();*/
		}catch(Exception e){
			e.printStackTrace();
			logger.error("p_record_order 中根据条件查询数据个数时出现异常："+e.toString());
		}finally{
			super.closeConnection();
		}
		return p_order2;
	}

	public String del_Order(String code) throws SQLException {
		int s=-1;
		String result="";
		try{
			openTransaction();
			//--<<更新p_record_list
			String sql="update p_record_order set sfyx='N'  where sfyx='Y' and status='Open' and staffcode='"+code+"' ";
			s=batchExcute(new String[]{sql});
			logger.info("PickUpRecord 删除 p_record_order 时    ====SQL:"+sql);
			if(s<1){
				throw new RuntimeException("删除异常!");
			}else{
				result=Util.getMsgJosnObject("success", "Success");
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("删除p_record_order信息异常！"+e.getMessage());
		}finally{
			super.closeConnection();
		}
		return result;
		
	}

	public int addList(String staffcode, String clientname, String location,String documentType, String scandate, String sender,
			String remark,String adminUsername,String documentId) throws SQLException {
		int num=-1;
		String sql="";
		try {
			openTransaction();
			//如果scanDate为空，则默认为当天日期
			String scanDate=Util.objIsNULL(scandate)?DateUtils.getDateToday():scandate;
			sql="insert into p_record_list(staffcode,clientName,location,sender,documentId,documentType,scanDate,status,result,creator,createdate,sfyx,upd_name,upd_date)" +
					"values('"+staffcode+"','"+clientname+"','"+location+"','"+sender+"','"+documentId+"','"+documentType+"','"+scanDate+"'," +
							" 'Submitted','"+remark+"','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','Y','','')";
			int refno=saveEntity(sql);
			if(refno<0){
				throw new RuntimeException("保存失败!");
			}
			/**--------------------------------保存  操作表--------------------------------------------*/
			String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate) values('"+refno+"','Submitted','"+adminUsername+"','"+DateUtils.getNowDateTime()+"');";
			num=update(sql2, null);
			if(num<0){
				throw new RuntimeException();
			}
			/**--------------------------------保存  操作表--------------------------------------------*/
			sumbitTransaction();//提交事物
		}catch (Exception e) {
			num=-1;
			e.printStackTrace();
			logger.error("新增单个p_record_List信息异常！"+e.getMessage());
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			super.closeConnection();
		}
		return num;
	}
	
	
	public String batchReady(String refnos,String staffCodes,String clientNames,String username) throws SQLException{
		int s=-1;
		String result="";
		String Email="";
		String staffname="";
		String sender="";
		String location="";
		String documentType="";
		try{
			openTransaction();
			String refno[]=refnos.split("~,~");
			String staffcode[]=staffCodes.split("~,~");
			String clientname[]=clientNames.split("~,~");
			String a[]=Util.getDistinct(staffcode);//数组去除重复数据
			String code=Arrays.toString(a).replace("[", "'").replace("]", "'").replace(",", "','").replaceAll(" ", "");
			
			String sql4="select count(distinct staffcode) from (select staffcode,Email from staff_list a union all select  EmployeeId ,Email from cons_list b ) xx where staffcode in ("+code+")";
			int num=findCount(sql4);
			
			if(num==a.length){
				for (int i = 0; i < refno.length; i++) {
					//--<<更新p_record_list
					String sql="update p_record_list set status='Ready', upd_name='"+username+"',upd_date='"+DateUtils.getNowDateTime()+"'" +
					" where status='Submitted' and sfyx='Y' and refno="+refno[i]+" ";
					String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate)" +
					" values('"+refno[i]+"','Ready','"+username+"','"+DateUtils.getNowDateTime()+"')";
					s=batchExcute(new String[]{sql,sql2});
					logger.info("PickUpRecord HKOAdm Approve Request  Convoy 时    ====SQL:"+sql+"\r\n"+sql+"\r\n"+sql);
				
					/****************************************发送邮件通知顾问********************************************************************/
					String sql5="select *from p_record_list where refno='"+refno[i]+"' ";
					
					Map<String, Object> map2 =findMap(sql5);
					if(map2.size()>0){
						sender=(String) map2.get("sender");
						location=(String) map2.get("location");
						documentType=(String)map2.get("documentType");
					}
					
					if("CP3".equalsIgnoreCase(location)){
						location="17/F Mailing Room";
					}else{
						location="40/F Mailing Room";
					}
					
					//cons_list--<<获取邮箱地址
					String sql3="select * from (select staffcode,Email,staffname from staff_list a" +
					" union all select  EmployeeId ,Email,EmployeeName from cons_list b ) xx where staffcode='"+staffcode[i]+"' ";
					
					Map<String, Object> map =findMap(sql3);
					if(map.size()>0){
						Email=(String) map.get("Email");
						staffname=(String) map.get("staffcode");
					}
					//System.out.println(Email+"---------------------");
					if(s>1){
						String body="Dear "+staffcode[i]+" ,<br/><br/><br/>" +
						"	Please collect your "+documentType+"" ;
						if(!Util.objIsNULL(clientname[i])){
							body+=" (Client Name："+clientname[i]+" )" ;
						}
						if(!Util.objIsNULL(sender)){
							 body+=" from "+sender;
						}
					    body+=" at "+location+" <br/>" +
						"Should you have any enquiries, please contact ADM hotline at ext 3667.<br/><br/><br/>" +
						"Administration Department";
						//String body="Dear "+staffcode[i]+",<br/>&nbsp;&nbsp;Your name card request has been processed and the name card will ready after 3 working days";
						if(Util.objIsNULL(Email)){
							throw new RuntimeException("The applicant email address is empty");
						}else{
							result=SendMail.send("Document Pick Up.",Email, body, "email.ftl", "");
							JSONObject json=new JSONObject(result);
							if(!"success".equalsIgnoreCase(json.get("state")+"")){
								throw new RuntimeException("HKOAdm_Ready"+json.getString("msg"));
							}
						}
					}else{
						throw new RuntimeException("HKOAdm_Ready Approval Error");
					}
				/****************************************发送邮件通知顾问********************************************************************/
				}
			}else{
				result= "{\"msg\": \"部分邮箱获取失败,拒绝操作!\" , \"state\": \"erroy\" }";
			}
			sumbitTransaction();//提交事物
		}catch(Exception e){
			result=Util.jointException(e);
			super.rollbackTransaction();
			logger.error("插入HKOAdm_Ready信息保存异常！"+e.getMessage());
		}finally{
			super.closeConnection();
		}
		return result;
	}
	/**
	 * 定时邮件提醒
	 */
	public int p_sendEmail() {
		int num=-1;
		String Email="";
		String staffname="";
		String result="";
		String staffcode="";
		String sender="";
		String location="";
		String documentType="";
		String clientName="";
		try {
			openTransaction();
			//-->查询所有为Ready状态的数据
			String sql4="select distinct staffcode from p_record_list where status='Ready' and sfyx='Y' ";
			
			List<Map<String,Object>> listmap=findListMap(sql4);
			for (int i = 0; i < listmap.size(); i++) {
				String sql5="select * from  p_record_emial where staffcode='"+listmap.get(i).get("staffcode")+"'" +
						" and date_format(createdate,'%Y-%m-%d')=date_format('"+DateUtils.getDateToday()+"','%Y-%m-%d') ";
				
				Map<String, Object> findEmail =findMap(sql5);
				if(findEmail.size()<=0){//如果当天有没有邮件记录，则发送邮件
				    /****************************************发送邮件通知顾问********************************************************************/
					//cons_list--<<获取邮箱地址
					String sql3="select * from (select staffcode,Email,staffname from staff_list a" +
						" union all select  EmployeeId ,Email,EmployeeName from cons_list b )" +
						" xx where staffcode='"+ listmap.get(i).get("staffcode")+"' ";
					Map<String, Object> map =findMap(sql3);
					if(map.size()>0){
						Email=(String) map.get("Email");
						staffname=(String) map.get("staffname");
						staffcode=(String)map.get("staffcode");
					}
					
					String sql6="select * from p_record_list where status='Ready' and sfyx='Y' and staffcode='"+listmap.get(i).get("staffcode")+"' ";
					Map<String, Object> map2 =findMap(sql6);
					if(map2.size()>0){
						sender=(String) map2.get("sender");
						location=(String) map2.get("location");
						documentType=(String)map2.get("documentType");
						clientName=(String)map2.get("clientName");
					}
					if("CP3".equalsIgnoreCase(location)){
						location="17/F Mailing Room";
					}else{
						location="40/F Mailing Room";
					}
					
					//String body="Dear "+staffname+",<br/>&nbsp;&nbsp;Your name card request has been processed and the name card will ready after 3 working days";
					String body="Dear "+staffcode+" ,<br/><br/><br/>" +
					"	Please collect your "+documentType+"" ;
					if(!Util.objIsNULL(clientName)){
						body+=" (Client Name："+clientName+" )" ;
					}
					if(!Util.objIsNULL(sender)){
						body+=" from "+sender;
					}
					body+=" at "+location+" <br/>" +
					"	Should you have any enquiries, please contact ADM hotline at ext 3667.<br/><br/><br/>" +
					"Administration Department";
					
					if(Util.objIsNULL(Email)){
						throw new RuntimeException("The applicant email address is empty");
					}else{
						/**
						 * 插入邮件记录
						 */
						String sql2="insert into p_record_emial(staffcode,creator,createdate)" +
								" values('"+listmap.get(i).get("staffcode")+"','System','"+DateUtils.getNowDateTime()+"')";
						int a=update(sql2,null);
						if(a<0){
							throw new RuntimeException("Save records of mail failure!");
						}
						
						result=SendMail.send("Document Pick Up.",Email, body, "email.ftl", "");
						JSONObject json=new JSONObject(result);
						if("success".equalsIgnoreCase(json.get("state")+"")){
							num=1;
						}else{
							num=-1;
							throw new RuntimeException("HKOAdm_Ready"+json.getString("msg"));
						}
					}
					/****************************************发送邮件通知顾问********************************************************************/
				}else{
					num=0;
				}
			}
		} catch (Exception e) {
			num=-1;
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				super.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num;
	}
	
	/*public Result queryPickUpListSet(String staffcode, String clientName,String scandateS, String scandateE,String status)throws SQLException{
		Result rss=null;
		try{
			conn=DBManager.getCon();
			StringBuffer sqlString=new StringBuffer("select a.refno,a.staffcode,a.clientName,a.location,a.sender,a.documentType,a.scanDate,a.status," +
					" a.result,a.upd_date,a.sfyx,(case when b.staffcode is null then '' else b.staffcode end)as code," +
					" a.createdate FROM p_record_list a LEFT JOIN p_record_order b on (a.refno=b.listId) where a.sfyx='Y' ");
				
			if(!Util.objIsNULL(staffcode)){
				sqlString.append(" and a.staffcode like '%"+staffcode+"%' ");
			}
			if(!Util.objIsNULL(clientName)){
				sqlString.append(" and a.clientName like '%"+clientName+"%' ");
			}
			if(!Util.objIsNULL(status)){
				sqlString.append(" and a.status like '%"+status+"%' ");
			}
			if(!Util.objIsNULL(scandateS)){
				sqlString.append(" and date_format(a.createdate,'%Y-%m-%d')>='"+scandateS+"' ");
			}
			if(!Util.objIsNULL(scandateE)){
				sqlString.append(" and date_format(a.createdate,'%Y-%m-%d')<='"+scandateE+"' ");
			}
			sqlString.append(" order by createdate");
			ps=conn.prepareStatement(sqlString.toString());
			rs=ps.executeQuery();
			rss=ResultSupport.toResult(rs);
			rs.close();
		}catch (Exception e) {
			logger.error("导出AccessCard 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			
			DBManager.closeCon(conn);
		}
		return rss;

	}*/

	public List<Map<String,Object>> queryPickUpListSet(String staffcode, String clientName,String scandateS, String scandateE,String status)throws SQLException{
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			StringBuffer sqlString=new StringBuffer("select a.documentId,a.refno,a.staffcode,a.clientName,a.location,a.sender,a.documentType,a.scanDate,a.status," +
					" a.result,a.upd_date,a.sfyx,(case when b.staffcode is null then '' else b.staffcode end)as code," +
					" a.createdate FROM p_record_list a LEFT JOIN p_record_order b on (a.refno=b.listId) where a.sfyx='Y' ");
				
			if(!Util.objIsNULL(staffcode)){
				sqlString.append(" and a.staffcode like '%"+staffcode+"%' ");
			}
			if(!Util.objIsNULL(clientName)){
				sqlString.append(" and a.clientName like '%"+clientName+"%' ");
			}
			if(!Util.objIsNULL(status)){
				sqlString.append(" and a.status like '%"+status+"%' ");
			}
			if(!Util.objIsNULL(scandateS)){
				sqlString.append(" and date_format(a.createdate,'%Y-%m-%d')>='"+scandateS+"' ");
			}
			if(!Util.objIsNULL(scandateE)){
				sqlString.append(" and date_format(a.createdate,'%Y-%m-%d')<='"+scandateE+"' ");
			}
			sqlString.append(" order by createdate");
			list=findListMap(sqlString.toString());
		}catch (Exception e) {
			logger.error("导出AccessCard 异常=="+e.getMessage());
			e.printStackTrace();
		}finally{
			super.closeConnection();
		}
		return list;

	}

	public String saveListInterface(String documentId,String staffcode,String clientname,String location,String sender,
 			String documentType,String scanDate,String adminUsername) throws SQLException {
		int num=-1;
		String sql="";
		String result="";
		try {
			if(Util.objIsNULL(documentId)||Util.objIsNULL(documentType)||Util.objIsNULL(staffcode)||Util.objIsNULL(location)){
				throw new RuntimeException("必填字段不能为空!");
			}
			
			openTransaction();
			//如果scanDate为空，则默认为当天日期
			scanDate=Util.objIsNULL(scanDate)?DateUtils.getDateToday():scanDate;
			sql="insert into p_record_list(staffcode,clientName,location,sender,documentId,documentType,scanDate,status,result,creator,createdate,sfyx,upd_name,upd_date)" +
					"values('"+staffcode+"','"+clientname+"','"+location+"','"+sender+"','"+documentId+"','"+documentType+"','"+scanDate+"'," +
							" 'Submitted','','"+adminUsername+"','"+DateUtils.getNowDateTime()+"','Y','','')";
			int refno=saveEntity(sql);
			if(refno<0){
				throw new RuntimeException("保存失败!");
			}
			/**--------------------------------保存  操作表--------------------------------------------*/
			String sql2="insert into p_record_operation(refno,operationType,operationName,operationDate) values('"+refno+"','Submitted','"+adminUsername+"','"+DateUtils.getNowDateTime()+"');";
			num=update(sql2, null);
			if(num<0){
				throw new RuntimeException("保存失败!");
			}
			/**--------------------------------保存  操作表--------------------------------------------*/
			
			/**--------------------------------发送邮件 Start--------------------------------------------*/
			/*String Email="";
			//cons_list--<<获取邮箱地址
			String sql3="select * from (select staffcode,Email,staffname from staff_list a" +
					" union all select  EmployeeId ,Email,EmployeeName from cons_list b ) xx where staffcode='"+staffcode+"' ";
			
			Map<String, Object> map =findMap(sql3);
			if(map.size()>0){
				Email=(String) map.get("Email");
			}
			
			if("CP3".equalsIgnoreCase(location)){
				location="17/F Mailing Room";
			}else{
				location="40/F Mailing Room";
			}
			String body="Dear "+staffcode+" ,<br/><br/><br/>" +
			"	Please collect your "+documentType+"" ;
			if(!Util.objIsNULL(clientname)){
				body+=" (Client Name："+clientname+" )" ;
			}
			if(!Util.objIsNULL(sender)){
				body+=" from "+sender;
			}
			body+=" at "+location+".<br/> " +
			"	Should you have any enquiries, please contact ADM hotline at ext 3667.<br/><br/><br/>" +
			"	Administration Department";
			
			String str="";
			if(Util.objIsNULL(Email)){
				throw new RuntimeException("The applicant email address is empty");
			}else{
				str=SendMail.send("Document Pick Up.",Email, body, "email.ftl", "");
				JSONObject json=new JSONObject(str);
				if(!"success".equalsIgnoreCase(json.get("state")+"")){
					throw new RuntimeException("HKOAdm_Ready"+json.getString("msg"));
				}
			}
			*/
			
			/**--------------------------------发送邮件 Start--------------------------------------------*/
			result=Util.getMsgJosnObject("success", "success");
			
			sumbitTransaction();//提交事物
		}catch (Exception e) {
			num=-1;
			result=Util.getMsgJosnObject("error", e.toString());
			e.printStackTrace();
			logger.error("新增p_record_List信息异常！"+e.getMessage());
			try {
				super.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			super.closeConnection();
		}
		return result;
	}
	
	
}
