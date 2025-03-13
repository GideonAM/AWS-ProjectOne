package com.awsprojectone.backend.service;

import com.awsprojectone.backend.dto.S3ObjectInfo;
import com.awsprojectone.backend.exception.BadInput;
import com.awsprojectone.backend.exception.FileNotFound;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileServerService {
    String uploadFile(MultipartFile file, String title, String description) throws MaxUploadSizeExceededException, IOException, BadInput;

    ByteArrayResource downloadFile(String fileId) throws FileNotFound;

    List<S3ObjectInfo> adminGetAllFiles();

    String deleteFileByName(String fileId) throws FileNotFound;
}
