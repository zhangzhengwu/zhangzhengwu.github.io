package servlet.Medical.Staff;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DateUtils;


import dao.StaffMedicalDao;
import dao.impl.StaffMedicalDaoImpl;
import entity.Medical_record_staff;

public class ModifyStaffMedicalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
doPost(request, response);
	}

	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String UserName=request.getSession().getAttribute("adminUsername").toString();
			String id=request.getParameter("id");
		String staffcode=request.getParameter("staffcode");
		String type=request.getParameter("type").substring(request.getParameter("type").lastIndexOf('-')+1);
		String MedicalDate=request.getParameter("MedicalDate");
		String MedicalFee=request.getParameter("MedicalFee");
		String staffname=request.getParameter("staffname");
		String company=request.getParameter("Company");
		String dept=request.getParameter("Department");
		String grade=request.getParameter("grade");
		String entitle=request.getParameter("entitle");//实际报销金额
		String return_oraginal=request.getParameter("return_oraginal");
		String pplan=request.getParameter("pplan");
		String packages=request.getParameter("package");
		//String maxamount=request.getParameter("max_amount");//最高报销次数
		String amount=request.getParameter("max_amount_money");//最高报销金额
		String medicalNormal=request.getParameter("Normal_Number");
		String medicalSpecial=request.getParameter("Special_Number");
		String Oral_Number=request.getParameter("Oral_Number");
		String Dental_Number=request.getParameter("Dental_Number");
		StaffMedicalDao smd=new StaffMedicalDaoImpl();
		String reType=request.getParameter("reType");
		String reEntitle=request.getParameter("reEntitle");
		String reDental=request.getParameter("Dental_Number");
		String email=request.getParameter("email");
		String add_Date=request.getParameter("add_Date");
		String add_Name=request.getParameter("add_Name");
		String upd_date=DateUtils.getNowDateTime();
		/**
		 * 为了便于区分添加时间和修改时间，故将最新时间添加到置为N的数据中
		 * 作为修改时间，而把添加时间保存在新保存的数据中来替换修改数据前的位置
		 */
		smd.updateMedical(Integer.parseInt(id),upd_date,UserName);//把被更新的置为N
		
		
		
		//判断type与reType的值是否相等
		if(!type.equals(reType)){//type相等，没有改变MedicalType  值 不需改变使用限额
		 //medicalType发生改变,做特殊处理
			/***********************判断选择的Medical Type   根据类型增加数量**************/
				if(reType.equals("SP")){//专科
					medicalSpecial=(Integer.parseInt(medicalSpecial)-1)+"";
				}else if(reType.equals("GP")){//普科
					medicalNormal=(Integer.parseInt(medicalNormal)-1)+"";
				}else if(reType.equals("Regular")){//口腔科
					Oral_Number=(Integer.parseInt(Oral_Number)-1)+"";
				}else if(reType.equals("Dental")){//牙科
					Dental_Number=(Double.parseDouble(Dental_Number)-Double.parseDouble(reEntitle))+"";
				}
			/**********************************/
				/***********************判断选择的Medical Type   根据类型增加数量**************/
				if(type.equals("SP")){//专科
					medicalSpecial=(Integer.parseInt(medicalSpecial)+1)+"";
				}else if(type.equals("GP")){//普科
					medicalNormal=(Integer.parseInt(medicalNormal)+1)+"";
				}else if(type.equals("Regular")){//口腔科
					Oral_Number=(Integer.parseInt(Oral_Number)+1)+"";
				}else if(type.equals("Dental")){//牙科
					Dental_Number=(Double.parseDouble(Dental_Number)+Double.parseDouble(entitle))+"";
				}
				/**********************************/
		}
	
		String term=(Integer.parseInt(medicalNormal)+Integer.parseInt(medicalSpecial)+Integer.parseInt(Oral_Number))+"";//报销总数
 //修改保存后的数据
			
		Medical_record_staff mrs=new Medical_record_staff(staffcode,staffname, company, dept, grade, pplan, packages,email, amount,type, term, 
				medicalNormal, medicalSpecial,Oral_Number,Dental_Number,MedicalDate, MedicalFee,DateUtils.strChToUs(add_Date), entitle, return_oraginal, "Y",add_Name,add_Date,UserName, upd_date, "Y"); 
			int num =smd.saveMedical(mrs);
			if(num>0){
				if(!type.equals(reType)){
				//保存更新结果成功，开始更新使用次数
				int mum_down=smd.updateSpecialDown(reType, Integer.parseInt(term), staffcode,reEntitle,reDental,MedicalDate);
				if(mum_down>-1){
					if(reType.equals("Dental")){
						term=(Integer.parseInt(term)-1)+"";
					}
					int mum_up=smd.updateSpecialUP(type, Integer.parseInt(term)-1, staffcode,add_Date,entitle,reDental,MedicalDate);
					if(mum_up>-1){
						out.print("保存成功!");
					}else
						out.print("保存失败!");
					
				}else
					out.print("保存失败!");
				
				}else{
					out.print("保存成功!");
				}
			}else{
				out.print("保存失败!");
			}
	 
		}catch(Exception e){
			e.printStackTrace();
			out.print("用户信息丢失，请重新登录....");
		}finally{
			out.flush();
			out.close();
		}
	}

}
