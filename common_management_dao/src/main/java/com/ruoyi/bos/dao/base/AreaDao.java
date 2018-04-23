package com.ruoyi.bos.dao.base;

import com.ruoyi.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zh
 * @version V1.0
 * @Title: AreaDao
 * @Description: <p>  </p>
 * @date 2018/4/18 21:25
 */

public interface AreaDao extends JpaRepository<Area,String>{

    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
