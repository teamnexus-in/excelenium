package in.teamnexus.excelenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import in.teamnexus.excelenium.controller.ExceleniumController;
import in.teamnexus.excelenium.service.SuiteService;
import in.teamnexus.excelenium.suite.executor.ScriptExecutor;

@SpringBootTest
@Profile("test")
class ExceleniumApplicationTests
{
    @Autowired
    ExceleniumController controller;
    
    @Autowired
    SuiteService suiteService;
    
    @Autowired
    ScriptExecutor scriptExecutor;
    
    @Test
    void controllerInitTest()
    {
        assertThat(controller).isNotNull();
    }

    @Test
    void suiteServiceInitTest()
    {
        assertThat(suiteService).isNotNull();
    }
    
    @Test
    void scriptExecutorTest()
    {
        assertThat(scriptExecutor).isNotNull();
    }

}
