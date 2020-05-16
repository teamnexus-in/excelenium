/**
 * 
 */
package in.teamnexus.excelenium.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.teamnexus.excelenium.config.RootConfig;
import in.teamnexus.excelenium.service.SuiteService;
import in.teamnexus.excelenium.suite.SuiteConfig;

/**
 * @author Prabhu
 *
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {RootConfig.class})
@WebAppConfiguration
@TestMethodOrder(OrderAnnotation.class)
class ExceleniumControllerTest
{
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    WebApplicationContext context;
    
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext applicationContext;
    
    @Autowired
    ExceleniumController controller;
    
    @Autowired
    SuiteService suiteService;

    @BeforeEach
    public void setup()
    {
        logger.info("Setup of the mockMvc");
        this.mockMvc = standaloneSetup(this.controller).build(); 
    }

    /**
     * Test method for {@link in.teamnexus.excelenium.controller.ExceleniumController#noFavicon()}.
     * @throws Exception 
     */
    @Test
    @Order(1)
    void testNoFavicon() throws Exception
    {
        logger.info("Testing /favicon.ico");
        assertThat(mockMvc).isNotNull();
        mockMvc.perform(get("/favicon.ico")).andExpect(status().isOk());
    }

    /**
     * Test method for {@link in.teamnexus.excelenium.controller.ExceleniumController#index()}.
     */
    @Test
    @Order(2)
    void testIndex()
    {
        logger.info("Testing /index");
        assertTrue(true);
    }

    /**
     * Test method for {@link in.teamnexus.excelenium.controller.ExceleniumController#showSuite(org.springframework.ui.Model)}.
     */
    @Test
    @Order(3)
    void testShowSuite()
    {
        logger.info("Testing /showSuite");
        assertTrue(true);
    }

    /**
     * Test method for {@link in.teamnexus.excelenium.controller.ExceleniumController#loadScript(org.springframework.web.multipart.MultipartFile, org.springframework.web.servlet.mvc.support.RedirectAttributes)}.
     */
    @Test
    @Order(4)
    void testLoadScript()
    {
        // Use a combination of WebDriver, HtmlUnit and MockMvc support provided in spring
        // https://docs.spring.io/spring-test-htmlunit/docs/current/reference/html5/
        logger.info("Testing /load");
        assertTrue(true);
    }

    /**
     * Test method for {@link in.teamnexus.excelenium.controller.ExceleniumController#saveSuite(in.teamnexus.excelenium.suite.SuiteConfig)}.
     * @throws Exception 
     */
    @Test
    @Order(5)
    void testSaveSuite() throws Exception
    {
        logger.info("Testing /save");
        String suiteContent = FileUtils.readFileToString(new File("test-suite.json"), "UTF-8");
        mockMvc.perform(post("/save").contentType(MediaType.APPLICATION_JSON).content(suiteContent)).andExpect(status().isOk());
        ResultActions actions = mockMvc.perform(get("/getSuite?suiteName=TestSuite")).andDo(print()).andExpect(status().isOk());
        
        MvcResult result = actions.andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        SuiteConfig config = mapper.readValue(content, SuiteConfig.class);
        assertThat("TestSuite".equals(config.getName()));
    }
    
    @Test
    @Order(6)
    void testGetSuite() throws Exception
    {
        logger.info("Testing /getSuite");
        String suiteContent = FileUtils.readFileToString(new File("test-suite.json"), "UTF-8");
        mockMvc.perform(post("/save").contentType(MediaType.APPLICATION_JSON).content(suiteContent)).andExpect(status().isOk());
        ResultActions actions = mockMvc.perform(get("/getSuite?suiteName=TestSuite")).andDo(print()).andExpect(status().isOk());
        
        MvcResult result = actions.andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        SuiteConfig config = mapper.readValue(content, SuiteConfig.class);
        assertThat("TestSuite".equals(config.getName()));
    }

    /**
     * Test method for {@link in.teamnexus.excelenium.controller.ExceleniumController#run(java.lang.String)}.
     */
    @Test
    @Order(7)
    void testRun()
    {
        logger.info("Testing /run");
        assertTrue(true);
    }

}
