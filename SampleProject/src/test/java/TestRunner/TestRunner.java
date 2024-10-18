package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/Features/adddevice.feature", glue="ApiTest")
public class TestRunner extends AbstractTestNGCucumberTests{

}
