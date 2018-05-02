package com.ruoyi.bos.service.base.impl;

import com.ruoyi.bos.dao.base.TakeTimeDao;
import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.TakeTime;
import com.ruoyi.bos.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Title: TakeTimeServiceImpl
* @Description: <p>  </p>
* @author zh
* @date 2018/4/26 15:47
* @version V1.0
*/

@Service
@Transactional(rollbackFor = Exception.class)
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeDao takeTimeDao;

    @Override
    public Page<TakeTime> findByPage(Pageable pageable) {
        return takeTimeDao.findAll(pageable);
    }
}
