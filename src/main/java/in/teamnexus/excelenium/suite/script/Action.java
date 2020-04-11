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

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * The class that defines any of the actions that could be performed on a web element.
 */
public class Action implements Executable
{

    /**
     * The ActionType enum defines the different actions that can be performed on a web element. The following table
     * provides information on how the columns from the excel sheet are interpreted by the various actions.
     * 
     * <table>
     * <tr>
     * <td><b>Action-Type</b></td>
     * <td><b>Element</b></td>
     * <td><b>Element Value</b></td>
     * <td><b>Attribute Name</b></td>
     * <td><b>Attribute Value</b></td>
     * </tr>
     * <tr>
     * <td>FILL</td>
     * <td>id or xpath of the element</td>
     * <td>Value to be filled in that element</td>
     * <td>True/False to indicate if the field needs to be submitted i.e., Enter key pressed. For example, "Search". Default "FALSE"</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CLEAR</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CLICK</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>RIGHT_CLICK</td>
     * <td>id or xpath of the element</td>
     * <td>0 based index indicating the option to be chosen in case of a native browser context menu, -ve value in case
     * of a Javascript generated context menu</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CHECK</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SELECT</td>
     * <td>id or xpath of the element</td>
     * <td>Text value of the item to be selected or "&lt;#random&gt;" to randomly select one of the items in the
     * dropdown</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>VERIFY_TEXT</td>
     * <td>id or xpath of the element</td>
     * <td>Text value to be verified</td>
     * <td>one of following options - <b>starts_with:</b> compares if the text of the element starts with the provided
     * text. <b>ends_with:</b> compares if the text element ends with the provided text.<b>contains:</b>checks if the
     * text of the element contains the provided text<b>full_text:</b> compares the text of the element to the provided
     * text</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>VERIFY_PRESENT</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>IS_VISIBLE</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>IS_HIDDEN</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CHECK_ATTRIBUTE</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>Name of the attribute</td>
     * <td>Value of the attribute</td>
     * </tr>
     * <tr>
     * <td>ACCEPT_POPUP</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>DISMISS_POPUP</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SWITCH_TO_IFRAME</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SWITCH_TO_PARENT</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CAPTURE_SCREEN</td>
     * <td>Optional filename</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>NAVIGATE</td>
     * <td>back, forward, refresh or url</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SET_VARIABLE</td>
     * <td>xpath of the element or variable name</td>
     * <td>variable name in case xpath set as element or variable value otherwise</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>UNSET_VARIABLE</td>
     * <td>variable name to be unset</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CLEAR_COOKIES</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>DELETE_COOKIE</td>
     * <td>Cookie name</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>ADD_COOKIE</td>
     * <td>Cookie name</td>
     * <td>Cookie Value</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SWITCH_TO_WINDOW</td>
     * <td>None</td>
     * <td>0 based index, where 0 always indicates the main window and subsequent windows opened are numbers
     * sequentially</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>EXECUTE_JAVASCRIPT</td>
     * <td>js file</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>WAIT_MSECS</td>
     * <td>None</td>
     * <td>Time to wait in milliseconds before executing next action</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>IS_ENABLED</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>IS_DISABLED</td>
     * <td>id or xpath of the element</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SET_WINDOW_SIZE</td>
     * <td>Positive integer value specifying the width</td>
     * <td>Positive integer value specifying the height</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>COMPARE_URL</td>
     * <td>Url to compare against the current browser url</td>
     * <td>one of following options - <b>starts_with:</b> compares if the current browser url starts with the provided
     * url. The url might contain additional query parameters that might need to be ignored. <b>ends_with:</b> compares
     * if the current browser url ends with the provided url.<b>contains:</b>checks if the browser url contains the
     * provided text<b>full_url:</b> compares the entire url</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>RUN_SCRIPT</td>
     * <td>bsh or groovy script</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>GET_DOM</td>
     * <td>id or xpath of the element</td>
     * <td>variable name</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SCROLL_WINDOW_BY</td>
     * <td>x pixels to scroll</td>
     * <td>y pixels to scroll</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>SCROLL_TO_ELEMENT</td>
     * <td>id or xpath of the element to scroll to</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>MAKE_REQUEST</td>
     * <td>url to request</td>
     * <td>variable name to store the response</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>HOVER</td>
     * <td>id or xpath of the element to hover</td>
     * <td>None</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>DRAG_AND_DROP</td>
     * <td>id or xpath of the source element</td>
     * <td>id or xpath of the target element</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>GET_CURRENT_URL</td>
     * <td>Variable name to save the current url</td>
     * <td>NONE</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>HAS_CSS_CLASS</td>
     * <td>id or xpath of the source element</td>
     * <td>comma separated list of css classes to verify</td>
     * <td>None</td>
     * <td>None</td>
     * </tr>
     * <tr>
     * <td>CHECK_CSS_ATTRIBUTE</td>
     * <td>id or xpath of the source element</td>
     * <td>css attribute</td>
     * <td>css attribute value</td>
     * <td>None</td>
     * </tr>
     * 
     * </table>
     * 
     * 
     * @author Prabhu
     * 
     */
    public enum ActionType
    {

