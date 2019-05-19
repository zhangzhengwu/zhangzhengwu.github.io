package servlet.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import util.ReadExcel;
import util.Util;

import com.coat.namecard.dao.NameCardConvoyDao;
import com.coat.namecard.dao.impl.NameCardConvoyDaoImpl;
import com.coat.pickuprecord.dao.PickUpRecordDao;
import com.coat.pickuprecord.dao.impl.PickUpRecordDaoImpl;

import dao.C_CompanyDao;
import dao.C_GetMarkPremiumDao;
import dao.ConsListDao;
import dao.ConsMacauDao;
import dao.EPaymentDao;
import dao.MedicalOptOutDao;
import dao.SeatDao;
import dao.impl.C_CompanyDaoImpl;
import dao.impl.C_GetMarkPremiumDaoImpl;
import dao.impl.ConsListDaoImpl;
import dao.impl.ConsMacauDaoImpl;
import dao.impl.EPaymentDaoImpl;
import dao.impl.MedicalOptOutDaoImpl;
import dao.impl.SeatDaoImpl;
import dao.impl.staffnamecard.StaffNameCardDaoImpl;
import dao.staffnamecard.StaffNameCardDao;

public class UpExcelServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger =Logger.getLogger(UpExcelServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");  
		String filename =null;
		String uploadfile=request.getParameter("uploadfile");
		String name=request.getParameter("filename");
		String user=(String) request.getSession().getAttribute("adminUsername");
		PrintWriter out = null;
		List<Object> list = new ArrayList<Object>();
		int result=0;
		String results="";
		int num=0;
		System.out.println(uploadfile+"===========================");
		try {
			out = response.getWriter();
			if(Util.objIsNULL(name)){
				filename= new String(uploadfile.getBytes("ISO-8859-1"),"UTF-8");// // 强制转换成utf-8
			}else{
				filename=name;
			}
			//System.out.println(filename);
			response.setContentType("text/html;charset=utf-8");
			Long befor =null;//标记上传文件处理开始时间
			Long after =null;//标记上传文件处理结束时间
			/** 上传文件开始处理 **/
			befor = System.currentTimeMillis(); // 标记初始时间（以毫秒记）
			if(filename.indexOf("Employee List")>-1){//Consultant List新增顾问上传
				ConsListDao clDao=new ConsListDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 1);
				clDao.saveConsultantList(list2);
			/*	for(int i=0;i<list2.size();i++){
					List<Object> sLinkedList= list2.get(i);
					System.out.print("第"+i+"行");
					
					for(int j=0;j<sLinkedList.size();j++){
						System.out.print("第"+j+"列--〉"+sLinkedList.get(j));
					}
					System.out.();
				}*/
				num=list2.size();
			}else if(filename.indexOf("CIB Consultant List")>-1){//
				ConsMacauDao clDao=new ConsMacauDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 1);
				
				num=clDao.saveMacauList(list2);
				result=list2.size()-num; //失败条数
				
			}else if(filename.indexOf("ePayment item list")>-1){
				EPaymentDao cDao = new EPaymentDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 1);
				num=cDao.saveEPaymentList(list2);
			}else if(filename.indexOf("Markeing Premium-stock2013-10")>-1){
				C_GetMarkPremiumDao cDao = new C_GetMarkPremiumDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 1);
				num=cDao.saveMarkPremiumList(list2);
			}else if(filename.indexOf("Company Asset Item List")>-1){
				C_CompanyDao cDao=new C_CompanyDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				num=cDao.saveCompanyAssetItem(list2);
			}else if(filename.indexOf("epayment")>-1){
				EPaymentDao cDao=new EPaymentDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, -1, 0);
				String xx=cDao.saveEpaymentRequest(list2,user);
				JSONObject json=new JSONObject(xx);
				results=json.getString("msg");
			}else if(filename.indexOf("StaffNameCard")>-1){
				StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=staffDao.uploadNameCardDetial(list2,user);
				JSONObject json=new JSONObject(xx);
				results=json.getString("msg");
			}else if(filename.indexOf("approveNameCard")>-1){
				NameCardConvoyDao namecardDao=new NameCardConvoyDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=namecardDao.uploadNameCard(list2,user);
				JSONObject json=new JSONObject(xx);
				results=json.getString("msg");
			}else if(filename.indexOf("SMS")>-1){
				PickUpRecordDao dao=new PickUpRecordDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=dao.uploadPickUpList(list2,user);
				JSONObject json=new JSONObject(xx);
				results=json.getString("msg");
			}else if(filename.indexOf("Vs code")>-1){
				PickUpRecordDao dao=new PickUpRecordDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=dao.uploadPickUp_HKCode(list2,user);
				JSONObject json=new JSONObject(xx);
				results=json.getString("msg");
			}else if(filename.indexOf("Vs code")>-1){
				PickUpRecordDao dao=new PickUpRecordDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=dao.uploadPickUp_HKCode(list2,user);
				JSONObject json=new JSONObject(xx);
				results=json.getString("msg");
			}else if(filename.indexOf("SeatList")>-1){
				SeatDao dao=new SeatDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=dao.uploadSeatList(list2,user);
				JSONObject json=new JSONObject(xx);
				if(json.get("state").equals("success")){
					
/*					String str1 = json.get("data").toString().replace("{","").replace("}", "");
					String[] str2 = str1.split(",");
					String[] str3 = str2[0].split(":");
					String[] str4 = str2[1].split(":");
					String[] str5 = str2[2].split(":");
					num = Integer.parseInt(str3[1]);
					result = Integer.parseInt(str4[1]);
					int total = Integer.parseInt(str5[1]);
					results = "总条数："+total+"条,成功条数:"+num+"条,其中有"+result+"条座位号已存在未上传";*/
					Map<String, Object> map = net.sf.json.JSONObject.fromObject(json.get("data").toString());
					int newnum = Integer.valueOf(map.get("addnum").toString());
					result = Integer.valueOf(map.get("updatenum").toString());
					num = (newnum+result);
					int total = Integer.valueOf(map.get("totalnum").toString());
					results="总条数："+total+"条,成功条数:"+num+"条,其中有"+result+"条进行了修改";
				}
			}else if(filename.indexOf("MedicalOutPutList")>-1){
				MedicalOptOutDao medicalOptOutDao = new MedicalOptOutDaoImpl();
				File file=new File(uploadfile);
				List<List<Object>> list2=ReadExcel.readExcel(file, 0, 20, 0);
				String xx=medicalOptOutDao.uploadMedicalOutPutList(list2,user);
				JSONObject json=new JSONObject(xx);
				if(json.get("state").equals("success")){
					Map<String, Object> map = net.sf.json.JSONObject.fromObject(json.get("data").toString());
					num = Integer.valueOf(map.get("addnum").toString());	
				}
			}
			
			/**上传文件时必须执行的语句 **/
			after= System.currentTimeMillis(); // 标记结束时
			String nums=((after-befor)/60000)+"";
			
			
			list.add(0,uploadfile);//上傳路徑
			list.add(1,num);//成功條數
			list.add(2,nums);//上傳時間
			list.add(3,result);//已存在的数据
			list.add(4,results);
			
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
			System.out.println("上传文件"+uploadfile+"-->时间-->"+nums+"--->成功条数-->"+num);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("在UpExcelServlet中上传"+filename.replaceAll("\\\\", "/").substring(
					filename.replaceAll("\\\\", "/").lastIndexOf("/") + 1)+"时出现"+e.toString());
					out.print("error");
		}finally{
			out.flush();
			out.close();
		}
		
	}

}
