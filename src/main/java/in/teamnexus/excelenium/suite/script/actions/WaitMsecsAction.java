/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Wait for the specified milliseconds before performing the next action. This is in addition to the implicit waits provided in Selenium.
 *
 * Column | Description
 * ----------|---------------
 * **Element** | Time to wait in milliseconds before executing next action
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 *
 * @author Prabhu
 */
public class WaitMsecsAction extends Action
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
        
        Thread.sleep(Long.parseLong(this.element));

        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if (this.element == null || this.element.isEmpty())
        {
            String str = String.format("%s - %s", this.actionName, "Element Name cannot be empty and has to be a positive integer.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if(this.element != null && !this.element.isEmpty() && !StringUtils.isNumeric(this.element))
        {
            String str = String.format("%s - %s", this.actionName, "Element Name has to be a positive integer.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if ((this.elementValue != null && !this.elementValue.isEmpty())
                || (this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "Preprocess values will be ignored");
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
    }

}
