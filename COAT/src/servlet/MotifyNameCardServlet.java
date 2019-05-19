package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.Constant;
import util.DateUtils;
import util.Util;
import dao.GetCNMDao;
import dao.MotifyDao;
import dao.PayerDao;
import dao.QueryChargeDao;
import dao.RequestRecordDao;
import dao.impl.GetCNMDaoImpl;
import dao.impl.MotifyDaoImpl;
import dao.impl.PayerDaoImpl;
import dao.impl.QueryChargeDaoImpl;
import dao.impl.RequestRecordDaoImpl;
import entity.Change_Record;
import entity.RequestNewBean;

public class MotifyNameCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(MotifyNameCardServlet.class);
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
		String DD=request.getParameter("DD");//判断是否是DD/DD Tree Head
		String reStaffNo=request.getParameter("reStaffNo");
		String reQuantity=request.getParameter("reQuantity");
		String rePayer=request.getParameter("rePayer");
		String reUrgent=request.getParameter("reUrgent");
		String Payer=request.getParameter("Payer");
		String StaffNo=request.getParameter("StaffNo");
		rnb.setStaff_code(request.getParameter("StaffNo"));
		rnb.setPayer(Payer);
		rnb.setName(request.getParameter("EnglishName"));
		rnb.setName_chinese(request.getParameter("ChineseName"));
		rnb.setTitle_english(request.getParameter("EnglishTitle_Department"));
		rnb.setTitle_chinese(request.getParameter("ChineseTitle_Department"));
		rnb.setExternal_english(request.getParameter("EnglishExternalTitle_Department"));
		rnb.setExternal_chinese(request.getParameter("ChineseExternalTitle_Department"));
		rnb.setAcademic_title_e(request.getParameter("AcademicTitle_e_Department"));
		rnb.setAcademic_title_c(request.getParameter("AcademicTitle_c_Department"));
		rnb.setProfess_title_e(request.getParameter("EnglishEducationTitle"));
		rnb.setProfess_title_c(request.getParameter("ChineseEducationTitle"));
		rnb.setTr_reg_no(request.getParameter("trg"));
		rnb.setCe_no(request.getParameter("CE"));
		rnb.setMpf_no(request.getParameter("MPF"));
		rnb.setE_mail(request.getParameter("Email"));
		rnb.setDirect_line(request.getParameter("DirectLine"));
		rnb.setFax(request.getParameter("FAX"));
		rnb.setBobile_number(request.getParameter("MobilePhone"));
		rnb.setUrgentDate(request.getParameter("UrgentDate"));
		rnb.setUpd_name(request.getSession().getAttribute("adminUsername").toString());
		rnb.setQuantity(request.getParameter("Quantity"));
		rnb.setUrgent(request.getParameter("urgent"));
		rnb.setLayout_type(request.getParameter("layout_type"));
		rnb.setUpd_date(DateUtils.getNowDateTime());
		rnb.setCAM_only(request.getParameter("CAM"));
		rnb.setCCL_only(request.getParameter("CCL"));
		rnb.setCFG_only(request.getParameter("CFG"));
		rnb.setCFS_only(request.getParameter("CFS"));
		rnb.setCFSH_only(request.getParameter("CFSH"));
		rnb.setCIS_only(request.getParameter("CIS"));
		rnb.setCMS_only(request.getParameter("CMS"));
		rnb.setBlank_only(request.getParameter("Blank"));
		rnb.setLocation(request.getParameter("location"));
		rnb.setAe_consultant(Util.objIsNULL(request.getParameter("aeConsultant"))?"N":request.getParameter("aeConsultant"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ET"))?"N":request.getParameter("ET"));
		rnb.setCard_type("N");
		StringBuffer stringBuffer=new StringBuffer("");
		boolean isElite=rrd.vailElite_request_new(rnb.getStaff_code(),rnb.getUrgentDate());
		 PayerDao payerDao=new PayerDaoImpl();
		 String EmployeeName="";
		 EmployeeName=payerDao.GetEnglishName(Payer);
			PayerDao pd=new PayerDaoImpl();
			int sum=pd.getadd(Payer)+Constant.NAMECARD_NUM;//用户名片限额
			int used=0;
			if(DD.equals("true")){//DD/DD Tree Head 成员享有400张无论standard、premium的名片
				used=pd.getDDused(Payer)-Integer.parseInt(reQuantity);//用户已印名片数量
			}
			else{//普通Consultant享有400张无论standard、premium的名片
				used=pd.getused(Payer)-Integer.parseInt(reQuantity);//用户已印名片数量
			}
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout ");
			}
			if(rnb.getEliteTeam().toUpperCase().trim().equals("Y")){//顾问为Elite Team 成员
				if(stringBuffer.length()>0)
					stringBuffer.append("+ ");
				stringBuffer.append("Elite Team  ");

			}
		if(!StaffNo.toLowerCase().trim().equals(Payer.toLowerCase().trim())){//名片名字与支付人名字 不相同
			stringBuffer.append(" Pay by "+EmployeeName+"("+Payer+")");
		}
		if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			if(stringBuffer.length()>0)
				stringBuffer.append("+ ");
			stringBuffer.append(" Urgent request on "+DateUtils.getDateToday());	
		}
		rnb.setMarks(stringBuffer.toString());
		MotifyDao md=new MotifyDaoImpl();
		GetCNMDao gm=new GetCNMDaoImpl();
		QueryChargeDao qd=new QueryChargeDaoImpl();
		RequestRecordDao rd=new RequestRecordDaoImpl();
		try{
		//	int isRoot = AdminDAO.getIsRoot(request.getSession().getAttribute("adminUsername").toString());
			if(!rnb.getQuantity().equals(reQuantity) || reUrgent.toUpperCase().equals("Y")|| !StaffNo.toUpperCase().equals(Payer) || rnb.getUrgent().toUpperCase().equals("Y")||rnb.getEliteTeam().toUpperCase().equals("Y")){//修改了数量
				//	if(1 == isRoot){//看是否是管理員
					int r=md.updateNumber(rnb,reStaffNo);//修改requestNew 
					
							if(r>0){//修改requestNew成功...
								md.saveNewRequest(rnb, rnb.getStaff_code());
							  int m=gm.updateNumber(rnb,reStaffNo);
								if(m>0){//修改历史表成功
									if(reStaffNo.length()<10){
									rd.updateRequestRecord(rnb,Payer, rePayer);
									}else{//针对临时code
										rd.saveRequestRecord(rnb.getUrgentDate(),Payer,rnb.getName(), rnb.getQuantity(), request.getSession().getAttribute("adminUsername").toString(),rnb.getLayout_type(),rnb.getUrgent());
									}
									//刪除财务
									qd.deleteChargeRecord(request.getSession().getAttribute("adminUsername").toString(),reStaffNo, rnb.getUrgentDate());
									 
										if(Integer.parseInt(rnb.getQuantity())==0){
											out.print("修改成功！");
											log.info("修改成功!");
											return ;
										}
										if(reUrgent.toUpperCase().equals("Y")&&rnb.getUrgent().toUpperCase().equals("N")){
											out.print("修改成功！");
											log.info("修改成功!");
											return ;
										}
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
											return;
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
												return;
											}else{//没有办理过，判断是否超过免费办理限额
												if(Integer.parseInt(rnb.getQuantity())>Constant.Elite_Team){//超过免费限额数量，进入财务
													Change_Record cr=new Change_Record();
													cr.setStaffCode(StaffNo);
													cr.setName(rnb.getName());
													cr.setNumber((Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team)+"");
													if(rnb.getLayout_type().toUpperCase().trim().equals("P"))
														cr.setAmount((Constant.Premium_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");
													else
														cr.setAmount((Constant.Standard_EliteTeam*(Integer.parseInt(rnb.getQuantity())-Constant.Elite_Team))+"");

													cr.setPayer(Payer);
													cr.setRemarks(rnb.getMarks());
													cr.setAddDate(rnb.getUrgentDate());
													cr.setUp_date(DateUtils.getNowDateTime());
													cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
													pd.saveChange(cr);
													return;
												}else{
													//不做处理
												}
											}

										}
										/*********************************************************************************/
										else{
											double price=Constant.S_PRICE/100.0;
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
												return;
											}

										}
										out.print("修改成功");
									}
								}else{
									out.print("修改異常，请稍候再试！");}
					/*}else{//不是管理员
						rnb.setStaff_code(reStaffNo);
						int r=md.updateAdditionals(rnb);
						
						if(r>0){//新表修改成功
							md.saveNewRequest(rnb, reStaffNo);
							int m=gm.updateCNM(rnb);
							if(m>0){
							out.print("修改成功!");
							}
						}else{out.print("修改異常，請稍候再試！");}
					}*/
			}else{//没有修改数量
				//rnb.setStaff_code(reStaffNo);
				//int r=md.updateAdditionals(rnb);
 		int r=md.updateNumber(rnb,reStaffNo);
				
				if(r>0){//新表修改成功
					md.saveNewRequest(rnb, reStaffNo);
					//int m=gm.updateCNM(rnb);
					 int m=gm.updateNumber(rnb,reStaffNo);
					if(m>0){
					out.print("修改成功");
					}
				}else{
					out.print("修改異常，请稍候再试！");
				}
				
			}
		}catch(Exception e){
			out.print("系統繁忙，請聯繫管理員！");
			log.error("在MogifyNameCardServlet 中 出现 "+e.toString());
		}
		finally{
			out.flush();
			out.close();
		}
		
	}

}
