package servlet.markpremium;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import dao.C_GetMarkPremiumDao;
import dao.QueryStationeryDao;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.QueryStationeryDaoImpl;

import util.DateUtils;

import entity.C_marOrder;
import entity.C_marProduct;
import entity.C_marRecord;

public class SaveMarketingPrimiumRecordServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(SaveMarketingPrimiumRecordServlet.class);
	synchronized public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("You have no legal power!");
		out.flush();
		out.close();
	}

	synchronized public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		try{
			String staffcode=request.getParameter("staffcode");
			String staffname=request.getParameter("staffname");
			String c_club=request.getParameter("c-club");
			String location=request.getParameter("location");
			String payMethod=request.getParameter("payMethod");
			String chequeNo=request.getParameter("chequeNo");
			String Banker =request.getParameter("Banker");
			Double totalMenoy=Double.parseDouble(request.getParameter("totalMenoy"));
			String sale_record=request.getParameter("sale_record");
			String ordertype=request.getParameter("ordertype");
 
			String cheque_number=request.getParameter("cheque_number");
			String cash_number=request.getParameter("cah_number");
			String club_number=request.getParameter("club_nubmer");
			String department=request.getParameter("department");
			QueryStationeryDao dao = new QueryStationeryDaoImpl();
			//订单号生成
			String oid = dao.getOrderIdByRandom("c_mar_order");
			String ordercode="M"+DateUtils.Ordercode()+oid;
			
			String addDate=DateUtils.getNowDateTime();
			 Set<String> errorString=new HashSet<String>();
			List<C_marRecord> list=new ArrayList<C_marRecord>();
			List<C_marProduct> list2=new ArrayList<C_marProduct>();
			C_GetMarkPremiumDao cgp=new C_GetMarkPremiumDaoImpl();
			list2=cgp.findMarProduct(ordertype.equals("Consultant")?"C":"S");
			String[] ss=sale_record.split(";");//组合订单数组
			
				for(int i=0;i<ss.length;i++){//遍历订单
				   String[] product=ss[i].toString().split(",");//组合产品数组
				  /* for(int j=0;j<sa.length;j++){
					   System.out.println(sa[j]);
				   }*/
				   for(int j=0;j<list2.size();j++){//遍历订单下面的产品
					   C_marProduct cp=list2.get(j);//获取产品
					   if(cp.getProcode().equals(product[0])){
						   if(Double.parseDouble(product[5])-Double.parseDouble(cp.getQuantity())>0){//购物车的数量大于库存数量
							   errorString.add(product[0]);//保存库存不足的数据
						   }
					   }
				   }
				   /**
				    * 消费记录表
				    */
				   if(errorString.size()<=0)
				   list.add(new C_marRecord(ordercode, product[0], product[1],product[2], Double.parseDouble(product[3].replace("HK$","")), Double.parseDouble(product[5]), Double.parseDouble(product[6].replace("HK$", "")), "", "", "", ""));
				}
			
	 if(errorString.size()>0){
			 JSONArray jsons=JSONArray.fromObject(errorString);
			 out.print(jsons.toString());
		 }else	{
			 if(cgp.saveMarRecord(list,new C_marOrder(ordercode,ordertype, staffname, staffcode, department,totalMenoy, addDate, c_club, location, payMethod,chequeNo, Banker, "Y", "", "", "", "",cash_number,club_number,cheque_number,util.Constant.C_Submitted))>0){
				 /*if(cgp.saveMarOrder(new C_marOrder(ordercode,ordertype, staffname, staffcode, totalMenoy, addDate, c_club, location, payMethod, chequeNo, Banker, "Y", "", "", "", ""))>0){
					 out.print("success");
					 log.info(staffcode+"在购买Marketing Premium成功!  ===ordercode："+ordercode+"===totalmenoy====:"+totalMenoy);
				 }else{//保存订单表失败
					 out.print("order");
					 log.error(staffcode+"在保存订单时出现异常  ===ordercode："+ordercode+"===totalmenoy====:"+totalMenoy);
				 }*/
				 log.info(staffcode+"在购买Marketing Premium成功!  ===ordercode："+ordercode+"===totalmenoy====:"+totalMenoy);
				 out.print("success");
			 }else{//保存消费记录表失败
				 out.print("record");
				 log.error(staffcode+"在保存订单详细时出现异常  ===ordercode："+ordercode+"===totalmenoy====:"+totalMenoy);
			 }
			 
		 }
		}catch(Exception e){
			e.printStackTrace();
			out.print("error");
			log.error("save Marketing Premium出现异常  ===："+e);
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
 

}
