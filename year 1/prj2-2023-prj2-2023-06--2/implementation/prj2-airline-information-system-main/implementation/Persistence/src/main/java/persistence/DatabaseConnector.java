package persistence;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class DatabaseConnector {
    static Map<String, DataSource> cache = new HashMap<>();

    public static DataSource getDataSource(final String sourceName) {

        return cache.computeIfAbsent(sourceName, (s) -> {
            Properties props = new Properties();
            try {
                props.load(DatabaseConnector.class.getClassLoader()
                        .getResourceAsStream("persistence.properties"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            PGSimpleDataSource source = new PGSimpleDataSource();
            String prefix = sourceName + ".";
            String[] serverNames = {props.getProperty(prefix + "dbhost")};

            source.setServerNames(serverNames);
            source.setUser(props.getProperty(prefix + "username"));
            source.setDatabaseName(props.getProperty(prefix + "dbname"));
            source.setPassword(props.getProperty(prefix + "password"));
            source.setCurrentSchema(props.getProperty(prefix + "schema"));

            return source;
        });
    }
}
