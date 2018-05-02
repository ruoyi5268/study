package com.ruoyi.bos.service.base.impl;

import com.ruoyi.bos.dao.base.CourierDao;
import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.Standard;
import com.ruoyi.bos.domain.base.TakeTime;
import com.ruoyi.bos.service.base.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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


/**
* @Title: CourierServiceImpl
* @Description: <p> 快递员模块service层 </p>
* @author zh
* @date 2018/4/17 20:38
* @version V1.0
*/

@Service
@Transactional(rollbackFor = Exception.class)
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierDao courierDao;

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Override
    public Page<Courier> findByPage(Courier model,Pageable pageable) {
        //按工号查询
        final String courierNum = model.getCourierNum();
        //按所属单位查询
        final String company = model.getCompany();
        //按快递员类型查询
        final String type = model.getType();
        //按收派标准查询
        final Standard standard = model.getStandard();

        //封装条件
        final Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(courierNum)){
                    Predicate p1 = cb.equal(root.get("courierNum"), courierNum);
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(company)){
                    Predicate p2 = cb.equal(root.get("company"), company);
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(type)){
                    Predicate p3 = cb.equal(root.get("courierNum"), type);
                    list.add(p3);
                }
                if (standard!=null && StringUtils.isNotBlank(standard.getName())){
                    Predicate p4 = cb.equal(root.get("courierNum"), type);
                    list.add(p4);
                }

                Predicate[] restrictions = new Predicate[list.size()];
                restrictions = list.toArray(restrictions);
                return cb.and(restrictions);
            }
        };


        return courierDao.findAll(spec,pageable);
    }

    /**
     * 添加/更新
     * @param model
     */
    @Override
    public void save(Courier model) {
        courierDao.save(model);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        courierDao.delete(Integer.parseInt(id));
    }

    /**
     * 作废
     * @param ids
     */
    @Override
    public void virtualDelete(String ids) {
        String[] strings = ids.split(",");
        for (String id : strings) {
            courierDao.virtualDelete(Integer.parseInt(id));
        }
    }

    /**
     * 查找与当前定区没有关联的未作废的快递员
     * @param fixedAreaId
     */
    @Override
    public List<Courier> findNoAssociationFixedArea(String fixedAreaId) {
        return courierDao.findNoAssociationFixedArea(fixedAreaId);
    }

    @Override
    public Courier findById(Integer id) {
        return courierDao.findOne(id);
    }


}
