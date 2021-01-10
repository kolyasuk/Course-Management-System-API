package edu.sombra.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/student")
    public String helloUser() {
        return "Hello student";
    }

    @GetMapping("/student/get")
    public String helloUserget() {
        return "Hello student get";
    }

    @GetMapping("/instructor")
    public String helloInstructor() {
        return "Hello instructor";
    }

    @GetMapping("/instructor/get")
    public String helloInstructorGet() {
        return "Hello instructor Get";
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/admin/get")
    public String helloadminGet() {
        return "Hello admin Get";
    }

}