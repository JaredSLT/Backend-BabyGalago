package tech.tresearchgroup.babygalago.controller.modules;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.palila.model.BaseSettings;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used for dependency injection. It sets up database controllers to be used elsewhere
 */
public class DatabaseModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseModule.class);

    @Provides
    HikariDataSource hikariDataSource(SettingsController settingsController) {
        switch (SettingsEntity.databaseType) {
            case MYSQL -> {
                String username = System.getenv("DB_USER");
                String password = System.getenv("DB_PASS");
                if (username == null) {
                    logger.info("Cannot connect to DB because your DB_USER environment variable is null");
                    System.exit(0);
                } else if (password == null) {
                    logger.info("Cannot connect to DB because your DB_PASS environment variable is null");
                    System.exit(0);
                }
                return getConfig(settingsController, username, password, settingsController.getDatabaseName());
            }
            case SQLITE -> {
                try {
                    return getSqliteConfig("babygalago");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    HikariDataSource getSqliteConfig(String database) throws SQLException {
        boolean createDatabase = !new File(database + ".db").exists();
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        String url = "jdbc:sqlite:" + database + ".db";
        config.setJdbcUrl(url);
        if (createDatabase) {
            Connection conn = DriverManager.getConnection(url);
            conn.close();
        }
        return new HikariDataSource(config);
    }

    HikariDataSource getConfig(SettingsController settingsController, String username, String password, String database) {
        HikariConfig config = new HikariConfig();
        String host = System.getenv("DB_HOST");
        config.setJdbcUrl("jdbc:mariadb://" + host + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(settingsController.getMaxDatabaseConnections());
        config.setMinimumIdle(settingsController.getMinDatabaseConnections());
        return new HikariDataSource(config);
    }
}
