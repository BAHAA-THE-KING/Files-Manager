package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.FileModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Integer> {
    @Query("SELECT f FROM FileModel f WHERE f.name = :fileName AND f.parent.id = :parentId AND f.path is null")
    FileModel findFolderByNameAndParentId(@Param("fileName") String fileName, @Param("parentId") Integer parentId);

    @Query("SELECT f FROM FileModel f WHERE f.name = :fileName AND f.parent is null AND f.path is null")
    FileModel findFolderByNameAndNullParentId(@Param("fileName") String fileName);

    @Query("SELECT f FROM FileModel f JOIN f.groupFiles gf JOIN gf.group g WHERE f.addedAt IS NULL AND f.creator.id = :userId AND g.id = :groupId")
    List<FileModel> findFileRequestsByUserIdAndGroupId(@Param("userId") Integer userId, @Param("groupId") Integer groupId);
}
