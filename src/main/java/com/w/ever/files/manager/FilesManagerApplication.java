package com.w.ever.files.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy
public class FilesManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesManagerApplication.class, args);
    }

}
