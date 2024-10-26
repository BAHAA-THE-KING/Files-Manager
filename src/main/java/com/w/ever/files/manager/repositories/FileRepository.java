package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.FileModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Integer> {
}
