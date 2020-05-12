/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class HasCssClassAction.
 *
 * @author Prabhu
 */
public class HasCssClassAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     * @throws Exception the exception
     */
    @Override
    public boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        if (elementValue == null || elementValue.isEmpty())
        {
            throw new ScriptException("Please specify the css classes to check");
        }

        String[] inputCls = elementValue.split(",");

        List<String> input = WebDriverUtil.getSanitizedList(inputCls);

        String cssClass = webElement.getAttribute("class");
        if (cssClass != null && !cssClass.isEmpty())
        {
            String[] elementCss = cssClass.trim().split(" ");
            List<String> elementList = WebDriverUtil.getSanitizedList(elementCss);
            if (elementList.containsAll(input))
            {
                reportsLogger.info(this.getFullyQualifiedName() + " found " + input + " classes applied to the element");
            }
            else
            {
                success = false;
                String warningMsg = "Specified css classes " + input + " are not applied to the element. Classes found: " + elementList;
                reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
            }
        }

        return success;

    }

}
