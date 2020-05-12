/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class VerifyTextAction.
 *
 * @author Prabhu
 */
public class VerifyTextAction extends Action
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

        if (webElement != null)
        {
            String verifyText = this.elementValue;
            String op = (this.attributeName == null || this.attributeName.isEmpty()) ? "full_text" : this.attributeName;
            String elementText = webElement.getText().trim();

            if ("full_text".equalsIgnoreCase(op))
            {
                success = elementText.equals(verifyText);
            }
            else if ("starts_with".equalsIgnoreCase(op))
            {
                success = elementText.startsWith(verifyText);
            }
            else if ("ends_with".equalsIgnoreCase(op))
            {
                success = elementText.endsWith(verifyText);
            }
            else if ("contains".equalsIgnoreCase(op))
            {
                success = elementText.contains(verifyText);
            }

            if (success)
            {
                WebDriverUtil.highlightElement(webDriver, webElement);
            }
            else
            {
                if (this.isStopOnError)
                {
                    throw new ScriptException("Stopping script as script cannot find element " + this.element + " that " + op + " value " + this.elementValue);
                }
                else
                {
                    success = false;
                    String warningMsg = "Cannot find element " + this.element + "that " + op + " value " + this.elementValue;
                    reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
                }
            }
        }
        return success;
    }
    
}
