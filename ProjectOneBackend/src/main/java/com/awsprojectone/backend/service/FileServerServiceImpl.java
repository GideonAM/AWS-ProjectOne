package com.awsprojectone.backend.service;

import com.awsprojectone.backend.auth.SendMails;
import com.awsprojectone.backend.dto.S3ObjectInfo;
import com.awsprojectone.backend.exception.FileNotFound;
import com.awsprojectone.backend.repository.FileRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServerServiceImpl implements FileServerService{

    private final FileRepository fileRepository;
    private final SendMails sendMails;
    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile, String title, String description) throws MaxUploadSizeExceededException {
        File convertedFile = convertMultipartToFile(multipartFile);
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();
        return "File uploaded successfully";
    }

    private File convertMultipartToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public ByteArrayResource downloadFile(String fileName) {
        S3Object object = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = object.getObjectContent();
        try {
            return new ByteArrayResource(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file");
        }
    }

    @Override
    public List<S3ObjectInfo> adminGetAllFiles() {
        ListObjectsV2Result objectsV2Result = amazonS3.listObjectsV2(bucketName);
        String region = amazonS3.getRegionName();
        return objectsV2Result.getObjectSummaries()
                .stream()
                .map(object -> new S3ObjectInfo(object.getKey(), "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + object.getKey()))
                .toList();
    }

    @Override
    public String deleteFileByName(String fileId) throws FileNotFound {
        amazonS3.deleteObject(bucketName, fileId);
        return "File deleted successfully";
    }

}
