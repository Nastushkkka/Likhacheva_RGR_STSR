package com.worktracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.worktracking.config.SecurityConfig;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SecurityConfig.class)
public class WorktrackingApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorktrackingApplication.class, args);
    }
}
