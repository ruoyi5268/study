package com.ruoyi.crm.test;

import com.ruoyi.crm.domain.Customer;
import com.ruoyi.crm.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
* @Title: CustomerTest
* @Description: <p>  </p>
* @author zhaohuan
* @date 2018/4/24 17:32
* @version V1.0
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CustomerTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void test(){
        List<Customer> noAssociation = customerService.findNoAssociation();
        for (Customer customer : noAssociation) {
            System.out.println(customer.getUsername());
        }
    }
    @Test
    public void assignCustomers2FixedAreaTest(){
        customerService.assignCustomers2FixedArea("DQ001",new Integer[]{2,4});
    }

}
