package com.example.demo.cucumber.steps;

import com.example.demo.Person;
import com.example.demo.PersonListResponse;
import com.example.demo.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class StepDef {

    // This is the state that we pass between steps
    // Each unit test, meaning each Scenario in Cucumber gets a brand new StepDef object
    // If that was not the case, we would have to reset this state in a @Before method
    private final StepData stepData = new StepData();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate testRestTemplate;
    private final MockMvc mockMvc;
    private final PersonRepository personRepository;

    public StepDef(TestRestTemplate testRestTemplate, WebApplicationContext context, PersonRepository personRepository) {
        this.testRestTemplate = testRestTemplate;
        // apply() is needed for user mocking
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.personRepository = personRepository;
    }

    @Before
    public void setup() {
        // DB persists so clean it before every test
        this.personRepository.deleteAll();
    }

    @SneakyThrows
    @Given("the application is running")
    public void the_application_is_running() {
        // more "real" call
        ResponseEntity<String> response = testRestTemplate.getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // more flexible and powerful approach
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
    }

    @SneakyThrows
    @Then("sleep for {int} seconds")
    public void sleep(int seconds) {
        Thread.sleep(1000 * seconds);
    }

    @SneakyThrows
    @When("I send a GET request to persons")
    public void getPersons() {
        // stepData.personListResponse = testRestTemplate.getForEntity("/api/persons", PersonListResponse.class);

        // MockMvc is more low level so the one line above is equivalent to below few lines
        // But you do get the advantage of controlling the user that the server will receive.
        MvcResult result = mockMvc.perform(get("/api/persons")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("SOME_ROLE")
                ))
                .andReturn();

        stepData.personListResponse = new ResponseEntity(
                objectMapper.readValue(result.getResponse().getContentAsString(), PersonListResponse.class),
                HttpStatusCode.valueOf(result.getResponse().getStatus()));
        log.info("response: {}", stepData.personListResponse);
    }
    @Then("the person list response status should be {int}")
    public void personListResponseStatus(int expectedCode) {
        assertEquals(expectedCode,  stepData.personListResponse.getStatusCode().value());
    }

    @Then("the person list should have size {int}")
    public void personListSize(int expectedSize) {
        assertEquals(expectedSize, stepData.personListResponse.getBody().getPersonList().size());
    }

    @SneakyThrows
    @When("I send a POST request to persons with firstName {string} and lastName {string}")
    public void createPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        String personJson = objectMapper.writeValueAsString(person);

        MvcResult result = mockMvc.perform(post("/api/persons")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .content(personJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        stepData.personResponse = new ResponseEntity(
                objectMapper.readValue(result.getResponse().getContentAsString(), Person.class),
                HttpStatusCode.valueOf(result.getResponse().getStatus()));
    }


}
