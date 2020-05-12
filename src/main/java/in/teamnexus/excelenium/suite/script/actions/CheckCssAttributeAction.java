/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckCssAttributeAction.
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
    public boolean executeAction(WebDriver webDriver)
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
