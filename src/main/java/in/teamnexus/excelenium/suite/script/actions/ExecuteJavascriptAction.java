/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Executes the provided JavaScript file.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | absolute path to the JavaScript file
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class ExecuteJavascriptAction extends Action
{

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Executes the Javascript file passed in the elementValue
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

        try
        {
            logger.info("Executing Javascript file");
            String script = FileUtils.readFileToString(new File(this.element), StandardCharsets.UTF_8);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            jsExecutor.executeScript(script);

        }
        catch (IOException e)
        {
            logger.error("Unable load script file", e);
            throw new ScriptException(e);
        }
        
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if (this.element == null || this.element.isEmpty())
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value, Attribute Name, Attribute Value fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if(this.element != null && !this.element.isEmpty())
        {
            File file = new File(this.element);
            if(!file.exists())
            {
                String str = String.format("%s - %s", this.actionName, "ERROR: The specified JavaScript file does not exist");
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(str);
            }
        }
        
        if ((this.elementValue != null && !this.elementValue.isEmpty())
                || (this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Preprocess values will be ignored");
            response.addMessage(str);
        }
      
    }

}
