/**
 * 
 */
package in.teamnexus.excelenium.service;

import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;

/**
 * @author Prabhu
 *
 */
public interface SuiteService
{
    void saveConfig(SuiteConfig config);
    void runSuite(String suiteName) throws ConfigException;
}
