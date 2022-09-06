package br.com.cybersolutions.execute.runners;

import br.com.cybersolutions.core.hooks.HookBefore;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
    features = "src/test/resources/features",
    glue = {"br.com.cybersolutions.execute", "br.com.cybersolutions.core"},

    tags = "@Regressivo",

    snippets = CucumberOptions.SnippetType.CAMELCASE
)

public class RunnerTest {

    @BeforeClass
    public static void frameworkSetup() {
        HookBefore.frameworkInitialSetup();
    }

}
