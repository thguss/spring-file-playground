package com.practice.file.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

	private final AmazonS3Client amazonS3Client;

	@Value("${aws.s3.bucket}")
	private String bucket;

	public String uploadFile(@NonNull MultipartFile file) throws IOException {
		String fileName = getRandomFileName(file.getOriginalFilename()); // 파일 이름

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());

		amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata); // 파일 업로드

		return amazonS3Client.getUrl(bucket, fileName).toString(); // 업로드한 파일 공개 url
	}

	private String getRandomFileName(String fileName) {
		return "project:" + LocalDate.now() + ":" + UUID.randomUUID() + ":" + fileName;
	}
}
