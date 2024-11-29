package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.FileHistoryModel;
import org.springframework.data.repository.CrudRepository;

public interface FileHistoryRepository extends CrudRepository<FileHistoryModel, Integer> {

}
