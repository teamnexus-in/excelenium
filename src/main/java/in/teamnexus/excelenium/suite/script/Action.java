/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the MIT license.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.script;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Stopwatch;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.actions.AcceptPopupAction;
import in.teamnexus.excelenium.suite.script.actions.AddCookieAction;
import in.teamnexus.excelenium.suite.script.actions.CaptureScreenAction;
import in.teamnexus.excelenium.suite.script.actions.CheckAttributeAction;
import in.teamnexus.excelenium.suite.script.actions.CheckCssAttributeAction;
import in.teamnexus.excelenium.suite.script.actions.ClearAction;
import in.teamnexus.excelenium.suite.script.actions.ClearCookiesAction;
import in.teamnexus.excelenium.suite.script.actions.ClickCheckAction;
import in.teamnexus.excelenium.suite.script.actions.CompareUrlAction;
import in.teamnexus.excelenium.suite.script.actions.DeleteCookieAction;
import in.teamnexus.excelenium.suite.script.actions.DismissPopupAction;
import in.teamnexus.excelenium.suite.script.actions.DragAndDropAction;
import in.teamnexus.excelenium.suite.script.actions.ExecuteJavascriptAction;
import in.teamnexus.excelenium.suite.script.actions.FillAction;
import in.teamnexus.excelenium.suite.script.actions.GetCurrentUrlAction;
import in.teamnexus.excelenium.suite.script.actions.GetDOMAction;
import in.teamnexus.excelenium.suite.script.actions.HasCssClassAction;
import in.teamnexus.excelenium.suite.script.actions.HoverAction;
import in.teamnexus.excelenium.suite.script.actions.IsDisabledAction;
import in.teamnexus.excelenium.suite.script.actions.IsEnabledAction;
import in.teamnexus.excelenium.suite.script.actions.IsHiddenAction;
import in.teamnexus.excelenium.suite.script.actions.IsVisibleAction;
import in.teamnexus.excelenium.suite.script.actions.MakeRequestAction;
import in.teamnexus.excelenium.suite.script.actions.NavigateAction;
import in.teamnexus.excelenium.suite.script.actions.RightClickAction;
import in.teamnexus.excelenium.suite.script.actions.RunScriptAction;
import in.teamnexus.excelenium.suite.script.actions.ScrollToElementAction;
import in.teamnexus.excelenium.suite.script.actions.ScrollWindowByAction;
import in.teamnexus.excelenium.suite.script.actions.SelectAction;
import in.teamnexus.excelenium.suite.script.actions.SetVariableAction;
import in.teamnexus.excelenium.suite.script.actions.SetWindowSizeAction;
import in.teamnexus.excelenium.suite.script.actions.SwitchToIFrameAction;
import in.teamnexus.excelenium.suite.script.actions.SwitchToParentAction;
import in.teamnexus.excelenium.suite.script.actions.SwitchToWindowAction;
import in.teamnexus.excelenium.suite.script.actions.UnsetVariableAction;
import in.teamnexus.excelenium.suite.script.actions.VerifyPresentAction;
import in.teamnexus.excelenium.suite.script.actions.VerifyTextAction;
import in.teamnexus.excelenium.suite.script.actions.WaitMsecsAction;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * The class that defines any of the actions that could be performed on a web
 * element.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actionType")
