package com.practice.file.controller;

import static java.util.Objects.*;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.practice.file.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {

	private final FileService fileService;

	@PostMapping
	public ResponseEntity<String> uploadFile(
		@RequestParam(name = "file", required = false) MultipartFile file
	) throws IOException {
		String response = nonNull(file) ? fileService.uploadFile(file) : "업로드할 파일 없음";
		return ResponseEntity.ok(response);
	}
}
