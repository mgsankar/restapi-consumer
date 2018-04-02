package com.test.demo.service;

import java.util.LinkedHashMap;

public interface ExamSoftService {
	public LinkedHashMap<?, ?>  getImagesByEarthDate(String earthDt) throws Exception;
	public void writeDownloadedFiles(LinkedHashMap imageList) throws Exception;
}