        /** Fills the value in the for the element selected, usually, text field or text area. Optionally can also do a submit in case of search boxes etc. */
        FILL,
        /** Clears the content of the element selected, usually, text field or text area. */
        CLEAR,
        /** Generates a mouse click for the element selected. */
        CLICK,
        /** Generates a mouse right-click for the element selected. */
        RIGHT_CLICK,
        /** Toggles the check box. */
        CHECK,
        /**
         * Selects the option in the drop down as specified in the Element Value column. A special variable <#random>
         * can be used to randomly select one of the values in the dropdown.
         */
        SELECT,
        /** Verifies the text content of the element. */
        VERIFY_TEXT,
        /** Verifies if an element is present in the DOM use IS_VISIBLE to check if the element is displayed. */
        VERIFY_PRESENT,
        /** Verifies if an element is displayed and not hidden. */
        IS_VISIBLE,
        /** Verifies if an element is hidden and not displayed. */
        IS_HIDDEN,
        /** Checks if the attribute is with the value specified is set for the element. */
        CHECK_ATTRIBUTE,
        /** Selects OK/Yes when the browser popup is displayed. */
        ACCEPT_POPUP,
        /** Selects Cancel/No when the browser popup is displayed. */
        DISMISS_POPUP,
        /** Switch to iframe specified. */
        SWITCH_TO_IFRAME,
        /** Switch to parent from iframe. */
        SWITCH_TO_PARENT,
        /** Captures the currently rendered screen in the browser. */
        CAPTURE_SCREEN,
        /** Navigates back/forward/to a particular url. */
        NAVIGATE,
        /** Sets a variable that can be later substituted in the script. */
        SET_VARIABLE,
        /** unsets a variable that has already been set. */
        UNSET_VARIABLE,
        /** clears all the cookies that are currently present */
        CLEAR_COOKIES,
        /** deletes the specified cookie */
        DELETE_COOKIE,
        /** adds the specified cookie with the given value */
        ADD_COOKIE,
        /** Switches to the specified window/tab - based on index from 0 to n. '0' always refers to the main window */
        SWITCH_TO_WINDOW,
        /** Executes a specified javascript file. */
        EXECUTE_JAVASCRIPT,
        /** Wait for the specified milliseconds before performing the next action. */
        WAIT_MSECS,
        /** Checks if the specified element is enabled. */
        IS_ENABLED,
        /** Checks if the specified element is disabled. */
        IS_DISABLED,
        /** Sets the window size of the browser based on the width and height specified. */
        SET_WINDOW_SIZE,
        /** Compares the current browser url with the provided url considering the options - starts_with and full_url. */
        COMPARE_URL,
        /**
         * Runs the provided beanshell script or groovy script. For beanshell, Refer: <a href="http://www.beanshell.org"
         * target="_blank">www.beanshell.org</a>. For Groovy script. Refer: <a href="http://groovy.codehaus.org"
         * target="_blank">groovy.codehaus.org</a>. The script is supplied with all the variable created using
         * ActionType.SET_VARIABLE and a few other internal variables in a map named "inputMap" that can be referenced
         * in the script. The logger object is also available in the name "logger" that can be used to print debug
         * messages. The script will have to create a HashMap in the name "result" and store all the results that it
         * wants printed in the log after execution. Also the "result" hashmap object should have an entry "status"
         * which is either true or false. The value true indicates the script executed successfully and false if there
         * were errors in the expected output.
         */
        RUN_SCRIPT,
        /** Gets the dom of the specified element and stores in the variable */
        GET_DOM,
        /** scrolls the window by specified x and y */
        SCROLL_WINDOW_BY,
        /** scrolls the window to the specified web element */
        SCROLL_TO_ELEMENT,
        /** Makes a GET request and stores the response in the variable name specified */
        MAKE_REQUEST,
        /** Hovers the mouse on the specified element */
        HOVER,
        /** Drag and drop the source element to target element */
        DRAG_AND_DROP,
        /** Gets the current url of the current focused window and saves it to the variable specified */
        GET_CURRENT_URL,
        /** Checks if the specified element has the css classes applied */
        HAS_CSS_CLASS,
        /** Checks if the css attribute of the element has the specified value */
        CHECK_CSS_ATTRIBUTE

    }

