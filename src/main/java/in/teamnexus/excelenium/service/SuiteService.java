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
     * @return true if successful, false otherwise
     */
    boolean saveSuite(SuiteConfig config);
    
    /**
     * Run suite.
     *
     * @param suiteName the suite name
     * @throws ConfigException the config exception
     */
    void runSuite(String suiteName) throws ConfigException;
    
    /**
     * Returns the suite Json of the specified suiteName
     * @param suiteName - name of the suite
     * @return
     */
    SuiteConfig getSuite(String suiteName);

    ServiceResponse validateSuite(SuiteConfig suite);
}
