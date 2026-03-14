package com.tanish.rate_limiter.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/test")
    public String testApi() {
        return "Request successful";
    }
}