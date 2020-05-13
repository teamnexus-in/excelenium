/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

}
