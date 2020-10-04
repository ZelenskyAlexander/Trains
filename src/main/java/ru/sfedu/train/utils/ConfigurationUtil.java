package ru.sfedu.train.utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static ru.sfedu.train.Main.PATH;
import static ru.sfedu.train.constants.Constants.CONFIG_PATH;


/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 *
 * @author Boris Jmailov
 */
public class ConfigurationUtil {

    private static final Properties configuration = new Properties();

    /**
     * Hides default constructor
     *
     *
     */
    private ConfigurationUtil() {
    }


    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException{
        FileInputStream in;
        try {
            in = new FileInputStream(CONFIG_PATH);
        } catch (FileNotFoundException e) {
            in = new FileInputStream(PATH);
        }

        try {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally{
            in.close();
        }
    }
    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }

}
