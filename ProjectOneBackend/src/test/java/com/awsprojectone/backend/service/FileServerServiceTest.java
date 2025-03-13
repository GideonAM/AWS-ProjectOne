package com.awsprojectone.backend.service;

import com.awsprojectone.backend.auth.SendMails;
import com.awsprojectone.backend.entity.FileEntity;
import com.awsprojectone.backend.exception.FileNotFound;
import com.awsprojectone.backend.repository.FileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
public class FileServerServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private SendMails sendMails;

    @InjectMocks
    private FileServerServiceImpl fileServerService;

    private FileEntity file;
    private FileEntity fileUpdatedDownload;

    @BeforeEach
    public void init() {
        file = FileEntity.builder()
                .title("Title of file")
                .description("Description of file")
                .file(new byte[0])
                .fileType("fileType")
                .build();

        fileUpdatedDownload = file = FileEntity.builder()
                .title("Title of file")
                .description("Description of file")
                .file(new byte[0])
                .fileType("fileType")
                .numberOfDownloads(1)
                .numberOfShares(0)
                .build();
    }

    @Test
    public void FileService_UploadFile_ReturnsString() throws Exception {

        when(fileRepository.save(Mockito.any(FileEntity.class))).thenReturn(file);

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        String response = fileServerService.uploadFile(multipartFile,
                "Title of file",
                "Description of file");

        Assertions.assertThat(response).isEqualTo("File uploaded successfully");
    }

    @Test
    public void FileService_DownloadFile_ReturnsString() throws FileNotFound {

        String fileId = UUID.randomUUID().toString();

        when(fileRepository.findById(fileId)).thenReturn(Optional.ofNullable(file));
        assert file != null;
        when(fileRepository.save(file)).thenReturn(fileUpdatedDownload);

        ByteArrayResource downloadedFile = fileServerService.downloadFile(fileId);

        Assertions.assertThat(downloadedFile).isNotNull();
    }

    @Test
    public void FileService_DeleteFileByName_ReturnsString() throws FileNotFound {

        String fileId = UUID.randomUUID().toString();

        when(fileRepository.findById(fileId)).thenReturn(Optional.ofNullable(file));
        String response = fileServerService.deleteFileByName(fileId);

        Assertions.assertThat(response.isBlank()).isEqualTo(false);
    }
}
