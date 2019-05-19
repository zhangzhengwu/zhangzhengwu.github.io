/**
 * UserAccessRightServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test.webservice.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserAccessRightServiceSoap_PortType extends Remote {

    /**
     * This WebMethod accepts encrypted message, system code, company
     * code and returns a user access right list in XML format
     */
    public GetUserAccessRightResponseGetUserAccessRightResult getUserAccessRight(String msg, String systemCode, String companyCode) throws RemoteException;
}
