package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.GroupModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<GroupModel, Integer> {
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM GroupModel g WHERE g.name = :name AND g.id <> :groupId")
    boolean existsByNameAndNotGroupId(@Param("name") String name, @Param("groupId") Integer groupId);

    @Query("SELECT CASE WHEN COUNT(g) = 1 THEN TRUE ELSE FALSE END FROM GroupModel g WHERE g.id <> :groupId")
    boolean exists(@Param("groupId") Integer groupId);

    @Query("SELECT g FROM GroupModel g WHERE g.name = :groupName")
    Optional<GroupModel> findByName(@Param("groupName") String groupName);
}
