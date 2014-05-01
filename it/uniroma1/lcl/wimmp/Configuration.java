package it.uniroma1.lcl.wimmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.io.*;

public class Configuration {
    
    private static final String applicationName = "wimmp";
    private static final String logFile = "log_file";
    private static final String configNotFoundError = "File config.properties not found in the working directory.";
    
    private static ArrayList<String> files = new ArrayList();
    private static HashMap<String, Object> resources = new HashMap();

    static {
        boolean state = true;
        resources.put("application", applicationName);
        resources.put("src", System.getProperty("user.dir"));
        
        Logger log = Logger.getLogger(applicationName);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        log.addHandler(consoleHandler);
        
        files.add("config.properties");
        
        for (String fileName : files) {
            Properties prop = new Properties();
	        InputStream input = null;
         
	        try {
		        input = Configuration.class.getClassLoader().getResourceAsStream(fileName);
		        if (input != null) {
		            prop.load(input);
             
		            Enumeration<?> e = prop.propertyNames();
		            while (e.hasMoreElements()) {
			            String key = (String) e.nextElement();
			            String value = prop.getProperty(key);
			            resources.put(key, value);
		            }
		        } else {
		            log.severe(configNotFoundError);
		            state = false;
		        }
	        } catch (IOException ex) {
	            log.severe(configNotFoundError);
	            state = false;
		        ex.printStackTrace();
	        } finally {
		        if (input != null) {
			        try {
				        input.close();
			        } catch (IOException e) {
				        e.printStackTrace();
			        }
		        }
	        }
        }
        
        if (resources.containsKey(logFile)) {
            String src = (String)resources.get("src");  
            String logFilePath = src + (String)resources.get(logFile);
            try {
                FileHandler fileHandler = new FileHandler(logFilePath);
                consoleHandler.setLevel(Level.OFF);
                log.addHandler(fileHandler);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        resources.put("logger", log);
        
        if(state) {
            log.config("Configuration successful.");
        } else {
            log.config("Configuration failed.");
        }
    }

    public static Object getResource(String name) {
        return resources.get(name);
    }
    
    public static boolean putResource(String name, Object resource) {
        if(!resources.containsKey(name)) {
            resources.put(name, resource);
            return true;
        }
        return false;
    }
}
