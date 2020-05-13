/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Checks if the css attribute of the element has the specified value
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | Css attribute name
 * **Attribute Name** | Css attribute value
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class CheckCssAttributeAction extends Action
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
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        String cssValue = webElement.getCssValue(this.elementValue);
        if (this.attributeName.equals(cssValue))
        {
            reportsLogger.info(this.getFullyQualifiedName() + " element has css attribute " + this.elementValue + " with value: " + cssValue);
        }
        else
        {
            success = false;
            String warningMsg = " element does not have css attribute " + this.elementValue + " with value: " + this.attributeName + " found: " + cssValue;
            reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
        }
        
        return success;

    }

}
