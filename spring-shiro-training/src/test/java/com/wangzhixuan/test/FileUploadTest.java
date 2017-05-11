package com.wangzhixuan.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangzhixuan.service.FileUploadResultService;

/**
 * @Description：DataSourceRoutingAspectTest
 * @author：Wangzhixuan 
 * @date：2015年8月19日 下午5:23:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FileUploadTest {

    @Autowired
    private FileUploadResultService fileUploadResultService;

    @Test
    public void testFileUploda() {
        Integer count = fileUploadResultService.updateFileUploadResultById(String.valueOf(94), null, "1");
        System.out.println(count);
    }
}

