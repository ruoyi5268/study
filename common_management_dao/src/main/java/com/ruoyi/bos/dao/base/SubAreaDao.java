package com.ruoyi.bos.dao.base;

import com.ruoyi.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zh
 * @version V1.0
 * @Title: SubAreaDao
 * @Description: <p>  </p>
 * @date 2018/4/23 17:04
 */

public interface SubAreaDao extends JpaRepository<SubArea,String>{

    @Query(nativeQuery = true, value = "select * from T_SUB_AREA t where C_FIXEDAREA_ID is null")
    List<SubArea> noAssociationSubarea();

//    @Query(nativeQuery = true, value = "select * from T_SUB_AREA t wh/**/ere C_FIXEDAREA_ID = ?")
    @Query("select s from SubArea s inner join s.fixedArea f where f.id = ?")
    List<SubArea> hasAssociationSubarea(String subareaFixedAreaId);

    @Modifying
//    @Query(nativeQuery = true, value = "update T_SUB_AREA set C_FIXEDAREA_ID = null where C_ID=?")
    @Query("update SubArea set fixedArea = null where id = ?")
    void disAssignSubArea2FixedArea(String subareaFixedAreaId);

    @Modifying
    @Query(nativeQuery = true, value = "update T_SUB_AREA set C_FIXEDAREA_ID = ? where C_ID = ?")
    void assignSubArea2FixedArea(String subareaFixedAreaId, String subareaId);
}
