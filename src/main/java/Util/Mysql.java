package Util;

import java.sql.*;


public class Mysql {

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://192.168.43.154:3306/Buy", "root", "russell");
//        return DriverManager.getConnection("jdbc:mariadb://172.20.10.6:3306/Buy", "root", "russell");
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }


    public static ResultSet executeSQL(String Sql) throws SQLException {

        ResultSet resultSet = null;
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        try {
            if (Sql.contains("select")) {
                resultSet = statement.executeQuery(Sql);
            } else {
                statement.execute(Sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Mysql.closeAll(conn, statement, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultSet;
    }
}

