Feature: REST API

  Scenario: Get all persons on blank db
    Given the application is running
    Then sleep for 1 seconds
    When I send a GET request to persons
    Then the person list response status should be 200
    And the person list should have size 0

  Scenario: Create person
    When I send a POST request to persons with firstName "Alice" and lastName "Doe"
    When I send a GET request to persons
    Then the person list response status should be 200
    And the person list should have size 1

