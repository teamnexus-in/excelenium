/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.script.ScriptException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Makes a GET request and stores the response in the variable name specified.
 *
 * Column | Description
 * ----------|---------------
 * **Element** | url to request
 * **Element Value** | Variable name to store response
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class MakeRequestAction extends Action
{
    private Logger logger = LoggerFactory.getLogger(getClass());

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

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try
        {
            String url = this.element;
            String responseVar = this.elementValue;
            logger.info("Making request to " + url);
            client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            Set<Cookie> cookies = webDriver.manage().getCookies();
            for (Iterator<Cookie> iterator = cookies.iterator(); iterator.hasNext();)
            {
                Cookie cookie = iterator.next();
                request.setHeader(cookie.getName(), cookie.getValue());
            }

            response = client.execute(request);
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            WebDriverUtil.getInstance().setVariable(responseVar, content);
        }
        catch (Exception e)
        {
            throw new ScriptException("Error making request: " + e.getMessage());
        }
        finally
        {
            if (client != null)
            {
                try
                {
                    client.close();
                }
                catch (IOException e)
                {
                    logger.warn("MakeRequest: Error on client close");
                }
            }

            if (response != null)
            {
                try
                {
                    response.close();
                }
                catch (IOException e)
                {
                    logger.warn("MakeRequest: Error on response close");
                }
            }

        }
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if ((this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Preprocess values will be ignored");
            response.addMessage(str);
        }
    }

}
