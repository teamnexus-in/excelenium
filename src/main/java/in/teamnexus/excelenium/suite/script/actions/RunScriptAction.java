/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Runs the provided beanshell script or groovy script.
 * 
 * For beanshell, Refer: https://www.beanshell.org[www.beanshell.org].
 * 
 * For Groovy script. Refer: https://groovy-lang.org[groovy-lang.org].
 * 
 *  The script is supplied with all the variables created using `SET_VARIABLE` and a few other internal variables in a map named `inputMap` that can be referenced in the script. 
 *  
 *  The logger object is also available in the name `logger` that can be used to print debug messages.
 *  
 *   The script will have to create a HashMap in the name `result` and store all the results that it wants printed in the log after execution.
 *   
 *    Also, the `result` HashMap object should have an entry `status` which is either true or false. The value `true`  indicates the script executed successfully and `false` if there were errors in the expected output.
 *    
 * Column | Description
 * ----------|---------------
 * **Element** | absolute path base directory that contains the bsh or groovy script files
 * **Element Value** | file name with extension (.bsh or .groovy)
 * **Attribute Name** |None
 * **Attribute Value** | None
 *     
 * @author Prabhu
 */
public class RunScriptAction extends Action
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Execute action.
     *
     * @param webDriver
     *            the web driver
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    @Override
    protected boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;

        HashMap<String, String> result = null;
        if (elementValue.endsWith(".bsh"))
        {
            result = runBeanshellScript(webDriver, element, elementValue);
        }
        else if (elementValue.endsWith(".groovy"))
        {
            result = runGroovyScript(webDriver, element, elementValue);
        }

        if (result != null)
        {
            String opStatus = result.get("status");
            boolean status = ((opStatus == null) || ("true".equalsIgnoreCase(opStatus)));
            if (status)
            {
                logger.info("User script executed successfully");
            }
            else
            {
                logger.error("User script execution failed");
            }
        }

        return success;
    }

    /**
     * @param webDriver
     * @param element
     *            - Root Directory of the script
     * @param elementValue
     *            - Script Name
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, String> runGroovyScript(WebDriver webDriver, String element, String elementValue) throws Exception
    {
        logger.info("Executing groovy script");
        final ImportCustomizer imports = new ImportCustomizer();
        imports.addStaticStars("java.lang.Math");
        imports.addStarImports("groovyx.net.http");
        imports.addStaticStars("groovyx.net.http.ContentType", "groovyx.net.http.Method");

        final SecureASTCustomizer secure = new SecureASTCustomizer();
        secure.setClosuresAllowed(true);

        final CompilerConfiguration config = new CompilerConfiguration();
        config.addCompilationCustomizers(imports, secure);
        
        WebDriverUtil util = WebDriverUtil.getInstance();
        

        GroovyScriptEngine gse = new GroovyScriptEngine(element);
        gse.setConfig(config);
        Binding binding = new Binding();
        binding.setVariable("logger", logger);
        binding.setVariable("inputMap", util.getMap());
        binding.setVariable("driver", webDriver);
        gse.run(elementValue, binding);
        HashMap<String, String> result = (HashMap<String, String>) binding.getVariable("result");
        return result;
    }

    /**
     * @param webDriver
     * @param element
     * @param elementValue
     * @return
     * @throws EvalError
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, String> runBeanshellScript(WebDriver webDriver, String element, String elementValue) throws Exception
    {
        WebDriverUtil util = WebDriverUtil.getInstance();
        logger.info("Executing bsh script");
        Interpreter ip = new Interpreter();
        ip.set("logger", logger);
        ip.set("inputMap", util.getMap());
        ip.set("driver", webDriver);
        String filePath = element + "/" + elementValue;
        ip.source(filePath);
        HashMap<String, String> result = (HashMap<String, String>) ip.get("result");
        return result;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element Name, Element Value fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if(this.element != null && !this.element.isEmpty())
        {
            File file = new File(this.element);
            if(!file.exists())
            {
                String str = String.format("%s - %s", this.actionName, "Directory specified in Element Name does not exist.");
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(ValidationMessage.TYPE_ERROR, str);
            }
        }

        if(this.elementValue != null && !this.elementValue.isEmpty())
        {
            File file = new File(this.element + File.separator + this.elementValue);
            if(!file.exists())
            {
                String str = String.format("%s - %s", this.actionName, "File specified in Element Value does not exist.");
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(ValidationMessage.TYPE_ERROR, str);
            }
        }
        
        if ((this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, " Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "Preprocess values will be ignored");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

    }

}
