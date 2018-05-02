package com.ruoyi.bos.service.base.impl;

import com.ruoyi.bos.dao.base.SubAreaDao;
import com.ruoyi.bos.domain.base.SubArea;
import com.ruoyi.bos.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Title: SubAreaServiceImpl
* @Description: <p>  </p>
* @author zh
* @date 2018/4/23 17:03
* @version V1.0
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class SubAreaServiceImpl implements SubAreaService{

    @Autowired
    private SubAreaDao subAreaDao;

    @Override
    public void save(SubArea subArea) {
        subAreaDao.save(subArea);
    }

    @Override
    public Page<SubArea> findByPage(Pageable pageable) {
        return subAreaDao.findAll(pageable);
    }

    @Override
    public List<SubArea> findAll() {
        return subAreaDao.findAll();
    }

    @Override
    public List<SubArea> noAssociationSubarea() {
        return subAreaDao.noAssociationSubarea();
    }

    @Override
    public List<SubArea> hasAssociationSubarea(String subareaFixedAreaId) {
        return subAreaDao.hasAssociationSubarea(subareaFixedAreaId);
    }
}
