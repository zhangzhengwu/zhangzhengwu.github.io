package util.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import util.Util;


public class Test {

	
	public static void main(String[] args) {
		 String className = "util.test.TestXml";
		  String methodName = "a";
		try{
			  Class clz = Class.forName(className);
			  //  
			  Object obj = clz.newInstance();
			  //获取方法  
			  Method m = obj.getClass().getDeclaredMethod(methodName);
			  //调用方法  
			  String  result = (String) m.invoke(obj);
			  System.out.println(result);
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
  public static void random(String[] pool,String[] user,int count,int type){
	  System.out.println("");
	  Random random=new Random();// 定义随机类
		Map<String,Integer> map=new HashMap<String,Integer>();
		System.out.println("-------------------");
		for (int j = 0; j < count; j++) {
			for (int i = 0; i < user.length; i++) {
				int num=random.nextInt(pool.length);
				System.out.println("  "+user[i]+"==>"+pool[num]);
				if(map.containsKey(pool[num])){
					map.put(pool[num], map.get(pool[num])+1);
				}else{
					map.put(pool[num], 1);
				}
			}
		}
		System.out.println("---------票数汇总----------");
		Set<String> keys=map.keySet();
		int max=0;
		String dian="";
		for (String key:keys) {
			System.out.println(key+"----"+map.get(key));
			if(map.get(key)>max){
				max=map.get(key);
				dian=key;
			}
		}
	String result=vailMap(map, max, dian);
		if(Util.objIsNULL(result)){
			System.out.println("");
			System.out.println("==========>最终结果("+dian+")<==========");
		}else{
			System.out.println("---------存在多个相同相同票数内容("+dian+result+"),再次进行投票----------");
			random(type==0?(dian+result).split(","):pool, user,count,type);
		}
  }
  
  public static String vailMap(Map<String,Integer> map,int num,String dian){
	  String result="";
		Set<String> keys=map.keySet();
		for (String key:keys) {
			if(map.get(key)==num && !dian.equalsIgnoreCase(key)){
					result+=","+key;
			}
		}
	  return result;
  }
	
	
	public static void mains(String[] args) {
		String[] pool={"鱼庄","老乡村","农家冲","嫂子面馆","快乐园","雅山","维客","六千","麦当劳","拉面"};
		String[] user={"wilson","king","hugo","orlando","toby","blithe"};
		System.out.println("饭店选项"+Arrays.toString(pool));
		System.out.println("投票人"+Arrays.toString(user));
		int count=getvotenum();
		while(count<=0){
			count=getvotenum();
		}
		int type=gettype();
		while(type<0 || type>1){
			type=gettype();
		}
		random(pool, user,count,type);
	}
	
	public static int getvotenum(){
		int num=-1;
		try{
			Scanner scanner = new Scanner(System.in);// 创建输入流扫描器
			System.out.println("请输入随机投票次数(大于0的数字):");
			num = Integer.parseInt(scanner.nextLine());
			if(num<1){
				System.out.println("只能输入大于0的数字");
			}
		}catch (Exception e) {
			System.out.println("非法输入");
		}
		return num;
	}
	
	public static int gettype(){
		int num=-1;
		try{
			Scanner scanner = new Scanner(System.in);// 创建输入流扫描器
			System.out.println("请选择存在多个票数并列第一的后续操作(0:从剩下的选择中再次随机;1:重新随机所有选择)");
			num = Integer.parseInt(scanner.nextLine());
			if(num>1){
				System.out.println("只能输入0或1");
			}
		}catch (Exception e) {
			System.out.println("非法输入");
		}
		return num;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void Test( final String urls,int num,int threadnum){
		ExecutorService exec=null;
		try{	 

			exec = Executors.newFixedThreadPool(threadnum);  
			for (int index = 0; index < num; index++) {  
				final int NO = index;  
				Runnable run = new Runnable() {  
					public void run() {  
						try {  
							long time1=System.currentTimeMillis();
							URL url = new URL(urls);
							InputStreamReader isr = new InputStreamReader(url.openStream());
							long time2=System.currentTimeMillis();
							System.out.println("Thread"+NO+"time:"+(time2-time1)+"ms");
							BufferedReader br = new BufferedReader(isr);
							String str;
							while ((str = br.readLine()) != null) {
							System.out.println(str);
							}
							br.close();
							isr.close();
						} catch (Exception e) {  
							e.printStackTrace();  

						}finally{
							
						}

					}  

				};  
				exec.execute(run);  
			}  
		}catch (Exception e) {

		}finally{
			// 退出线程池  
			exec.shutdown();  
		}

	}
}
