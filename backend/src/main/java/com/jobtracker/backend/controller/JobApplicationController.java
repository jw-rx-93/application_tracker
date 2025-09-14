package com.jobtracker.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
  @GetMapping 
  public String getAllApplication() {
    return "List of All job applications";
  }

  @GetMapping("/test")
  public String test(){
    return "Job Application API is working";
  }
}