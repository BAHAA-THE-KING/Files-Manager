package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.GroupModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends CrudRepository<GroupModel, Integer> {
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM GroupModel g WHERE g.name = :name AND g.id <> :groupId")
    boolean existsByNameAndNotGroupId(@Param("name") String email, @Param("groupId") Integer groupId);

    @Query("SELECT CASE WHEN COUNT(g) = 1 THEN TRUE ELSE FALSE END FROM GroupModel g WHERE g.id <> :groupId")
    boolean exists(@Param("groupId") Integer groupId);
}