package com.ruoyi.bos.dao.base;

import com.ruoyi.bos.domain.base.Courier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zh
 * @version V1.0
 * @Title: CourierDao
 * @Description: <p>  </p>
 * @date 2018/4/17 20:39
 */

@Service
public interface CourierDao extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

    @Modifying
    @Query("update Courier set deltag = '1' where id = ?")
    void virtualDelete(int id);
}
