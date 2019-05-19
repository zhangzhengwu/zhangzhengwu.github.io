/**
 * GetUserCredential.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetUserCredential  implements java.io.Serializable {
    private java.lang.String domainCode;

    private java.lang.String uid;

    private java.lang.String adminPassword;

    public GetUserCredential() {
    }

    public GetUserCredential(
           java.lang.String domainCode,
           java.lang.String uid,
           java.lang.String adminPassword) {
           this.domainCode = domainCode;
           this.uid = uid;
           this.adminPassword = adminPassword;
    }


    /**
     * Gets the domainCode value for this GetUserCredential.
     * 
     * @return domainCode
     */
    public java.lang.String getDomainCode() {
        return domainCode;
    }


    /**
     * Sets the domainCode value for this GetUserCredential.
     * 
     * @param domainCode
     */
    public void setDomainCode(java.lang.String domainCode) {
        this.domainCode = domainCode;
    }


    /**
     * Gets the uid value for this GetUserCredential.
     * 
     * @return uid
     */
    public java.lang.String getUid() {
        return uid;
    }


    /**
     * Sets the uid value for this GetUserCredential.
     * 
     * @param uid
     */
    public void setUid(java.lang.String uid) {
        this.uid = uid;
    }


    /**
     * Gets the adminPassword value for this GetUserCredential.
     * 
     * @return adminPassword
     */
    public java.lang.String getAdminPassword() {
        return adminPassword;
    }


    /**
     * Sets the adminPassword value for this GetUserCredential.
     * 
     * @param adminPassword
     */
    public void setAdminPassword(java.lang.String adminPassword) {
        this.adminPassword = adminPassword;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetUserCredential)) return false;
        GetUserCredential other = (GetUserCredential) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.domainCode==null && other.getDomainCode()==null) || 
             (this.domainCode!=null &&
              this.domainCode.equals(other.getDomainCode()))) &&
            ((this.uid==null && other.getUid()==null) || 
             (this.uid!=null &&
              this.uid.equals(other.getUid()))) &&
            ((this.adminPassword==null && other.getAdminPassword()==null) || 
             (this.adminPassword!=null &&
              this.adminPassword.equals(other.getAdminPassword())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDomainCode() != null) {
            _hashCode += getDomainCode().hashCode();
        }
        if (getUid() != null) {
            _hashCode += getUid().hashCode();
        }
        if (getAdminPassword() != null) {
            _hashCode += getAdminPassword().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUserCredential.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUserCredential"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domainCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "domainCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "uid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adminPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "adminPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
