package com.ruoyi.bos.web.action.common;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Title: BaseAction
* @Description: <p> 作为所有本项目Action的基础父类,继承ActionSupport类,实现模型驱动接口,实现获取模型对象的公共方法等 </p>
* @author zh
* @date 2018/4/17 18:27
* @version V1.0
*/
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

    protected T model;

    /**
     * 实例化模型对象
     * @return
     */
    @Override
    public T getModel() {
        //获取调用者的类
        Class<? extends BaseAction> aClass = this.getClass();
        //获取BaseAction类型
        Type superclass = aClass.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)superclass;

        //获取参数类型列表
        Type[] typeArguments = pt.getActualTypeArguments();
        Class clazz = (Class) typeArguments[0];

        //获取参数类型的实例对象
        try {
            model = (T)clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }


    /**
     * 从页面获取分页参数
     * 当前页面,属性驱动封装数据
     */
    protected int page;
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 从页面获取分页参数
     * 每页显示条数
     */
    protected int rows;
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Page对象转json
     */
    public void java2Json(Page page, String[] excludes){
        Map<String, Object> map = new HashMap<>(2);
        map.put("total",page.getTotalElements());
        map.put("rows",page.getContent());

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);

        String json = JSONObject.fromObject(map,jsonConfig).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 集合转json
     */
    public void java2Json(List list, String[] excludes){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);

        String json = JSONArray.fromObject(list,jsonConfig).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
