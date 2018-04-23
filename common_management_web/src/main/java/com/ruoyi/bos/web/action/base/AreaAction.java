package com.ruoyi.bos.web.action.base;

import com.ruoyi.bos.domain.base.Area;
import com.ruoyi.bos.service.base.AreaService;
import com.ruoyi.bos.util.PinYin4jUtils;
import com.ruoyi.bos.web.action.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @Title: AreaAction
* @Description: <p> 区域模块 </p>
* @author zh
* @date 2018/4/18 21:05
* @version V1.0
*/

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name="list",type = "redirect",location = "/pages/base/area.jsp")
})
public class AreaAction extends BaseAction<Area>{

    @Autowired
    private AreaService areaService;

    /**
     * 从页面获取上传的文件名称
     */
    private String uploadFile;
    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    @Action("areaAction_findByPage")
    public String findBypage(){

        Pageable pageable = new PageRequest(page-1,rows);
        Page<Area> page = areaService.findByPage(pageable);
        this.java2Json(page,new String[]{"subareas"});
        return NONE;
    }

    /**
     * 解析上传的文件,并将文件内容转换成Area对象,保存到list集合,调用service保存
     * @return
     */
    @Action("areaAction_importXls")
    public String importXls(){

        System.out.println(uploadFile);

        try {
            List<Area> list = new ArrayList<>();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(new File(uploadFile)));
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            for (Row row : sheet) {

                if(row.getRowNum()!=0){
                    String id = row.getCell(0).getStringCellValue();
                    String province = row.getCell(1).getStringCellValue();
                    String city = row.getCell(2).getStringCellValue();
                    String district = row.getCell(3).getStringCellValue();
                    String postcode = row.getCell(4).getStringCellValue();

                    Area area = new Area(id,province,city,district,postcode,null,null);

                    /**
                     * 使用pinyin4j工具生成简码和城市编码
                     */
                    province = province.substring(0, province.length() - 1);
                    city = city.substring(0, city.length() - 1);
                    district = district.substring(0, district.length() - 1);

                    String name=province+city+district;
                    String[] headByString = PinYin4jUtils.getHeadByString(name);
                    String shortcode = StringUtils.join(headByString, "");
                    area.setShortcode(shortcode);

                    String citycode = PinYin4jUtils.hanziToPinyin(city,"");
                    area.setCitycode(citycode);


                    list.add(area);

                }
            }

            areaService.save(list);
            hssfWorkbook.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }

}
