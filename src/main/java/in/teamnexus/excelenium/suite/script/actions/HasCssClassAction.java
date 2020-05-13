/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Checks if the specified element has the css classes applied
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | Comma separated list of css classes
 * **Attribute Name** |None
 * **Attribute Value** | None
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
    protected boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        if (elementValue == null || elementValue.isEmpty())
        {
            throw new ScriptException("Please specify the css classes to check");
        }

        String[] inputCls = elementValue.split(",");

        List<String> input = getSanitizedList(inputCls);

        String cssClass = webElement.getAttribute("class");
        if (cssClass != null && !cssClass.isEmpty())
        {
            String[] elementCss = cssClass.trim().split(" ");
            List<String> elementList = getSanitizedList(elementCss);
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

    public  List<String> getSanitizedList(String[] inputCls)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < inputCls.length; i++)
        {
            if (inputCls[i] != null || !inputCls[i].isEmpty())
            {
                arrayList.add(inputCls[i].trim());
            }
        }
        return arrayList;
    }
    
}
