package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.FileModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Integer> {
    @Query("SELECT f FROM FileModel f WHERE f.name = :fileName AND f.parent <> :parentId")
    FileModel findByNameAndParentId(@Param("fileName") String fileName, @Param("parentId") Integer parentId);
}
