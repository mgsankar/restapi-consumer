package com.test.demo.controller;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.service.ExamSoftService;

@RequestMapping("/api")
@RestController
public class ExamSoftTestController {

    static Logger log = Logger.getLogger(ExamSoftTestController.class.getName());
    
    @Autowired
    private ExamSoftService examSoftService;
 
    @RequestMapping(value = "/downloadPictures/{earthDt}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> downloadPictures(@PathVariable("earthDt") String earthDt ) {
        ResponseEntity<?> response = null;
        
        try {
            if (earthDt != null && !StringUtils.isEmpty(earthDt)) {
            	LinkedHashMap<?,?> imageList = examSoftService.getImagesByEarthDate(earthDt);
            	examSoftService.writeDownloadedFiles(imageList);
            }
        } catch (Exception e) {
            log.info("error:" + e.getMessage());
            response = new ResponseEntity<String>("Error", HttpStatus.CONFLICT);
        }
        response = new ResponseEntity<String>("Images are downloaded successfully for the earth date :"+earthDt, HttpStatus.OK);
        return response;

    }
}
