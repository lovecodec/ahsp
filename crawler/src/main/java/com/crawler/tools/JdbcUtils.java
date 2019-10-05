package com.crawler.tools;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Dr.chen
 */

public class JdbcUtils {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
        properties.load(in);
        System.out.println(properties.getProperty("jdbc.username"));
        System.out.println(properties.getProperty("jdbc.password"));
        System.out.println(properties.getProperty("jdbc.driver"));
        System.out.println(properties.getProperty("jdbc.url"));

    }

    public static Connection getMysqlConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dachuang", "root", "root");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeCon(ResultSet rs, Statement st, Connection con) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeCon(Statement st, Connection con) {
        try {
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
