package com.ruoyi.bos.service.base;

import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.TakeTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: CourierService
 * @Description: <p>  </p>
 * @date 2018/4/17 20:38
 */

public interface CourierService {
    Page<Courier> findByPage(Courier model, Pageable pageable);

    void save(Courier model);

    void delete(String id);

    void virtualDelete(String id);

    List<Courier> findNoAssociationFixedArea(String fixedAreaId);

    Courier findById(Integer id);
}
