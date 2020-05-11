package in.teamnexus.excelenium.suite.script.actionrunner;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

public interface ActionRunner
{
    
    /**
     * Executes the action based on the action type of the implementing classes
     * @param webDriver - webDriver instance
     * @param action - the action object that invokes this ActionRunner instance
     */
    void executeAction(WebDriver webDriver, Action action);
}
