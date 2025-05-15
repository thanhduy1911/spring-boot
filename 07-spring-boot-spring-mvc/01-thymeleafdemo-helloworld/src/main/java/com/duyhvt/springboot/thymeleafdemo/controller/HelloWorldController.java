package com.duyhvt.springboot.thymeleafdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    // need a controller method to show initial HTML form
    @RequestMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }

    // need a controller method to process the HTML form
    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld";
    }

    // need a controller method to read from data and add data to the model
    @RequestMapping("/processFormVersionTwo")
    public String letShoutDude(HttpServletRequest request, Model model) {

        // read the request parameter from the HTML form
        String name = request.getParameter("studentName");
        // convert data to UPPERCASE
        name = name.toUpperCase();
        // create a message
        String result = "Yo! " + name;
        // add message to the model
        model.addAttribute("message", result);

        return "helloworld";
    }

    @RequestMapping("/processFormVersionThree")
    public String processFormVersionThree(HttpServletRequest request, Model model) {

        // read the request parameter from the HTML form
        String name = request.getParameter("studentName");
        // convert data to UPPERCASE
        name = name.toUpperCase();
        // create a message
        String result = "Yo! " + name;
        // add message to the model
        model.addAttribute("message", result);

        return "helloworld";
    }
}
