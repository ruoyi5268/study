package com.ruoyi.bos.dao.base;

import com.ruoyi.bos.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author zhaohuan
 * @version V1.0
 * @Title: FixedAreaDao
 * @Description: <p>  </p>
 * @date 2018/4/23 19:25
 */

public interface FixedAreaDao extends JpaRepository<FixedArea, String>, JpaSpecificationExecutor<FixedArea> {

}
