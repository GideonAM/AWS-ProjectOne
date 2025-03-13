package com.awsprojectone.backend.controller;

import com.awsprojectone.backend.dto.S3ObjectInfo;
import com.awsprojectone.backend.service.FileServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@CrossOrigin
public class AdminController {

    private final FileServerServiceImpl fileServerService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fileServerService.uploadFile(file, title, description));
    }

    @GetMapping("/admin/all-files")
    public ResponseEntity<List<S3ObjectInfo>> adminGetAllFiles() {
        return ResponseEntity.ok(fileServerService.adminGetAllFiles());
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteFileById(@PathVariable(name = "fileId") String fileId) {
        return ResponseEntity.ok(fileServerService.deleteFileByName(fileId));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(name = "id") String fileName) {
        ByteArrayResource file = fileServerService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)).body(file);
    }

}
