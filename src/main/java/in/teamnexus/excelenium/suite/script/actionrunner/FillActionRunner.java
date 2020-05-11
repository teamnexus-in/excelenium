/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actionrunner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * @author Prabhu
 *
 */
public class FillActionRunner implements ActionRunner
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void executeAction(WebDriver webDriver, Action action)
    {
        WebElement webElement = action.getWebElement(webDriver, action.getElement());

        action.doPreProcess(webDriver, webElement);
        
        String fillValue = action.getElementValue();
        webElement.sendKeys(fillValue);
        if("true".equalsIgnoreCase(action.getAttributeName()))
        {
            webElement.submit();
        }

    }

}
