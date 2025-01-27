package com.example.demo.cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class StepDef {

    // This is the state that we pass between steps
    // Each unit test, meaning each Scenario in Cucumber gets a brand new StepDef object
    // If that was not the case, we would have to reset this state in a @Before method
    private final StepData stepData = new StepData();

    private final TestRestTemplate testRestTemplate;

    public StepDef(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @Before
    void setup() {

    }

    @Given("the application is running")
    public void the_application_is_running() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
