package com.example.demo.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
// Where to find Step Definitions and other configs (will search sub-packages)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
        value = "com.example.demo.cucumber"
)
// Helps Cucumber find this config, makes every scenario count as a single junit "test"
// The class holding this annotation must be in a package quoted in GLUE_PROPERTY above
@CucumberContextConfiguration
public class CucumberIT extends SpringIT {
}
