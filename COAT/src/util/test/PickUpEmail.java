package util.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ReadExcel;
import util.SendMail;
import util.Util;

public class PickUpEmail {
	/**
	 * 获取顾问信息以及电话和密码信息
	 * @author kingxu
	 * @date 2016-8-3
	 * @param args
	 * @return void
	 */
	public static void main2(String[] args) {
		try{
			
			
			
		File file=new File("H:\\日常工作维护\\COAT-Special request for Document Pick Up\\Consultant.xlsx");
		List<List<Object>> list=ReadExcel.readExcel(file, 2, 7, 1);
		
		File file2=new File("H:\\日常工作维护\\COAT-Special request for Document Pick Up\\Consultant.xlsx");
		List<List<Object>> list2=ReadExcel.readExcel(file2, 1, 7, 1);
		List<Object> cellList2=null;
		int rowNum=list2.size();
		Map<String,String[]> map=new HashMap<String, String[]>();
		String[] vaule=new String[2];
		for(int i=0;i<rowNum;i++){
			cellList2=list2.get(i);
			if(!Util.objIsNULL(cellList2.get(0))){
				vaule=new String[2];
				vaule[0]=cellList2.get(1)+"";
				vaule[1]=cellList2.get(2)+"";
				map.put(cellList2.get(0)+"", vaule);
			}
			 
		}
		
		
		
		
		
		
		List<Object> cellList=null;
		int num=list.size();
		int cellNum=0;
		int n=0;
		String extension="";
		String password="";
		String staffcode="";
		String resigned="";
		String[] v=null;
		for(int i=0;i<num;i++){//遍历行
			cellList=list.get(i);
			cellNum=cellList.size();
			extension=(cellList.get(0)+"").replace(".00", "");
			password=(cellList.get(1)+"").replace(".00", "");
			staffcode=cellList.get(4)+"";
			if(!Util.objIsNULL(extension)&&!Util.objIsNULL(password)&&!Util.objIsNULL(staffcode)&&staffcode.length()==6&&map.containsKey(staffcode)){
				n++;
				v=map.get(staffcode);
				//for(int j=0;j<cellNum;j++){
					System.out.println(extension+"	"+password+"	"+staffcode+"	"+v[0]+"	"+v[1]);
				//}
			}
			
		}
		System.out.println(n+"---------");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public static void main(String[] args) {
	try{
		String content="";
		File file=new File("H:\\日常工作维护\\COAT-Special request for Document Pick Up\\Consultant.xlsx");
		List<List<Object>> list=ReadExcel.readExcel(file, 0, 7, 1);
		int rowNum=list.size();
		List<Object> cellList=null;
		int cellNum=0;
		String extension="";
		String password="";
		for(int i=0;i<rowNum;i++){
			cellList=list.get(i);
			//cellNum=cellList.size();
			extension=(cellList.get(0)+"").replace(".00", "");
			password=(cellList.get(1)+"").replace(".00", "");
			content="Dear "+cellList.get(3)+",<br/>" +
					"  Please be informed that the phone password of "+extension+" is "+password+".<br/>" +
							"在此通知閣下電話內線 "+extension+"的密碼為 "+password+".<br/><br/>";
			content+="Please make note of the password for using the Electronic Document Pick Up System in the future<br/>" +
					"請記下該密碼，以便日後使用電子接收文件系統<br/><br/>" +
					"Should you have any enquiries, please contact ADM hotline at ext 3667<br/>" +
					"如有任何疑問，歡迎致電行政部熱線3667查詢。<br/><br/>" +
					"Administration Department行政部";
				send(cellList.get(4)+"", content);
			//System.out.println(content);
			System.out.println();
			
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
}


private static void send(String to,String contont) {
	try{
		String result=SendMail.send("Phone password notification/ 電話密碼通知" , to, contont);
		System.out.println(to+"--"+result);
		
	}catch (Exception e) {
		e.printStackTrace();
	}

}
	
	
	
	
}
