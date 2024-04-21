package com.github.fjbaldon.banksys.data.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionManager {

    INSTANCE;

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private final HikariConfig config = new HikariConfig();
    private final HikariDataSource ds;

    ConnectionManager() {
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/BankSys");
        config.setUsername("banksys");
        config.setPassword("sysknab");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
}



