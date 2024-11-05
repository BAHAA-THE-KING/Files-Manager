package com.w.ever.files.manager.repositories;

import com.w.ever.files.manager.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface BookRepository extends CrudRepository<Book,Long> {
}
