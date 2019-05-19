package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;
import util.DateUtils;
import util.Util;
import dao.AddRequestDao;
import dao.AdminDAO;
import dao.ConsconvoyNamecardDao;
import dao.PayerDao;
import dao.RequestRecordDao;
import dao.impl.AddRequestDaoImpl;
import dao.impl.ConsconvoyNamecardDaoImpl;
import dao.impl.PayerDaoImpl;
import dao.impl.RequestRecordDaoImpl;
import entity.Change_Record;
import entity.RequestNewBean;

@SuppressWarnings("serial")
public class AddNewRequestconvoyServlet extends HttpServlet {
	Logger log=Logger.getLogger(AddNewRequestconvoyServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		RequestNewBean rnb=new RequestNewBean();
		RequestRecordDao rrd=new RequestRecordDaoImpl();
		String adminUsername =null;
		try{
		adminUsername= request.getSession().getAttribute("adminUsername").toString();
		String StaffNo=request.getParameter("StaffNos");
		String Payer=request.getParameter("payers");
		String urgentDate=request.getParameter("urgentDate");
		String pay=request.getParameter("pays");
		StringBuffer stringBuffer=new StringBuffer("");
		//String sfs=Util.objIsNULL(request.getParameter("sfs"))?"N":request.getParameter("sfs");
		String DD=request.getParameter("DD");//判断是否是DD/DD Tree Head
		rnb.setStaff_code(request.getParameter("StaffNos"));
		rnb.setName(request.getParameter("EnglishNames"));
		rnb.setName_chinese(request.getParameter("ChineseNames"));
		rnb.setTitle_english(request.getParameter("EnglishPositions"));
		rnb.setTitle_chinese(request.getParameter("ChinesePositions"));
		rnb.setExternal_english(request.getParameter("EnglishExternalTitles"));
		rnb.setExternal_chinese(request.getParameter("ChineseExternalTitles"));
		rnb.setAcademic_title_e(request.getParameter("EnglishAcademicTitles"));
		rnb.setAcademic_title_c(request.getParameter("ChineseAcademicTitles"));
		rnb.setProfess_title_e(request.getParameter("EnglishProfessionalTitles"));
		rnb.setProfess_title_c(request.getParameter("ChineseProfessionalTitles"));
		rnb.setUrgentDate(Util.objIsNULL(urgentDate)?DateUtils.getNowDateTime():urgentDate);
		rnb.setTr_reg_no(request.getParameter("TR_RegNos"));
		rnb.setCe_no(request.getParameter("CENOs"));
		rnb.setMpf_no(request.getParameter("MPFA_NOs"));
		rnb.setE_mail(request.getParameter("Emails"));
		rnb.setDirect_line(request.getParameter("DirectLines"));
		rnb.setFax(request.getParameter("FAXs"));
		rnb.setBobile_number(request.getParameter("Mobiles"));
		rnb.setQuantity(request.getParameter("nums"));
		rnb.setLocation(request.getParameter("locatins"));
		rnb.setLayout_type(request.getParameter("types").trim());
	    rnb.setAe_consultant(Util.objIsNULL(request.getParameter("AEs"))?"N":request.getParameter("AEs"));
	    rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
		rnb.setCard_type("N");
		rnb.setUpd_name(request.getSession().getAttribute("adminUsername").toString());
		rnb.setUpd_date(Util.dateToStrWithoutHm(new Date())); 
		rnb.setCFS_only(Util.objIsNULL(request.getParameter("CFS"))?"N":request.getParameter("CFS"));
		rnb.setCAM_only(Util.objIsNULL(request.getParameter("CAM"))?"N":request.getParameter("CAM"));
		rnb.setCIS_only(Util.objIsNULL(request.getParameter("CIS"))?"N":request.getParameter("CIS"));
		rnb.setCCL_only(Util.objIsNULL(request.getParameter("CCL"))?"N":request.getParameter("CCL"));
		rnb.setCFSH_only(Util.objIsNULL(request.getParameter("CFSH"))?"N":request.getParameter("CFSH"));
		rnb.setCMS_only(Util.objIsNULL(request.getParameter("CMS"))?"N":request.getParameter("CMS"));
		rnb.setCFG_only(Util.objIsNULL(request.getParameter("CFG"))?"N":request.getParameter("CFG"));
		rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
		rnb.setPayer(request.getParameter("payers"));
		rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		boolean isElite=rrd.vailElite_request_new(rnb.getStaff_code());
		if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
			stringBuffer.append("Premium Layout ");
		}
		if(rnb.getEliteTeam().toUpperCase().trim().equals("Y")){//顾问为Elite Team 成员
			if(stringBuffer.length()>0)
				stringBuffer.append("+ ");
			stringBuffer.append("Elite Team  ");

		}
		if(!StaffNo.toLowerCase().trim().equals(Payer.toLowerCase().trim())){//名片名字与支付人名字 不相同
			if(stringBuffer.length()>0)
				stringBuffer.append("+ ");
			stringBuffer.append(" Pay by "+pay+"("+Payer+")");
		}
		if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			if(stringBuffer.length()>0)
				stringBuffer.append("+ ");
			stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
		}
		rnb.setMarks(stringBuffer.toString());
		 
