package com.awsprojectone.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class S3ObjectInfo {
    private String name;
    private String url;
}
