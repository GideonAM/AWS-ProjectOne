package com.awsprojectone.backend.controller;

import com.awsprojectone.backend.exception.FileNotFound;
import com.awsprojectone.backend.service.FileServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileServerController {

    private final FileServerService fileServerService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(name = "id") String fileName) throws FileNotFound {
        ByteArrayResource file = fileServerService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)).body(file);
    }

}
