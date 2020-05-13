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
            String filePath = this.element;
            String script = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
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

}
