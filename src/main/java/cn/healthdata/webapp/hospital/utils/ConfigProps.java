package cn.healthdata.webapp.hospital.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ConfigProps {
    private static final String CONFIG_PROPERTIES_FILE = "/etc/public_platform/config.properties";
    private static ConfigProps instance;
    private static Logger logger = Logger.getLogger(ConfigProps.class);

    private Properties properties;

    private ConfigProps() {
        init();
    }

    public static synchronized ConfigProps getInstance() {
        if (instance == null) {
            instance = new ConfigProps();
        }

        return instance;
    }

    private void init() {
        properties = new Properties();
        
        try {
        	FileInputStream fis = FileUtils.openInputStream(new File(CONFIG_PROPERTIES_FILE));
            properties.load(fis);
            
            IOUtils.closeQuietly(fis);
        } catch (IOException e) {
        	try {
        		properties.load(ConfigProps.class.getResourceAsStream("/config.properties"));
        	} catch(IOException ex) {}
        	
            logger.error("Error occurs while loading config.properties.", e);
        }
    }

    public String getConfigValue(String configName) {
        return properties.getProperty(configName, "");
    }
}
