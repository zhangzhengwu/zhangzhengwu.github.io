package org.tempuri;

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

		/*UserAccessRightService urs=new UserAccessRightServiceLocator();
		try {
			GetUserAccessRightResponseGetUserAccessRightResult uars=new GetUserAccessRightResponseGetUserAccessRightResult();
			UserAccessRightServiceSoap_PortType usp=new UserAccessRightServiceSoap_BindingStub(new URL(urs.getUserAccessRightServiceSoapAddress()), new Service());
		uars=usp.getUserAccessRight("3%2fOacdl3f0zEeHeI7pyz3yHyJOTe7NTYqqacxkPNRlumvvkdWKJr9Q%3d%3d", "eip", "convoy");
			Object[] objs=uars.get_any();
			for(Object c:objs){
				String ss=c.toString();
			 ss=ss.substring(ss.indexOf("<username>")+10, ss.indexOf("</username>"));
				System.out.println(ss.substring(ss.length()-6));
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
			System.out.println(0);
		}
		*/
		findusers("XGM-a417eb077b5a4b9d9c7d82382388ef8d");
		
	}
	
	public static void findusers(String msg){
		UserAccessRightService urs=new UserAccessRightServiceLocator();
		try {
			GetUserAccessRightResponseGetUserAccessRightResult uars=new GetUserAccessRightResponseGetUserAccessRightResult();
			UserAccessRightServiceSoap usp=new UserAccessRightServiceSoapStub(new URL(urs.getUserAccessRightServiceSoapAddress()), new Service());
		uars=usp.getUserAccessRight(msg, "eip", "convoy");
			Object[] objs=uars.get_any();
			MessageElement[] me=uars.get_any();
			System.out.println(me[0].getElementsByTagName("staffcode").item(0).getChildNodes().item(0)); //获取到具体的值

			for(Object c:objs){
				String ss=c.toString();
			// ss=ss.substring(ss.indexOf("<username>")+10, ss.indexOf("</username>"));
			//	System.out.println(ss.substring(ss.length()-6));
				System.out.println(ss);
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
