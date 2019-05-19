package com.test.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Service;
import org.apache.axis.message.MessageElement;

public class WebServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 
		findusers("XGM-016752dbccab43c09138d572dbaec3b8");
		
	}
	
	public static void findusers(String msg){
		UserAccessRightService urs=new UserAccessRightServiceLocator();
		try {
			GetUserAccessRightResponseGetUserAccessRightResult uars=new GetUserAccessRightResponseGetUserAccessRightResult();
			UserAccessRightServiceSoap_PortType usp=new UserAccessRightServiceSoap_BindingStub(new URL(urs.getUserAccessRightServiceSoapAddress()), new Service());
		uars=usp.getUserAccessRight(msg, "eip", "convoy");
			Object[] objs=uars.get_any();
			//MessageElement[] me=uars.get_any();
		//	System.out.println(me[0].getElementsByTagName("username").item(0).getChildNodes().item(0)); //获取到具体的值

			for(Object c:objs){
				String ss=c.toString();
			// ss=ss.substring(ss.indexOf("<username>")+10, ss.indexOf("</username>"));
				//System.out.println(ss.substring(ss.length()-6));
				//System.out.println(ss);
			}
			//System.out.println(obj);
		
		
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
