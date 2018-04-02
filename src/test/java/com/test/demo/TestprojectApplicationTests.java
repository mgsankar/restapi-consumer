package com.test.demo;

import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.demo.service.ExamSoftService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestprojectApplicationTests {

    
    @Autowired
    private ExamSoftService examSoftService;
    
	@Test
	public void testGetImagesByEarthDate() throws Exception {
	
		LinkedHashMap<?,?> imageList = examSoftService.getImagesByEarthDate("2017-12-17");
		Assert.assertNotNull(imageList);//assertEquals("NO IMAGES AVAILABLE", expected, actual, delta); assertNotNull(, imageList);
	}

}
