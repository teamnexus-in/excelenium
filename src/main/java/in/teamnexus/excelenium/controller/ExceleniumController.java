package in.teamnexus.excelenium.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.teamnexus.excelenium.service.SuiteService;
import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;
import in.teamnexus.excelenium.suite.exception.ScriptException;

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
    String loadScript(MultipartFile file, Model model) throws ScriptException
    {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try
        {
            if (file.isEmpty())
            {
                throw new ScriptException("Empty file " + filename);
            }
            if (filename.contains(".."))
            {
                // This is a security check
                throw new ScriptException("Cannot store file with relative path outside current directory " + filename);
            }
            try (InputStream inputStream = file.getInputStream())
            {
                String script = IOUtils.toString(inputStream, "UTF-8");
                logger.debug("Uploaded Script:" + script);
                //Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e)
        {
            throw new ScriptException("Failed to load file " + filename, e);
        }
        return "suite";
    }

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    @ResponseBody
    String saveSuite(@RequestBody SuiteConfig config) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(config);
        service.saveConfig(config);
        logger.debug("Saving:" + data);
        return "{\"success\": true}";
    }

    @GetMapping(path = "/run", produces = "application/json")
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