@JsonSubTypes({ 
    @JsonSubTypes.Type(name="ACCEPT_POPUP",  value = AcceptPopupAction.class),
    @JsonSubTypes.Type(name="ADD_COOKIE",  value = AddCookieAction.class),
    @JsonSubTypes.Type(name="CAPTURE_SCREEN",  value = CaptureScreenAction.class),
    @JsonSubTypes.Type(name="CHECK",  value = ClickCheckAction.class),
    @JsonSubTypes.Type(name="CHECK_ATTRIBUTE",  value = CheckAttributeAction.class),
    @JsonSubTypes.Type(name="CHECK_CSS_ATTRIBUTE",  value = CheckCssAttributeAction.class),
    @JsonSubTypes.Type(name="CLEAR",  value = ClearAction.class),
    @JsonSubTypes.Type(name="CLEAR_COOKIES",  value = ClearCookiesAction.class),
    @JsonSubTypes.Type(name="CLICK",  value = ClickCheckAction.class),
    @JsonSubTypes.Type(name="COMPARE_URL",  value = CompareUrlAction.class),
    @JsonSubTypes.Type(name="DELETE_COOKIE",  value = DeleteCookieAction.class),
    @JsonSubTypes.Type(name="DISMISS_POPUP",  value = DismissPopupAction.class),
    @JsonSubTypes.Type(name="DRAG_AND_DROP",  value = DragAndDropAction.class),
    @JsonSubTypes.Type(name="EXECUTE_JAVASCRIPT",  value = ExecuteJavascriptAction.class),
    @JsonSubTypes.Type(name="FILL",  value = FillAction.class),
    @JsonSubTypes.Type(name="GET_CURRENT_URL",  value = GetCurrentUrlAction.class),
    @JsonSubTypes.Type(name="GET_DOM",  value = GetDOMAction.class),
    @JsonSubTypes.Type(name="HAS_CSS_CLASS",  value = HasCssClassAction.class),
    @JsonSubTypes.Type(name="HOVER",  value = HoverAction.class),
    @JsonSubTypes.Type(name="IS_DISABLED",  value = IsDisabledAction.class),
    @JsonSubTypes.Type(name="IS_ENABLED",  value = IsEnabledAction.class),
    @JsonSubTypes.Type(name="IS_HIDDEN",  value = IsHiddenAction.class),
    @JsonSubTypes.Type(name="IS_VISIBLE",  value = IsVisibleAction.class),
    @JsonSubTypes.Type(name="MAKE_REQUEST",  value = MakeRequestAction.class),
    @JsonSubTypes.Type(name="NAVIGATE",  value = NavigateAction.class),
    @JsonSubTypes.Type(name="RIGHT_CLICK",  value = RightClickAction.class),
    @JsonSubTypes.Type(name="RUN_SCRIPT",  value = RunScriptAction.class),
    @JsonSubTypes.Type(name="SCROLL_TO_ELEMENT",  value = ScrollToElementAction.class),
    @JsonSubTypes.Type(name="SCROLL_WINDOW_BY",  value = ScrollWindowByAction.class),
    @JsonSubTypes.Type(name="SELECT",  value = SelectAction.class),
    @JsonSubTypes.Type(name="SET_VARIABLE",  value = SetVariableAction.class),
    @JsonSubTypes.Type(name="SET_WINDOW_SIZE",  value = SetWindowSizeAction.class),
    @JsonSubTypes.Type(name="SWITCH_TO_IFRAME",  value = SwitchToIFrameAction.class),
    @JsonSubTypes.Type(name="SWITCH_TO_PARENT",  value = SwitchToParentAction.class),
    @JsonSubTypes.Type(name="SWITCH_TO_WINDOW",  value = SwitchToWindowAction.class),
    @JsonSubTypes.Type(name="UNSET_VARIABLE",  value = UnsetVariableAction.class),
    @JsonSubTypes.Type(name="VERIFY_PRESENT",  value = VerifyPresentAction.class),
    @JsonSubTypes.Type(name="VERIFY_TEXT",  value = VerifyTextAction.class),
    @JsonSubTypes.Type(name="WAIT_MSECS",  value = WaitMsecsAction.class)
    })
public abstract class Action implements Executable
{

     /** The action name. */
    protected String actionName;

    /** The action type. */
    protected String actionType;

    /** The element to select - either XPATH or id. */
    protected String element;

    /** The element value - used in case of FILL and VERIFY_TEXT. */
    protected String elementValue;

    /** The attribute name of the element. */
    protected String attributeName;

    /** The attribute value of the element. */
    protected String attributeValue;

    /** Whether to stop the script on execution. */
    protected boolean isStopOnError;

    /** Whether to execute the action in the script. */
    protected boolean isExecute;

    /**
     * PreProcessAction to be performed before executing the action on this element-
     * .
     */
    PreProcessAction preProcess;

