package com.ruoyi.crm.service;

import com.ruoyi.crm.domain.Customer;

import javax.jws.WebService;
import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: CustomerService
 * @Description: <p>  </p>
 * @date 2018/4/24 16:01
 */

@WebService
public interface CustomerService {

    /**
     * 查询未关联定区的客户
     * @return 客户列表
     */
    public List<Customer> findNoAssociation();

    /**
     * 查询已关联定区的客户
     * @param fixedAreaId 定区
     * @return 客户列表
     */
    public List<Customer> findHasAssociation(String fixedAreaId);

    /**
     * 定区关联客户
     * @param customerFixedAreaId
     */
    public void assignCustomers2FixedArea(String customerFixedAreaId, Integer[] customerIds);

}
