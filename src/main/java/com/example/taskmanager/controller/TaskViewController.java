package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class TaskViewController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/tasks/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";

    }
    @GetMapping("/tasks")
    public String showTasks(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        List<Task> tasks = taskService.findByUser(user);
        model.addAttribute("tasks", tasks);


        if ("wolek".equals(username)) {
            model.addAttribute("showMessage", true);
            model.addAttribute("message", "Daj żyć BABOOOO");
            model.addAttribute("imagePath", "/images/your_image.jpg");
        } else {
            model.addAttribute("showMessage", false);
        }
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute Task task) {

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
    }
}
