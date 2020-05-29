/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Verifies the text content of the element.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | Text value to be verified
 * **Attribute Name** | One of following options <br/> **starts_with** : compares if the text of the element starts with the provided text.<br/> **ends_with** : compares if the text element ends with the provided text.<br/> **contains** :checks if the text of the element contains the provided text<br/> **full_text** : compares the text of the element to the provided text
 * **Attribute Value** | None

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
    protected boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;

        WebElement webElement = this.getWebElement(webDriver, this.element);
        WebDriverUtil util = WebDriverUtil.getInstance();
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
                util.highlightElement(webDriver, webElement);
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

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty())
                || (this.attributeName == null || this.attributeName.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element Name, Element Value, Attribute Name fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
  
        if(this.attributeName != null && !this.attributeName.isEmpty() && !this.attributeName.matches("starts_with|ends_with|contains|full_text"))
        {
            String str = String.format("%s - %s", this.actionName, " Attribute Name fields has to have one of the values - starts_with, ends_with, contains, or full_text.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if (this.attributeValue != null && !this.attributeValue.isEmpty())
        {
            String str = String.format("%s - %s", this.actionName, "Attribute Value fields will be ignored.");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

        if (this.preProcess != null)
        {
            this.preProcess.validate(this.actionName, response);
        }        
    }
    
}
