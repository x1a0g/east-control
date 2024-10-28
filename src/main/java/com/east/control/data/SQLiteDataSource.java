package com.east.control.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class SQLiteDataSource implements DataSource {

    private static volatile DataSource dataSource;

    private SQLiteDataSource() {
        // Private constructor to prevent instantiation from outside.
    }

    public static DataSource getInstance() {
        if (dataSource == null) {
            synchronized (SQLiteDataSource.class) {
                if (dataSource == null) {
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl("jdbc:sqlite:D:\\code\\java\\project\\east-control\\db\\east-control.db");
                    config.setDriverClassName("org.sqlite.JDBC");
                    config.setMaximumPoolSize(10); // 设置最大连接数
                    config.setIdleTimeout(30000); // 设置空闲超时时间
                    config.setConnectionTestQuery("SELECT 1"); // 测试连接的SQL语句

                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ((HikariDataSource) dataSource).getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return ((HikariDataSource) dataSource).getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    // 实现其他DataSource方法...
}