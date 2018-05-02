package com.ruoyi.crm.dao;

import com.ruoyi.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: CustomerDao
 * @Description: <p>  </p>
 * @date 2018/4/24 17:27
 */
public interface CustomerDao extends JpaRepository<Customer, Integer>{

    @Query("from Customer where fixedAreaId is null")
    List<Customer> findNoAssociation();

    @Query("from Customer where fixedAreaId = ?")
    List<Customer> findHasAssociation(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void disassociation(String customerFixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId = ? where id = ?")
    void association(String customerFixedAreaId, Integer customerId);
}
