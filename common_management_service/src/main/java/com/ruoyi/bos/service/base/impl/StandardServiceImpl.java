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

import java.util.List;

/**
* @Title: StandardServiceImpl
* @Description: <p>  </p>
* @author zh
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

    /**
     * 保存/修改取派标准
     * @param model
     */
    @Override
    public void save(Standard model) {
        standardDao.save(model);
    }

    /**
     * 删除取派标准
     * @param iids
     */
    @Override
    public void delete(String ids) {
        String[] strings = ids.split(",");
        for (String id : strings) {
            standardDao.delete(Integer.parseInt(id));
        }
    }

    @Override
    public List<Standard> findAll() {
        return standardDao.findAll();
    }
}
