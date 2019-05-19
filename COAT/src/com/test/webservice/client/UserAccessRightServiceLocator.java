/**
 * UserAccessRightServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

import util.Util;

public class UserAccessRightServiceLocator extends Service implements UserAccessRightService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAccessRightServiceLocator() {
    }


    public UserAccessRightServiceLocator(EngineConfiguration config) {
        super(config);
    }

    public UserAccessRightServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserAccessRightServiceSoap
    private String UserAccessRightServiceSoap_address = "http://www.convoy.info/eip/services/useraccessrightservice.asmx";

    public String getUserAccessRightServiceSoapAddress() {
    	if("true".equals(Util.getProValue("public.system.uat"))){
    		UserAccessRightServiceSoap_address="http://lab7.convoy.com.hk/xgm/services/UserAccessRightService?wsdl";
    	}
        return UserAccessRightServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String UserAccessRightServiceSoapWSDDServiceName = "UserAccessRightServiceSoap";

    public String getUserAccessRightServiceSoapWSDDServiceName() {
        return UserAccessRightServiceSoapWSDDServiceName;
    }

    public void setUserAccessRightServiceSoapWSDDServiceName(java.lang.String name) {
        UserAccessRightServiceSoapWSDDServiceName = name;
    }

    public UserAccessRightServiceSoap_PortType getUserAccessRightServiceSoap() throws ServiceException {
       URL endpoint;
        try {
            endpoint = new URL(UserAccessRightServiceSoap_address);
        }
        catch (MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserAccessRightServiceSoap(endpoint);
    }

    public UserAccessRightServiceSoap_PortType getUserAccessRightServiceSoap(URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
           UserAccessRightServiceSoap_BindingStub _stub = new UserAccessRightServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getUserAccessRightServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserAccessRightServiceSoapEndpointAddress(String address) {
        UserAccessRightServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("unchecked")
	public Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (UserAccessRightServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                UserAccessRightServiceSoap_BindingStub _stub = new UserAccessRightServiceSoap_BindingStub(new URL(UserAccessRightServiceSoap_address), this);
                _stub.setPortName(getUserAccessRightServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("unchecked")
	public Remote getPort(QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserAccessRightServiceSoap".equals(inputPortName)) {
            return getUserAccessRightServiceSoap();
        }
        else  {
            Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://tempuri.org/", "UserAccessRightService");
    }

    @SuppressWarnings("unchecked")
	private HashSet ports = null;

    @SuppressWarnings("unchecked")
	public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://tempuri.org/", "UserAccessRightServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("UserAccessRightServiceSoap".equals(portName)) {
            setUserAccessRightServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
