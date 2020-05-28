/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Sets the window size of the browser based on the width and height specified.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | Positive integer value specifying the width
 * **Element Value** | Positive integer value specifying the height
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class SetWindowSizeAction extends Action
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
        
        int width = Integer.parseInt(this.element);
        int height = Integer.parseInt(this.elementValue);
        webDriver.manage().window().setSize(new Dimension(width, height));
        
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value cannot be empty and has to be integer values.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if(this.element != null && !this.element.isEmpty() && !StringUtils.isNumeric(this.element))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name has to be a positive integer value.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        if(this.elementValue != null && !this.elementValue.isEmpty() && !StringUtils.isNumeric(this.elementValue))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Value has to be a  positive integer value.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if ((this.attributeName != null && !this.attributeName.isEmpty())
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
