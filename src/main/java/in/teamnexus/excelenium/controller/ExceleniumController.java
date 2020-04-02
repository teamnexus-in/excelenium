package in.teamnexus.excelenium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceleniumController
{
    @GetMapping("/favicon.ico")
    @ResponseBody
    void  noFavicon()
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
        return "suite";
    }
    
    @PostMapping("/script")
    String loadScript()
    {
        return "suite";
    }

}