    /** The action name. */
    private String actionName;

    /** The action type. */
    private String actionType;

    /** The element to select - either XPATH or id. */
    private String element;

    /** The element value - used in case of FILL and VERIFY_TEXT. */
    private String elementValue;

    /** The attribute name of the element. */
    private String attributeName;

    /** The attribute value of the element. */
    private String attributeValue;

    /** Whether to stop the script on execution. */
    private boolean isStopOnError;

    /** Whether to execute the action in the script. */
    private boolean isExecute;

    /**
     * PreProcessAction to be performed before executing the action on this element- .
     */
    PreProcessAction preProcess;

    // Removing post process for now
    /*
     * PostProcessAction to be performed after executing the action on this element PostProcessAction postProcess;
     */

    /** The ActionType enum. */
    private ActionType aType;

    /** The script object that executes this action. */
    private Script script;

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(Action.class);

    /** The reports logger. */
    @JsonIgnore
    private Logger reportsLogger;

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

    /**
     * Sets the action type - should be one of the values in ActionType.
     * 
     * @param actionType
     *            the new action type
     */
    public void setActionType(String actionType)
    {
        this.actionType = actionType;
        if (actionType.equalsIgnoreCase("fill"))
        {
            aType = ActionType.FILL;
        }
        else if (actionType.equalsIgnoreCase("clear"))
        {
            aType = ActionType.CLEAR;
        }
        else if (actionType.equalsIgnoreCase("click"))
        {
            aType = ActionType.CLICK;
        }
        else if (actionType.equalsIgnoreCase("right_click"))
        {
            aType = ActionType.RIGHT_CLICK;
        }
        else if (actionType.equalsIgnoreCase("check"))
        {
            aType = ActionType.CHECK;
        }
        else if (actionType.equalsIgnoreCase("select"))
        {
            aType = ActionType.SELECT;
        }
        else if (actionType.equalsIgnoreCase("verify_text"))
        {
            aType = ActionType.VERIFY_TEXT;
        }
        else if (actionType.equalsIgnoreCase("verify_present"))
        {
            aType = ActionType.VERIFY_PRESENT;
        }
        else if (actionType.equalsIgnoreCase("is_visible"))
        {
            aType = ActionType.IS_VISIBLE;
        }
        else if (actionType.equalsIgnoreCase("is_hidden"))
        {
            aType = ActionType.IS_HIDDEN;
        }
        else if (actionType.equalsIgnoreCase("check_attribute"))
        {
            aType = ActionType.CHECK_ATTRIBUTE;
        }
        else if (actionType.equalsIgnoreCase("accept_popup"))
        {
            aType = ActionType.ACCEPT_POPUP;
        }
        else if (actionType.equalsIgnoreCase("dismiss_popup"))
        {
            aType = ActionType.DISMISS_POPUP;
        }
        else if (actionType.equalsIgnoreCase("switch_to_iframe"))
        {
            aType = ActionType.SWITCH_TO_IFRAME;
        }
        else if (actionType.equalsIgnoreCase("switch_to_parent"))
        {
            aType = ActionType.SWITCH_TO_PARENT;
        }
        else if (actionType.equalsIgnoreCase("capture_screen"))
        {
            aType = ActionType.CAPTURE_SCREEN;
        }
        else if (actionType.equalsIgnoreCase("navigate"))
        {
            aType = ActionType.NAVIGATE;
        }
        else if (actionType.equalsIgnoreCase("set_variable"))
        {
            aType = ActionType.SET_VARIABLE;
        }
        else if (actionType.equalsIgnoreCase("unset_variable"))
        {
            aType = ActionType.UNSET_VARIABLE;
        }
        else if (actionType.equalsIgnoreCase("clear_cookies"))
        {
            aType = ActionType.CLEAR_COOKIES;
        }
        else if (actionType.equalsIgnoreCase("delete_cookie"))
        {
            aType = ActionType.DELETE_COOKIE;
        }
        else if (actionType.equalsIgnoreCase("add_cookie"))
        {
            aType = ActionType.ADD_COOKIE;
        }
        else if (actionType.equalsIgnoreCase("switch_to_window"))
        {
            aType = ActionType.SWITCH_TO_WINDOW;
        }
        else if (actionType.equalsIgnoreCase("execute_javascript"))
        {
            aType = ActionType.EXECUTE_JAVASCRIPT;
        }
        else if (actionType.equalsIgnoreCase("wait_msecs"))
        {
            aType = ActionType.WAIT_MSECS;
        }
        else if (actionType.equalsIgnoreCase("is_enabled"))
        {
            aType = ActionType.IS_ENABLED;
        }
        else if (actionType.equalsIgnoreCase("is_disabled"))
        {
            aType = ActionType.IS_DISABLED;
        }
        else if (actionType.equalsIgnoreCase("set_window_size"))
        {
            aType = ActionType.SET_WINDOW_SIZE;
        }
        else if (actionType.equalsIgnoreCase("compare_url"))
        {
            aType = ActionType.COMPARE_URL;
        }
        else if (actionType.equalsIgnoreCase("run_script"))
        {
            aType = ActionType.RUN_SCRIPT;
        }
        else if (actionType.equalsIgnoreCase("get_dom"))
        {
            aType = ActionType.GET_DOM;
        }
        else if (actionType.equalsIgnoreCase("scroll_window_by"))
        {
            aType = ActionType.SCROLL_WINDOW_BY;
        }
        else if (actionType.equalsIgnoreCase("scroll_to_element"))
        {
            aType = ActionType.SCROLL_TO_ELEMENT;
        }
        else if (actionType.equalsIgnoreCase("make_request"))
        {
            aType = ActionType.MAKE_REQUEST;
        }
        else if (actionType.equalsIgnoreCase("hover"))
        {
            aType = ActionType.HOVER;
        }
        else if (actionType.equalsIgnoreCase("drag_and_drop"))
        {
            aType = ActionType.DRAG_AND_DROP;
        }
        else if (actionType.equalsIgnoreCase("get_current_url"))
        {
            aType = ActionType.GET_CURRENT_URL;
        }
        else if (actionType.equalsIgnoreCase("has_css_class"))
        {
            aType = ActionType.HAS_CSS_CLASS;
        }
        else if (actionType.equalsIgnoreCase("check_css_attribute"))
        {
            aType = ActionType.CHECK_CSS_ATTRIBUTE;
        }

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

    //    /**
    //     * Gets the post process.
    //     * 
    //     * @ return the post process
    //     */
    //    public PostProcessAction getPostProcess()
    //    {
    //        return this.postProcess;
    //    }
    //
    //    /**
    //     * Sets the post process.
    //     * 
    //     * @==  param postProcess
    //     *            the new post process
    //     */
    //    public void setPostProcess(PostProcessAction postProcess)
    //    {
    //        this.postProcess = postProcess;
    //        if (this.postProcess != null)
    //        {
    //            this.postProcess.setAction(this);
    //        }
    //    }

    /**
     * Execute.
     *
     * @param webDriver the web driver
     * @throws ScriptException the script exception
     */
    @Override
    public void execute(WebDriver webDriver) throws ScriptException
    {
        boolean hasError = false;
        boolean hasWarning = false;
        String warningMsg = null;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try
        {
            this.element = WebDriverUtil.substitute(this.element);
            this.elementValue = WebDriverUtil.substitute(this.elementValue);
            this.attributeName = WebDriverUtil.substitute(this.attributeName);
            this.attributeValue = WebDriverUtil.substitute(this.attributeValue);
            if (isExecuteWithoutFindElement())
            {
                doActionsWithoutFindElement(webDriver);
                return;
            }

            if (aType == ActionType.SET_VARIABLE && !this.element.startsWith("/"))
            {
                WebDriverUtil.setVariable(this.element, this.elementValue);
                return;
            }

            WebElement webElement = getWebElement(webDriver, this.element);

            if (aType == ActionType.SWITCH_TO_IFRAME)
            {
                try
                {
                    webDriver.switchTo().frame(webElement);
                }
                catch (Exception e)
                {
                    logger.error("Action: " + this.getFullyQualifiedName() + " failed - Cannot frame " + this.element);
                }

                // no other process required
                return;
            }
            if (preProcess != null)
            {
                logger.debug(preProcess.toString());
                preProcess.setReportsLogger(reportsLogger);
                preProcess.execute(webDriver, webElement);
            }

            switch (aType)
            {
                case FILL:
                    String fillValue = this.elementValue;
                    webElement.sendKeys(fillValue);
                    if("true".equalsIgnoreCase(attributeName))
                    {
                        webElement.submit();
                    }
                    break;

                case CLEAR:
                    webElement.clear();
                    break;

                case CLICK:
                case CHECK:
                {
                    logger.debug("aType: " + aType + "WebElement:" + webElement.toString());
                    WebDriverUtil.clickElement(webDriver, webElement);
                    break;
                }

                case RIGHT_CLICK:
                {
                    Actions actions = new Actions(webDriver);
                    actions = actions.contextClick(webElement);
                    int itemToSelect = -1;
                    if (this.elementValue != null && !this.elementValue.isEmpty())
                    {
                        itemToSelect = Integer.parseInt(this.elementValue);
                    }
                    if (itemToSelect >= 0)
                    {
                        for (int i = 0; i < itemToSelect; i++)
                        {
                            actions.sendKeys(Keys.ARROW_DOWN);
                        }
                        actions.sendKeys(Keys.ENTER);
                    }
                    actions.perform();
                    break;
                }

                case SELECT:
                {
                    String selText = this.elementValue;
                    Select select = new Select(webElement);
                    if ("<#random>".equalsIgnoreCase(elementValue))
                    {
                        int size = select.getOptions().size();
                        Random rand = new Random();
                        int selIdx = rand.nextInt(size);
                        logger.debug("Selected Index:" + selIdx);
                        select.selectByIndex(selIdx);
                        logger.debug("Item Selected:" + select.getFirstSelectedOption().getText());
                    }
                    else
                    {
                        logger.debug("Select text:" + selText);
                        select.selectByVisibleText(selText);
                        logger.debug("Item Selected:" + select.getFirstSelectedOption().getText());
                    }
                    break;
                }

                case VERIFY_PRESENT:
                {
                    if (webElement != null)
                    {
                        WebDriverUtil.highlightElement(webDriver, webElement);
                    }
                    break;
                }

                case VERIFY_TEXT:
                {
                    if (webElement != null)
                    {
                        String verifyText = this.elementValue;
                        String op = (this.attributeName == null || this.attributeName.isEmpty()) ? "full_text" : this.attributeName;
                        boolean success = false;
                        String elementText = webElement.getText().trim();

                        if ("full_text".equalsIgnoreCase(op))
                        {
                            success = elementText.equals(verifyText);
                        }
                        else if ("starts_with".equalsIgnoreCase(op))
                        {
                            success = elementText.startsWith(verifyText);
                        }
                        else if ("ends_with".equalsIgnoreCase(op))
                        {
                            success = elementText.endsWith(verifyText);
                        }
                        else if ("contains".equalsIgnoreCase(op))
                        {
                            success = elementText.contains(verifyText);
                        }

                        if (success)
                        {
                            WebDriverUtil.highlightElement(webDriver, webElement);
                        }
                        else
                        {
                            if (this.isStopOnError)
                            {
                                throw new ScriptException("Stopping script as script cannot find element "
                                        + this.element + " that " + op + " value " + this.elementValue);

                            }
                            else
                            {
                                hasWarning = true;
                                warningMsg = "Cannot find element " + this.element + "that " + op + " value "
                                        + this.elementValue;
                            }
                        }
                    }
                    break;
                }

                case IS_VISIBLE:
                {

                    if (webElement.isDisplayed())
                    {
                        reportsLogger.info(this.getFullyQualifiedName() + " found " + this.element + " visible");
                    }
                    else
                    {
                        if (this.isStopOnError)
                        {
                            throw new ScriptException("Stopping script as script could not find visible element ");
                        }
                        else
                        {
                            hasWarning = true;
                            warningMsg = " could not find visible element " + this.element;
                        }
                    }
                    break;
                }

                case IS_HIDDEN:
                {

                    if (!webElement.isDisplayed())
                    {
                        reportsLogger.info(this.getFullyQualifiedName() + " found " + this.element + " hidden");
                    }
                    else
                    {
                        if (this.isStopOnError)
                        {
                            throw new ScriptException("Stopping script as " + this.element + " is visible");
                        }
                        else
                        {
                            hasWarning = true;
                            warningMsg = " visible element " + this.element;
                        }
                    }
                    break;
                }

                case CHECK_ATTRIBUTE:
                {
                    String attr = webElement.getAttribute(this.attributeName).trim();
                    if (attr != null)
                    {
                        if (attr.equals(this.attributeValue))
                        {
                            reportsLogger.info("Attribute: " + this.attributeName + " with value: "
                                    + this.attributeValue + " found");
                        }
                        else
                        {
                            if (this.isStopOnError)
                            {
                                throw new ScriptException("Stopping script as  Attribute: " + this.attributeName
                                        + " value: " + attr + " does not match " + this.attributeValue);

                            }
                            else
                            {
                                hasWarning = true;
                                warningMsg = "Attribute: " + this.attributeName + " value: " + attr
                                        + " does not match " + this.attributeValue;
                            }
                        }

                    }
                    else
                    {
                        if (this.isStopOnError)
                        {
                            throw new ScriptException("Stopping script as " + this.attributeName + " not found");
                        }
                        else
                        {
                            hasWarning = true;
                            warningMsg = "Attribute: " + this.attributeName + " not found";
                        }
                    }
                    break;
                }

                case SET_VARIABLE:
                {
                    if (webElement != null)
                    {
                        String variableValue = webElement.getText().trim();
                        String variableName = this.elementValue;
                        WebDriverUtil.setVariable(variableName, variableValue);
                    }
                    break;
                }

                case EXECUTE_JAVASCRIPT:
                {
                    WebDriverUtil.executeJavascript(webDriver, this.elementValue);
                    break;
                }

                case IS_ENABLED:
                {
                    if (webElement.isEnabled())
                    {
                        WebDriverUtil.highlightElement(webDriver, webElement);
                        reportsLogger.info(this.getFullyQualifiedName() + " found " + this.element + " enabled");
                    }
                    else
                    {
                        if (this.isStopOnError)
                        {
                            throw new ScriptException("Stopping script as " + this.element + " disabled");
                        }
                        else
                        {
                            hasWarning = true;
                            warningMsg =  "Found " + this.element + " disabled";
                        }
                    }
                }

                case IS_DISABLED:
                {
                    if (!webElement.isEnabled())
                    {
                        WebDriverUtil.highlightElement(webDriver, webElement);
                        reportsLogger.info(this.getFullyQualifiedName() + " found " + this.element + " disabled");
                    }
                    else
                    {
                        if (this.isStopOnError)
                        {
                            throw new ScriptException("Stopping script as " + this.element + " is enabled");
                        }
                        else
                        {
                            hasWarning = true;
                            warningMsg =  "Found " + this.element + " enabled";
                        }
                    }
                }

                case GET_DOM:
                {
                    String dom = webElement.getAttribute("outerHTML");
                    WebDriverUtil.setVariable(this.elementValue, dom);
                    break;
                }

                case SCROLL_TO_ELEMENT:
                {
                    //Coordinates coord = ((RemoteWebElement) webElement).getCoordinates();
                    Point point = webElement.getLocation();//coord.onPage();
                    WebDriverUtil.scrollTo(webDriver, point.getX(), point.getY());
                    break;
                }

                case HOVER:
                {
                    WebDriverUtil.hover(webDriver, webElement);
                    break;
                }

                case DRAG_AND_DROP:
                {
                    WebElement targetElement = getWebElement(webDriver, this.elementValue);
                    WebDriverUtil.dragAndDrop(webDriver, webElement, targetElement);
                    break;
                }

                case HAS_CSS_CLASS:
                {
                    if (elementValue == null || elementValue.isEmpty())
                    {
                        throw new ScriptException("Please specify the css classes to check");
                    }

                    String[] inputCls = elementValue.split(",");

                    ArrayList<String> input = WebDriverUtil.getSanitizedList(inputCls);

                    String cssClass = webElement.getAttribute("class");
                    if (cssClass != null && !cssClass.isEmpty())
                    {
                        String[] elementCss = cssClass.trim().split(" ");
                        ArrayList<String> elementList = WebDriverUtil.getSanitizedList(elementCss);
                        if(elementList.containsAll(input))
                        {
                            reportsLogger.info(this.getFullyQualifiedName() + " found " + input + " classes applied to the element");
                        }
                        else
                        {
                            hasWarning = true;
                            warningMsg = "Specified css classes " + input + " are not applied to the element. Classes found: " + elementList;
                        }
                    }

                    break;
                }

                case CHECK_CSS_ATTRIBUTE:
                {
                    String cssValue = webElement.getCssValue(this.elementValue);
                    if(this.attributeName.equals(cssValue))
                    {
                        reportsLogger.info(this.getFullyQualifiedName() + " element has css attribute " + this.elementValue + " with value: " + cssValue);
                    }
                    else
                    {
                        hasWarning = true;
                        warningMsg = " element does not have css attribute " + this.elementValue + " with value: " + this.attributeName + " found: " + cssValue;
                    }
                    break;
                }

                default:
                    break;

            }

            //            if (postProcess != null)
            //            {
            //                postProcess.setReportsLogger(reportsLogger);
            //                postProcess.execute(webDriver, webElement);
            //            }
        }
        catch (Exception e)
        {
            hasError = true;
            logger.error("Exception in action: ", e);
            reportsLogger.error(this.getFullyQualifiedName() + " with action: " + aType + " failed: " + e.getMessage());
            try
            {
                WebDriverUtil.captureScreenshot(webDriver, (this.actionName + "-" + MDC.get("browser")
                        + System.currentTimeMillis() + ".png"));
            }
            catch (Exception e1)
            {
                logger.error("Error while capturing screenshot");
            }
            if (this.isStopOnError() || script.isStopOnError())
            {
                throw new ScriptException("Error in Action: " + aType);
            }
        }
        finally
        {
            stopwatch.stop();
            if (!hasError && !hasWarning)
            {
                reportsLogger.info(this.getFullyQualifiedName() + " with action: " + aType + " completed in "
                        + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " msecs");
            }
            else if (hasWarning)
            {
                reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
            }
        }

    }

    /**
     * @param webDriver
     * @param elementString
     *            TODO
     * @return
     */
    private WebElement getWebElement(WebDriver webDriver, String elementString)
    {
        WebElement webElement = null;
        WebDriverWait wait = (new WebDriverWait(webDriver, 10));

        if (elementString.startsWith("/"))
        {
            //webElement = webDriver.findElement(By.xpath(this.element));
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementString)));
            WebDriverUtil.highlightElement(webDriver, webElement);
        }
        else
        {
            //webElement = webDriver.findElement(By.id(this.element));
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementString)));
            WebDriverUtil.highlightElement(webDriver, webElement);
        }
        return webElement;
    }

    /**
     * @return
     */
    private boolean isExecuteWithoutFindElement()
    {
        boolean retVal = (aType == ActionType.CAPTURE_SCREEN || aType == ActionType.NAVIGATE
                || aType == ActionType.SWITCH_TO_PARENT || aType == ActionType.UNSET_VARIABLE
                || aType == ActionType.DELETE_COOKIE || aType == ActionType.CLEAR_COOKIES
                || aType == ActionType.ADD_COOKIE || aType == ActionType.SWITCH_TO_WINDOW
                || aType == ActionType.ACCEPT_POPUP || aType == ActionType.DISMISS_POPUP
                || aType == ActionType.WAIT_MSECS || aType == ActionType.SET_WINDOW_SIZE
                || aType == ActionType.COMPARE_URL || aType == ActionType.RUN_SCRIPT
                || aType == ActionType.SCROLL_WINDOW_BY || aType == ActionType.MAKE_REQUEST
                || aType == ActionType.GET_CURRENT_URL || (aType == ActionType.EXECUTE_JAVASCRIPT && (this.element == null || "".equals(this.element))));
        return retVal;
    }

    /**
     * @param webDriver
     * @throws Exception
     * @throws ScriptException
     */
    private void doActionsWithoutFindElement(WebDriver webDriver) throws Exception, ScriptException
    {
        switch (aType)
        {

            case SWITCH_TO_PARENT:
            {
                webDriver.switchTo().defaultContent();
                break;
            }

            case CAPTURE_SCREEN:
            {
                String browser = MDC.get("browser");
                String fileName = ((this.element == null) ? (this.actionName + "-" + browser
                        + System.currentTimeMillis() + ".png") : (browser + this.element));
                WebDriverUtil.captureScreenshot(webDriver, fileName);
                logger.info("Screenshot captured to " + fileName);
                break;
            }

            case NAVIGATE:
            {
                if ("back".equalsIgnoreCase(this.element))
                {
                    webDriver.navigate().back();
                }
                else if ("forward".equalsIgnoreCase(this.element))
                {
                    webDriver.navigate().forward();
                }
                else if ("refresh".equalsIgnoreCase(this.element))
                {
                    webDriver.navigate().refresh();
                }
                else if (element.startsWith("http://") || element.startsWith("https://"))
                {
                    webDriver.navigate().to(this.element);
                }
                else
                {
                    throw new ScriptException("Unsupported navigate option: " + this.element);
                }
                break;
            }

            case UNSET_VARIABLE:
            {
                WebDriverUtil.unsetVariable(this.element);
                break;
            }

            case CLEAR_COOKIES:
            {
                webDriver.manage().deleteAllCookies();
                break;
            }

            case DELETE_COOKIE:
            {
                webDriver.manage().deleteCookieNamed(this.element);
                break;
            }

            case ADD_COOKIE:
            {
                Cookie cookie = new Cookie(this.element, this.elementValue);
                webDriver.manage().addCookie(cookie);
                break;
            }

            case SWITCH_TO_WINDOW:
            {
                WebDriverUtil.switchToWindow(webDriver, this.elementValue);
                break;
            }

            case EXECUTE_JAVASCRIPT:
            {
                WebDriverUtil.executeJavascript(webDriver, this.elementValue);
                break;
            }

            case ACCEPT_POPUP:
            {
                webDriver.switchTo().alert().accept();
                break;
            }

            case DISMISS_POPUP:
            {
                webDriver.switchTo().alert().dismiss();
                break;
            }

            case WAIT_MSECS:
            {
                Thread.sleep(Long.parseLong(this.elementValue));
                break;
            }

            case SET_WINDOW_SIZE:
            {
                WebDriverUtil.setWindowSize(webDriver, Integer.parseInt(this.element), Integer.parseInt(this.elementValue));
                break;
            }

            case COMPARE_URL:
            {
                String currentUrl = webDriver.getCurrentUrl();
                if (this.elementValue.equalsIgnoreCase("starts_with"))
                {
                    if (!currentUrl.startsWith(this.element))
                    {
                        throw new ScriptException("url does not start with" + this.element);
                    }
                }
                else if (this.elementValue.equalsIgnoreCase("full_url"))
                {
                    if (!currentUrl.equals(this.element))
                    {
                        throw new ScriptException("url does not match " + this.element);
                    }
                }
                else if (this.elementValue.equalsIgnoreCase("ends_with"))
                {
                    if (!currentUrl.endsWith(this.element))
                    {
                        throw new ScriptException("url does not end with " + this.element);
                    }
                }
                else if (this.elementValue.equalsIgnoreCase("contains"))
                {
                    if (!currentUrl.contains(this.element))
                    {
                        throw new ScriptException("url does not contain " + this.element);
                    }
                }
                else
                {
                    throw new ScriptException("Unsupported option " + this.elementValue + " for operation " + aType);
                }
                break;
            }

            case RUN_SCRIPT:
            {
                WebDriverUtil.runScript(webDriver, this.element);
                break;
            }

            case SCROLL_WINDOW_BY:
            {
                WebDriverUtil.scrollBy(webDriver, Integer.parseInt(this.element), Integer.parseInt(this.elementValue));
                break;
            }

            case MAKE_REQUEST:
            {
                WebDriverUtil.makeRequest(webDriver, this.element, this.elementValue);
                break;
            }

            case GET_CURRENT_URL:
            {
                WebDriverUtil.getCurrentUrl(webDriver, this.element);
                break;
            }

            default:
                break;
        }
    }

    /**
     * Gets the fully qualified name of the Action including Script.
     * 
     * @return the fully qualified name
     */
    String getFullyQualifiedName()
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
     * Checks if is stop on error - true if the script is to be stopped on error, false otherwise.
     * 
     * @return true, if is stop on error
     */
    public boolean isStopOnError()
    {
        return this.isStopOnError;
    }

    /**
     * Sets the stop on error - true if the script is to be stopped on error, false otherwise.
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
        builder.append(", aType=");
        builder.append(aType);
        builder.append("]");
        return builder.toString();
    }

}
