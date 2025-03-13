package com.awsprojectone.backend.controller;

import com.awsprojectone.backend.dto.S3ObjectInfo;
import com.awsprojectone.backend.exception.BadInput;
import com.awsprojectone.backend.exception.FileNotFound;
import com.awsprojectone.backend.service.FileServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/file")
public class AdminController {

    private final FileServerService fileServerService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<String> uploadFile(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description
    ) throws IOException, BadInput {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fileServerService.uploadFile(file, title, description));
    }

    @GetMapping("/admin/all-files")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<S3ObjectInfo>> adminGetAllFiles() {
        return ResponseEntity.ok(fileServerService.adminGetAllFiles());
    }

    @DeleteMapping("/delete/{fileId}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteFileById(@PathVariable(name = "fileId") String fileId) throws FileNotFound {
        return ResponseEntity.ok(fileServerService.deleteFileByName(fileId));
    }

}
