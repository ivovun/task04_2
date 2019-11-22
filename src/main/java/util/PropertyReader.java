package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyReader {
    private  static Properties properties;

    static {
        properties = new Properties();
        loadProperties(properties, "common.properties");
        loadProperties(properties, "hibernate.properties");
        loadProperties(properties, "jdbc.properties");
    }

    private static void loadProperties(Properties properties, String name) {
        try {
            URL url_common = Thread.currentThread().getContextClassLoader().getResource(name);
            // пришлось проверку поставить  иначе идет  Warning: Method invocation 'getPath' may produce 'NullPointerException'
            if (url_common != null) {
                properties.load(new FileInputStream(url_common.getPath()));
            }
        } catch (IOException e) {
            System.out.println( "Error: "+name+" is absent!!");
        }
    }

    public static String getProperty(String prop) {
        return properties.getProperty(prop);
    }
}