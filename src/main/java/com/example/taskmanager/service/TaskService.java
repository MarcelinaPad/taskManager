package com.example.taskmanager.service;


import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task save( Task task) {
        return taskRepository.save(task);
    }

    public void deletedById(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findTasksInProgress(){
        return taskRepository.findByStatus(TaskStatus.W_TRAKCIE);
    }
}
