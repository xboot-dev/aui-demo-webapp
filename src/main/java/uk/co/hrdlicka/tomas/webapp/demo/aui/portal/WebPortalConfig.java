/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.Properties;

import org.apache.log4j.Logger;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.ConfigurationManager;

/**
 * Web Portal Configuration
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class WebPortalConfig {
    private static final Logger log = Logger.getLogger(WebPortalConfig.class);

    public static final String CONFIG_NAME = "portal.cfg.internal";
    public static final String VERSION = "portal.version";
    public static final String SETTING_DATABASE_VERSION = "portal.database.version";

    public static void loadConfig() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        Properties props = config.loadConfig(String.format("/%s", CONFIG_NAME), WebPortalConfig.class);

        if (props != null) {
            config.merge(props);
        }
    }

    public static String getVersion() {
        String version = ConfigurationManager.getInstance().getProperty(VERSION);
        return ConfigurationManager.getVersion(version);
    }

    public static String getBuildNumber() {
        String version = ConfigurationManager.getInstance().getProperty(VERSION);
        return ConfigurationManager.getBuildNumber(version);
    }
}