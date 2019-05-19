package util.test;

import java.io.StringReader;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestXml {

	public void a(){
		
		System.out.println("-------aaa-------");
	}
	
	public static void main(String[] args) {
		String xml="<userauth><systemid>eip</systemid><userid>kingxu</userid><username>kingxu BOC106</username><staffcode>BOC106</staffcode><COMPANY_CODE>convoy</COMPANY_CODE><DEPARTMENT_CODE/><TELEPHONE/><EMAIL_ADDR/><LANG>E</LANG><STATUS>I</STATUS><domaincode>CONVOY</domaincode><USER_GIVENNAME/><USER_SURNAME/><USER_DISPLAY_NAME/><project id='ALL'><PROJECT_ID>ALL</PROJECT_ID><PROJECT_NAME>ALL Project</PROJECT_NAME><description>Home</description><uri>default.aspx?FrameCode=NW</uri><menu id='CCS_000000002'><description>Static Data</description><function id='CCS_000000090'/><function id='CCS_000000088'/></menu><menu id='CCS_000000001'><description>Transaction</description><function id='CCS_000000040'/></menu><menu id='CCS_000000041'><description>Consultant</description><function id='CCS_000000074'/></menu><menu id='CCS_000000048'><description>Reports</description><function id='CCS_000000076'/></menu><menu id='CCS_000000102'><description>Report Corner</description><function id='CCS_000000106'/><function id='CCS_000000108'/></menu><menu id='CCS_000000065'><description>Administration</description><function id='CCS_000000071'/></menu></project><DEPARTMENT/></userauth>";
		Document personDoc;
		try {
			personDoc = new SAXReader().read(new StringReader(new String(xml.getBytes("iso-8859-1"),"GB2312")));
			Element rootElt = personDoc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
			 Iterator body = rootElt.elementIterator();
			while (body.hasNext()) {
				 Element recordEless = (Element) body.next();
				 System.out.println(recordEless.getName()+"["+recordEless.getData()+"]");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	
	}
	
	}