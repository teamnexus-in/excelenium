/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Sets a variable that can be later substituted in the script.
 * 
  * Column | Description
 * ----------|---------------
 * **Element** | xpath of the element or variable name
 * **Element Value** | variable name in case xpath is specified in the element field or variable value otherwise
 * **Attribute Name** |None
 * **Attribute Value** | None
 *
 * @author Prabhu
 */
public class SetVariableAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     */
    @Override
    protected boolean executeAction(WebDriver webDriver)
    {
        boolean success = true;
        WebDriverUtil util = WebDriverUtil.getInstance();

        if (!this.element.startsWith("/"))
        {
            util.setVariable(this.element, this.elementValue);
            return true;
        }

        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        if (webElement != null)
        {
            String variableValue = webElement.getText().trim();
            String variableName = this.elementValue;
            util.setVariable(variableName, variableValue);
        }

        return success;

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

        if ((this.element != null && !this.element.isEmpty())
                || (this.elementValue != null && !this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

        if (this.preProcess != null)
        {
            this.preProcess.validate(this.actionName, response);
        }
    }

}
