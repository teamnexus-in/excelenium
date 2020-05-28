/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Switches to the specified window/tab - based on index from 0 to n.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** |  0 based index, where 0 always indicates the main window and subsequent windows opened are numbers sequentially
 * **Element Value** |None
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class SwitchToWindowAction extends Action
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
        
        WebDriverUtil.getInstance().switchToWindow(webDriver, this.element);
        
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
        
        if(this.element != null &&  !this.element.isEmpty() && !StringUtils.isNumeric(this.element))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name has to be a 0-based numeric index.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
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
