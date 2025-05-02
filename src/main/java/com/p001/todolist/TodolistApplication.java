package com.p001.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.p001.todolist")
public class TodolistApplication {

 public static void main(String[] args) {
  SpringApplication.run(TodolistApplication.class, args);
 }

}
