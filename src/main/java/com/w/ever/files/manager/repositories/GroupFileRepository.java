package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.GroupFileModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupFileRepository extends CrudRepository<GroupFileModel, Integer> {
    @Query("SELECT CASE WHEN COUNT(gf) > 0 THEN TRUE ELSE FALSE END FROM GroupFileModel gf WHERE gf.file.id <> :fileId AND gf.group.id = :groupId")
    boolean fileInGroup(@Param("fileId") Integer fileId, @Param("groupId") Integer groupId);
}
