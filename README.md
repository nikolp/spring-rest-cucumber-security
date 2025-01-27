# spring-rest-cucumber-security
Spring web app with cucumber integration tests and security

```mvn clean verify```
will run the unit tests using surefire plugin and then do any *IT.java integration tests,
in this case `CucumberIT` using failsafe plugin.

You can also run CucumberIT directly in IntelliJ