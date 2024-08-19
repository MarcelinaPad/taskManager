package com.example.taskmanager.service;


import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.workerEmail.EmailWorker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Lazy
    private EmailWorker emailWorker;
    @Autowired
    private UserService userService;


    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }
    public Optional<Task> findByIdAndUser(Long id, User user) {
        return taskRepository.findByIdAndUser(id, user);
    }

    @Transactional
    public Task save( Task task) {
        Task savedTask = taskRepository.save(task);


        emailWorker.sendTaskListEmail(savedTask.getUser().getEmail());
        return savedTask;
    }

    public void deletedById(Long id) {
        taskRepository.deleteById(id);
    }

   public List<Task> findTasksInProgress(){
        return taskRepository.findByStatus(TaskStatus.W_TRAKCIE);
    }

    public List<Task> findTasksByUserEmail(String email) {
        User user = userService.findByEmail(email);
        return taskRepository.findByUser(user);
    }
}
