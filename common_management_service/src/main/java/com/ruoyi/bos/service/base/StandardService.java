package com.ruoyi.bos.service.base;

import com.ruoyi.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author zhaohuan
 * @version V1.0
 * @Title: StandardService
 * @Description: <p>  </p>
 * @date 2018/4/14 19:27
 */

public interface StandardService {
    /**
     * 分页查询收派标准数据
     * @param pageable
     * @return
     */
    Page<Standard> findByPage(Pageable pageable);

    void save(Standard model);
}
