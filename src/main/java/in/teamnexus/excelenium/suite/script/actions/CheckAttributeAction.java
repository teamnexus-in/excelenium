/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckAttributeAction.
 *
 * @author Prabhu
 */
public class CheckAttributeAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     * @throws Exception the exception
     */
    @Override
    public boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);
        
        String attr = webElement.getAttribute(this.attributeName).trim();
        if (attr != null)
        {
            if (attr.equals(this.attributeValue))
            {
                reportsLogger.info("Attribute: " + this.attributeName + " with value: " + this.attributeValue + " found");
            }
            else
            {
                if (this.isStopOnError)
                {
                    throw new ScriptException("Stopping script as  Attribute: " + this.attributeName + " value: " + attr + " does not match " + this.attributeValue);

                }
                else
                {
                    success = false;
                    String warningMsg = "Attribute: " + this.attributeName + " value: " + attr + " does not match " + this.attributeValue;
                    reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
                }
            }

        }
        else
        {
            if (this.isStopOnError)
            {
                throw new ScriptException("Stopping script as " + this.attributeName + " not found");
            }
            else
            {
                success = false;
                String warningMsg = "Attribute: " + this.attributeName + " not found";
                reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
            }
        }
        return success;
    }

}
