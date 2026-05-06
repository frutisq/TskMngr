package com.example.tskmngr.repository;

import com.example.tskmngr.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
