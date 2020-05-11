/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actionrunner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * @author Prabhu
 *
 */
public class ClearActionRunner implements ActionRunner
{

    @Override
    public void executeAction(WebDriver webDriver, Action action)
    {
        WebElement webElement = action.getWebElement(webDriver, action.getElement());

        action.doPreProcess(webDriver, webElement);
        
        webElement.clear();
    }

}
