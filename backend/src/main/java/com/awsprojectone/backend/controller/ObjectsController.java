package com.awsprojectone.backend.controller;

import com.awsprojectone.backend.dto.S3ObjectInfo;
import com.awsprojectone.backend.service.ObjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@CrossOrigin
public class ObjectsController {

    private final ObjectsService objectsService;

    @PostMapping
    public ResponseEntity<String> uploadObject(
            @RequestParam(name = "file") MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectsService.uploadObject(file));
    }

    @GetMapping
    public ResponseEntity<List<S3ObjectInfo>> getObjects() {
        return ResponseEntity.ok(objectsService.getObjects());
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteObjectByName(@PathVariable(name = "fileId") String fileId) {
        return ResponseEntity.ok(objectsService.deleteObjectByName(fileId));
    }

}
