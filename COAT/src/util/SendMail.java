package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;




public class SendMail {

	public static void mainsss(String[] args) {

	}

	public static void main(String[] args) {
		
		try{
			/*String body="Dear Sir/Madam,<br/><br/>"
					+"         Please Be advised that the following seat arrangement will be effective from next Monday.<br/><br/>"
					+"<style type='text/css'>#xx{border-collapse:collapse;border:1px solid #999; width:800px;}#xx tr td{border:1px solid #999;text-align:center;width:16%;vertical-align: middle;height:20px;}#first td{height:25px;vertical-align: middle;}</style>"
					+"        <table id='xx' cellpadding=0 cellspacing=0 >"
					+"<tr id='first' style='background-color:black;font-color:white;'><td >Staff Code</td><td >Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
					+"<tr><td>staffcode</td><td>staffname</td><td>seatno</td><td>pigenBoxno</td><td>extno</td><td>floor location</td></tr></table><br/><br/>"
					+"ADM will notify you by email again for key collection";
			//String result=getProValue("public.email.head.img");
			String result=send("Test", "wilson.shen@convoy.com.hk", null, null, null, body, null, "email.ftl", "Best Regards,<br/>Administration<br/>Operations Department");
			System.out.println(result);*/
			//String body="xxx";
			//String result=send("xx", "king.xu@convoychina.com", body, "CAD_BJBS", null, "asdg");
			System.out.println("==============");
			Map<String, String> map=new HashMap<String, String>();
			map.put("to", "king.xu@convoy.com.hk;toby.huang@convoychina.com");
			map.put("subject", "降级提示");
			map.put("body", "this is test mail content.");
			map.put("attr", "http://192.168.225.39:8080/mailReport/120160908050233/生日表.xls");
			//map.put("webapp", "YjQ4MTJiOWIzNTUxMTJhNzA3NTk3NDdhMzQ5ZDVmNTY=");
			map.put("webapp", "OIS");
	 
			String result=HttpUtil.post("http://szosvr.convoy.com.hk/ExchangeMail/SendMailServlet", map);
			
			System.out.println(result);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{

		}
	}
	/**
	 * 邮件发送(没有抄送人和密送人和附件和appid和模板和签名)
	 * @author kingxu
	 * @date 2015-8-12
	 * @param subject
	 * @param to
	 * @param contont
	 * @param ftl
	 * @param sign
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @return String
	 */
	public static String send(String subject,String to,String contont) throws MalformedURLException, IOException{
		return send(subject, to, null, null, null, contont, null, null, null);
	}
	/**
	 * 邮件发送(没有抄送人和密送人和附件和appid)
	 * @author kingxu
	 * @date 2015-8-12
	 * @param subject
	 * @param to
	 * @param contont
	 * @param ftl
	 * @param sign
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @return String
	 */
	public static String send(String subject,String to,String contont,String ftl,String sign) throws MalformedURLException, IOException{
		return send(subject, to, null, null, null, contont, null, ftl, sign);
	}

	/**
	 * 邮件发送(没有抄送人和密送人和附件)
	 * @author kingxu
	 * @date 2015-8-5
	 * @param subject 邮件标题
	 * @param to 收件人 分号分割多个收件人
	 * @param contont 邮件内容，支持HTML内容
	 * @param webappId 邮件服务器appid
	 * @param ftl 邮件内容模板
	 * @param sign 邮件签名
	 * @return 返回json对象 [{"state":"","msg",""}]
	 * @throws MalformedURLException
	 * @throws IOException
	 * @return String
	 */
	public static String send(String subject,String to,String contont,String webappId,String ftl,String sign) throws MalformedURLException, IOException{
		return send(subject, to, null, null, null, contont, webappId, ftl, sign);
	}

	/**
	 * 邮件发送(没有抄送人和密送人)
	 * @author kingxu
	 * @date 2015-8-5
	 * @param subject 邮件标题
	 * @param to 收件人 分号分割多个收件人
	 * @param attr 附件地址,分号分割多个附件地址
	 * @param contont 邮件内容，支持HTML内容
	 * @param webappId 邮件服务器appid
	 * @param ftl 邮件内容模板
	 * @param sign 邮件签名
	 * @return 返回json对象 [{"state":"","msg",""}]
	 * @throws MalformedURLException
	 * @throws IOException
	 * @return String
	 */
	public static String send(String subject,String to,String attr,String contont,String webappId,String ftl,String sign) throws MalformedURLException, IOException{
		return send(subject, to, null, null, attr, contont, webappId, ftl, sign);
	}

	/**
	 * 邮件发送
	 * @author kingxu
	 * @date 2015-8-4
	 * @param subject 邮件标题
	 * @param to 收件人 分号分割多个收件人
	 * @param cc 抄送人 分号分割多个抄送人
	 * @param bc 暗送人 分号分割多个暗送人
	 * @param attr 附件地址,分号分割多个附件地址
	 * @param contont 邮件内容，支持HTML内容
	 * @param webappId 邮件服务器appid
	 * @param ftl 邮件内容模板
	 * @param sign 邮件签名
	 * @return 返回json对象 [{"state":"","msg",""}]
	 * @throws MalformedURLException
	 * @throws IOException
	 * @return String
	 * @author kingxu
	 * 2015-06-03 16:18:57
	 */
	public static String send(String subject,String to,String cc,String bc,String attr,String contont,String webappId,String ftl,String sign) throws MalformedURLException, IOException{
		String result="";
		try{
			Map<String,Object> maps=new HashMap<String, Object>();
			maps.put("title", "Email Content");
			maps.put("body", contont);
			maps.put("sign", Util.objIsNULL(sign)?"":sign);
			Map<String,String> map=new HashMap<String, String>();
			map.put("subject", subject+(getProValue("public.system.uat").equalsIgnoreCase("true")?("---Test--to["+to+"]cc["+cc+"]bc["+bc+"]"):""));
			map.put("to", getProValue("public.system.uat").equalsIgnoreCase("true")?getProValue("public.email.uat.address"):to);
			map.put("cc", Util.objIsNULL(cc)?"":(getProValue("public.system.uat").equalsIgnoreCase("true")?getProValue("public.email.uat.address"):cc));
			map.put("bc", Util.objIsNULL(bc)?"":(getProValue("public.system.uat").equalsIgnoreCase("true")?getProValue("public.email.uat.address"):bc));
			if(!Util.objIsNULL(ftl)){
				contont=FreeMarkerBuildMail.createEmailContent(maps, ftl);
				map.put("attr", Util.objIsNULL(attr)?getProValue("public.email.head.img"):(attr+";"+getProValue("public.email.head.img")));
			}else{
				map.put("attr", attr);
			}
			map.put("body", contont);
			map.put("webapp", Util.objIsNULL(webappId)?getProValue("public.email.appid"):webappId);
			result=HttpUtil.post(getProValue("public.email.server"), map);
			//System.out.println(result);
			if(result.toUpperCase().indexOf("HTTP STATUS 404")>-1 || Util.objIsNULL(result)){
				result=Util.getMsgJosnObject("exception", "Mailbox server did not start!");
			}
		}catch (HttpHostConnectException e) {
			if(e.getCause().toString().indexOf("Connection refused: connect")>-1){
				result=Util.getMsgJosnObject("exception", "email server address exception");
			}else{
				result=Util.jointException(e);
			}
		}catch (Exception e) {
			result=Util.jointException(e);
		}finally{

		}
		return result;

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
			Properties p = new Properties();  
			InputStream in = SendMail.class.getClassLoader().getResourceAsStream("configure.properties");
			p.load(in);
			/*for(Object o:p.keySet()){
					System.out.println(o+"---"+p.get(o));
				}*/
			for(int i=0;i<name.length;i++){
				value[i]=p.getProperty(name[i], "");
			}
			in.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
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
			Properties p = new Properties();  
			InputStream in = SendMail.class.getClassLoader().getResourceAsStream("configure.properties");
			p.load(in);
			/*for(Object o:p.keySet()){
					System.out.println(o+"---"+p.get(o));
				}*/
			if(p.size()>0){
				value=p.getProperty(name, "");
			}else{
				System.out.println("属性名-->"+name);
			}
			in.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return value;
	}
	public static void mains(String[] args) {
		try{
			String path="D:\\apache-tomcat-6.0.44\\webapps\\Emap\\resource\\img";
			String body="this is Test Maildjfaaaaaaaaaaaaaaaaaaasssssssskasldkjgklasdgklasjdgkljaskldgjklasdjgklasjdgkljasdklgjklasdjgkljasdgkl大港口垃圾啊思考了德国进口骄傲SD卡龙桂江阿喀琉斯多晶硅卡拉斯的经过考虑骄傲是考虑到国家垃圾我饿价格拉卡斯加贷款了国家爱哦我就赶快拉斯加帝国<br/>" +
					"  pls check the attr!<br/>" +
					"<br/>" +
					"<br/>" +
					"";
			String sign="--------------<br/>" +
					"King.Xu<br/>" +
					"康宏中国/ITD<br/>";

			Map<String,Object> maps=new HashMap<String, Object>();
			maps.put("title", "this mail title");
			maps.put("body", body);
			maps.put("sign", sign);
			body=FreeMarkerBuildMail.createEmailContent(maps, "email.ftl");

			Map<String,String> map=new HashMap<String, String>();
			map.put("subject", "Test Email");
			map.put("body", body);
			map.put("webapp", "COAT");
			map.put("attr", path+"\\convoy_log.gif");
			map.put("to", "king.xu@convoy.com.hk");
			//String url = "http://172.16.20.26:8888/ExchangeMail/SendMailServlet";
			String result=HttpUtil.post(Constant.EmailServer, map);
			JSONObject json=new JSONObject(result);
			System.out.println(json.get("state"));
			System.out.println(json.get("msg"));

		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}
