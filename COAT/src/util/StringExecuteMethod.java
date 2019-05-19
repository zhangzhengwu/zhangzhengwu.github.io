package util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Staff_listBean;

public class StringExecuteMethod {

	
	
	public static void main(String[] args) {
		String className="com.coat.consultant.dao.impl.ConsultantInfoDaoImpl";
		String methodName="synchConsultantInfo";
	 executeMethod(className, methodName);
	}
	
public static void executeMethod(String className,String methodName){
	Map<String,String> map=new HashMap<String, String>();
	
		
		try{
			  Class clz = Class.forName(className);
			  //  
			  Object obj = clz.newInstance();
			  //获取方法  
			  Method m = obj.getClass().getDeclaredMethod(methodName);
			  //调用方法  
			//  String  result = (String) m.invoke(obj);
			//  System.out.println(result);
			  m.invoke(obj);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
