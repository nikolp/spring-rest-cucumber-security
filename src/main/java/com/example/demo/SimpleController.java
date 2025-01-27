package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
@Slf4j
public class SimpleController {
    private final PersonRepository personRepository;

    public SimpleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Create a new Person
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.ok(savedPerson);
    }

    // Get a Person by ID
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Integer id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all Persons
    @GetMapping
    public ResponseEntity<PersonListResponse> getAllPersons(@AuthenticationPrincipal User authPrincipal) {
        log.info("User: {}", authPrincipal);
        List<Person> persons = personRepository.findAll();
        PersonListResponse response = new PersonListResponse();
        response.setPersonList(persons);
        return ResponseEntity.ok(response);
    }
}