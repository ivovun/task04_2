package util;

import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection getConnection() throws RuntimeException {
        try {
            Class.forName(PropertyReader.getProperty("jdbcSqlDriver"));
            return DriverManager.getConnection(PropertyReader.getProperty("jdbcURL"),
                    PropertyReader.getProperty("jdbcUsername"),
                    PropertyReader.getProperty("jdbcPassword"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void setConfigurationPropertyForName(Configuration configuration, String name)  {
        configuration.setProperty(name, PropertyReader.getProperty(name));
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Configuration getConfiguration(Class[] classes) {
        Configuration configuration = new Configuration();
        for (Class concreteClass : classes) {
            configuration.addAnnotatedClass(concreteClass);
        }

        setConfigurationPropertyForName(configuration, "hibernate.dialect");
        setConfigurationPropertyForName(configuration, "hibernate.connection.driver_class");
        setConfigurationPropertyForName(configuration, "hibernate.connection.url");
        setConfigurationPropertyForName(configuration, "hibernate.connection.serverTimezone");
        setConfigurationPropertyForName(configuration, "hibernate.connection.username");
        setConfigurationPropertyForName(configuration, "hibernate.connection.password");
        setConfigurationPropertyForName(configuration, "hibernate.show_sql");
        setConfigurationPropertyForName(configuration, "hibernate.hbm2ddl.auto");

        return configuration;
    }

}
