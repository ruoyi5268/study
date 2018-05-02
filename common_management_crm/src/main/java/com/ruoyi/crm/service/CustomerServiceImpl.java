package com.ruoyi.crm.service;

import com.ruoyi.crm.dao.CustomerDao;
import com.ruoyi.crm.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.List;

/**
* @Title: CustomerServiceImpl
* @Description: <p>  </p>
* @author zh
* @date 2018/4/24 16:02
* @version V1.0
*/

@Service("customerService")
@Transactional(rollbackFor = Exception.class)
//@WebService
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> findNoAssociation() {
        return customerDao.findNoAssociation();
    }

    @Override
    public List<Customer> findHasAssociation(String fixedAreaId) {
        return customerDao.findHasAssociation(fixedAreaId);
    }

    /**
     * 定区关联客户
     * @param customerFixedAreaId
     */
    @Override
    public void assignCustomers2FixedArea(String customerFixedAreaId, Integer[] customerIds) {
        //1.先将与该定区关联的客户全部解除关联
        customerDao.disassociation(customerFixedAreaId);
        //2.将将要与定区进行关联的客户关联
        if(customerIds != null && customerIds.length > 0){
            for (Integer customerId : customerIds) {
                customerDao.association(customerFixedAreaId, customerId);
            }
        }
    }
}
