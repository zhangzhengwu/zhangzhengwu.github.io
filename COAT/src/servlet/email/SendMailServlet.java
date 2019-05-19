package servlet.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import dao.ConsListDao;
import dao.impl.ConsListDaoImpl;

import util.Constant;
import util.DateUtils;
import util.Util;

public class SendMailServlet extends HttpServlet {

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

		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String method=request.getParameter("method");
		try{
			
			String basePath=request.getRequestURL().substring(0,request.getRequestURL().indexOf(request.getRequestURI()));
			if(method.equals("medical")){
				MedialClaimSendMail(request, response, basePath);
			}else if(method.equals("attendance")){
				AttendanceSendMail(request, response, basePath);
			}else{
				out.print("未授权的访问");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}
	
	
	 
	/**
	 * Medical Claim 邮件发送
	 * @param basePath
	 */
	void MedialClaimSendMail(HttpServletRequest request, HttpServletResponse response,String basePath){
		StringBuffer result=new StringBuffer();
		PrintWriter out = null;
		try{
			 out = response.getWriter();
			String subject="The details of this month's medical claims is attached for your checking/reference.The claimed amount will be refunded with the coming commission payment.";
			String to="";
			String cc="";
			String contont="";
			String attr="";
			String mail="";
			String returnResult="";
			String tempcode="";
			HttpURLConnection htp=null;
			
			String path=Constant.MedicalMail+DateUtils.getDateToday();
			File file=new File(path);
			File[] tempList = file.listFiles();
			ConsListDao consDao=new ConsListDaoImpl();
			if(!Util.objIsNULL(tempList)){
				//遍历文件夹
				for(int i = 0; i < tempList.length; i++){
					if(!tempList[i].isHidden()&&tempList[i].isFile()){
						tempcode=tempList[i].getName().substring(0,tempList[i].getName().indexOf("."));
						attr=tempList[i].toString();
						to=consDao.findMailByCode(tempcode);
						//to="king.xu@convoy.com.hk;jackie.li@convoy.com.hk;jane.wen@convoy.com.hk";
						if(!Util.objIsNULL(to)){
							mail="subject="+subject+"&" +
								"to="+to+"&" +
								"cc="+cc+"&" +
								"body="+contont+"&" +
								"attr="+attr+"&" +
								"webapp=medical";
							htp=(HttpURLConnection)new URL(basePath+"/ExchangeMail/MassSendMailServlet").openConnection();
							htp.setDoOutput(true);
							htp.setRequestMethod("POST");
							htp.setUseCaches(false);
							htp.setInstanceFollowRedirects(true);
							htp.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
							OutputStream tarparm=htp.getOutputStream();
							
							System.out.println("Client----第"+(i+1)+"次调用Servlet");
							tarparm.write(mail.getBytes());//传递参数
							tarparm.flush();
							tarparm.close();
					        
					        InputStreamReader isr=new InputStreamReader(htp.getInputStream());
				            BufferedReader br=new BufferedReader(isr);
		
				            if(br.ready()) {
				        	   returnResult=br.readLine();//获取返回结果
								System.out.println("Client-----获取返回结果：==="+returnResult);
								result.append(tempcode+"-"+returnResult+"\r\n");
							}
				           htp.disconnect(); 
				           System.out.println();
				           Thread.sleep(100);
				           System.out.println("----------------------------------------------");						
						
						}else{
							result.append(tempcode+"-邮箱未找到\r\n");
						}
					}
				}
		}else{
			result.append("文件列表为空");
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			result.append(e.getMessage());
		}finally{
			writeTxt2(Constant.MedicalMailLog+DateUtils.getDateToday()+".log", result.toString());
			out.print(result);
			out.flush();
			out.close();
		}
		
		
	}
	/**
	 * Attendance 发送邮件
	 * @param basePath
	 */
	void AttendanceSendMail(HttpServletRequest request, HttpServletResponse response,String basePath){
		StringBuffer result=new StringBuffer();
		PrintWriter out =null;
		try{
			 out = response.getWriter();
			String subject="Monthly attendance report";
			String to="";
			String cc="";
			String contont="";
			String attr="";
			String mail="";
			String returnResult="";
			String tempcode="";
			HttpURLConnection htp=null;
			
			String path=Constant.AttendanceMail+DateUtils.getDateToday();
			File file=new File(path);
			File[] tempList = file.listFiles();
			Map<String,String[]> map=getConsultantEmail(Constant.AttendanceMailList);
			if(!Util.objIsNULL(tempList)){	
			//遍历文件夹
				for(int i = 0; i < tempList.length; i++){
					if(!tempList[i].isHidden()&&tempList[i].isFile()){
						tempcode=tempList[i].getName().substring(0,tempList[i].getName().indexOf(" "));
						
						attr=tempList[i].toString();
						//System.out.println("----------"+consDao.findMailByCode(tempcode));
						if(!Util.objIsNULL(map.get(tempcode))){
							to=map.get(tempcode)[1];
							System.out.println("------------"+to);
							//to="king.xu@convoy.com.hk;jackie.li@convoy.com.hk;jane.wen@convoy.com.hk";
							String englishname=map.get(tempcode)[0];
							System.out.println(englishname+"---"+to);
							if(!Util.objIsNULL(to)&&!Util.objIsNULL(englishname)){
								contont="Dear "+(englishname)+",<br/><br/>" +
										"Pls kindly find attached monthly attendance report of "+(DateUtils.getBeforeMonth_eng())+" of your team for your reference. <br/><br/>" +
										"For any inquiry, pls contact <a>SZOAdm@convoy.com.hk</a> .<br/><br/><br/>" +
										"Regards<br/><br/>" +
										"Admin Team<br/><br/>" +
										"Shared Service Centre<br/><br/>"+
										"Convoy Financial Services Limited";
								
								mail="subject="+subject+"&" +
									"to="+to+"&" +
									"cc="+cc+"&" +
									"body="+contont+"&" +
									"attr="+attr+"&" +
									"webapp=medical";
								
								htp=(HttpURLConnection)new URL(basePath+"/ExchangeMail/MassSendMailServlet").openConnection();
								htp.setDoOutput(true);
								htp.setRequestMethod("POST");
								htp.setUseCaches(false);
								htp.setInstanceFollowRedirects(true);
								htp.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
								OutputStream tarparm=htp.getOutputStream();
								
								System.out.println("Client----第"+(i+1)+"次调用Servlet");
								tarparm.write(mail.getBytes());//传递参数
								tarparm.flush();
								tarparm.close();
						        
						        InputStreamReader isr=new InputStreamReader(htp.getInputStream());
					            BufferedReader br=new BufferedReader(isr);
			
					            if(br.ready()) {
					        	   returnResult=br.readLine();//获取返回结果
									System.out.println("Client-----获取返回结果：==="+returnResult);
									result.append(tempcode+"-"+returnResult+"\r\n");
								}
					           htp.disconnect(); 
					           System.out.println();
					           Thread.sleep(100);
					           System.out.println("----------------------------------------------");						
							
							}else{
								result.append(tempcode+"-邮箱未找到\r\n");
							}
						
						}else{
							result.append(tempcode+"-邮箱未找到\r\n");
						}
					}
				}	
			}else{
				result.append("文件列表为空");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result.append(e.getMessage());
		}finally{
			writeTxt2(Constant.AttendanceMailLog+DateUtils.getDateToday()+".log", result.toString());
			out.print(result);
			out.flush();
			out.close();
		}
		
		
	}
	
    public void writeTxt2(String downFile,String ss1) {
    	try {   
    		File f = new File(downFile);   
    		if(!f.exists()){    
    			/* FileUtil.deleteAll(downFile);
				 System.out.println(downFile + "已删除！");
			 }else {*/
    			f.createNewFile();//不存在则创建
    			System.out.println(downFile + "已创建！");
    			
    		}
    		FileOutputStream fis=new FileOutputStream(f); 
    		OutputStreamWriter isr=new OutputStreamWriter(fis,"GBK"); 
    		BufferedWriter br=new BufferedWriter(isr);
    		br.write(ss1);
    		br.close(); 
    	} catch (Exception e) 
    	{   
    		e.printStackTrace();  
    	} 
    	
    }
	
	public Map<String,String[]> getConsultantEmail(String downFile){
		//downFile=Const.COMMISSION_HK_EXCEL+"\\email\\"+DateUtils.getYear()+"-"+(DateUtils.getMonth()<10?"0":"")+DateUtils.getMonth()+"\\Email_"+DateUtils.getYear()+"-"+(DateUtils.getMonth()<10?"0":"")+DateUtils.getMonth()+".xls";
		Map<String,String[]> map=new HashMap<String, String[]>();
		FileInputStream fs=null;
		HSSFWorkbook workbook=null;
		try{
			fs = new FileInputStream(downFile);  
			 workbook = new HSSFWorkbook(fs);  
			 //讀取Excel Sheet頁     读取本年度的 sheet 数据
			 HSSFSheet sheet = workbook.getSheetAt(0);  //+DateUtils.getYear()
			 HSSFRow row=null;
			 int rowNum = sheet.getLastRowNum();//行數
			 for(int i = 1 ; i <= rowNum ; i++){  //開始讀取的行數 i 下标以0开始计算
				 row = sheet.getRow(i);//當前行  
				//System.out.println(row.getCell(0)+"--"+row.getCell(1));
				map.put(row.getCell(0).toString(),new String[]{Util.objIsNULL(row.getCell(1))?"":row.getCell(1).toString(),Util.objIsNULL(row.getCell(2))?"":row.getCell(2).toString()});
				//list.add(row.getCell(0)+";~;"+row.getCell(1)+";~;"+row.getCell(2));
			 }
	
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			 try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	return map;	
	}
	
	public static void main(String[] args) {

	/*	String path="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Report sent out\\Attendance\\2015-03-09\\";
		File file=new File(path);
		File[] tempList = file.listFiles();
		System.out.println("该目录下对象个数："+tempList.length);
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				System.out.println("文     件："+tempList[i]);
				System.out.println("文件夹名："+tempList[i].getName());
				System.out.println(tempList[i].getName().substring(0, tempList[i].getName().indexOf(" ")));
			}
			if (tempList[i].isDirectory()) {
				System.out.println("文件夹："+tempList[i]);
			}
		}*/
		//String path=Constant.AttendanceMail+DateUtils.getDateToday();
		String path="D:\\Test";
		
		
		String[] T=new String[]{"AB0318","AC0024","AI1218","AJ6379","AK0577","AL0065","AT7251","BC4987","BK1483","BL0402","BL2810","BN3163","BS0252","BT1766","BY0440","CC0117","CK0200","CK4432","CS0635","CV0110","CY2070","CY2560","CY4753","DC1004","DC2493","DN1558","DS0062","DZ5263","EF1458","EL2052","EL4741","ER0290","EW5364","FL0415","FN5385","FT1527","GL5084","GW0905","HE0268","IC1396","IL0098","IT0230","IW1012","IW1741","IW4812","JC3275","JF2071","JK0104","JK0789","JL1510","JL3974","JS0526","JT0781","JT3426","JW0773","JW0979","JW2732","JY0431","JY2383","JY5588","KC0552","KC1940","KC3597","KF3104","KH1716","KK1175","KK1557","KK3607","KL2755","KS0487","KT2005","KT6059","KY3421","LC0806","LK0090","LO0376","LW0183","MC0103","MC0542","MC0827","MC1038","MH6319","ML0168","ML2083","ML2888","ML4417","ML5197","NC4000","NE0244","NK0022","PL0064","PL3123","PW0270","RC1041","RC1792","RG0212","RL0072","RL0845","RL2603","RL4194","RW0144","RW1436","SA0133","SC1177","SC3533","SC5081","SH1351","SL2115","SL3333","SS3440","ST3360","SY1254","TC1972","TC2120","TC3345","TC5407","TH1180","TL3325","TL3758","TT4303","WH0509","WL6366","WP2756","WW3145","YL0165","EL0450","AC2402","DK3259","JC1128","AS0677","CS2677","CP3246","CY1519","JU0215","CL3880","AT5281","AT0484","JC0014","KK0112","KL4762","KM2015","CH2847","EL1298","FC1136","CC5850","DP0184","IC3646","AN2128","CN2351","GM4693","ES0353","FC0058","EG0386","BL6478","CH0111","CP0838","JH0465","KC1442","AX0127","GH0299","BH0618","CN4503","CY1434","JL4805","AC1013","HL5422","CN1650","JC6121","GC1367","IM1759","CC1302","EF4416","AC3572","JL4974","CL0900","KL5142","DW2404","CN5254","DC0082","GL2789","JY0242","GS0102","AY0337","JJ0835","AC1299","IL5891","EL0638","EC0969","AQ0394","JC5747","JW5093","AL3786","AL0902","BC0216","CC4708","JM1608","CY2681","KS0956","KW2153","LC1810","LW3826","MC1415","MH3840","MK6704","MM1603","MY6034","NL1575","NL1604","NN1869","NW3781","PH0915","PH1540","PL2665","PS1165","PY6490","RF0429","RH0173","RM3242","RN5179","RO0162","RS0390","SC5256","SE0241","SH0541","SL0434","SL1121","SL2736","SN6621","SO0066","SY0070","TC5194","TC5384"};
		System.out.println(path);
		File file=new File(path);
		File[] tempList = file.listFiles();
		if(!Util.objIsNULL(tempList)){
			//遍历文件夹
			for(int i = 0; i < tempList.length; i++){
				if(!tempList[i].isHidden()){//排除隐藏文件
					boolean flag=true;
					
					for(String a:T){
						//System.out.println("----->"+a);
						if(tempList[i].getName().indexOf(a)<0){
							flag=false;
						}else{
							flag=true;
							break ;
						}
						
					}
					if(!flag){
						System.out.println(tempList[i].getName());
					}
				}
			}
			
		}
 
		
	}

}
