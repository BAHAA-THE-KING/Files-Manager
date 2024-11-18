package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.GroupUserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupUserRepository extends CrudRepository<GroupUserModel, Integer> {
    @Query("SELECT CASE WHEN COUNT(gu) > 0 THEN TRUE ELSE FALSE END FROM GroupUserModel gu WHERE gu.user.id <> :userId AND gu.group.id = :groupId AND gu.joinedAt IS NULL AND gu.invitationExpiresAt < CURRENT_TIMESTAMP")
    boolean invitationExists(@Param("userId") Integer userId, @Param("groupId") Integer groupId);

    @Query("SELECT CASE WHEN COUNT(gu) > 0 THEN TRUE ELSE FALSE END FROM GroupUserModel gu WHERE gu.user.id <> :userId AND gu.group.id = :groupId AND gu.joinedAt IS NOT NULL")
    boolean userJoined(@Param("userId") Integer userId, @Param("groupId") Integer groupId);
}
