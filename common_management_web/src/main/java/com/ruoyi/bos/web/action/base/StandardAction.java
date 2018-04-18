package com.ruoyi.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.ruoyi.bos.domain.base.Standard;
import com.ruoyi.bos.service.base.StandardService;
import com.ruoyi.bos.web.action.common.BaseAction;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @Title: StandardAction
* @Description: <p>  </p>
* @author zh
* @date 2018/4/14 19:10
* @version V1.0
*/
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name = "list",type = "redirect",location = "/pages/base/standard.jsp")
})
public class StandardAction extends BaseAction<Standard> {

    @Autowired
    private StandardService standardService;

    /**
     * 获取批量作废的id
     */
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 作废取派标准
     */
    @Action("standardAction_deleteBatch")
    public String deleteBatch(){
        if (StringUtils.isNotBlank(ids)){
            standardService.delete(ids);
        }
        return "list";
    }

    /**
     * 添加/修改收派标准数据
     */
    @Action("standardAction_save")
    public String save(){

        standardService.save(model);
        return "list";
    }

    /**
     * 用于分页展示收派标准
     * @return 跳转至列表页面
     */
    @Action("standardAction_findByPage")
    public String findByPage() throws IOException {

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Standard> list = standardService.findByPage(pageable);

        this.java2Json(list, null);
        return NONE;
    }

    /**
     * 用于查询所有标准
     * @return
     */
    @Action("standardAction_findAll")
    public String findAll(){
        List<Standard> list = standardService.findAll();
        java2Json(list,null);
        return NONE;
    }

}
