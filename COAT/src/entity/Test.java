package entity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		
		try {
			System.out.println("本机的IP = " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		          
		       

		
		
	/*	int count=12;
		int preCount=10;
		//System.out.println(5120%500);
		for (int i = 1; i <= count; i++) {
			 if(i % preCount == 0){
				 //System.out.println("---2---"+i);
			 } 
			 //System.out.println(i+"---1---"+(count/preCount)*preCount);
			 if(i > (count/preCount)*preCount ){
				System.out.println(i+"---3---"+(count/preCount)*preCount);
			 }
			 if(preCount > (count%preCount) ){
				System.out.println(i+"---3---"+(count/preCount)*preCount);
			 }
			 
			 
		}*/
	
		
		 
		
		/*
		 * Date date = new Date(); String dateStr = ""; String weekStr = "";
		 * Calendar calendar = Calendar.getInstance(); int week =
		 * calendar.get(Calendar.DAY_OF_WEEK) - 1; switch (week) { case 0:
		 * weekStr = "星期日"; break; case 1: weekStr = "星期一"; break; case 2:
		 * weekStr = "星期二"; break; case 3: weekStr = "星期三"; break; case 4:
		 * weekStr = "星期四"; break; case 5: weekStr = "星期五"; break; case 6:
		 * weekStr = "星期六"; break; } Date dateBegin = new Date(); Date dateEnd =
		 * new Date(); DateFormat shortDateFormat =
		 * DateFormat.getDateInstance(0); dateStr =
		 * shortDateFormat.format(date); dateBegin.setTime(date.getTime() -
		 * (long) (week) * 24 * 60 * 60 * 1000); String dateBeginStr =
		 * shortDateFormat.format(dateBegin); dateEnd.setTime(date.getTime() +
		 * (long) (7 - week - 1) * 24 * 60 * 60 1000); String dateEndStr =
		 * shortDateFormat.format(dateEnd); System.out.println("今天是当年的第" +
		 * calendar.get(Calendar.WEEK_OF_YEAR) + "周");
		 * System.out.println("今天是当月的" +
		 * calendar.get(calendar.DAY_OF_WEEK_IN_MONTH) + "周");
		 * System.out.println("今天是" + weekStr); System.out.println("本周的开始时间是" +
		 * dateBeginStr); System.out.println("本周的结束时间是" + dateEndStr);
		 * calendar.set(Calendar.DAY_OF_WEEK, 1); System.out.println("本周的开始时间是" +
		 * (calendar.get(Calendar.MONTH) + 1) + "月" +
		 * calendar.get(Calendar.DATE) + "日");
		 * calendar.set(Calendar.DAY_OF_WEEK, 7);
		 * System.out.println("本周的开始结束时间是" + (calendar.get(Calendar.MONTH) + 1) +
		 * "月" + calendar.get(Calendar.DATE) + "日");
		 */

	/*	final String dayNames[] = { "true", "星期一", "星期二", "星期三", "星期四", "星期五",
				"true" };

		String s = "2012-07-15";
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		System.out.println(dayNames[dayOfWeek - 1]);*/
		
		/*long two=1342086200410L; 
		long three=1342086257571L; 
		double a =  (three - two) /60000; 
		DecimalFormat df1 = new DecimalFormat("##0.00");
		System.out.println(df1.format(a)); */
		 
		/*String D1 = "2012-01-02";
		String D2 = "2012-01-02";
		
		System.out.println(DateUtils.compare_date(D1,	D2));*/
	/*	String nowdate = "09:2" +
				"" +
				" 7:49";
		DecimalFormat df1 = new DecimalFormat("##0.00%");


		String aDouble =df1.format((1)-((9/(double)21)+(3/(double)21)));
		 
		System.out.println(aDouble);*/
		/*String stageDate = "2012-01-15";
		String ym = "2012-02-31";
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		*/
//		System.out.println(new Double("0.00%".replace("%", ""))); 
		
		
		
		
		//System.out.println(DateUtils.addOneDate("2012-02-29")); 
	}
	public static boolean compTime(String s1,String s2){
		if (s1.indexOf(":")<0||s1.indexOf(":")<0) {
		System.out.println("格式不正确");
		}else{
		String[]array1 = s1.split(":");
		int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60+Integer.valueOf(array1[2]);
		 
		String[]array2 = s2.split(":");
		int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60+Integer.valueOf(array2[2]);
		 
		return total1-total2>0?true:false;
		}
		return false;

		}
 

	public String idWeek() {

		final String dayNames[] = { "true", "星期一", "星期二", "星期三", "星期四", "星期五",
				"true" };

		String s = "2012-07-15";
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		System.out.println(dayNames[dayOfWeek - 1]);
		
		return dayNames[dayOfWeek - 1];
	}
	/*
	 * public static String getIpAddr(HttpServletRequest request) { String ip =
	 * request.getHeader("x-forwarded-for"); if(ip == null || ip.length() == 0 ||
	 * "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("Proxy-Client-IP"); } if(ip == null || ip.length() == 0 ||
	 * "unknown".equalsIgnoreCase(ip)) { ip =
	 * request.getHeader("WL-Proxy-Client-IP"); } if(ip == null || ip.length() ==
	 * 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getRemoteAddr(); }
	 * return ip; }
	 */

	// Process p = Runtime.getRuntime().exec("cmd /C start msimn.exe
	// /eml:d:/test.eml");
	/*
	 * DateUtils d=new DateUtils(); Calendar
	 * day=Calendar.getInstance(Locale.CHINA);
	 * day.setFirstDayOfWeek(Calendar.MONDAY);//周一为每个星期的第一天 SimpleDateFormat
	 * date=new SimpleDateFormat("yyyy-MM-dd"); SimpleDateFormat date1=new
	 * SimpleDateFormat("yyyy"); SimpleDateFormat date2=new
	 * SimpleDateFormat("MM"); SimpleDateFormat date3=new
	 * SimpleDateFormat("dd"); int
	 * MaxDay=d.getDays(Integer.parseInt(date1.format(day.getTime())),Integer.parseInt(date2.format(day.getTime())));
	 * 
	 * day.setTimeInMillis(System.currentTimeMillis()-(1000*60*60*24*2));//设置当前时间
	 * System.out.println("当前时间为："+date.format(day.getTime()));//输出当前时间
	 * 
	 * day.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY); int
	 * year=Integer.parseInt(date1.format(day.getTime())); int
	 * month=Integer.parseInt(date2.format(day.getTime())); int
	 * day1=Integer.parseInt(date3.format(day.getTime()));
	 * 
	 * if(day1+1>MaxDay){ System.out.println(year+"-0"+(month+1)+"-"+"01");
	 * }else{ System.out.println("周一时间："+year+"-0"+month+"-"+(day1+1)); }
	 */
	/*
	 * String b="200"; double a=72.0/100.0;
	 * System.out.println(a*Double.parseDouble(b));
	 */

	/** ********************************************獲取本機IP以及用戶名*************************************** */
	/**
	 * System.out.println(System.getProperty("user.name")); String ip; try {
	 * InetAddress in = InetAddress.getLocalHost(); InetAddress[] all =
	 * InetAddress.getAllByName(in.getHostName()); //通过本机主机名，遍历多个ip for (int i =
	 * 0; i < all.length; i++) { String tmp=null;
	 * tmp=ip=all[i].getHostAddress().toString(); System.out.println("IP = "
	 * +tmp);//输出计算机所有的ip地址 // if (isInnerIP(ipToLong(tmp)) ==
	 * -1)//检查是不是外网ip，如果是就保存文件 // { /*WriteFile(ip);//将ip地址写入文件
	 * System.out.println("IP保存在同目录IP.txt文件中");
	 * 
	 * System.out.println("同目录IP.txt文件中的IP是：" + ReadFile());
	 */
	/*
	 * // System.out.println(ip); // } } } catch (UnknownHostException e) { } }
	 * 
	 * public static int isInnerIP(long a_ip)//检查ip地址是否是内网ip { int bValid = -1;
	 * try{ if ((a_ip >> 24 == 0xa) || (a_ip >> 16 == 0xc0a8) || (a_ip >> 22 ==
	 * 0x2b0)) { bValid = 0; } }catch(Exception e){ e.printStackTrace(); }
	 * return bValid; } // 将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理 public
	 * static long ipToLong(String strIP) { long[] ip = new long[4];
	 * //先找到IP地址字符串中.的位置 int position1 = strIP.indexOf("."); int position2 =
	 * strIP.indexOf(".", position1 + 1); int position3 = strIP.indexOf(".",
	 * position2 + 1); //将每个.之间的字符串转换成整型 ip[0] =
	 * Long.parseLong(strIP.substring(0, position1)); ip[1] =
	 * Long.parseLong(strIP.substring(position1 + 1, position2)); ip[2] =
	 * Long.parseLong(strIP.substring(position2 + 1, position3)); ip[3] =
	 * Long.parseLong(strIP.substring(position3 + 1)); return (ip[0] << 24) +
	 * (ip[1] << 16) + (ip[2] << 8) + ip[3]; }
	 */
	/** ****************************************************************************************************** */
	/*
	 * SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm"); String
	 * str=sdf.format(new Date()); System.out.print(str);
	 */

}

class sas{
	private String a;

	/**
	 * @param a the a to set
	 */
	public void setA(String a) {
		this.a = a;
	}

	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}
}