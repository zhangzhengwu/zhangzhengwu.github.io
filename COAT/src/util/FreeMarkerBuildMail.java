package util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 利用freemarker模板模板来发送邮件
 */
public class FreeMarkerBuildMail {
	public static String createEmailContent(Map<String,Object> map,String templateFile) {
		Writer out=null;	
		String html="";
		try {
				Configuration cfg = FreeMarkerManager.getConfiguration();
				Template template = cfg.getTemplate(templateFile);
				//最终输出的位置
				out = new StringWriter();
				
				/*//定义数据模型
				Map rootMap = new HashMap();
				rootMap.put("productInfo", productInfo);
				rootMap.put("email", email);
				rootMap.put("city", city);
				rootMap.put("date", date);*/
				//模板引擎解释模板
				template.process(map, out);
				html=out.toString();
				out.flush();
				out.close();
				//返回解释的String
				return html;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}finally{
			}
	}
	
	
	public static void main(String[] args) {
		String htmlStr="<img  src=\"jskdjgklsjdlg.jpg\" /><br/><img  src=\"C:\\c.jpg\" />";
		Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(htmlStr);
		while(m.find())
		{
		System.out.println(m.group(1));
		}
	                    
		String a="sss/sss/sss/xxxxx";
		String b="xxx";
		System.out.println(a.substring(a.lastIndexOf("/")+1));
		System.out.println();
		
	}

}