    // // Removing post process for now
    //
    // PostProcessAction to be performed after executing the action on this element
    // PostProcessAction postProcess;
    


    /** The script object that executes this action. */
    protected Script script;

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(Action.class);

    /** The reports logger. */
    @JsonIgnore
    protected Logger reportsLogger;

    /**
     * Gets the action name.
     * 
     * @return the actionName
     */
    public String getActionName()
    {
        return actionName;
    }

    /**
     * Sets the action name.
     * 
     * @param actionName
     *            the actionName to set
     */
    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    /**
     * Gets the action type.
     * 
     * @return the action type
     */
    public String getActionType()
    {
        return this.actionType;
    }

    /*
     * Sets the action type
     * 
     * 
     * @param actionType
     *            the new action type
     *
     * @author Prabhu
     */
    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }

    /**
     * Gets the element.
     * 
     * @return the element
     */
    public String getElement()
    {
        return this.element;
    }

    /**
     * Sets the element.
     * 
     * @param element
     *            - either XPATH or element id
     */
    public void setElement(String element)
    {
        this.element = element;
    }

    /**
     * Gets the element value.
     * 
     * @return the element value
     */
    public String getElementValue()
    {
        return this.elementValue;
    }

    /**
     * Sets the element value.
     * 
     * @param elementValue
     *            the new element value
     */
    public void setElementValue(String elementValue)
    {
        this.elementValue = elementValue;
    }

    /**
     * Gets the attribute name.
     * 
     * @return the attribute name
     */
    public String getAttributeName()
    {
        return this.attributeName;
    }

    /**
     * Sets the attribute name.
     * 
     * @param attributeName
     *            the new attribute name
     */
    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }

    /**
     * Gets the attribute value.
     * 
     * @return the attribute value
     */
    public String getAttributeValue()
    {
        return this.attributeValue;
    }

    /**
     * Sets the attribute value.
     * 
     * @param attributeValue
     *            the new attribute value
     */
    public void setAttributeValue(String attributeValue)
    {
        this.attributeValue = attributeValue;
    }

    /**
     * Gets the pre process.
     * 
     * @return the pre process
     */
    public PreProcessAction getPreProcess()
    {
        return this.preProcess;
    }

    /**
     * Sets the pre process.
     * 
     * @param preProcess
     *            the new pre process
     */
    public void setPreProcess(PreProcessAction preProcess)
    {
        this.preProcess = preProcess;
        if (this.preProcess != null)
        {
            this.preProcess.setAction(this);
        }
    }

    // 
    //  Gets the post process.
    // 
    //  @ return the post process
    // 
    // public PostProcessAction getPostProcess()
    // {
    // return this.postProcess;
    // }
    //
    //
    // Sets the post process.
    //
    //@== param postProcess
    // the new post process
    // 
    // public void setPostProcess(PostProcessAction postProcess)
    // {
    // this.postProcess = postProcess;
    // if (this.postProcess != null)
    // {
    // this.postProcess.setAction(this);
    // }
    // }

    protected abstract boolean executeAction(WebDriver webDriver) throws Exception;

    /**
     * Execute.
     *
     * @param webDriver
     *            the web driver
     * @throws ScriptException
     *             the script exception
     */
    @Override
    public void execute(WebDriver webDriver) throws ScriptException
    {
        boolean hasError = false;
        boolean success = true;
        WebDriverUtil util = WebDriverUtil.getInstance();
        Stopwatch stopwatch = Stopwatch.createStarted();
        try
        {
            this.element = util.substitute(this.element);
            this.elementValue = util.substitute(this.elementValue);
            this.attributeName = util.substitute(this.attributeName);
            this.attributeValue = util.substitute(this.attributeValue);

            success = executeAction(webDriver);

            // if (postProcess != null)
            // {
            // postProcess.setReportsLogger(reportsLogger);
            // postProcess.execute(webDriver, webElement);
            // }
        }
        catch (Exception e)
        {
            hasError = true;
            logger.error("Exception in performing action: ", e);
            reportsLogger.error(this.getFullyQualifiedName() + " with action: " + actionType + " failed: " + e.getMessage());
            try
            {
                
                WebDriverUtil.getInstance().captureScreenshot(webDriver, (this.actionName + "-" + MDC.get("browser") + System.currentTimeMillis() + ".png"));
            }
            catch (Exception e1)
            {
                logger.error("Error while capturing screenshot");
            }
            if (this.isStopOnError() || script.isStopOnError())
            {
                throw new ScriptException("Error in Action: " + actionType);
            }
        }
        finally
        {
            stopwatch.stop();
            if (!hasError && success)
            {
                reportsLogger.info(this.getFullyQualifiedName() + " with action: " + actionType + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " msecs");
            }
        }

    }

    /**
     * Does the PreProcessAction on actions that can specify the preprocess actions.
     * 
     * @param webDriver
     * @param webElement
     */
    public void doPreProcess(WebDriver webDriver, WebElement webElement)
    {
        if (preProcess != null)
        {
            logger.debug(preProcess.toString());
            preProcess.setReportsLogger(reportsLogger);
            preProcess.execute(webDriver, webElement);
        }
    }

    /**
     * Gets the web element.
     *
     * @param webDriver
     *            the web driver
     * @param elementString
     *            the element string
     * @return the web element
     */
    public WebElement getWebElement(WebDriver webDriver, String elementString)
    {
        WebElement webElement = null;
        WebDriverWait wait = (new WebDriverWait(webDriver, 10));
        WebDriverUtil util = WebDriverUtil.getInstance();

        if (elementString.startsWith("/"))
        {
            // webElement = webDriver.findElement(By.xpath(this.element));
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementString)));
            util.highlightElement(webDriver, webElement);
        }
        else
        {
            // webElement = webDriver.findElement(By.id(this.element));
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementString)));
            util.highlightElement(webDriver, webElement);
        }
        return webElement;
    }

   
    /**
     * Gets the fully qualified name of the Action including Script.
     * 
     * @return the fully qualified name
     */
    protected String getFullyQualifiedName()
    {
        return this.script.getName() + "/" + this.getActionName();
    }

    /**
     * Sets the script which contains this action.
     * 
     * @param script
     *            script which contains this action.
     */
    public void setScript(Script script)
    {
        this.script = script;

    }

    /**
     * Gets the reports logger.
     * 
     * @return the reportsLogger
     */
    public Logger getReportsLogger()
    {
        return reportsLogger;
    }

    /**
     * Sets the reports logger.
     * 
     * @param reportsLogger
     *            the reportsLogger to set
     */
    public void setReportsLogger(Logger reportsLogger)
    {
        this.reportsLogger = reportsLogger;
    }

    /**
     * Checks if is stop on error - true if the script is to be stopped on error,
     * false otherwise.
     * 
     * @return true, if is stop on error
     */
    public boolean isStopOnError()
    {
        return this.isStopOnError;
    }

    /**
     * Sets the stop on error - true if the script is to be stopped on error, false
     * otherwise.
     * 
     * @param isStopOnError
     *            true if the script is to be stopped on error, false otherwise
     */
    public void setStopOnError(boolean isStopOnError)
    {
        this.isStopOnError = isStopOnError;
    }

    /**
     * Checks if is execute.
     * 
     * @return true, if is execute
     */
    public boolean isExecute()
    {
        return this.isExecute;
    }

    /**
     * Sets the execute.
     * 
     * @param isExecute
     *            - true if the action is to be executed, skipped if false
     */
    public void setExecute(boolean isExecute)
    {
        this.isExecute = isExecute;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Action [actionName=");
        builder.append(actionName);
        builder.append(", actionType=");
        builder.append(actionType);
        builder.append(", element=");
        builder.append(element);
        builder.append(", elementValue=");
        builder.append(elementValue);
        builder.append(", attributeName=");
        builder.append(attributeName);
        builder.append(", attributeValue=");
        builder.append(attributeValue);
        builder.append(", isStopOnError=");
        builder.append(isStopOnError);
        builder.append(", isExecute=");
        builder.append(isExecute);
        builder.append(", preProcess=");
        builder.append(preProcess);
        builder.append("]");
        return builder.toString();
    }

}
