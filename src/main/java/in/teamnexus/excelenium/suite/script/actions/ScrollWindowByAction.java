/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Scrolls the window by specified x and y pixels
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | number of `x` pixels to scroll
 * **Element Value** | number of `y` pixels to scroll
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class ScrollWindowByAction extends Action
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
        
        int x = Integer.parseInt(this.element);
        int y = Integer.parseInt(this.elementValue);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String script = "window.scrollBy(" + x + ", " + y + ")";
        jsExecutor.executeScript(script);
        
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value cannot be empty and has to be numeric values.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if(this.element != null && !this.element.isEmpty() && !StringUtils.isNumeric(this.element))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name has to be a numeric value.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        if(this.elementValue != null && !this.elementValue.isEmpty() && !StringUtils.isNumeric(this.elementValue))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Value has to be a numeric value.");
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
