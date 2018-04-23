package com.ruoyi.bos.service.base.impl;

import com.ruoyi.bos.dao.base.AreaDao;
import com.ruoyi.bos.domain.base.Area;
import com.ruoyi.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @Title: AreaServiceImpl
* @Description: <p>  </p>
* @author zh
* @date 2018/4/18 21:24
* @version V1.0
*/

@Service
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl implements AreaService{

    @Autowired
    private AreaDao areaDao;

    @Override
    public void save(List<Area> list) {
        areaDao.save(list);
    }

    @Override
    public Page<Area> findByPage(Pageable pageable) {
        return areaDao.findAll(pageable);
    }

    @Override
    public Area findByProvinceAndCityAndDistrict(String province, String city, String district) {
        return areaDao.findByProvinceAndCityAndDistrict(province,city,district);
    }
}
