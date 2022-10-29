package com.pro2111.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	File save(MultipartFile file);

	File[] getAllFile();

	Boolean removeFile(String fileName);
}
