package com.ruoyi.bos.web.action.base;

import com.ruoyi.bos.domain.base.TakeTime;
import com.ruoyi.bos.service.base.TakeTimeService;
import com.ruoyi.bos.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

/**
* @Title: TakeTimeAction
* @Description: <p>  </p>
* @author zh
* @date 2018/4/26 15:46
* @version V1.0
*/
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name = "list",type = "redirect",location = "/pages/base/take_time.jsp")
})
public class TakeTimeAction extends BaseAction<TakeTime>{

    @Autowired
    private TakeTimeService takeTimeService;

    /**
     * 分页显示
     * @return
     */
    @Action("takeTimeAction_findByPage")
    public String findByPage(){

        Pageable pageable = new PageRequest(page-1,rows);
        Page<TakeTime> list = takeTimeService.findByPage(pageable);
        this.java2Json(list, null);
        return NONE;
    }


}
