package com.ruoyi.bos.service.base;

import com.ruoyi.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: SubAreaService
 * @Description: <p>  </p>
 * @date 2018/4/23 17:01
 */


public interface SubAreaService {
    void save(SubArea subArea);

    Page<SubArea> findByPage(Pageable pageable);

    List<SubArea> findAll();
}
