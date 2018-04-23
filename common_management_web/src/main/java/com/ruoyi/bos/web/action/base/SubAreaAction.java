package com.ruoyi.bos.web.action.base;

import com.ruoyi.bos.domain.base.Area;
import com.ruoyi.bos.domain.base.SubArea;
import com.ruoyi.bos.service.base.AreaService;
import com.ruoyi.bos.service.base.SubAreaService;
import com.ruoyi.bos.util.ExcelUtil;
import com.ruoyi.bos.web.action.common.BaseAction;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;
import java.io.*;
import java.util.List;


/**
* @Title: SubAreaAction
* @Description: <p>  </p>
* @author zh
* @date 2018/4/23 15:30
* @version V1.0
*/

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
        @Result(name = "list",type = "redirect",location = "/pages/base/sub_area.jsp")
})
public class SubAreaAction extends BaseAction<SubArea> {

    private String uploadFile;
    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    @Autowired
    private SubAreaService subAreaService;

    @Autowired
    private AreaService areaService;

    /**
     * 导入分区数据
     * @return
     */
    @Action("subAreaAction_importXls")
    public String importXls(){

        try {
            //获取整个工作表
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(uploadFile)));
            //获取某一张工作表
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //遍历工作表中的每一行
            for (Row row : sheet) {
                //第一行是表头
                if(row.getRowNum()==0){
                    continue;
                }else {
                    //分区编号
                    String id = row.getCell(0).getStringCellValue();
                    //省
                    String province = row.getCell(1).getStringCellValue();
                    //市
                    String city = row.getCell(2).getStringCellValue();
                    //区
                    String district = row.getCell(3).getStringCellValue();
                    //关键字
                    String keyWords = row.getCell(4).getStringCellValue();
                    //起始号
                    String startNum = row.getCell(5).getStringCellValue();
                    //终止号
                    String endNum = row.getCell(6).getStringCellValue();
                    //单双号
                    Character single = row.getCell(7).getStringCellValue().toCharArray()[0];
                    //辅助关键字
                    String assistKeyWords = row.getCell(8).getStringCellValue();
                    //瞬时态
                    Area area = new Area(province,city,district);
                    area = areaService.findByProvinceAndCityAndDistrict(area.getProvince(),area.getCity(),area.getDistrict());
                    SubArea subArea = new SubArea(id, startNum, endNum, single, keyWords, assistKeyWords, area, null);
                    subAreaService.save(subArea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**
     * 分页展示分区数据
     */
    @Action("subAreaAction_findByPage")
    public String findByPage(){

        Pageable pageable = new PageRequest(page-1, rows);
        Page<SubArea> page = subAreaService.findByPage(pageable);
        this.java2Json(page, new String[]{"subareas","couriers"});
        return NONE;
    }

    @Action("subAreaAction_exportXls")
    public String exportXls(){
        //1.获取数据
        List<SubArea> list = subAreaService.findAll();

        //2.excel标题
        String[] title = {"分区编号","省","市","区","关键字","起始号","终止号","单双号","辅助关键字"};

        //3.excel文件名
        String fileName = "分区信息表.xls";

        //4.sheet名
        String sheetName = "分区信息表";
        String [][] content = new String[list.size()][title.length];
        for (int i = 0; i < list.size(); i++) {
            SubArea subArea = list.get(i);
            content[i][0] = subArea.getId();
            content[i][1] = subArea.getArea().getProvince();
            content[i][2] = subArea.getArea().getCity();
            content[i][3] = subArea.getArea().getDistrict();
            content[i][4] = subArea.getKeyWords();
            content[i][5] = subArea.getStartNum();
            content[i][6] = subArea.getEndNum();
            content[i][7] = subArea.getSingle().toString();
            content[i][8] = subArea.getAssistKeyWords();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        HttpServletResponse response = ServletActionContext.getResponse();
        //响应到客户端
        try {
            ExcelUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "list";
    }




}
