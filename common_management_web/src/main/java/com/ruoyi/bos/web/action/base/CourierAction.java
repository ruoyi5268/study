package com.ruoyi.bos.web.action.base;

import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.Standard;
import com.ruoyi.bos.domain.base.TakeTime;
import com.ruoyi.bos.service.base.CourierService;
import com.ruoyi.bos.web.action.common.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
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
import java.io.IOException;
import java.util.List;

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

    @Action("courierAction_findTakeTime")
    public String findTakeTime(){
        Courier courier = courierService.findById(model.getId());
        TakeTime takeTime = courier.getTakeTime();
        String json = JSONArray.fromObject(takeTime).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }


    /**
     * 属性驱动封装定区id
     */
    private String fixedAreaId;
    public void setFixedAreaId(String fixedAreaId) {
        this.fixedAreaId = fixedAreaId;
    }

    /**
     * 查找没有被当前定区关联的快递员(1.没有被作废 2.不负责本定区)
     * @return
     */
    @Action("courierAction_listajax")
    public String listajax(){
        List<Courier> list = courierService.findNoAssociationFixedArea(fixedAreaId);
        this.java2Json(list, new String[]{"fixedAreas"});
        return NONE;
    }

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
