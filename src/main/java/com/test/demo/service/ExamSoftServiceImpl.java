package com.test.demo.service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ExamSoftServiceImpl implements ExamSoftService {
    static Logger log = Logger.getLogger(ExamSoftServiceImpl.class.getName());
	
	@Value("${download_folder}")
	private String downloadFolder;
	
    @SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<?, ?> getImagesByEarthDate(String earthDt) throws Exception {
		ResponseEntity<LinkedHashMap> respEntity = null;
		long t1 = 0;
		log.info("Selected Earth Date: " + earthDt);
		String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=" + earthDt
				+ "&api_key=DEMO_KEY";
		try {
			t1 = System.currentTimeMillis();
			respEntity = (ResponseEntity<LinkedHashMap>) getRestTemplate().getForEntity(url, LinkedHashMap.class);
			long t2 = System.currentTimeMillis();
			log.info("Time taken to get the images by REST call ="+ (t2-t1) +" millseconds");
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		log.info("respEntity" + respEntity);
		//parseResponse();
		long t3 = System.currentTimeMillis();
		log.info("Time taken to download the images from NASA source is"+ (t3-t1) +" millseconds");
		return respEntity.getBody();
    }
    
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
		return restTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void writeDownloadedFiles(LinkedHashMap imageList) throws Exception {
		log.info("response size="+imageList.size());
		Map<String, List<String>> test1 = new LinkedHashMap<String, List<String>>();
		imageList.forEach((key,value) -> {
		    System.out.println(key + " -> " + value);
		    List<LinkedHashMap> photos = (ArrayList<LinkedHashMap>) value;
		    log.info("photos size="+photos.size());
			for (LinkedHashMap map : photos) {
				
				Integer id = (Integer) map.get("id");
				Integer sol = (Integer) map.get("sol");
				String img_url = (String) map.get("img_src");
				log.info("id=" + id + ": sol=" + sol + ": Image URL:" + img_url);
				try {
					downloadImage(img_url,sol+"-"+id);
				} catch (Exception e) {
					log.info(e.getMessage());
				}

			}
		});
	
	}

	private void downloadImage(String imgSrc,String fileName) throws Exception{
		URL url = new URL(imgSrc);
        File file = new File(downloadFolder+"\\"+fileName+".JPG");
        FileUtils.copyURLToFile(url, file);		
	}

	
	 
}
