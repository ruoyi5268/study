package com.ruoyi.bos.dao.base;

import com.ruoyi.bos.domain.base.Standard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
* @Title: StandardDaoTest
* @Description: <p>  </p>
* @author zhaohuan
* @date 2018/4/12 23:16
* @version V1.0
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest {

    @Autowired
    private StandardDao standardDao;

    @Test
    public void testFindAll(){
        List<Standard> list = standardDao.findAll();
        System.out.println(list);
    }

}
