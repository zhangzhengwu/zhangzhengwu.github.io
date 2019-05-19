package servlet.email;

import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import util.Constant;
import util.SendMail;
import util.Util;

public class SendNoticeMailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		System.out.println(request.getHeader("Referer"));
		out.print(Util.writeHtml("<span style='position:fixed;top:50%;left:40%;font-size:24px;color:red;'>You don't have the permission!</span>"));
		out.flush();
		out.close();
		doPost(request, response);
	}
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String result="";
		PrintWriter out=response.getWriter();
		try{
			String path=request.getRealPath("/resource/img");
			System.out.println("==============:>"+path);
			String body="this is Test Maildjfaaaaaaaaaaaaaaaaaaasssssssskasldkjgklasdgklasjdgkljaskldgjklasdjgklasjdgkljasdklgjklasdjgkljasdgkl大港口垃圾啊思考了德国进口骄傲SD卡龙桂江阿喀琉斯多晶硅卡拉斯的经过考虑骄傲是考虑到国家垃圾我饿价格拉卡斯加贷款了国家爱哦我就赶快拉斯加帝国<br/>" +
			"  pls check the attr!<br/>" +
			"<br/>" +
			"<br/>--------------<br/>" +
			"King.Xu<br/>" +
			"康宏中国/ITD<br/>";
			System.out.println("====start");
			result=SendMail.send("this is test mail", "king.xu@convoy.com.hk", null, null, null, body, Constant.webappId,"email.ftl","");
			JSONObject json=new JSONObject(result);
			System.out.println(json.get("state"));
			System.out.println(json.get("msg"));
		}catch (Exception e) {
			e.printStackTrace();
			result=Util.getMsgJosnObject("error", e.getMessage());
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
	}

}
