package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.FileModel;
import com.w.ever.files.manager.models.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {
}

