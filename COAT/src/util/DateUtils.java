package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.impl.EtraineeDaoImpl;
  
/**  
 * 日期工具类 by  
 * */  
public class DateUtils   
{

	public static String Ordercode(){
		   Date date = new Date();   
		   DateFormat df = new SimpleDateFormat("yyyyMMdd");
			
		   String nowDate =df.format(date);
		return nowDate;
	}
	public static String nowtime(){
		Date date = new Date();   
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String nowDate =df.format(date);
		return nowDate;
	}
	
 
	
	/**
	 * 计算两个时间之间的时间差
	 * @param startTime
	 * @param type
	 * @return
	 */
	public static String Time_difference(String startTime,String type){
		String times=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=null;
		try {
			Date start=df.parse(startTime);
			now=df.parse(df.format(new Date()));
			
			long time=now.getTime()-start.getTime();
			
			long day = time / (24 * 60 * 60 * 1000);
			long hour = (time / (60 * 60 * 1000) - day * 24);
			long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		if(type.equals("day")){
			times=day+"";
		}else if(type.equals("hour")){
			times=hour+"";
		}else if(type.equals("min")){
			times=min+"";
		}else if(type.equals("s")){
			times=s+"";
		}
					
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	/**
	 * 计算两个时间之间的时间差
	 * @param startTime
	 * @param type
	 * @return
	 */
	public static String Time_difference(long startTime,String type){
		String times=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=null;
		try {
			 
			now=df.parse(df.format(new Date()));
			
			long time=now.getTime()-startTime;
			
			long day = time / (24 * 60 * 60 * 1000);
			long hour = (time / (60 * 60 * 1000) - day * 24);
			long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if(type.equals("day")){
				times=day+"";
			}else if(type.equals("hour")){
				times=hour+"";
			}else if(type.equals("min")){
				times=min+"";
			}else if(type.equals("s")){
				times=s+"";
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	
	/**
	 *“yyyy-MM-dd ”转换为格式   MMM/yyyy”
	 * @param viewtime
	 * @return
	 */
	public static String strChToUs(String viewtime) { 
		try {
			Date time = new Date(); 
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
			SimpleDateFormat sdf = 
				new SimpleDateFormat("MMM yyyy", Locale.US); 

			time = sdf2.parse(viewtime);
			viewtime = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return viewtime;
	}
	
	/**
	 * 格式化日期为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String strToYMD(String date){
		try {
			if(Util.objIsNULL(date)){
				return"";
			}
			Date time = new Date(); 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			time = sdf.parse(date);
			date=sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static String strChToUsDMY(String viewtime) { 
		System.out.println(viewtime);
		try {
			Date time = new Date(); 
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat sdf = 
				new SimpleDateFormat("dd-MMM-yyyy", Locale.US); 
			
			time = sdf2.parse(viewtime);
			viewtime = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewtime;
	}
	
	/**
 	 * dd/MMM/yyyy”转换为格式“yyyy-MM-dd ” 
 	 * @param viewtime
 	 * @return
 	 */
 	public static String strUsToCh(String viewtime) { 
 		Date time = new Date(); 

    	//Z 对于格式化来说，使用 RFC 822 4-digit 时区格式 ,Locale.US表示使用了美国时间 
    	SimpleDateFormat sdf = 
    		new SimpleDateFormat("dd MMM yyyy", Locale.US); 

 	   try {
			time = sdf.parse(viewtime);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 

			viewtime = sdf2.format(time); 
			
 	   } catch (ParseException e) {
			e.printStackTrace();
		}
 	   return viewtime;
 	}
 	
 	/**
 	 * yyyy/MM/dd”转换为格式“yyyy-MM-dd ” 
 	 * @param viewtime
 	 * @return
 	 */
 	public static String strUsToCh2(String viewtime) { 
 		if(Util.objIsNULL(viewtime)){
			return"";
		}
 		Date time = new Date(); 

    	//Z 对于格式化来说，使用 RFC 822 4-digit 时区格式 ,Locale.US表示使用了美国时间 
    	SimpleDateFormat sdf = 
    		new SimpleDateFormat("yyyy/MM/dd", Locale.US); 

 	   try {
			time = sdf.parse(viewtime);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 

			viewtime = sdf2.format(time); 
			
 	   } catch (ParseException e) {
			e.printStackTrace();
		}
 	   return viewtime;
 	}
 	
 	/**
 	 * dd/MM/yyyy”转换为格式“yyyy-MM-dd ” 
 	 * @param viewtime
 	 * @return
 	 */
 	public static String strUsToCh3(String viewtime) {
 		if(Util.objIsNULL(viewtime)){
			return"";
		}
 		Date time = new Date(); 

    	//Z 对于格式化来说，使用 RFC 822 4-digit 时区格式 ,Locale.US表示使用了美国时间 
    	SimpleDateFormat sdf = 
    		new SimpleDateFormat("dd/MM/yyyy", Locale.US); 

 	   try {
			time = sdf.parse(viewtime);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd"); 

			viewtime = sdf2.format(time); 
			
 	   } catch (ParseException e) {
			e.printStackTrace();
		}
 	   return viewtime;
 	}
/**
 * 为Admin中MedicalMonth判断财务结算月份
 * @return
 */
 	public static int getMedicalMonth(){
 		int MedicalMonth=0 ;
 		if(getDay()>=26){
 	 			MedicalMonth=(getMonth()+1)>12?1:(getMonth()+1);
 	 		 }else{
 	 			MedicalMonth=getMonth();
 	 		 }
 		return MedicalMonth;
 	}
 
/**
 	 * 为Admin中MedicalMonth判断财务结算月份
 	 * @return
 	 */
 	public static String getMedicalMonth_Year(){
 		String MedicalMonth_Year="";
 		if(getDay()>=26){
 		
 			//MedicalMonth=(getMonth()+1)>12?1:(getMonth()+1);
 			if(getMonth()+1>12){
 				MedicalMonth_Year=(getYear()+1)+"-1";
 			}else{
 				MedicalMonth_Year=getYear()+"-"+(getMonth()+1);
 			}
 			
 		}else{
 			MedicalMonth_Year=getYear()+"-"+getMonth();
 		}
 		return MedicalMonth_Year;
 	}
 	
 	
	/**
	 * 比较两个时间之间相差的分钟数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int ExpiredDays(String startDate,String endDate){
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		   int space=0;   
		 try {  
		           Date date = sdf.parse("2012-12-13 11:00:00");  //开始时间
		            Date dateNow = sdf.parse("2012-12-13 11:31:00"); // 结束时间
		            int s_m=date.getMinutes();  //开始时间分钟数 
		            int s_h=date.getHours();//开始时间小时数
		            int e_h=dateNow.getHours();//结束时间小时数
		            int e_m=dateNow.getMinutes();//结束时间分钟数
		         
		          space=(e_h-s_h)*60+e_m-s_m;
		          System.out.println(space);
		          
	        } catch (ParseException e) {  
		            e.printStackTrace();  
		        }  
	        return space;
	}
	/**
	 * 月份加3个月  -- 用于trainee 过试用期
	 * @param dateStr
	 * @return
	 */
	public static Date StrToDate(String dateStr) {
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");

		Date date ;
		try {
			date = sdfInput.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			date = new Date();
		}
		
		return date;
	}
	/**
	 * 月份加3个月  -- 用于trainee 过试用期
	 * @param dateStr
	 * @return
	 */
	public static String addMonth(String dateStr) {
		  Calendar ca = Calendar.getInstance();
		  String s = "";
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Date dd;
		  try {
		   dd = sdf.parse(dateStr);
		   ca.setTime(dd);
		   ca.add(Calendar.MONTH, 3);
		   s = sdf.format(ca.getTime());
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  
		  return s;
		 }
	/**
	 * 月份加1个月 
	 * @param dateStr
	 * @return
	 */
	public static String addOneMonth(String dateStr) {
		Calendar ca = Calendar.getInstance();
		String s = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dd;
		try {
			dd = sdf.parse(dateStr);
			ca.setTime(dd);
			ca.add(Calendar.MONTH, 1);
			s = sdf.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	/**
	 * 月份减1个月  -- 用于删除 同一天最新上传上个月的数据
	 * @param dateStr
	 * @return
	 */
	public static String delAfterMonth(String dateStr) {
		Calendar ca = Calendar.getInstance();
		String s = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dd;
		try {
			dd = sdf.parse(dateStr);
			ca.setTime(dd);
			ca.add(Calendar.MONTH, -1);
			s = sdf.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	/**
	 * 月份减1个月  -- 用于删除上个月的数据
	 * @param dateStr
	 * @return
	 */
	public static String delMonthafter(String dateStr) {
		Calendar ca = Calendar.getInstance();
		String s = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dd;
		try {
			dd = sdf.parse(dateStr);
			ca.setTime(dd);
			ca.add(Calendar.MONTH, -1);
			s = sdf.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	/**
	 * 编辑月份
	 * @param dateStr 当前时间
	 * @param month 操作月份
	 * @return
	 */
	public static String EditMonthafter(String dateStr,int month) {
		Calendar ca = Calendar.getInstance();
		String s = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dd;
		try {
			dd = sdf.parse(dateStr);
			ca.setTime(dd);
			ca.add(Calendar.MONTH, month);
			s = sdf.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	/**
	 * 月份减2个月  -- 用于convoy china 导出
	 * @param dateStr
	 * @return
	 */
	public static String delTwoMonth(String dateStr) {
		Calendar ca = Calendar.getInstance();
		String s = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dd;
		try {
			dd = sdf.parse(dateStr);
			ca.setTime(dd);
			ca.add(Calendar.MONTH, -2);
			s = sdf.format(ca.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	/**
	 * 周4,5 加4天，用于trainee计算入职打卡date
	 * @param nowdate
	 * @return
	 */
	public static String addTWODate(String nowdate) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(nowdate);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			//判断是否为 周4,5 
			if(DateUtils.isWeekOfnumber(nowdate)==4 || DateUtils.isWeekOfnumber(nowdate)==5){
				cal.add(Calendar.DATE, +4);  //加4天
			}else {
				EtraineeDaoImpl etraineeDao = new EtraineeDaoImpl();
				//cal.add(Calendar.DATE, +2);  //加2天
				int daynum = 1;
				for (int i = 1; i <= 2; i++) {
					 if(i==1){
						cal.add(Calendar.DATE, +1);   
						String aString = df.format(cal.getTime());
						//判断是否为 周4,
						if (DateUtils.isWeekOfnumber(aString)==4 ) {
							cal.add(Calendar.DATE, +1);  //加1天
							break;
						}
						//判断是否为 周5 
						if(DateUtils.isWeekOfnumber(aString)==5){
							cal.add(Calendar.DATE, +4);  //加4天
							break;
						}
						//验证是否为假期
						if (!Util.objIsNULL(etraineeDao.isholidayForUtil(aString)))
						{ 
							cal.add(Calendar.DATE, +daynum+1);   
						} 
					 }else {
					 	cal.add(Calendar.DATE, +1);  
						String aString = df.format(cal.getTime());
						 
						//验证是否为假期
						if (!Util.objIsNULL(etraineeDao.isholidayForUtil(aString)))
						{	 
							cal.add(Calendar.DATE, +daynum); 
						} 
					}
				
				}
			
			}
			
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}
	/**
	 * 获取加一天记录 
	 * @param nowdate
	 * @return
	 */
	public static String addOneDate(String nowdate) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(nowdate);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, +2);  //加1天
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}
	/**
	 * 获取上一天记录
	 * @param nowdate
	 * @return
	 */
	public static String afterDate(String nowdate) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(nowdate);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, -1);  //减1天
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}
	/**
	 * 获取上一天记录
	 * @param nowdate
	 * @return
	 */
	public static String betweenDate(String nowdate,int num) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(nowdate);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, num);  //减1天
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}
	 
	/**
	 * 比较三个日期大小
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1,String DATE2, String DATE3) { 
		
		int num = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		try { 
			Date dt1 = df.parse(DATE1); 
			Date dt2 = df.parse(DATE2); 
			Date dt3 = df.parse(DATE3); 
			if (dt1.getTime() <= dt3.getTime() && dt3.getTime() <= dt2.getTime() ) { 
				num=1; 
				/*} else if (dt1.getTime() <= dt2.getTime()) { 
	                System.out.println("dt1在dt2后"); 
	                return -1; */
			} else { 
				num=0; 
			} 
		} catch (Exception exception) { 
			exception.printStackTrace(); 
		} 
		return num; 
	} 
	/**
	 * 比较俩个日期大小
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	 public static int compare_date(String DATE1, String DATE2) { 
	       
	       int num = 0;
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	        try { 
	            Date dt1 = df.parse(DATE1); 
	            Date dt2 = df.parse(DATE2); 
	            if (dt1.getTime() >= dt2.getTime()) { 
	                num=1; 
	            /*} else if (dt1.getTime() <= dt2.getTime()) { 
	                System.out.println("dt1在dt2后"); 
	                return -1; */
	            } else { 
	                num=0; 
	            } 
	        } catch (Exception exception) { 
	            exception.printStackTrace(); 
	        } 
	        return num; 
	    } 

	/**  
	 * 取得当月天数  
	 * */  
	public static int getCurrentMonthLastDay()   
	{   
	    Calendar a = Calendar.getInstance();   
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天   
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天   
	    int maxDate = a.get(Calendar.DATE);   
	    return maxDate;   
	}   
	 //返回当前年月   带英文
   public static String getEYearMonth()   
   {   
	   Date date = new Date();   
	   DateFormat df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
		
	   String nowDate =df.format(date);

	   return nowDate; 
   }   
   //返回当前年月   带英文
   public static String zhEYearMonth(String dates)   
   {   
	   DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	   Date date=null;
	try {
		date = sf.parse(dates);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   DateFormat df = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
	   
	   String nowDate =df.format(date);
	   
	   return nowDate; 
   }   
   /**
    * 两个时间进行比较（s1>s2返回true）
    * @param s1
    * @param s2
    * @return boolean
    */
   public static boolean compTime(String s1,String s2){
		if (s1.indexOf(":")<0||s1.indexOf(":")<0) {
		//System.out.println("格式不正确");
		}else{
		String[]array1 = s1.split(":");
		int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60+Integer.valueOf(array1[2]);
		 
		String[]array2 = s2.split(":");
		int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60+Integer.valueOf(array2[2]);
		 
		return total1-total2>0?true:false;
		}
		return false;

		}


   /**
    * 是否是周末
    * @return 
    */
   public static String idWeek(String datetime) {

		final String dayNames[] = { "true", "星期一", "星期二", "星期三", "星期四", "星期五",
				"true" };

	 
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(datetime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		 return dayNames[dayOfWeek - 1];
		//return dayOfWeek-1+"";
	}
   /**
    * 根据输入日期获取下两天日期，周六周末不算
    * @param nowdate
    * @return
    */
	public static String getNextDate(String date) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(date);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			if(getWeek(date).equals("5")){
				cal.add(Calendar.DATE, +3);  //加3天
			}else if(getWeek(date).equals("6")){
				cal.add(Calendar.DATE, +2);  //加2天
			}else{
				cal.add(Calendar.DATE, +2);  //加2天
			}
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}  
   
   
   
   /**
    * 根据输入日期获取该日期下一周周一至周五的日期区间
    * @param nowdate
    * @return
    */
	public static String getNextWeekMondayToFridayDate(String date) { 
		String needDate = "";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1;
		Calendar cal2;
		try {
			Date  d = df.parse(date);     
			cal1 = Calendar.getInstance();
			cal1.setTime(d);
			cal2 = Calendar.getInstance();
			cal2.setTime(d);
			if(getWeek(date).equals("1")){
				cal1.add(Calendar.DATE, +7);  //加7天
				cal2.add(Calendar.DATE, +11);  //加11天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("2")){
				cal1.add(Calendar.DATE, +6);  //加6天
				cal2.add(Calendar.DATE, +10);  //加10天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());				
			}else if(getWeek(date).equals("3")){
				cal1.add(Calendar.DATE, +5);  //加5天
				cal2.add(Calendar.DATE, +9);  //加9天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("4")){
				cal1.add(Calendar.DATE, +4);  //加4天
				cal2.add(Calendar.DATE, +8);  //加8天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("5")){
				cal1.add(Calendar.DATE, +3);  //加3天
				cal2.add(Calendar.DATE, +7);  //加7天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("6")){
				cal1.add(Calendar.DATE, +2);  //加2天
				cal2.add(Calendar.DATE, +6);  //加6天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			} else {
				cal1.add(Calendar.DATE, +1);  //加1天
				cal2.add(Calendar.DATE, +5);  //加5天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}
		} catch (ParseException e) {
			return null;
		}
		return needDate;
	}   
	/**
	 * 根据输入日期获取最临近的周五至下一周的周四的日期区间
	 * @param nowdate
	 * @return
	 */
	public static String getNearFridayToNextWeekThursdayDate(String date) { 
		String needDate = "";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1;
		Calendar cal2;
		try {
			Date  d = df.parse(date);     
			cal1 = Calendar.getInstance();
			cal1.setTime(d);
			cal2 = Calendar.getInstance();
			cal2.setTime(d);
			if(getWeek(date).equals("1")){
				cal1.add(Calendar.DATE, +4);  //加4天
				cal2.add(Calendar.DATE, +10);  //加10天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("2")){
				cal1.add(Calendar.DATE, +3);  //加3天
				cal2.add(Calendar.DATE, +9);  //加9天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());				
			}else if(getWeek(date).equals("3")){
				cal1.add(Calendar.DATE, +2);  //加2天
				cal2.add(Calendar.DATE, +8);  //加8天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("4")){
				cal1.add(Calendar.DATE, +1);  //加1天
				cal2.add(Calendar.DATE, +7);  //加7天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("5")){
				cal1.add(Calendar.DATE, +7);  //加7天
				cal2.add(Calendar.DATE, +13);  //加13天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("6")){
				cal1.add(Calendar.DATE, +6);  //加6天
				cal2.add(Calendar.DATE, +12);  //加12天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			} else {
				cal1.add(Calendar.DATE, +5);  //加5天
				cal2.add(Calendar.DATE, +11);  //加11天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}
		} catch (ParseException e) {
			return null;
		}
		return needDate;
	}   
	/**
	 * 根据输入日期获取该日期本周周一至周五的日期区间
	 * @param nowdate
	 * @return
	 */
	public static String getThisWeekMondayToFridayDate(String date) { 
		String needDate = "";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1;
		Calendar cal2;
		try {
			Date  d = df.parse(date);     
			cal1 = Calendar.getInstance();
			cal1.setTime(d);
			cal2 = Calendar.getInstance();
			cal2.setTime(d);
			if(getWeek(date).equals("1")){
				cal1.add(Calendar.DATE, +0);  //加0天
				cal2.add(Calendar.DATE, +4);  //加4天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("2")){
				cal1.add(Calendar.DATE, -1);  //减1天
				cal2.add(Calendar.DATE, +3);  //加3天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());				
			}else if(getWeek(date).equals("3")){
				cal1.add(Calendar.DATE, -2);  //减2天
				cal2.add(Calendar.DATE, +2);  //加2天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("4")){
				cal1.add(Calendar.DATE, -3);  //减3天
				cal2.add(Calendar.DATE, +1);  //加1天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("5")){
				cal1.add(Calendar.DATE, -4);  //减4天
				cal2.add(Calendar.DATE, +0);  //加0天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("6")){
				cal1.add(Calendar.DATE, -5);  //减5天
				cal2.add(Calendar.DATE, -1);  //减1天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			} else {
				cal1.add(Calendar.DATE, -6);  //减6天
				cal2.add(Calendar.DATE, -2);  //减2天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}
		} catch (ParseException e) {
			return null;
		}
		return needDate;
	}   
	/**
	 * 根据输入日期获取该日期前一周周五至本周周四的日期区间
	 * @param nowdate
	 * @return
	 */
	public static String getBeforeWeekFridayToThisWeekThursdayDate(String date) { 
		String needDate = "";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1;
		Calendar cal2;
		try {
			Date  d = df.parse(date);     
			cal1 = Calendar.getInstance();
			cal1.setTime(d);
			cal2 = Calendar.getInstance();
			cal2.setTime(d);
			if(getWeek(date).equals("1")){
				cal1.add(Calendar.DATE, -3);  //减3天
				cal2.add(Calendar.DATE, +3);  //加3天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("2")){
				cal1.add(Calendar.DATE, -4);  //减4天
				cal2.add(Calendar.DATE, +2);  //加2天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());				
			}else if(getWeek(date).equals("3")){
				cal1.add(Calendar.DATE, -5);  //减5天
				cal2.add(Calendar.DATE, +1);  //加1天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("4")){
				cal1.add(Calendar.DATE, -6);  //减6天
				cal2.add(Calendar.DATE, +0);  //加0天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("5")){
				cal1.add(Calendar.DATE, -7);  //减7天
				cal2.add(Calendar.DATE, -1);  //减1天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}else if(getWeek(date).equals("6")){
				cal1.add(Calendar.DATE, -8);  //减8天
				cal2.add(Calendar.DATE, -2);  //减2天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			} else {
				cal1.add(Calendar.DATE, -9);  //减9天
				cal2.add(Calendar.DATE, -3);  //减3天
				needDate = df.format(cal1.getTime())+" to "+df.format(cal2.getTime());
			}
		} catch (ParseException e) {
			return null;
		}
		return needDate;
	}   
   
	/**
	 * 根据输入日期获取该日期下一周周一的日期
	 * @param nowdate
	 * @return
	 */
	public static String getNextWeekMondayDate(String date) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(date);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			if(getWeek(date).equals("1")){
				cal.add(Calendar.DATE, +7);  //加7天
			}else if(getWeek(date).equals("2")){
				cal.add(Calendar.DATE, +6);  //加6天
			}else if(getWeek(date).equals("3")){
				cal.add(Calendar.DATE, +5);  //加5天
			}else if(getWeek(date).equals("4")){
				cal.add(Calendar.DATE, +4);  //加4天
			}else if(getWeek(date).equals("5")){
				cal.add(Calendar.DATE, +3);  //加3天
			}else if(getWeek(date).equals("6")){
				cal.add(Calendar.DATE, +2);  //加2天
			} else {
				cal.add(Calendar.DATE, +1);  //加1天
			}
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}   
	/**
	 * 根据输入日期获取该日期最临近的一个周五日期
	 * @param nowdate
	 * @return
	 */
	public static String getNearFridayDate(String date) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(date);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			if(getWeek(date).equals("1")){
				cal.add(Calendar.DATE, +4);  //加4天
			}else if(getWeek(date).equals("2")){
				cal.add(Calendar.DATE, +3);  //加3天
			}else if(getWeek(date).equals("3")){
				cal.add(Calendar.DATE, +2);  //加2天
			}else if(getWeek(date).equals("4")){
				cal.add(Calendar.DATE, +1);  //加1天
			}else if(getWeek(date).equals("5")){
				cal.add(Calendar.DATE, +7);  //加7天
			}else if(getWeek(date).equals("6")){
				cal.add(Calendar.DATE, +6);  //加6天
			} else {
				cal.add(Calendar.DATE, +5);  //加5天
			}
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}   
	/**
	 * 根据输入日期获取该日期下一周周五的日期
	 * @param nowdate
	 * @return
	 */
	public static String getNextWeekFridayDate(String date) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(date);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			if(getWeek(date).equals("1")){
				cal.add(Calendar.DATE, +11);  //加11天
			}else if(getWeek(date).equals("2")){
				cal.add(Calendar.DATE, +10);  //加10天
			}else if(getWeek(date).equals("3")){
				cal.add(Calendar.DATE, +9);  //加9天
			}else if(getWeek(date).equals("4")){
				cal.add(Calendar.DATE, +8);  //加8天
			}else if(getWeek(date).equals("5")){
				cal.add(Calendar.DATE, +7);  //加7天
			}else if(getWeek(date).equals("6")){
				cal.add(Calendar.DATE, +6);  //加6天
			} else {
				cal.add(Calendar.DATE, +5);  //加5天
			}
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}   
	/**
	 * 根据输入日期获取该日期下一周的下一周的周四的日期
	 * @param nowdate
	 * @return
	 */
	public static String getNextNextWeekThursdayDate(String date) { 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal;
		try {
			Date  d = df.parse(date);     
			cal = Calendar.getInstance();
			cal.setTime(d);
			if(getWeek(date).equals("1")){
				cal.add(Calendar.DATE, +17);  //加17天
			}else if(getWeek(date).equals("2")){
				cal.add(Calendar.DATE, +16);  //加16天
			}else if(getWeek(date).equals("3")){
				cal.add(Calendar.DATE, +15);  //加15天
			}else if(getWeek(date).equals("4")){
				cal.add(Calendar.DATE, +14);  //加14天
			}else if(getWeek(date).equals("5")){
				cal.add(Calendar.DATE, +13);  //加13天
			}else if(getWeek(date).equals("6")){
				cal.add(Calendar.DATE, +12);  //加12天
			} else {
				cal.add(Calendar.DATE, +11);  //加11天
			}
		} catch (ParseException e) {
			return null;
		}
		return df.format(cal.getTime());
	}   
	
   
   
   /**
    * 根据输入日期返回当天星期几
    * @param datetime
    * @return
    */
   public static String getWeek(String datetime) {

		final String dayNames[] ={"0","1","2","3","4","5","6"} ;

	 
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();

		try {
			date = sdfInput.parse(datetime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		 return dayNames[dayOfWeek - 1];
	}
   /**
    * 是否是周末
    * @return 
    */
   public static int isWeekOfnumber(String datetime) {
	   SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
	   
	   Calendar calendar = Calendar.getInstance();
	   Date date = new Date();
	   try {
		   date = sdfInput.parse(datetime);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   
	   calendar.setTime(date);
	   int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	   //return dayNames[dayOfWeek - 1];
	   return dayOfWeek-1;
   }
	 /**
	  * 得到指定月的天数
	  * */
	 public static int getMonthLastDay(int year, int month)
	 {
	  Calendar a = Calendar.getInstance();
	  a.set(Calendar.YEAR, year);
	  a.set(Calendar.MONTH, month - 1);
	  a.set(Calendar.DATE, 1);//把日期设置为当月第一天
	  a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
	  int maxDate = a.get(Calendar.DATE);
	  return maxDate;
	 } 
	 /**
	  * 得到指定月的上一个月的天数
	  * */
	 public static int getMaxDay(int year, int month)
	 {
	  Calendar a = Calendar.getInstance();
	  if(month==1){
		  a.set(Calendar.YEAR,year-1);
		  a.set(Calendar.MONTH, month-1+12);
	  }else{
	  a.set(Calendar.YEAR, year);
	  a.set(Calendar.MONTH, month - 2);
	  }
	  a.set(Calendar.DATE, 1);//把日期设置为当月第一天
	  a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
	  int maxDate = a.get(Calendar.DATE);
	  return maxDate;
	 } 

    //返回当前年月日   
   public static String getNowDate()   
    {   
        Date date = new Date();   
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);   
        return nowDate;   
    }   
 //返回当前年月日   yyyy-MM-dd HH:mm:ss
   public static String getNowDateTime()   
    {   
        Date date = new Date();   
        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);   
        return nowDate;   
    }   
   //返回当前年月日   yyyy-MM-dd
   public static String getDateToday()   
   {   
	   Date date = new Date();   
	   String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(date);   
	   return nowDate;   
   }   
  
   //返回当前年月   
   public static String getYearMonth()   
   {   
	   Date date = new Date();   
	   String nowDate = new SimpleDateFormat("yyyy-MM").format(date);   
	   return nowDate; 
   }   
    //返回当前年份   
   public static  int getYear()   
    {   
        Date date = new Date();   
        String year = new SimpleDateFormat("yyyy").format(date);   
        return Integer.parseInt(year);   
    }   
    //返回当前月份   
    public static int getMonth()   
    {   
        Date date = new Date();   
        String month = new SimpleDateFormat("MM").format(date);   
        return Integer.parseInt(month);   
    }   
    public static String getMonth_eng()   
    {   
        Date date = new Date();   
        String month = new SimpleDateFormat("MMM",Locale.ENGLISH).format(date);   
        return month;   
    }  
    public static String getBeforeMonth_eng()   
    {   
    	Date d = new Date();   
    	Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, -1);  //减1个月
    	String month = new SimpleDateFormat("MMM",Locale.ENGLISH).format(cal.getTime());   
    	return month;   
    }  
    
    //返回当前时分  HH:mm
    public static String getNowHHmm(){   
         Date date = new Date();   
         String nowDate = new SimpleDateFormat("HH:mm").format(date);   
         return nowDate;   
    } 
   
    
    //返回当前年份   
	public static  int getYear(String years)   
    {   
    	String year ="";
  	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	        try {
				Date dt1 = df.parse(years);  
				year = new SimpleDateFormat("yyyy").format(dt1);   
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}   
			return Integer.parseInt(year);
    }   
    //返回当前月份   
	public static int getMonth(String months)   
    {   
    	String month ="";
    	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	        try {
				Date dt1 = df.parse(months);  
				 month = new SimpleDateFormat("MM").format(dt1);   
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}   
			return Integer.parseInt(month);
    }   
    //返回当前天   
    public static int getDay()   
    {   
    	Date date = new Date();   
    	String month = new SimpleDateFormat("dd").format(date);   
    	return Integer.parseInt(month);   
    }   
    //返回当前时间-----小时
    public static int getHour()   
    {   
    	Date date = new Date();   
    	String month = new SimpleDateFormat("HH").format(date);   
    	return Integer.parseInt(month);   
    }   
    //判断闰年   
    static boolean isLeap(int year)   
    {   
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))   
            return true;   
        else  
            return false;   
    }   
  
    //返回当月天数   
  public static int getDays(int year, int month)   
    {   
        int days;   
        int FebDay = 28;   
        if (isLeap(year))   
            FebDay = 29;   
        switch (month)   
        {   
            case 1:   
            case 3:   
            case 5:   
            case 7:   
            case 8:   
            case 10:   
            case 12:   
                days = 31;   
                break;   
            case 4:   
            case 6:   
            case 9:   
            case 11:   
                days = 30;   
                break;   
            case 2:   
                days = FebDay;   
                break;   
            default:   
                days = 0;   
                break;   
        }   
        return days;   
    }   
  
    //返回当月星期天数   
    int getSundays(int year, int month)   
    {   
        int sundays = 0;   
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");   
        Calendar setDate = Calendar.getInstance();   
        //从第一天开始   
        int day;   
        for (day = 1; day <= getDays(year, month); day++)   
        {   
            setDate.set(Calendar.DATE, day);   
            String str = sdf.format(setDate.getTime());   
            if (str.equals("星期日"))   
            {   
                sundays++;   
            }   
        }   
        return sundays;   
    }   
    /**
     * 根据时间获取小时和分钟
     * @param date
     * @return
     */
  public static  String getHourAndSecond(String date){
    	String time ="";
  	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	try{
    		time=new  SimpleDateFormat("HH:mm").format(df.parse(date));
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    	return time;
    }
  
	public static int  checkdate(String checkValue) {
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(checkValue);
		boolean b = m.matches();
		if (b) {
			//System.out.println("格式正确");
			return 1;
		} else {
			System.out.println("格式错误 "+checkValue);
			return 0;
		}
	}
	
	/**
	 * “yyyy-M-d ”转换为格式 yyyy-MM-dd”
	 * 
	 * @param viewtime
	 * @return
	 */
	public static String strChToCH(String viewtime) {
		try {
			Date time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			time = sdf.parse(viewtime);
			viewtime = sdf.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewtime;
	}
	
	/**
	 * 两个日期相减，返回天数
	 * @param begin_date
	 * @param end_date
	 * @return
	 */
	 public static long getTwoDay(String begin_date, String end_date) {
	  long day = 0;
	  try {
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   String sdate = format.format(Calendar.getInstance().getTime());
		   Date dt1 = format.parse(begin_date); 
		   Date dt2 = format.parse(end_date); 
		   if (dt1 == null) {
			   dt1 = format.parse(sdate);
		   }
		   if (end_date == null) {
			   dt2 = format.parse(sdate);
		   }
		   day = (dt2.getTime() - dt1.getTime())
		     / (24 * 60 * 60 * 1000);
	  } catch (Exception e) {
	   return -1;
	  }
	  return day;
	 }
	 
	 
}  
 
