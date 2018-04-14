package com.ruoyi.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.ruoyi.bos.domain.base.Standard;
import com.ruoyi.bos.service.base.StandardService;
import net.sf.json.JSONObject;
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
import java.util.Map;


/**
* @Title: StandardAction
* @Description: <p>  </p>
* @author zhaohuan
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
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    @Autowired
    private StandardService standardService;

    /**
     * 作废取派标准
     */
    @Action("standardAction_delete")
    public String delete(){
        System.out.println("=================");
        //TODO
        return NONE;
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
     * 当前页面,属性驱动封装数据
     */
    private int page;
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 每页显示条数
     */
    private int rows;
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * 用于分页展示收派标准
     * @return 跳转至列表页面
     */
    @Action("standardAction_findByPage")
    public String findByPage() throws IOException {

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Standard> list = standardService.findByPage(pageable);

        //将页面需要的数据封装至map
        Map<String,Object> map = new HashMap<>(16);
        map.put("total",list.getTotalPages());
        map.put("rows",list.getContent());

        //转为json格式返回页面
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().print(json);
        return NONE;
    }

    /**
     * 利用模型驱动封装数据
     */
    private Standard model = new Standard();

    @Override
    public Standard getModel() {
        return model;
    }
}
