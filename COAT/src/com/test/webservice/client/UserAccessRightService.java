/**
 * UserAccessRightService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.webservice.client;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface UserAccessRightService extends Service {
    public String getUserAccessRightServiceSoapAddress();

    public UserAccessRightServiceSoap_PortType getUserAccessRightServiceSoap() throws ServiceException;

    public UserAccessRightServiceSoap_PortType getUserAccessRightServiceSoap(java.net.URL portAddress) throws ServiceException;
}
