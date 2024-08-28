package com.example.taskmanager.controller;

import jakarta.servlet.RequestDispatcher;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == 400)  {
                model.addAttribute("message", "Bad Request: Your request was not valid.");
                model.addAttribute("backgroundColor", "#f8d7da");
            } else {
                model.addAttribute("message", "An unexpected error occurred.");
                model.addAttribute("backgroundColor", "#f8d7da");
            }
        }
        return "error";
    }
}
