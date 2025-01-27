package com.example.demo.cucumber.steps;

import com.example.demo.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class StepData {
    ResponseEntity<List<Person>> personListResponse;
}
