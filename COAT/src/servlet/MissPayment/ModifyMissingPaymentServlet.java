package servlet.MissPayment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.MissingPaymentDao;
import dao.impl.MissingPaymentDaoImpl;
import entity.Missingpayment;

public class ModifyMissingPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(ModifyMissingPaymentServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String result="";
		String adminUsername = "";
		try{
			adminUsername=request.getSession().getAttribute("adminUsername").toString();
		 Missingpayment rnb = new Missingpayment();
		 String id = request.getParameter("Id");
		 System.out.println(id+"+++++++++++++1+++++++++++"+adminUsername);
		rnb.setId(new Integer(id.trim()));
		rnb.setReceivedDate(request.getParameter("updreceived").trim());
		rnb.setStaffcode(request.getParameter("staffcode").trim());
		rnb.setStaffcode2(request.getParameter("staffcode2").trim());
		rnb.setPrincipal(request.getParameter("principal").trim());
		rnb.setClientname(request.getParameter("clientname").trim());
		rnb.setPolicyno(request.getParameter("policyno").trim());
		rnb.setMissingsum(request.getParameter("missingsum").trim());
		rnb.setPremiumDate(request.getParameter("premiumdate").trim());
		rnb.setRemark("");//request.getParameter("remark").trim()
		rnb.setReason(request.getParameter("reason").trim());
		rnb.setDatafrom(request.getParameter("datefrom").trim());
		if (!id.trim().equals("")) {
			 MissingPaymentDao smd=new MissingPaymentDaoImpl();
			 int num=-1;
			 num=smd.delMissing(id,  adminUsername);
			 System.out.println(id+"+++++++++++delMissing+++++++++++"+adminUsername);
			 if(num>0){//是否新增历史记录
				 int numb = -1;
				 numb = smd.saveMissing(rnb, adminUsername);
				 System.out.println(id+"++++++++++++saveMissing+++++++++++"+adminUsername);
				 if (numb > 0) {
					 result="修改成功!";
				}
				
			 }else{
				 result="修改成功，但历史数据保存出错!";
			 }
		}else {
			result="修改数据保存出错!";
		}	 
			log.info(adminUsername+"在ModifyMissingPaymentServlet中修改staff数据时,结果为："+result);
		}catch(Exception e){
			log.error(adminUsername+"在ModifyMissingPaymentServlet中修改staff数据时出现："+e);
			e.printStackTrace();
			result="修改失败："+e.toString();
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

}