		/*******************等待处理*****************************/

 
			if(Integer.parseInt(rnb.getQuantity())<=0){
				int isRoot = new AdminDAO().getIsRoot(adminUsername);
				if(0 == isRoot){
					log.error("操作(Quantiry=0  删除操作)失败，权限不够!   操作员："+adminUsername);
					out.print("<script>alert('您不是管理員無法刪除數據!');location.href='addNameCard.jsp';</script>");
				}
			}else{
				AddRequestDao ad=new AddRequestDaoImpl();
				RequestRecordDao rr=new RequestRecordDaoImpl();
				PayerDao pd=new PayerDaoImpl();
				/**
				 * 保存新表数据
				 */
				int sum=pd.getadd(Payer)+Constant.MacauNAMECARD_NUM;//用户名片限额
				int used=0;
			/*	if(DD.equals("true")){//DD/DD Tree Head 成员享有400张无论standard、premium的名片
					used=pd.getDDused(Payer)-(pd.payerNumber(Payer));//用户已印名片数量
				}
				else{//普通Consultant享有400张无论standard、premium的名片
*/					used=pd.getused(Payer)-(pd.payerNumber(Payer));//用户已印名片数量
			//	}
				int num=ad.saveNewRequest(rnb);
				if (num>0) {
				/**
				 * 保存历史数据
				 */
				ad.saveMasterHistory(rnb);
				/**
				 * 保存req_record数据
				 */
				int r=-1;
				if(StaffNo.length()<10){//针对临时code
					r=rr.saveRequestRecord(rnb.getUrgentDate(),Payer,rnb.getName(), rnb.getQuantity(), request.getSession().getAttribute("adminUsername").toString(),rnb.getLayout_type(),rnb.getUrgent());
				}else{
					r=1;
				}
				if(r>0){
	/*			if(sfs.equals("Y")){//如果StaffNo为系统随机Code直接进财务
					double price;
					if(rnb.getLayout_type().toUpperCase().trim().equals("S")){//名片類型为标准类型
						price=(Constant.S_PRICE/100.0)*Double.parseDouble(rnb.getQuantity());
						if(rnb.getUrgent().toUpperCase().equals("Y")){
							price=price*2;
						}
					}
					else{
						price=(Constant.P_PRICE/100.0)*Double.parseDouble(rnb.getQuantity());
						if(rnb.getUrgent().toUpperCase().equals("Y")){
							price=price*2;
						}
					}
					if(rnb.getLayout_type().toUpperCase().trim().equals("S")){//名片類型为标准类型
							price=(Constant.S_PRICE/100.0)*Double.parseDouble(rnb.getQuantity());
						if(rnb.getEliteTeam().toUpperCase().equals("Y")){
							price=price*2;
						}
						if(rnb.getUrgent().toUpperCase().equals("Y")){
							price=price*2;
						}
					}else{
								price=(Constant.P_PRICE/100.0)*Double.parseDouble(rnb.getQuantity());
						if(rnb.getEliteTeam().toUpperCase().equals("Y")){
							price=price*2;
						}
						if(rnb.getUrgent().toUpperCase().equals("Y")){
							price=price*2;
						}
					}
					Change_Record cr=new Change_Record();
					cr.setStaffCode(StaffNo);
					cr.setName(rnb.getName());
					cr.setNumber(rnb.getQuantity());
					cr.setAmount(price+"");
					cr.setPayer(Payer);
					cr.setRemarks(rnb.getMarks());
					cr.setAddDate(rnb.getUrgentDate());
					cr.setUp_date(DateUtils.getNowDateTime());
					cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
					pd.saveChange(cr);
					return;
				}*/
			
				
					/**************************需要自己支付***************************************/
					if(!StaffNo.toUpperCase().equals(Payer) || rnb.getUrgent().toUpperCase().equals("Y")){//需要额外支付
						double price=0;
						if(rnb.getUrgent().toUpperCase().equals("Y")){//加快
							if(rnb.getLayout_type().toUpperCase().trim().equals("P") && !rnb.getEliteTeam().toUpperCase().equals("Y")){//名片为自定义类型
								price=Constant.Premium_Urgent*Integer.parseInt(rnb.getQuantity());
							}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
								
								price=Constant.Standard_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
							}else if(rnb.getLayout_type().toUpperCase().trim().equals("P") && rnb.getEliteTeam().toUpperCase().equals("Y")){//如果是Elite Team
								
								price=Constant.Premium_EliteTeam_Urgent*Integer.parseInt(rnb.getQuantity());
							}else if(rnb.getLayout_type().toUpperCase().trim().equals("S") && !rnb.getEliteTeam().toUpperCase().equals("Y")){
								price=Constant.Standard_Urgent*Integer.parseInt(rnb.getQuantity());//普通加快的价格
							}
						}else{//为他人支付
							price=Constant.Standard*Integer.parseInt(rnb.getQuantity());//普通的价格
						}
						Change_Record cr=new Change_Record();
						cr.setStaffCode(StaffNo);
						cr.setName(rnb.getName());
						cr.setNumber(rnb.getQuantity());
						cr.setAmount(price+"");
						cr.setPayer(Payer);
						cr.setRemarks(rnb.getMarks());
						cr.setAddDate(rnb.getUrgentDate());
						cr.setUp_date(DateUtils.getNowDateTime());
						cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
						pd.saveChange(cr);
					 
					}else if(rnb.getEliteTeam().toUpperCase().equals("Y") && !rnb.getUrgent().toUpperCase().equals("Y")){//选择Elite Team  （100张免费限额）

						if(isElite){//办理过Elite Team ，直接进入财务
							Change_Record cr=new Change_Record();
							cr.setStaffCode(StaffNo);
							cr.setName(rnb.getName());
							cr.setNumber(rnb.getQuantity());
							if(rnb.getLayout_type().toUpperCase().trim().equals("P"))
								cr.setAmount((Constant.Premium_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
							else
								cr.setAmount((Constant.Standard_EliteTeam*Double.parseDouble(rnb.getQuantity()))+"");
							cr.setPayer(Payer);
							cr.setRemarks(rnb.getMarks());
							cr.setAddDate(rnb.getUrgentDate());
							cr.setUp_date(DateUtils.getNowDateTime());
							cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
							pd.saveChange(cr);
						 
						}else{//没有办理过，判断是否超过免费办理限额
							if(Integer.parseInt(rnb.getQuantity())>Constant.Elite_Team){//超过免费限额数量，进入财务
								Change_Record cr=new Change_Record();
								cr.setStaffCode(StaffNo);
								cr.setName(rnb.getName());
								cr.setNumber((Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team)+"");
								if(rnb.getLayout_type().toUpperCase().trim().equals("P")){
									cr.setAmount((Constant.Premium_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
								}else{
									cr.setAmount((Constant.Standard_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
								}
								cr.setPayer(Payer);
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
								pd.saveChange(cr);
							 
							}else{
								//不做处理
							}
						}

					}else{
					double price=Constant.Standard;
						if(Integer.parseInt(rnb.getQuantity())+used>sum){//当次印刷名片数量与已印名片数量大于名片限额
								if(used>=sum){//已印名片数量大于名片限额
								price=price*Double.parseDouble(rnb.getQuantity());
								}else{
									rnb.setQuantity((Integer.parseInt(rnb.getQuantity())+used-sum)+"");
									price=price*Double.parseDouble(rnb.getQuantity());
								}
								if(rnb.getMarks().trim().equals("")){
									rnb.setMarks("Standard Layout");
								}
								Change_Record cr=new Change_Record();
								cr.setStaffCode(StaffNo);
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								cr.setAmount(price+"");
								cr.setPayer(Payer);
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
								pd.saveChange(cr);
						}
						}
					} 
				}
				//保存完数据 在修改审核状态_Y
				ConsconvoyNamecardDao cdao = new ConsconvoyNamecardDaoImpl();
				int ok = cdao.updateConsShzt(adminUsername, urgentDate, StaffNo, "Y",request.getParameter("remarks"));
				if(ok>0){
					//System.out.println("修改审核状态成功！");
				}
			}
			if(Integer.parseInt(rnb.getQuantity())>0 && rnb.getUrgent().toUpperCase().equals("Y")){
				response.sendRedirect("Success.jsp");
				}else if(Integer.parseInt(rnb.getQuantity())>0 && !(rnb.getUrgent().toUpperCase().equals("Y"))){
					out.print("<script type='text/javascript'>alert('审核通过成功！');location.href='namecard/approveNameCard.jsp';</script>");
				
				}
				else{
					int isRoot = new AdminDAO().getIsRoot(adminUsername);
					if(isRoot==1){
						response.sendRedirect("payer.jsp");
					}
				}
		}catch (Exception e){
			e.printStackTrace();
			out.print("<script type='text/javascript'>alert('审核异常=="+e.toString()+"');</script>");
		}finally{
			
			out.flush();
			out.close();
		}
 
	}

}
