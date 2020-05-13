/**
 * 
 * \mainpage excelenium
 *
 * excelenium is a Spring Boot based web application that enables running Selenium Automation tests from inside the browser. It is a self-contained jar file that runs in the browser and provides and environment to prepare the scripts and execute them on various browsers, that includes - Mozilla Firefox, Google Chrome, Microsoft Edge, Opera and Safari.
 *
 * It provides interesting features like separate logging and reporting of the tests run in different browsers. It also provides a feature to take screenshot at any particular moment during the test.
 * 
 * Please refer the user manual for preparing and executing the suite and for the prerequisites.
 *
 * This is the documentation for the source code.
 */

/**
 * This is the entry point for the Spring Boot Application - The ExceleniumApplication class. Once the application context is loaded, the requests are handled by the 
 * in.teamnexus.excelenium.controller.  ExceleniumController class which in turn uses the in.teamnexus.excelenium.service.ExceleniumSuiteService for performing various functions
 */
package in.teamnexus.excelenium;