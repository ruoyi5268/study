package com.ruoyi.bos.web.action.base;

import com.ruoyi.bos.domain.base.Courier;
import com.ruoyi.bos.domain.base.FixedArea;
import com.ruoyi.bos.domain.base.SubArea;
import com.ruoyi.bos.service.base.FixedAreaService;
import com.ruoyi.bos.web.action.common.BaseAction;
import com.ruoyi.crm.domain.Customer;
import com.ruoyi.crm.service.CustomerService;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;


/**
* @Title: FixedAreaAction
* @Description: <p>  </p>
* @author zh
* @date 2018/4/23 19:16
* @version V1.0
*/

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name = "list",type = "redirect",location = "/pages/base/fixed_area.jsp")
})
public class FixedAreaAction extends BaseAction<FixedArea>{

    @Autowired
    private FixedAreaService fixedAreaService;

    @Autowired
    private CustomerService customerProxy;

    /**
     * 属性驱动封装分区id
     */
    private List<String> subareaIds;
    public void setSubareaIds(List<String> subareaIds) {
        this.subareaIds = subareaIds;
    }

    /**
     * 属性驱动封装关联分区的定区ID
     */
    private String subareaFixedAreaId;
    public void setSubareaFixedAreaId(String subareaFixedAreaId) {
        this.subareaFixedAreaId = subareaFixedAreaId;
    }

    /**
     * 关联分区
     * @return
     */
    @Action("fixedAreaAction_assignSubArea2FixedArea")
    public String assignSubArea2FixedArea(){
        fixedAreaService.assignSubArea2FixedArea(subareaFixedAreaId, subareaIds);
        return "list";
    }

    /**
     * 属性驱动封装快递员ID
     */
    private Integer courierId;
    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    /**
     * 关联快递员到定区
     * @return
     */
    @Action("fixedAreaAction_associationCourierToFixedArea")
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(model.getId(), courierId);
        return "list";
    }

    /**
     * 属性驱动封装关联客户的定区ID
     */
    private String customerFixedAreaId;
    public void setCustomerFixedAreaId(String customerFixedAreaId) {
        this.customerFixedAreaId = customerFixedAreaId;
    }

    /**
     * 属性驱动封装关联ID定区的客户
     */
    private List<Integer> customerIds;
    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    /**
     * 双击表格中的某一条数据，南部区域查找所有与该定区关联的所有分区
     * @return
     */
    @Action("fixedAreaAction_findAssociationSubArea")
    public String findAssociationSubArea(){
        List<SubArea> hasAssociation = fixedAreaService.findAssociationSubArea(model.getId());
        this.java2Json(hasAssociation, new String[]{"subareas","couriers"});
        return NONE;
    }
    /**
     * 双击表格中的某一条数据，南部区域查找所有与该定区关联的所有客户
     * @return
     */
    @Action("fixedAreaAction_findAssociationCustomers")
    public String findAssociationCustomers(){
        List<Customer> hasAssociation = customerProxy.findHasAssociation(model.getId());
        this.java2Json(hasAssociation, null);
        return NONE;
    }

    /**
     * 双击表格中的某一条数据，南部区域查找所有与该定区关联的所有快递员
     * @return
     */
    @Action("fixedAreaAction_findAssociationCourier")
    public String findAssociationCourier(){
        List<Courier> list = fixedAreaService.findAssociationCourier(model.getId());
        this.java2Json(list, new String[]{"fixedAreas"});
        return NONE;
    }

    /**
     * 关联客户
     * @return
     */
    @Action("fixedAreaAction_assignCustomers2FixedArea")
    public String assignCustomers2FixedArea(){
        customerProxy.assignCustomers2FixedArea(customerFixedAreaId, customerIds);
        return "list";
    }


    /**
     * 查找已关联定区的客户
     * @return
     */
    @Action("fixedAreaAction_hasAssociation")
    public String hasAssociation(){
        List<Customer> hasAssociation = customerProxy.findHasAssociation(model.getId());
        this.java2Json(hasAssociation, null);
        return NONE;
    }

    /**
     * 查找未关联定区的客户
     * @return
     */
    @Action("fixedAreaAction_noAssociation")
    public String noAssociation(){
        List<Customer> noAssociation = customerProxy.findNoAssociation();
        this.java2Json(noAssociation, null);
        return NONE;
    }

    /**
     * 分页展示
     * @return
     */
    @Action("fixedAreaAction_findByPage")
    public String findByPage(){
        Pageable pageable = new PageRequest(page-1,rows);
        Page<FixedArea> page = fixedAreaService.findByPage(model,pageable);
        this.java2Json(page, new String[]{"subareas","couriers"});
        return NONE;
    }

    /**
     * 保存定区
     * @return
     */
    @Action("fixedAreaAction_save")
    public String save(){
        fixedAreaService.save(model);
        return "list";
    }

}
