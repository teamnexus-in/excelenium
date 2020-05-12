/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SetVariableAction.
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
    public boolean executeAction(WebDriver webDriver)
    {
        boolean success = true;
        
        if (!this.element.startsWith("/"))
        {
            WebDriverUtil.setVariable(this.element, this.elementValue);
            return true;
        }
        
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);
        
        if (webElement != null)
        {
            String variableValue = webElement.getText().trim();
            String variableName = this.elementValue;
            WebDriverUtil.setVariable(variableName, variableValue);
        }
        
        return success;

    }

}
