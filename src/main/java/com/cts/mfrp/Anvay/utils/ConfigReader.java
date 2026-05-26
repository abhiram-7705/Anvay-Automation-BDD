package com.cts.mfrp.Anvay.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props;

    public static void load() {
        if (props != null) 
        	return;

        try {
            String env = System.getProperty("env", "prod");
            String fileName = "config-" + env + ".properties";

            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (input == null) 
            {
                input = ConfigReader.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");
            }

            if (input == null) {
                throw new RuntimeException("No config file found");
            }

            props = new Properties();
            props.load(input);

        } 
        
        catch (IOException e) {
            throw new RuntimeException("Cannot load config: " + e.getMessage());
        }
    }

    public static String get(String key) {
        if (props == null) 
        	load();
        
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) 
        	return sysProp;
        
        String val = props.getProperty(key);
        if (val == null) 
        	throw new RuntimeException("Key not found: " + key);
        
        return val.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}