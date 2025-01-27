package com.example.demo.cucumber.steps;

import com.example.demo.Person;
import com.example.demo.PersonListResponse;
import org.springframework.http.ResponseEntity;

public class StepData {
    ResponseEntity<PersonListResponse> personListResponse;
    ResponseEntity<Person> personResponse;
}
