package com.ruoyi.bos.web.action.base;

import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.Standard;
import com.ruoyi.bos.service.base.CourierService;
import com.ruoyi.bos.web.action.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

/**
* @Title: CourierAction
* @Description: <p> 快递员模块 </p>
* @author zh
* @date 2018/4/17 20:35
* @version V1.0
*/

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name = "list",type = "redirect",location = "/pages/base/courier.jsp")
})
public class CourierAction extends BaseAction<Courier>{

    @Autowired
    private CourierService courierService;

    /**
     * 快递员信息分页展示,ajax请求
     * 查询条件都被封装在model中
     * @return
     */
    @Action("courierAction_findByPage")
    public String findByPage(){

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Courier> list = courierService.findByPage(model,pageable);
        this.java2Json(list,new String[]{"fixedAreas"});

        return NONE;
    }

    /**
     * 添加或修改快递员信息
     * @return
     */
    @Action("courierAction_save")
    public String save(){
        courierService.save(model);
        return "list";
    }

    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 批量作废
     * @return
     */
    @Action("courierAction_deleteBatch")
    public String deleteBatch(){
        if(StringUtils.isNotBlank(ids)){
           courierService.virtualDelete(ids);
        }
        return "list";
    }



}
