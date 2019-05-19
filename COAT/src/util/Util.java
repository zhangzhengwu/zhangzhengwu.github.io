package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.PrintException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import antlr.Utils;




public class Util {
	
	static Logger log=Logger.getLogger(Util.class);
	
	/**
	 * 输出Logger和Context内容
	 * @author kingxu
	 * @date 2015-10-19
	 * @param logger
	 * @return void
	 */
	public static void printContext_Logger(Logger logger,ServletContext context,String value){
		logger.info(value);
		context.log(value);
		System.out.println(value);
	}
	/**
	 * 输出Logger内容
	 * @author kingxu
	 * @date 2015-10-19
	 * @param logger
	 * @param context
	 * @return void
	 */
	public static void printLogger(Logger logger,String value){
		logger.info(value);
		System.out.println(value);
	}
	
	/**
	 * 数组去重并返回新的数组
	 * @param num
	 * @return
	 */
	 public static String[] getDistinct(String num[]) {
         List<String> list = new ArrayList<String>();
         for (int i = 0; i < num.length; i++) {
             if (!list.contains(num[i])) {//如果list数组不包括num[i]中的值的话，就返回true。
                 list.add(num[i]); //在list数组中加入num[i]的值。已经过滤过。
             }
         }
         return list.toArray(new String[list.size()]);
         
	 }
	
	
	 final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,    
         99999999, 999999999, Integer.MAX_VALUE };    
 static int sizeOfInt(int x) {    
     for (int i = 0;; i++)    
         if (x <= sizeTable[i])    
             return i + 1;    
 }    
 public static void main(String[] args) {    
/*     System.out.println(sizeOfInt(1234));    
     System.out.println(sizeOfInt(123));    
     System.out.println(sizeOfInt(12345));    
     System.out.println(sizeOfInt(12));    
	 System.out.println(excelColIndexToStr(27));*/
	 System.out.println(getDatetime(DateUtils.getNowDateTime()));
	 
 }    
	
	
	/**
	 * 服务器获取客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {   
		String ip = request.getHeader("X-Forwarded-For");   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("Proxy-Client-IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("WL-Proxy-Client-IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("HTTP_CLIENT_IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getRemoteAddr();   
		}   
		return ip;   
	} 

	public static void IteratorRequest(HttpServletRequest request){
		Enumeration enu=request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();
			System.out.println(paraName+": "+request.getParameter(paraName));  
		}  
	}
	/**
	 * 根据字符串拼接like字符串值
	 * @author kingxu
	 * @date 2015-10-13
	 * @param value
	 * @return
	 * @return String
	 */
	public static String modifyString(String value){
		String v="";
		if(objIsNULL(value)){
			v="%%";
		}else{
			v="%"+value+"%";
		}
		return v;
	}
	/**
	 * 开始日期为空初始化
	 * @author kingxu
	 * @date 2015-10-13
	 * @param value
	 * @return
	 * @return String
	 */
	public static String modifystartdate(String value){
		String v="";
		if(objIsNULL(value)){
			v="1900-01-01";
		}else{
			v=value;
		}
		return v;
	}
	/**
	 * 结束日期为空初始化
	 * @author kingxu
	 * @date 2015-10-13
	 * @param value
	 * @return
	 * @return String
	 */
	public static String modifyenddate(String value){
		String v="";
		if(objIsNULL(value)){
			v="2099-12-31";
		}else{
			v=value;
		}
		return v;
	}
	
	
	public static String getRequestParameter(HttpServletRequest request){
		Enumeration enu=request.getParameterNames();  
		String r="";
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement();
			if(Util.objIsNULL(r)){
				r+="{"+paraName+":\""+(paraName.equals("password")?"******":request.getParameter(paraName))+"\"";
			}else{
				r+=","+paraName+":\""+(paraName.equals("password")?"******":request.getParameter(paraName))+"\"";
			}

		}  
		if(!Util.objIsNULL(r)){
			r+="}";
		}
		return r;
	}

	public static String html(String content) { 

		if(content==null)
			return "";         
		String html = content; 
		html = StringUtils.replace(html, "'", "&qpos;"); 
		html = StringUtils.replace(html, "\"", "&quot;");
		html = StringUtils.replace(html, "/", "&frasl;");
		html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格 
		//html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格 
		html = StringUtils.replace(html, "<", "&lt;"); 
		html = StringUtils.replace(html, ">", "&gt;"); 
		return html; 


	} 

	/**
	 * 输出页面异常
	 * @author kingxu
	 * @date 2015-9-9
	 * @param response
	 * @param msg
	 * @return void
	 */
	public static void outExcetion(HttpServletResponse response,String msg){
		PrintWriter out=null;
		try{
			out=response.getWriter();
			out.print(msg);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
	}


	/**
	 * 过滤不需要的数据
	 * 
	 * @param filterName
	 * @return
	 */
	public static JsonConfig filter(final String[] filterName) {

		JsonConfig cfg = new JsonConfig();

		cfg.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				boolean b = false;
				for (String fn : filterName) {
					if (name.equals(fn)) {// 过滤不想要的数据
						b = true;
					}
				}
				return b;
			}
		});
		return cfg;
	}


	/**
	 * 初始化　page信息
	 * @param request
	 * @return
	 */
	public static Pager pageInfo(HttpServletRequest request){
		Pager page=new Pager();
		String pagenow=request.getParameter("pagenow");
		String pagesize=request.getParameter("pagesize");
		page.setPagenow(Util.objIsNULL(pagenow)?1:Integer.parseInt(pagenow));
		page.setPagesize(Util.objIsNULL(pagesize)?15:Integer.parseInt(pagesize));
		return page; 
	}

	public static String getMsgJosn(String state,String msg){
		String rsp = "[{\"msg\": \""+msg+"\" , \"state\": \""+state+"\" }]";
		return rsp;
	}
	public static String getMsgJosnObject(String state,String msg){
		JSONObject json=new JSONObject();
		json.put("state", state);
		json.put("msg", msg);
		json.put("data", "");
		return json.toString();
	}
	public static String getMsgJosnObject(String state,String msg,Object obj){
		JSONObject json=new JSONObject();
		json.put("state", state);
		json.put("msg", msg);
		json.put("data", obj);
		return json.toString();
	}
	public static JSONObject getJosnObject(String state,String msg,Object obj){
		JSONObject json=new JSONObject();
		json.put("state", state);
		json.put("msg", msg);
		json.put("data", obj);
		return json;
	}
	public static JSONObject getJosnObject(String state,String msg){
		JSONObject json=new JSONObject();
		json.put("state", state);
		json.put("msg", msg);
		json.put("data", "");
		return json;
	}
	public static JSONObject getJosnObject_success(){
		return getJosnObject("success","success");
	}
	public static JSONObject getJosnSuccessReturn(Object obj){
		return getJosnObject("success","success",obj);
	}
	public static JSONObject getJosnErrorReturn(String obj){
		return getJosnObject("error",obj,"");
	}
	public static JSONObject getJosnObject_error(){
		return getJosnObject("error","error","");
	}
	
	public static String getMsgJosnObject_success(){
		return getMsgJosnObject("success","success");
	}
	public static String getMsgJosnSuccessReturn(Object obj){
		return getMsgJosnObject("success","success",obj);
	}
	
	public static String getMsgJosnErrorReturn(String obj){
		return getMsgJosnObject("error",obj,"");
	}
	
	public static String getMsgJonfornum(int num){
		if(!Util.objIsNULL(num)&&num>0){
			return getMsgJosnObject("success","success");
		}else{
			return getMsgJosnObject("error","error");
		}
	}
	
	public static String getMsgJosnObject_error(){
		String rsp = "{\"msg\": \"error\" , \"state\": \"error\" , \"data\": \"\" }";
		return rsp;
	}

	public static String jointException(Exception e){
		e.printStackTrace();
		JSONObject json=new JSONObject();
		json.put("state", "exception");
		json.put("msg", e.getMessage());
		json.put("data", e.getMessage());
		
		//String rsp = "{\"msg\": \""+e.getMessage().replaceAll("[", "【").replaceAll("]", "】")+"\" , \"state\": \"exception\" }";
		return json.toString();
	}

	public static String  writeHtml(String value){
		StringBuffer html=new StringBuffer("");
		html.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		html.append("<HTML>");
		html.append("<HEAD><TITLE>A Servlet</TITLE></HEAD>");
		html.append("<BODY>");
		html.append(value);
		html.append("</BODY>");
		html.append("</HTML>");
		return html.toString();
	}
	/**
	 * 下载服务器文件到浏览器
	 * @author kingxu
	 * @date 2015-8-20
	 * @param path
	 * @param orgname
	 * @param response
	 * @return
	 * @return HttpServletResponse
	 * @throws IOException 
	 */
	public static  HttpServletResponse download(String path, String orgname, HttpServletResponse response) throws IOException {
		OutputStream toClient=null;
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			//String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(!Util.objIsNULL(orgname)?(URLEncoder.encode(orgname,"utf-8")).getBytes():URLEncoder.encode(filename,"utf-8").getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			
		}catch (IOException ex) {
			ex.printStackTrace();
			response.getWriter().print(ex.getMessage());
			response.getWriter().flush();
			response.getWriter().close();
		}finally{
			if(!Util.objIsNULL(toClient)){
				toClient.flush();
				toClient.close();
			}
		}
		return response;
	}

	public static void printException(Exception e){
		StackTraceElement[] messages=e.getStackTrace();
		StringBuffer str = new StringBuffer();
		int length = messages.length;
		for (int i = 0; i < length; i++) {
			str.append("\r\n" + "    Link:" + messages[i].toString());
			str.append("\r\n" + "    ClassName:" + messages[i].getClassName());
			str.append("\r\n" + "    FileName:" + messages[i].getFileName());
			str.append("\r\n" + "    MethodName:" + messages[i].getMethodName());
			str.append("\r\n" + "    LineNumber:" + messages[i].getLineNumber());
			str.append("\r\n" + "    Message:" + e.getMessage());
			str.append("\r\n" + "    Cause:" + e.getCause());
			//System.out.println(Arrays.toString(messages));
			
			log.error(str);
		}
		System.out.println(Arrays.toString(messages));
		System.out.println(e.getClass().getSimpleName());
		System.out.println(str);
	}

	/**
	 * 拼接异常
	 * @param e 具体异常项
	 * @return
	 */
	public static String joinException(Exception e){
		if(Util.getProValue("public.system.uat").equals("true")){
			e.printStackTrace();
			//printException(e);
		}
		return getMsgJosnObject("exception", e.getMessage()+"");
	}
	/**
	 * 根据返回值自动判断是否成功
	 * @param num
	 * @return
	 */
	public static String returnValue(int num){
		if(num>0){
			return getMsgJosnObject("success", "success");
		}else{
			return getMsgJosnObject("error", "error");
		}
	}


	/**
	 * 根据数组批量获取内容
	 * @author kingxu
	 * @date 2015-8-4
	 * @param name
	 * @return
	 * @return String[]
	 */
	public static String[] getProValue(String[] name){
		String[] value=new String[name.length];
		try{
			if(objIsNULL(pro)){
				Properties p = new Properties();  
				InputStream in = SendMail.class.getClassLoader().getResourceAsStream("configure.properties");
				p.load(in);
				pro=new HashMap<String, String>((Map)p);
				/*for(Object o:p.keySet()){
						System.out.println(o+"---"+p.get(o));
					}*/
				for(int i=0;i<name.length;i++){
					value[i]=p.getProperty(name[i], "");
				}
				in.close();
				
			}else{
				//System.out.println("配置文件读取缓存("+name+")");
				for(int i=0;i<name.length;i++){
					value[i]=pro.get(name[i]);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 配置文件缓存map
	 */
	static Map<String,String> pro;
	/**
	 * 根据名称在配置文件中获取内容
	 * @author kingxu
	 * @date 2015-8-4
	 * @param name
	 * @return
	 * @return String
	 */
	public static String getProValue(String name){
		String value="";
		try{
			if(objIsNULL(pro)){
				Properties p = new Properties();  
				InputStream in = SendMail.class.getClassLoader().getResourceAsStream("configure.properties");
				p.load(in);
				pro=new HashMap<String, String>((Map)p);
				/*for(Object o:p.keySet()){
						System.out.println(o+"---"+p.get(o));
					}*/
				if(p.size()>0){
					value=p.getProperty(name, "");
				}else{
					System.out.println("属性名-->"+name);
				}
				in.close();
			}else{
			//	System.out.println("配置文件读取缓存("+name+")");
				value=pro.get(name);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return value;
	}
 

	/**
	 * 去掉字符串 所有空格
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	/*catch (IllegalArgumentException e) {
    }
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchMethodException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	/*** 
	 * 遍历实体类的属性和数据类型以及属性值 
	 *  
	 * @author King.Xu 
	 * @date 2013-09-26 上午10:25:02 
	 * @company  
	 * @version v1.3 
	 * @see 相关类 
	 * @since 相关/版本 
	 */  
	public static String reflectTest(Object model) {  
		try{
			// 获取实体类的所有属性，返回Field数组  
			Field[] field = model.getClass().getDeclaredFields();  
			//以字符串形式返回式
			StringBuffer entity=new StringBuffer("");
			// 遍历所有属性  
			for (int j = 0; j < field.length; j++) {  
				try{// 获取属性的名字  
					String name = field[j].getName();  
					// 将属性的首字符大写，方便构造get，set方法  
					entity.append("["+name+":");
					name = name.substring(0, 1).toUpperCase() + name.substring(1);  
					// 获取属性的类型  
					String type = field[j].getGenericType().toString();  
					// 如果type是类类型，则前面包含"class "，后面跟类名  
					// System.out.println("属性为：" + name);
					
					if(type.equals("class java.lang.Short")||type.equals("class java.lang.String")||type.equals("int")||type.equals("class java.lang.Integer")||type.equals("class java.lang.Double")||type.equals("class java.lang.Boolean"))
						entity.append(model.getClass().getMethod("get"+ name).invoke(model)+"]");
					else
						entity.append("null]");
				}catch(Exception e){
					entity.append("null]");
				}
			}  
			return entity.toString();
		} finally{

		}

	}  

	/**
	 * OBJ是否为null或者为“ “
	 * 
	 * @param obj
	 * @return null或者为“ “:TRUE
	 */
	public static boolean objIsNULL(Object obj) {
		if (obj == null)
			return true;
		if (obj.toString().trim().length() == 0)
			return true;
		if (obj.toString().toLowerCase().equals("null"))
			return true;
		if (obj.toString().toLowerCase().equals(" "))
			return true;
		if (obj.toString().toLowerCase().equals(""))
			return true;
		return false;
	}
	/**
	 * 根据输入的date获取yyyy-MM-dd HH:mm:ss.SSS格式的时间 
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String getDatetime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		if(date == null)
			date = java.util.Calendar.getInstance().getTime();
		String strDate = formatter.format(date);
		return strDate;
	}
	/**
	 * 根据输入的date字符串获取Date
	 * 
	 * @param strdate
	 *            String
	 * @return Date
	 */
	public static Date getDatetime(String strdate) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date date;
		try {
			date = formatter.parse(strdate);
		} catch (ParseException e) {
			date = java.util.Calendar.getInstance().getTime();
		}
		return date;
	}
	public static String timeStart = " 00:00:00.000";
	public static String timeEnd = " 23:59:59.999";
	/**
	 * 将java.util.Date类型的对象转换为java.sql.Timestamp类型 
	 * 
	 * @param dt
	 *            Date
	 * @return Timestamp
	 */
	public static Timestamp convertSqlDate(Date dt) {
		if (dt == null) {
			return new java.sql.Timestamp(0);
		}
		return new java.sql.Timestamp(dt.getTime());
	}
	/**
	 * 以TimeStamp格式返回当前的系统时间
	 * 
	 * @return Timestamp add by liuwanfu
	 */

	public static Timestamp getCurrentTimeStamp() {
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		return tm;
	}
	/**
	 * 将yyyy-mm-dd格式的ｄａｔｅ以Timestamp格式返回
	 * 
	 * @param datestr
	 *            String
	 * @return Timestamp
	 */
	public static Timestamp getTimestampFromDateStr(String datestr) {
		return convertSqlDate(getDatetime(datestr));
	}
	/**
	 * StrToDate :字符串转化成时间类型（字符串可以是任意类型，只要是时间类型）
	 *
	 * @param time String 输入的日�?
	 * @return Date 返回的日�?
	 * @throws ParseException 转换失败
	 */
	public static Date StrToDate(String time) throws ParseException {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date d = sdf.parse(time);
		return d;
	}
	/**
	 * 按照给定格式返回代表日期的字符串
	 *
	 * @param pDate
	 *          Date
	 * @param format
	 *          String 日期格式
	 * @return String 代表日期的字符串
	 */
	public static String formatDate(java.util.Date pDate, String format) {
		if (pDate == null) {
			pDate = new java.util.Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(pDate);
	}
	public static String convoy="administrator_convoy";
	/**
	 * 获得给定时间的第二天零时的日期对�? 例如：若给定时间为（2004-08-01 11:30:58），将获得（2004-08-02
	 * 00:00:00）的日期对象 若给定时间为�?2004-08-31 11:30:58），将获得（2004-09-01 00:00:00）的日期对象
	 *
	 * @param dt
	 *          Date 给定的java.util.Date对象
	 * @return Date java.util.Date对象
	 */
	public static Date getNextDay(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return new GregorianCalendar(cal.get(Calendar.YEAR), cal
				.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH) + 1).
				getTime();
	}
	/**
	 * 获得给定时间的前�?天零时的日期对象 例如：若给定时间为（2004-08-02 11:30:58），将获得（2004-08-01
	 * 00:00:00）的日期对象 若给定时间为�?2004-09-01 11:30:58），将获得（2004-08-31 00:00:00）的日期对象
	 *
	 * @param dt
	 *          Date 给定的java.util.Date对象
	 * @return Date java.util.Date对象
	 */
	public static Date getLastDay(Date dt){
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);

		return new GregorianCalendar(cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH) - 1).
				getTime();
	}
	/**
	 * 根据输入的date获取HH:mm格式的时间
	 * 
	 * @param date
	 * @return String
	 */
	public static String getHourAndMinute(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"HH:mm");
		if(date == null)
			date = java.util.Calendar.getInstance().getTime();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * Date日期转换为不含时分秒的字符串
	 * @param time 日期
	 * @return 不包含时分秒日期字符
	 */
	public static String dateToStrWithoutHm(Date time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(time == null)
			time = java.util.Calendar.getInstance().getTime();
		String strDate = formatter.format(time);
		return strDate;
	}
	/**
	 * 字符串转换为含时分秒的日期
	 * @param time 日期字符串，包含时分秒
	 * @return 包含时分秒的Date型的日期
	 */
	public static Date strToDateWithHm(String time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(time);
		} catch (ParseException e) {
			date = java.util.Calendar.getInstance().getTime();
		}
		return date;
	}
	public static void deltables(String tables) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from "+tables;
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			int num = ps.executeUpdate();

			System.out.println("============="+tables+"=============" + num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
	}
	public static void deltableforPA(String tables) {
		Connection con = null;
		PreparedStatement ps = null;
		//String sql = "delete from "+tables+" where grade!='PA'";
		String sql = "delete from "+tables;
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			int num = ps.executeUpdate();
			System.out.println("============="+tables+"==============" + num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
	}
	public static void nq_addition() {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from nq_additional where remarks='SA' or remarks='PA' or remarks='CA' where year(now())=year(add_date)";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			int num = ps.executeUpdate();

			System.out.println("=======delete======nq_additional===='SA'===='PA'===='CA'=====" + num);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
	}
	public static int findnq_addition(String staffcode) {
		Connection con = null;
		PreparedStatement ps = null;
		int num=-1;
		String sql = "select count(*) as Num from nq_additional where sfyx='Y' and remarks='New Comer' and additional=100 and initials='"+staffcode+"'";
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				num=rs.getInt("Num");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	}
	//把Excel表格中的数据转换成String
	public static String cellToString(HSSFCell cell) { 
		String cellvalue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 如果当前cell的type为date类型
					if(cell.getDateCellValue().equals("")){
						cellvalue="";
					}else{
						SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
						cellvalue = formater.format(cell.getDateCellValue());


					}
				} else {//  当前cell的type为数值类型
					double num = cell.getNumericCellValue();
					DecimalFormat formatter = new java.text.DecimalFormat("########.########");
					cellvalue = formatter.format(num);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:// 如果当前cell的type为字符串
				if((cell.getStringCellValue())!=" "){
					cellvalue = cell.getStringCellValue().trim().replace("'","''");
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN://布尔类型
				cellvalue=cell.getBooleanCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				//cellvalue=String.valueOf(cell.getNumericCellValue());
				try {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					cellvalue = String.valueOf(cell.getRichStringCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				cellvalue=cell.getStringCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_ERROR://非法数据
				cellvalue=cell.getErrorCellValue()+"";
				break;
			default:// 既不为data类型也不为String
				//cellvalue = "";
				cellvalue =  String.valueOf(cell.getRichStringCellValue());
			}
		} else {// cell单元格对象为其他
			cellvalue="";
		}
		return cellvalue;
	}
	//把Excel表格中的数据转换成String
	public static String cellToBVCASEString(HSSFCell cell) { 
		String cellvalue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 如果当前cell的type为date类型
					if(cell.getDateCellValue().equals("")){
						cellvalue="";
					}else{
						SimpleDateFormat formater = new SimpleDateFormat(
								"yyyy-MM-dd");
						cellvalue = formater.format(cell.getDateCellValue());
					}
				} else {//  当前cell的type为数值类型
					double num = cell.getNumericCellValue();
					DecimalFormat formatter = new java.text.DecimalFormat("#.###");
					cellvalue = formatter.format(num);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:// 如果当前cell的type为字符串
				if((cell.getStringCellValue())!=" "){
					cellvalue = cell.getStringCellValue().trim().replace("'","''");
				}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN://布尔类型
				cellvalue=cell.getBooleanCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellvalue=cell.getCellFormula()+"";
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				cellvalue=cell.getStringCellValue()+"";
				break;
			case HSSFCell.CELL_TYPE_ERROR://非法数据
				cellvalue=cell.getErrorCellValue()+"";
				break;
			default:// 既不为data类型也不为String
				cellvalue = "";
			}
		} else {// cell单元格对象为其他
			cellvalue="";
		}
		return cellvalue;
	}

	/**
	 * 格式化XML报文
	 * @param str
	 * @return
	 * @author 沈威
	 */
	/* public static String format(String str){  
		  SAXReader reader = new SAXReader();  
		  // 注释：创建一个串的字符输入流  
		  StringReader in = new StringReader(str);  
		  Document doc = null;
		  try {
				doc = reader.read(in);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}  
		  // System.out.println(doc.getRootElement());  
		  // 注释：创建输出格�?  
		  OutputFormat formater = OutputFormat.createPrettyPrint();  
		  //formater=OutputFormat.createCompactFormat();  
		  // 注释：设置xml的输出编�?  
		  formater.setEncoding("utf-8");  
		  // 注释：创建输�?(目标)  
		  StringWriter out = new StringWriter();  
		  // 注释：创建输出流  
		  XMLWriter writer = new XMLWriter(out, formater);  
		  // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中�??  
		  try {
			writer.write(doc);  
			  writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		  // 注释：返回我们格式化后的结果  
		  return out.toString();  
	  }  
	 */


	/**
	 * 获取当前系统日期（格式自定义）
	 */
	public static String getDate(String  str){
		SimpleDateFormat sdf=new SimpleDateFormat(str);
		String date=sdf.format(new Date());
		return date;
	}
	

    /**
     * Excel column index begin 1
     * @param colStr
     * @param length
     * @return
     */
    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * Excel column index begin 1
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

}
