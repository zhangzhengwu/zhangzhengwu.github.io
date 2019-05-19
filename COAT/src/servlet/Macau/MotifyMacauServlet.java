package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MacauDao;
import dao.PayerDao;
import dao.impl.MacauDaoImpl;
import dao.impl.PayerDaoImpl;

import util.Constant;
import util.DateUtils;
import util.Util;

import entity.Change_Macau;
import entity.NewMacau;

public class MotifyMacauServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String username=request.getSession().getAttribute("adminUsername").toString();
		NewMacau rnb=new NewMacau();
		String reStaffNo=request.getParameter("reStaffNo");
		String reQuantity=request.getParameter("reQuantity");
		String rePayer=request.getParameter("rePayer");
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
		rnb.setUpd_name(username);
		rnb.setQuantity(request.getParameter("Quantity"));
		rnb.setUrgent(request.getParameter("urgent"));
		rnb.setLayout_type(request.getParameter("layout_type"));
		rnb.setUpd_date(DateUtils.getDateToday());
		rnb.setCAM_only(request.getParameter("CAM"));
		rnb.setCCL_only(request.getParameter("CCL"));
		rnb.setCFG_only(request.getParameter("CFG"));
		rnb.setCFS_only(request.getParameter("CFS"));
		rnb.setCFSH_only(request.getParameter("CFSH"));
		rnb.setCIS_only(request.getParameter("CIS"));
		rnb.setCMS_only(request.getParameter("CMS"));
		rnb.setCIB_only(request.getParameter("CIB"));
		rnb.setBlank_only(request.getParameter("Blank"));
		rnb.setLocation(request.getParameter("location"));
		rnb.setAe_consultant(Util.objIsNULL(request.getParameter("aeConsultant"))?"N":request.getParameter("aeConsultant"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ET"))?"N":request.getParameter("ET"));
		rnb.setCard_type("N");
		StringBuffer stringBuffer=new StringBuffer("");
		
		 PayerDao payerDao=new PayerDaoImpl();
		 String EmployeeName="";
		 EmployeeName=payerDao.VailPayer(Payer, username);
			int sum=payerDao.getadd(Payer)+Constant.MacauNAMECARD_NUM;//用户名片限额
			int used=payerDao.getused(Payer)-Integer.parseInt(reQuantity);//用户已印名片数量;
		if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
			stringBuffer.append("Premium Layout ");
		}
		if(!StaffNo.toLowerCase().trim().equals(Payer.toLowerCase().trim())){//名片名字与支付人名字 不相同
			stringBuffer.append(" Pay by "+EmployeeName+"("+Payer+")");
		}
		if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
		stringBuffer.append(" Urgent request on "+rnb.getUrgentDate());	
		}
		rnb.setMarks(stringBuffer.toString());
		MacauDao md=new MacauDaoImpl();
		try{
			
			//int isRoot = AdminDAO.getIsRoot(request.getSession().getAttribute("adminUsername").toString());
			if(!rnb.getQuantity().equals(reQuantity) || rnb.getUrgent().equals("Y")|| !StaffNo.equals(reStaffNo)){//修改了数量
				//	if(1 == isRoot){//看是否是管理員
					int r=md.updateNumber(rnb,reStaffNo);//修改requestNew 
							if(r>0){//修改requestNew成功...
								md.saveMacauNew(rnb, reStaffNo);//插入request_Macau
							  int m=md.updateMasterNumber(rnb,reStaffNo);//更新历史表
								if(m>0){//修改历史表成功
								md.updateRequestRecord(rnb,Payer, rePayer);//更新已经用过的数据
									//刪除财务表数据
									md.deleteChargeRecord(username,reStaffNo, rnb.getUrgentDate());
									if(!StaffNo.toUpperCase().equals(Payer) || rnb.getUrgent().toUpperCase().equals("Y") || rnb.getLayout_type().toUpperCase().equals("P")){//需要额外支付
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
										if((int)price==0){
											out.print("修改成功！");
											return ;
										}
										Change_Macau cr=new Change_Macau();
										cr.setStaffCode(StaffNo);
										cr.setName(rnb.getName());
										cr.setNumber(rnb.getQuantity());
										cr.setAmount(price+"");
										cr.setPayer(Payer);
										cr.setRemarks(rnb.getMarks());
										cr.setUp_date(DateUtils.getNowDateTime());
										cr.setAddDate(rnb.getUrgentDate());
										cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
										payerDao.saveMacauChange(cr);
									}else{//有可能只有标准超出部分
										double price=Constant.S_PRICE/100.0;
											if((used+Double.parseDouble(rnb.getQuantity())-Double.parseDouble(reQuantity))>sum){//当次印刷名片数量与已印名片数量大于名片限额
												if(used-Double.parseDouble(reQuantity)>sum){
													}
												else{
													rnb.setQuantity((Double.parseDouble(rnb.getQuantity())-Double.parseDouble(reQuantity)+used-sum)+"");
												}
												price=price*Double.parseDouble(rnb.getQuantity());
												if((int)price==0){
													out.print("修改成功！");
													return ;
												}
												if(rnb.getMarks().trim().equals("")){
													rnb.setMarks("Standard Layout");
												}
												Change_Macau cr=new Change_Macau();
												cr.setStaffCode(StaffNo);
												cr.setName(rnb.getName());
												cr.setNumber(rnb.getQuantity());
												cr.setAmount(price+"");
												cr.setPayer(Payer);
												cr.setRemarks(rnb.getMarks());
												cr.setUp_date(DateUtils.getNowDateTime());
												cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
												cr.setAddDate(rnb.getUrgentDate());
												payerDao.saveMacauChange(cr);
											}
										}
										out.print("修改成功");
									}
								}else{
									out.print("修改異常，请稍候再试！");}
				/*	}else{//不是管理员
						int r=md.updateNumber(rnb,reStaffNo);//修改requestNew 
						if(r>0){//修改requestNew成功...
							md.saveMacauNew(rnb, reStaffNo); //插入request_Macau
							int m=md.updateMasterNumber(rnb,reStaffNo);//更新历史表
							if(m>0){
							out.print("修改成功!");
							}
						}else{out.print("修改異常，請稍候再試！");}
					}*/
			}else{//没有修改数量
				//rnb.setStaff_code(reStaffNo);
				//int r=md.updateAdditionals(rnb);
				int r=md.updateNumber(rnb,reStaffNo);//修改requestNew 
				if(r>0){//修改requestNew成功...
					md.saveMacauNew(rnb, reStaffNo); //插入request_Macau
					int m=md.updateMasterNumber(rnb,reStaffNo);//更新历史表
					if(m>0){
					out.print("修改成功!");
					}
				}else{
					out.print("修改異常，请稍候再试！");
				}
				
			}
		}catch(Exception e){
			out.print("系統繁忙，請聯繫管理員！");
		}
		finally{
			out.flush();
			out.close();
		}
		
	}

}
