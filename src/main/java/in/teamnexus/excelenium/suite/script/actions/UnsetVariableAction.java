package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Unsets a variable that has already been set.
 *
 * Column | Description
 * ----------|---------------
 * **Element** | variable name to be unset
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class UnsetVariableAction extends Action
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
        
        WebDriverUtil.getInstance().unsetVariable(this.element);
        
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if (this.element == null || this.element.isEmpty())
        {
            String str = String.format("%s - %s", this.actionName, "Element Name cannot be empty.");
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
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }
    }

}
