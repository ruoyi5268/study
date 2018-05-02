
package com.ruoyi.crm.service;

import com.ruoyi.crm.domain.Customer;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CustomerService", targetNamespace = "http://service.crm.ruoyi.com/")
@XmlSeeAlso({
//    ObjectFactory.class
})
public interface CustomerService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<com.ruoyi.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findHasAssociation", targetNamespace = "http://service.crm.ruoyi.com/", className = "com.ruoyi.crm.service.FindHasAssociation")
    @ResponseWrapper(localName = "findHasAssociationResponse", targetNamespace = "http://service.crm.ruoyi.com/", className = "com.ruoyi.crm.service.FindHasAssociationResponse")
    public List<Customer> findHasAssociation(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assignCustomers2FixedArea", targetNamespace = "http://service.crm.ruoyi.com/", className = "com.ruoyi.crm.service.AssignCustomers2FixedArea")
    @ResponseWrapper(localName = "assignCustomers2FixedAreaResponse", targetNamespace = "http://service.crm.ruoyi.com/", className = "com.ruoyi.crm.service.AssignCustomers2FixedAreaResponse")
    public void assignCustomers2FixedArea(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    List<Integer> arg1);

    /**
     * 
     * @return
     *     returns java.util.List<com.ruoyi.crm.service.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findNoAssociation", targetNamespace = "http://service.crm.ruoyi.com/", className = "com.ruoyi.crm.service.FindNoAssociation")
    @ResponseWrapper(localName = "findNoAssociationResponse", targetNamespace = "http://service.crm.ruoyi.com/", className = "com.ruoyi.crm.service.FindNoAssociationResponse")
    public List<Customer> findNoAssociation();

}
