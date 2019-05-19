package entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;



public class EmailWriteToFile {
	// 定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等

	private String displayName;

	private String to;

	private String from;

	private String smtpServer;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@SuppressWarnings("unchecked")
	public Vector getFile() {
		return file;
	}

	@SuppressWarnings("unchecked")
	public void setFile(Vector file) {
		this.file = file;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public String getPassword() {
		return password;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public boolean isIfAuth() {
		return ifAuth;
	}

	private String username;

	private String password;

	private String subject;

	private String content;

	private boolean ifAuth; // 服务器是否要身份认证

	private String filename = "";

	@SuppressWarnings("unchecked")
	private Vector file = new Vector(); // 用于保存发送附件的文件名的集合

	private String contentType = "text/html";

	private String charset = "utf-8";

	@SuppressWarnings("unchecked")
	public void addFile(String filename) {
		file.add(filename);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 设置SMTP服务器地址
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * 设置发件人的地址
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 设置显示的名称
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 设置服务器是否需要身份认证
	 */
	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}
	/**
	 * 设置E-mail用户名
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	/**
	 * 设置E-mail密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 设置接收者
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 设置主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 设置主体内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public EmailWriteToFile() {

	}

	private int port = 25;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 发送邮件
	 *
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public boolean send() throws FileNotFoundException, IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("state", "success");
		String message = "邮件发送成功！";
		Session session = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", port);
		try {

			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);

			session.setDebug(false);
			@SuppressWarnings("unused")
			Transport trans = null;
			Message msg = new MimeMessage(session);
			try {
				Address from_address = new InternetAddress(from, displayName);
				msg.setFrom(from_address);
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content.toString(), getContentType() + "; charset=" + getCharset());
			mp.addBodyPart(mbp);
			if (!file.isEmpty()) {// 有附件
				Enumeration efile = file.elements();
				while (efile.hasMoreElements()) {
					mbp = new MimeBodyPart();
					filename = efile.nextElement().toString(); // 选择出每一个附件名
					FileDataSource fds = new FileDataSource(filename); // 得到数据源
					mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
					mbp.setFileName(MimeUtility.encodeText(fds.getName(), getCharset(),"B")); // 得到文件名同样至入BodyPart
					mp.addBodyPart(mbp);
				}
				file.removeAllElements();
			}
			msg.setContent(mp); // Multipart加入到信件
			msg.setSentDate(new Date()); // 设置信件头的发送日期
			// 发送信件
			msg.saveChanges();
			File f = new File("d:/test.eml");
			msg.writeTo(new FileOutputStream(f));

		} catch (AuthenticationFailedException e) {
			map.put("state", "failed");
			message = "邮件发送失败！错误原因： " + "身份验证错误!";
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			message = "邮件发送失败！错误原因： " + e.getMessage();
			map.put("state", "failed");
			e.printStackTrace();
			Exception ex = null;
			if ((ex = e.getNextException()) != null) {
				System.out.println(ex.toString());
				ex.printStackTrace();
			}
			return false;
		}
		// System.out.println(" 提示信息:"+message);
		map.put("message", message);
		return true;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		try{
			//cfsmail.convoy.com.hk
			EmailWriteToFile o = new EmailWriteToFile();
			o.setSmtpServer("cfsmail.convoy.com.hk");
			o.setPassword("kingxu");
			o.setFrom("Xu.King@convoy.com.hk");
			o.setDisplayName("XIAOcong");
			o.setTo("984463633@qq.com");
			o.setSubject("Test Subject");
			o.setContent("text/html");
			o.setCharset("utf-8");
			o.addFile("D:/Users/kingxu/Desktop/psb.gif");
			o.send();
			@SuppressWarnings("unused")
			Process p = Runtime.getRuntime().exec("cmd /C start msimn.exe /eml:d:/test.eml");
			//cfsmail01.convoy.com.hk
			/*o.setSmtpServer("smtp.qq.com");
	o.setFrom("984463633@qq.com");
	o.setDisplayName("XIAOcong520");
	o.setTo("1808411978@qq.com");
	o.setSubject("Test Subject");
	o.setContent("text/html");
	o.setCharset("utf-8");
	o.addFile("C:/Users/kingxu/Desktop/9.jpg");
	o.send();
	@SuppressWarnings("unused")
	Process p = Runtime.getRuntime().exec("cmd /C start msimn.exe /eml:d:/test.eml");*/
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}











