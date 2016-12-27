/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Configuration Manager
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class ConfigurationManager {
    private static final Logger log = Logger.getLogger(ConfigurationManager.class);
    private Properties props;
    private boolean portalConfig;

    public static final String PORTAL_CONFIG = "portal.config";
    public static final String PORTAL_CONFIG_NAME = "portal.cfg";
    public static final String VERSION_DEFAULT = "1.0.0.0";

    private ConfigurationManager() {
        portalConfig = false;
        props = loadPortalConfig();

        if (props != null) {
            portalConfig = true;
        }

        Properties p = loadConfig(String.format("/%s", PORTAL_CONFIG_NAME), ConfigurationManager.class);

        if (portalConfig) {
            merge(p, true);

        } else {
            props = p;
        }
    }

    private static class SingletonHelper {
        private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    }

    public static ConfigurationManager getInstance() {
		/*
		 * Java Singleton Design Pattern Best Practices with Examples
		 * http://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-with-examples
		 */
        return SingletonHelper.INSTANCE; // Bill Pugh Singleton Implementation
    }

    public boolean isPortalConfig() {
        return portalConfig;
    }

    public String getProperty(final String key) {
        return getProperty(props, key);
    }

    public String getProperty(final Properties p, final String key) {
        if (p != null) {
            return p.getProperty(key);
        }

        return null;
    }

    public String getProperty(final String key, final String defVal) {
        return getProperty(props, key, defVal);
    }

    public String getProperty(final Properties p, final String key, final String defVal) {
        final String value = getProperty(p, key);

        if (value != null) {
            return value;
        }

        return defVal;
    }

    public void merge(final Properties p) {
        merge(p, false);
    }

    public void merge(final Properties p, final boolean override) {
        if (p == null) {
            return;
        }

        for (Entry<?, ?> entry : p.entrySet()) {
            boolean addEntry = true;

            if (props.containsKey(entry.getKey())) {
                if (override) {
                    props.remove(entry.getKey());

                } else {
                    addEntry = false;
                }
            }

            if (addEntry) {
                props.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Properties loadConfig(final String filePath, final Class<?> clazz) {
        final InputStream resourceAsStream = clazz.getResourceAsStream(filePath);

        Properties p = new Properties();

        try {
            p.load(resourceAsStream);

        } catch (final IOException ex) {
            log.error(String.format("Unable to read configuration from file '%s'!", filePath), ex);
            p = null;

        } finally {
            try {
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }

            } catch (final IOException ex) {
                log.error("Could not close stream!", ex);
                p = null;
            }
        }

        return p;
    }

    public Properties loadPortalConfig() {
        String configFile = System.getProperty(PORTAL_CONFIG);

        if (StringUtils.isNullOrEmpty(configFile)) {
            String currentFolder = FileUtils.getCurrentFolder();

            if (currentFolder.endsWith("/WEB-INF/lib")) {
                String cfgFile = String.format("%s/%s", currentFolder.replace("/WEB-INF/lib", ""), PORTAL_CONFIG_NAME);

                if (FileUtils.existsFile(cfgFile)) {
                    log.debug(String.format("Portal webapp has been detected! Trying to load configuration file %s", cfgFile));
                    configFile = cfgFile;
                }
            }
        }

        if (StringUtils.isNullOrEmpty(configFile)) {
            return null;
        }

        Properties customProps = null;

        log.info(String.format("%s=%s", PORTAL_CONFIG, configFile));
        log.info(String.format("Loading Portal configuration from file %s", configFile));

        FileInputStream fis = null;

        try {
            File file = new File (configFile);
            fis = new FileInputStream(file);
            customProps = new Properties();
            customProps.load(fis);

        } catch (final IOException ex) {
            log.warn(String.format("Unable to read Portal configuration from file '%s'!", configFile));
            customProps = null;

        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }

            } catch (final IOException ex) {
                log.error("Could not close file stream!", ex);
                customProps = null;
            }
        }


        return customProps;
    }

    public static String getVersion(String version) {
        if (StringUtils.isNullOrEmpty(version)) {
            version = VERSION_DEFAULT;
        }

        String[] data = version.replace(".", ":").split(":");

        if (data != null && data.length == 4) {
            version = String.format("%s.%s.%s", data[0], data[1], data[2]);
        }

        return version;
    }

    public static String getBuildNumber(String version) {
        if (StringUtils.isNullOrEmpty(version)) {
            version = VERSION_DEFAULT;
        }

        String[] data = version.replace(".", ":").split(":");

        if (data != null && data.length == 4 && !data[3].equals("0")) {
            return String.format("%s", data[3]);
        }

        return "unknown";
    }
}