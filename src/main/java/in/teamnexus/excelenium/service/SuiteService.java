/**
 * 
 */
package in.teamnexus.excelenium.service;

import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;

/**
 * The Interface SuiteService.
 *
 * @author Prabhu
 */
public interface SuiteService
{
    
    /**
     * Save config.
     *
     * @param config the config
     */
    void saveConfig(SuiteConfig config);
    
    /**
     * Run suite.
     *
     * @param suiteName the suite name
     * @throws ConfigException the config exception
     */
    void runSuite(String suiteName) throws ConfigException;
}
