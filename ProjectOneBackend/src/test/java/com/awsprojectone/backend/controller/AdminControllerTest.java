package com.awsprojectone.backend.controller;

import com.awsprojectone.backend.config.JwtService;
import com.awsprojectone.backend.dto.S3ObjectInfo;
import com.awsprojectone.backend.service.FileServerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileServerService fileServerService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void AdminController_UploadFile_ReturnString() throws Exception {

        String title = "FileName";
        String description = "Description";
        MockMultipartFile file = new MockMultipartFile(
                title, "file.txt", MediaType.TEXT_PLAIN_VALUE, "File content".getBytes()
        );

        String message = "File uploaded successfully";

        when(fileServerService
                .uploadFile(file, title, description)
        ).thenReturn(message);

        ResultActions response = mockMvc.perform(multipart("/file/upload")
                                .file("file", file.getBytes())
                                .param("title", title)
                                .param("description", description));

        response.andExpect(status().isCreated());
    }

    @Test
    public void AdminController_AdminGetAllFile_ReturnFiles() throws Exception {
        List<S3ObjectInfo> fileEntityList = Collections.singletonList(mock(S3ObjectInfo.class));

        when(fileServerService
                .adminGetAllFiles()
        ).thenReturn(fileEntityList);

        ResultActions response = mockMvc.perform(get("/file/admin/all-files")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(fileEntityList)));

        response.andExpect(status().isOk());
    }

    @Test
    public void AdminController_AdminDeleteFile_ReturnFiles() throws Exception {
        String fileId = "fileId";
        String message = "File deleted successfully";

        given(fileServerService
                .deleteFileByName(fileId)
        ).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(delete("/file/delete/" + fileId)
                .contentType(MediaType.TEXT_PLAIN)
                .content(message));

        response.andExpect(status().isOk());
    }
}
