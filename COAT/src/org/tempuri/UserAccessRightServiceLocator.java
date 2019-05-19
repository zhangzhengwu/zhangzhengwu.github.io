/**
 * UserAccessRightServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class UserAccessRightServiceLocator extends org.apache.axis.client.Service implements org.tempuri.UserAccessRightService {

    public UserAccessRightServiceLocator() {
    }


    public UserAccessRightServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserAccessRightServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserAccessRightServiceSoap
    private java.lang.String UserAccessRightServiceSoap_address = "http://lab7.convoy.com.hk/xgm/services/UserAccessRightService/";

    public java.lang.String getUserAccessRightServiceSoapAddress() {
        return UserAccessRightServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserAccessRightServiceSoapWSDDServiceName = "UserAccessRightServiceSoap";

    public java.lang.String getUserAccessRightServiceSoapWSDDServiceName() {
        return UserAccessRightServiceSoapWSDDServiceName;
    }

    public void setUserAccessRightServiceSoapWSDDServiceName(java.lang.String name) {
        UserAccessRightServiceSoapWSDDServiceName = name;
    }

    public org.tempuri.UserAccessRightServiceSoap getUserAccessRightServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserAccessRightServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserAccessRightServiceSoap(endpoint);
    }

    public org.tempuri.UserAccessRightServiceSoap getUserAccessRightServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.UserAccessRightServiceSoapStub _stub = new org.tempuri.UserAccessRightServiceSoapStub(portAddress, this);
            _stub.setPortName(getUserAccessRightServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserAccessRightServiceSoapEndpointAddress(java.lang.String address) {
        UserAccessRightServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.UserAccessRightServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.UserAccessRightServiceSoapStub _stub = new org.tempuri.UserAccessRightServiceSoapStub(new java.net.URL(UserAccessRightServiceSoap_address), this);
                _stub.setPortName(getUserAccessRightServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserAccessRightServiceSoap".equals(inputPortName)) {
            return getUserAccessRightServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "UserAccessRightService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "UserAccessRightServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserAccessRightServiceSoap".equals(portName)) {
            setUserAccessRightServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
