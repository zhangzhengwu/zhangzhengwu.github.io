/**
 * UserAccessRightServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface UserAccessRightServiceSoap extends java.rmi.Remote {

    /**
     * This WebMethod accepts encrypted
     *         message, system code, company code and returns a user access
     * right
     *         list in XML format
     */
    public org.tempuri.GetUserAccessRightResponseGetUserAccessRightResult getUserAccessRight(java.lang.String msg, java.lang.String systemCode, java.lang.String companyCode) throws java.rmi.RemoteException;

    /**
     * This WebMethod accepts encrypted
     *         message, system code, company code and returns a user access
     * right
     *         list with detail user info in XML format
     */
    public org.tempuri.GetUserAccessRightWithUserDetailsResponseGetUserAccessRightWithUserDetailsResult getUserAccessRightWithUserDetails(java.lang.String msg, java.lang.String systemCode, java.lang.String companyCode) throws java.rmi.RemoteException;
    public java.lang.String getUserCredential(java.lang.String domainCode, java.lang.String uid, java.lang.String adminPassword) throws java.rmi.RemoteException;
}
