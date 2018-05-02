package com.ruoyi.bos.service.base.impl;

import com.ruoyi.bos.dao.base.CourierDao;
import com.ruoyi.bos.dao.base.FixedAreaDao;
import com.ruoyi.bos.dao.base.SubAreaDao;
import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.FixedArea;
import com.ruoyi.bos.domain.base.SubArea;
import com.ruoyi.bos.service.base.FixedAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* @Title: FixedAreaServiceImpl
* @Description: <p>  </p>
* @author zh
* @date 2018/4/23 19:21
* @version V1.0
*/

@Service
@Transactional(rollbackFor = Exception.class)
public class FixedAreaServiceImpl implements FixedAreaService{

    @Autowired
    private FixedAreaDao fixedAreaDao;

    @Autowired
    private CourierDao courierDao;

    @Autowired
    private SubAreaDao subAreaDao;

    @Override
    public Page<FixedArea> findByPage(FixedArea model, Pageable pageable) {


        //按定区编码查询
        final String fixedAreaId = model.getId();
        //按所属单位查询
        final String company = model.getCompany();

        //封装条件
        final Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //按定区编码查询
                if (StringUtils.isNotBlank(fixedAreaId)){
                    Predicate p1 = cb.equal(root.get("id"), fixedAreaId);
                    list.add(p1);
                }
                //按所属单位查询
                if (StringUtils.isNotBlank(company)){
                    Predicate p2 = cb.equal(root.get("company"), company);
                    list.add(p2);
                }
                Predicate[] restrictions = new Predicate[list.size()];
                restrictions = list.toArray(restrictions);
                return cb.and(restrictions);
            }
        };

        return fixedAreaDao.findAll(spec, pageable);
    }

    @Override
    public void save(FixedArea model) {
        fixedAreaDao.save(model);
    }

    @Override
    public void associationCourierToFixedArea(String id, Integer courierId) {
        FixedArea fixedArea = fixedAreaDao.findOne(id);
        Courier courier = courierDao.findOne(courierId);
        //定区关联快递员
        fixedArea.getCouriers().add(courier);
    }

    /**
     * 查找定区关联的快递员
     * @param id
     * @return
     */
    @Override
    public List<Courier> findAssociationCourier(String id) {
        FixedArea fixedArea = fixedAreaDao.findOne(id);
        Set<Courier> couriers = fixedArea.getCouriers();
        List<Courier> list = new ArrayList<Courier> ();
        list.addAll(couriers);
        return  list;
    }

    /**
     * 关联分区
     * @param subareaFixedAreaId
     * @param subareaIds
     */
    @Override
    public void assignSubArea2FixedArea(String subareaFixedAreaId, List<String> subareaIds) {
        //1.先把已关联到本定区的分区解除关联
        subAreaDao.disAssignSubArea2FixedArea(subareaFixedAreaId);
        //2.新关联
        if(subareaIds != null && subareaIds.size()>0){
            for (String subareaId : subareaIds) {
                subAreaDao.assignSubArea2FixedArea(subareaFixedAreaId, subareaId);
            }
        }
    }

    /**
     * 查询某个定区关联的所有分区
     * @param id
     * @return
     */
    @Override
    public List<SubArea> findAssociationSubArea(String id) {
        FixedArea fixedArea = fixedAreaDao.findOne(id);
        Set<SubArea> subareas = fixedArea.getSubareas();
        List<SubArea> list = new ArrayList<SubArea> ();
        list.addAll(subareas);
        return  list;
    }
}
