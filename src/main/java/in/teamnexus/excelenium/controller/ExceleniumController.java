package in.teamnexus.excelenium.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.teamnexus.excelenium.service.SuiteService;
import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;

@Controller
public class ExceleniumController
{
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    SuiteService service;
    
    @GetMapping("/favicon.ico")
    @ResponseBody
    void noFavicon()
    {

    }

    @GetMapping("/")
    String index()
    {
        return "index";
    }

    @GetMapping("/script")
    String createNewScript()
    {
        logger.debug("Creating new suite");
        return "suite";
    }

    @PostMapping("/script")
    String loadScript()
    {
        return "suite";
    }

    @PostMapping(path="/save", consumes="application/json", produces="application/json")
    @ResponseBody
    String saveSuite(@RequestBody SuiteConfig config) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(config);
        service.saveConfig(config);
        logger.debug("Saving:" + data);
        return "{\"success\": true}";
    }
    
    @GetMapping(path = "/run", produces="application/json")
    @ResponseBody
    String run(@RequestParam String suiteName) throws ConfigException, Exception
    {
        logger.debug("Running the suite: " + suiteName);
        String suite = "{\"name\":\"testSuite\",\"settings\":{\"name\":\"testSuite\",\"serverUrl\":\"http://localhost:8080/\",\"browsers\":[{\"name\":\"chrome\",\"enabled\":false,\"driverPath\":\"\"},{\"name\":\"firefox\",\"enabled\":true,\"driverPath\":\"D:/Progs/selenium/webdrivers/geckodriver/geckodriver.exe\"},{\"name\":\"edge\",\"enabled\":false,\"driverPath\":\"\"},{\"name\":\"opera\",\"enabled\":false,\"driverPath\":\"\"},{\"name\":\"safari\",\"enabled\":false,\"driverPath\":\"\"}]},\"scripts\":[{\"name\":\"test\",\"actions\": [{\"execute\": true,\"stopOnError\": true,\"actionName\": \"clickNew\",\"actionType\": \"CLICK\",\"element\": \"btn-new\",\"elementValue\": \"\",\"attributeName\": \"\",\"attributeValue\": \"\",\"preProcess\": {\"actionType\": \"\",\"attributeName\": \"\",\"attributeValue\": \"\"}},{\"execute\": true,\"stopOnError\": true,\"actionName\": \"fillSuite\",\"actionType\": \"FILL\",\"element\": \"tb-suite-name\",\"elementValue\": \"Test Suite\",\"attributeName\": \"\",\"attributeValue\": \"\",\"preProcess\": {\"actionType\": \"\",\"attributeName\": \"\",\"attributeValue\": \"\"}},{\"execute\": true,\"stopOnError\": true,\"actionName\": \"fillUrl\",\"actionType\": \"FILL\",\"element\": \"tb-server-url\",\"elementValue\": \"http://localhost:8080\",\"attributeName\": \"\",\"attributeValue\": \"\",\"preProcess\": {\"actionType\": \"\",\"attributeName\": \"\",\"attributeValue\": \"\"}}]}],\"userAgent\":null,\"runConcurrent\":false}";
        ObjectMapper mapper = new ObjectMapper();
        SuiteConfig cfg = mapper.readValue(suite, SuiteConfig.class);
        service.saveConfig(cfg);
        service.runSuite(cfg.getSettings().getName());
        return "{\"success\": true}";
    }

}
