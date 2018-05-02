package com.ruoyi.bos.service.base;

import com.ruoyi.bos.domain.base.TakeTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; /**
 * @author zh
 * @version V1.0
 * @Title: TakeTimeService
 * @Description: <p>  </p>
 * @date 2018/4/26 15:47
 */

public interface TakeTimeService {
    Page<TakeTime> findByPage(Pageable pageable);
}
