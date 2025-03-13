package com.awsprojectone.backend.repository;

import com.awsprojectone.backend.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

    @Query(value = """
            select new FileEntity(f.id, f.title, f.description, f.numberOfDownloads, f.numberOfShares)
            from FileEntity f
            """)
    List<FileEntity> adminGetAllFiles();

    @Query(value = """
            select new FileEntity(f.id, f.title, f.description)
            from FileEntity f
            """)
    List<FileEntity> userGetAllFiles();

    @Query(value = """
            select new FileEntity(f.id, f.title, f.description,  f.numberOfDownloads, f.numberOfShares)
            from FileEntity f
            where f.title ilike %:fileName%
            """)
    List<FileEntity> adminSearchForFile(String fileName);

    @Query(value = """
            select new FileEntity(f.id, f.title, f.description)
            from FileEntity f
            where f.title ilike %:fileName%
            """)
    List<FileEntity> userSearchForFile(String fileName);
}
