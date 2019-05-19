package servlet.Stationery;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Constant;
import util.DateUtils;

import dao.QueryStationeryDao;
import dao.impl.QueryStationeryDaoImpl;
import entity.C_stationeryOrder;
import entity.C_stationeryProduct;
import entity.C_stationeryRecord;

public class StationeryOrderRecordServlet extends HttpServlet {

	/**
	 * @author skyjiang
	 * 订单表和消费记录表 Servlet
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 */
	private static final long serialVersionUID = 1L;
	synchronized public  void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String str = request.getParameter("str");
		String priceall = request.getParameter("priceall");
		String location = request.getParameter("location");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String staffOrCons = request.getParameter("staffOrCons");
		String departhead = request.getParameter("departhead");
		String cash = request.getParameter("cash");
		String club = request.getParameter("club");
		String cheque = request.getParameter("cheque");
		String  cheque_no = request.getParameter("cheque_no");
		String banker = request.getParameter("banker");
		List<C_stationeryRecord> recordList = new ArrayList<C_stationeryRecord>();
		List<C_stationeryProduct> productList = new ArrayList<C_stationeryProduct>();
		QueryStationeryDao dao = new QueryStationeryDaoImpl();
		C_stationeryOrder order = new C_stationeryOrder();
		/*Date date = new Date();
    	date = new Date(date.getYear(),date.getMonth(),date.getDate());
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");*/
     	String oid = dao.getOrderIdByRandom("c_stationery_order");
    	String orcode = "S"+DateUtils.Ordercode()/*format.format(date).replaceAll("-", "")*/+oid;
		order.setOrdercode(orcode);
		order.setPriceall(Double.valueOf(priceall));
		order.setSfyx("Y");
		order.setStatus("Submitted");
		order.setClientcode(code);
		order.setClientname(name);
		order.setLocation(location);
		order.setCash(cash);
		order.setC_club(club);
		order.setCheque(cheque);
		order.setBanker(banker);
		order.setCheque_no(cheque_no);
		String BlBZ = "";
		if(staffOrCons.equals("CONS")){
			order.setStaffOrCons(Constant.ORDER_TYPE_Cons);
			BlBZ = "C";
		}else{
			order.setStaffOrCons(Constant.ORDER_TYPE_Staff);
			BlBZ = "S";
		}
		order.setDeparthead(departhead);
		
		C_stationeryRecord record = null;
		C_stationeryProduct product = null;
		C_stationeryProduct productBean = null;
		String[] array = str.split(";");
		String numStr = "";
		
		List<C_stationeryProduct> proList = dao.queryStationery(BlBZ);
		int flag = 0;
		for (int i = 0; i < array.length; i++) {
			String[] arr = array[i].split(",");
			record = new C_stationeryRecord();
			record.setOrdercode(orcode);
			if("".equals(arr[0])){
				arr[0] = "0";
			}
			record.setProcode(arr[0]);
			record.setProname(arr[1]);
			record.setProcname(arr[2]);
			record.setPrice(Double.valueOf(arr[3]));
			record.setQuantity(Double.valueOf(arr[5]));
			record.setPriceall(Double.valueOf(arr[6]));
			recordList.add(record);
			
			
			/*去产品表修改库存数量*/
			product = new C_stationeryProduct();
			product.setProcode(arr[0]);
			product.setEnglishname(arr[1]);
			product.setChinesename(arr[2]);
			product.setQuantity(Double.valueOf(arr[5]));
			productList.add(product);


			for (int  j = 0; j < proList.size(); j++) {
				productBean = (C_stationeryProduct)proList.get(j);
				if(product.getProcode().equals(productBean.getProcode())){
					if(product.getQuantity() > productBean.getQuantity()){
						numStr += productBean.getProcode()+","+productBean.getQuantity()+",1;";
						flag = 1;
					}else{
						numStr += product.getProcode()+","+product.getQuantity()+",0;";
					}
					break;
				}
			}
		}
		if(flag == 1){
			out.print(numStr);
			return ;
		}
		
//		//原来的方式
//		int result1 = dao.saveOrderStationery(order);
//		int result2 = dao.saveRecordStationery(recordList);
//		int result3 = dao.updateProQuantity(productList);
//		
//		if(result1 >0 && result2 == recordList.size() ){
//			out.print(Integer.valueOf(0).toString()+"-"+orcode);
//		}

		//修改后的方式的方式
		int result1 = dao.saveThreeStationery(recordList, order, productList);
		
		if(result1 >0 ){
			out.print(Integer.valueOf(0).toString()+"-"+orcode);
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
