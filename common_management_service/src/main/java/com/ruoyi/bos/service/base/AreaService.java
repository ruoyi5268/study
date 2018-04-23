package com.ruoyi.bos.service.base;

import com.ruoyi.bos.domain.base.Area;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: AreaService
 * @Description: <p>  </p>
 * @date 2018/4/18 21:23
 */


public interface AreaService {
    void save(List<Area> list);
}
