package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM UserModel u WHERE u.email = :email AND u.id <> :userId")
    boolean existsByEmailAndNotUserId(@Param("email") String email, @Param("userId") Integer userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM UserModel u WHERE u.username = :username AND u.id <> :userId")
    boolean existsByUsernameAndNotUserId(@Param("username") String username, @Param("userId") Integer userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM UserModel u WHERE u.id <> :userId")
    boolean exists(@Param("userId") Integer userId);

    Optional<UserModel> findUserByUsername(String username);
}
