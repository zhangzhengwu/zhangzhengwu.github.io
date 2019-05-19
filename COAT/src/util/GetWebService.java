package util;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Service;

import com.test.webservice.client.GetUserAccessRightResponseGetUserAccessRightResult;
import com.test.webservice.client.UserAccessRightService;
import com.test.webservice.client.UserAccessRightServiceLocator;
import com.test.webservice.client.UserAccessRightServiceSoap_BindingStub;
import com.test.webservice.client.UserAccessRightServiceSoap_PortType;

public  class GetWebService {
	
	public  String getUserNames(String msg){
		System.out.println("888888888");
	String username=null;
	try {
		UserAccessRightService urs=new UserAccessRightServiceLocator();
		GetUserAccessRightResponseGetUserAccessRightResult uars=new GetUserAccessRightResponseGetUserAccessRightResult();
		UserAccessRightServiceSoap_PortType usp=new UserAccessRightServiceSoap_BindingStub(new URL(urs.getUserAccessRightServiceSoapAddress()), new Service());
		uars=usp.getUserAccessRight(msg, "eip", "convoy");
		Object[] objs=uars.get_any();
		for(Object c:objs){
			username=c.toString();
		 username=username.substring(username.indexOf("<username>")+10, username.indexOf("</username>"));
			username=username.substring(username.length()-6);
		}
		System.out.println(username+"----");
	
	
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
		username=null;
		e.printStackTrace();
	}
	System.out.println(username);
	return username;
	}
}
