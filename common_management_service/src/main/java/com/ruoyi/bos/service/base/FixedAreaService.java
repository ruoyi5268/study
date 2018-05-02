package com.ruoyi.bos.service.base;

import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.FixedArea;
import com.ruoyi.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: FixedAreaService
 * @Description: <p>  </p>
 * @date 2018/4/23 19:19
 */

public interface FixedAreaService {
    Page<FixedArea> findByPage(FixedArea model, Pageable pageable);

    void save(FixedArea model);
    void associationCourierToFixedArea(String id, Integer courierId);

    List<Courier> findAssociationCourier(String id);

    void assignSubArea2FixedArea(String subareaFixedAreaId, List<String> subareaIds);

    List<SubArea> findAssociationSubArea(String id);
}
