package com.ruoyi.bos.service.base.impl;

import com.ruoyi.bos.dao.base.StandardDao;
import com.ruoyi.bos.domain.base.Standard;
import com.ruoyi.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* @Title: StandardServiceImpl
* @Description: <p>  </p>
* @author zhaohuan
* @date 2018/4/14 19:27
* @version V1.0
*/
@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardDao standardDao;

    /**
     * 分页查询收派标准数据
     * @param pageable
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<Standard> findByPage(Pageable pageable) {
        return standardDao.findAll(pageable);
    }

    @Override
    public void save(Standard model) {
        standardDao.save(model);
    }
}
