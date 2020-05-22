package in.teamnexus.excelenium.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.teamnexus.excelenium.service.SuiteService;
import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;
import in.teamnexus.excelenium.suite.exception.ScriptException;

@Controller
public class ExceleniumController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SuiteService service;

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void noFavicon()
    {

    }

    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/suite")
    public ModelAndView showSuite(Model model)
    {
        boolean isFileLoad = false;
        String suiteContent;

        Map<String, Object> map = model.asMap();

        ModelAndView mav = new ModelAndView("suite");

        if (map != null)
        {
            isFileLoad = (boolean) Optional.ofNullable(map.get("isFileLoad")).orElse(Boolean.FALSE);
            if (isFileLoad)
            {
                suiteContent = (String) map.get("suiteContent");
                mav.addObject("suiteContent", suiteContent);
            }
        }

        mav.addObject("isFileLoad", isFileLoad);

        return mav;
    }

    @PostMapping("/load")
    public String loadScript(@RequestParam(value = "suiteFile") MultipartFile file, RedirectAttributes redirectAttrs) throws ScriptException
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
                // logger.debug("Uploaded Script:" + script);
                redirectAttrs.addFlashAttribute("isFileLoad", true);
                redirectAttrs.addFlashAttribute("suiteContent", script);
            }
        }
        catch (IOException e)
        {
            throw new ScriptException("Failed to load file " + filename, e);
        }

        return "redirect:/suite";
    }

    @PostMapping(path = "/save", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String saveSuite(@RequestBody SuiteConfig suite) throws Exception
    {
        // TODO: Fix response
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(suite);
        service.saveSuite(suite);
        logger.debug("Saving:" + data);
        return "{\"success\": true}";
    }

    @GetMapping(path = "/run", produces = "application/json")
    @ResponseBody
    public String run(@RequestParam String suiteName) throws ConfigException, Exception
    {
        // TODO: Fix response
        service.runSuite(suiteName);
        return "{\"success\": true}";
    }
    
    @GetMapping(path="/getSuite", produces = "application/json")
    @ResponseBody
    public SuiteConfig getSuite(@RequestParam String suiteName)
    {
        return service.getSuite(suiteName);
    }

}
