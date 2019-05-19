package servlet.Macau;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import util.DateUtils;
import util.Util;

import dao.AdminDAO;
import dao.MacauDao;
import dao.PayerDao;
import dao.RequestRecordDao;
import dao.impl.MacauDaoImpl;
import dao.impl.PayerDaoImpl;
import dao.impl.RequestRecordDaoImpl;
import entity.Change_Macau;
import entity.NewMacau;

public class AddNewCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String adminUsername = request.getSession().getAttribute("adminUsername").toString();
		String StaffNo=request.getParameter("StaffNos");
		String Payer=request.getParameter("payers");
		String urgentDate=request.getParameter("urgentDate");
		String pay=request.getParameter("pays");
		StringBuffer stringBuffer=new StringBuffer("");
		String sfs=Util.objIsNULL(request.getParameter("sfs"))?"N":request.getParameter("sfs");
		NewMacau rnb=new NewMacau();
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
		rnb.setUrgentDate(DateUtils.getNowDateTime());
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
		rnb.setCIB_only(Util.objIsNULL(request.getParameter("CIB"))?"N":request.getParameter("CIB"));
		rnb.setBlank_only(Util.objIsNULL(request.getParameter("Blank"))?"N":request.getParameter("Blank"));
		rnb.setPayer(request.getParameter("payers"));
		rnb.setUrgent(Util.objIsNULL(request.getParameter("urgent"))?"N":request.getParameter("urgent"));
		rnb.setEliteTeam(Util.objIsNULL(request.getParameter("ETs"))?"N":request.getParameter("ETs"));
		try{
			if(rnb.getLayout_type().toUpperCase().trim().equals("P")){//名片为自定义类型
				stringBuffer.append("Premium Layout ");
			}
			if(!StaffNo.toLowerCase().trim().equals(Payer.toLowerCase().trim())){//名片名字与支付人名字 不相同
				stringBuffer.append(" Pay by "+pay+"("+Payer+")");
			}
			if(rnb.getUrgent().equals("Y")){//如果选中URgentCase
			stringBuffer.append(" Urgent request on "+urgentDate);	
			}
			rnb.setMarks(stringBuffer.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		/*******************等待处理*****************************/

		try{
			if(Integer.parseInt(rnb.getQuantity())<=0){
				int isRoot = new AdminDAO().getIsRoot(adminUsername);
				if(0 == isRoot){
					out.print("<script>alert('您不是管理員無法刪除數據!');location.href='addNameCard.jsp';</script>");
				}
			}else{
			MacauDao ad=new MacauDaoImpl();
			RequestRecordDao rr=new RequestRecordDaoImpl();
			PayerDao pd=new PayerDaoImpl();
			/**
			 * 保存新表数据
			 */
			int sum=pd.getadd(Payer)+Constant.NAMECARD_NUM;//用户名片限额
			int used=pd.getused(Payer);//用户已印名片数量
			int num=ad.saveNewRequest(rnb);//保存newMacau（新增数据）
			if (num>0) {
				/**
				 * 保存历史数据
				 */
				ad.saveMasterHistory(rnb);
				/**
				 * 保存req_record数据（用过的数据）
				 */
				int r=rr.saveRequestRecord(rnb.getUrgentDate(),Payer,rnb.getName(), rnb.getQuantity(), request.getSession().getAttribute("adminUsername").toString(),rnb.getLayout_type(),rnb.getUrgent());
				if(r>0){
				if(sfs.equals("Y")){//如果StaffNo为系统随机Code直接进财务
					double price=0;
				/**	if(rnb.getLayout_type().toUpperCase().trim().equals("S")){//名片類型为标准类型
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
					}**/
					if(rnb.getLayout_type().toUpperCase().trim().equals("S")){//名片類型为标准类型
						if(rnb.getEliteTeam().toUpperCase().equals("Y")){
							if(StaffNo.toUpperCase().equals(Payer)){
								price=(Constant.S_PRICE/100.0)*Double.parseDouble(rnb.getQuantity());
							}else{
								price=(Constant.S_PRICE/100.0)*Double.parseDouble(rnb.getQuantity())*2;
							}
						}
						if(rnb.getUrgent().toUpperCase().equals("Y")){
							price=price*2;
						}
					}else{
						if(StaffNo.toUpperCase().equals(Payer)){
							price=(Constant.P_PRICE/100.0)*Double.parseDouble(rnb.getQuantity());
						}else{
							price=(Constant.P_PRICE/100.0)*Double.parseDouble(rnb.getQuantity())*2;
						}
						if(rnb.getUrgent().toUpperCase().equals("Y")){
							price=price*2;
						}
					}
					Change_Macau cr=new Change_Macau();
					cr.setStaffCode(StaffNo);
					cr.setName(rnb.getName());
					cr.setNumber(rnb.getQuantity());
					cr.setAmount(price+"");
					cr.setPayer(Payer);
					cr.setRemarks(rnb.getMarks());
					cr.setAddDate(rnb.getUrgentDate());
					cr.setUp_date(DateUtils.getNowDateTime());
					cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
					pd.saveMacauChange(cr);
					return;
				}
			
				
				/**************************需要自己支付***************************************/
				
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
					Change_Macau cr=new Change_Macau();
					cr.setStaffCode(StaffNo);
					cr.setName(rnb.getName());
					cr.setNumber(rnb.getQuantity());
					cr.setAmount(price+"");
					cr.setPayer(Payer);
					cr.setRemarks(rnb.getMarks());
					cr.setAddDate(rnb.getUrgentDate());
					cr.setUp_date(DateUtils.getNowDateTime());
					cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
					
					pd.saveMacauChange(cr);
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
								Change_Macau cr=new Change_Macau();
								cr.setStaffCode(StaffNo);
								cr.setName(rnb.getName());
								cr.setNumber(rnb.getQuantity());
								cr.setAmount(price+"");
								cr.setPayer(Payer);
								cr.setRemarks(rnb.getMarks());
								cr.setAddDate(rnb.getUrgentDate());
								cr.setUp_date(DateUtils.getNowDateTime());
								cr.setUp_name(request.getSession().getAttribute("adminUsername").toString());
								
								pd.saveMacauChange(cr);
						}
						
					}
				} 
			}
		}
			}catch (Exception e){
				e.printStackTrace();
			}
			finally{
				//  if(Integer.parseInt(rnb.getQuantity())>0 && rnb.getUrgent().toUpperCase().equals("Y")){
					out.print("<script type='text/javascript'>alert('添加成功！');location.href='Macau/addNameCard.jsp';</script>");
			 //	 }
				//  else{
					 // out.print("<script type='text/javascript'>alert('添加成功！');location.href='Macau/addNameCard.jsp';</script>");
					//int isRoot = AdminDAO.getIsRoot(adminUsername);
				//	if(isRoot==1){
					//	response.sendRedirect("Macau/payer.jsp");
					//}
				// } 
				out.flush();
				out.close();
			}
 
	}

}
