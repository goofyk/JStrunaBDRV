package UMainPack;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class USqlite {

    public static void main(String[] args) {
//        createNewDatabase("log.db");
//        createTableLogs("log.db");
    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connect(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTableLogs(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        String sql = "CREATE TABLE LOGS\n" +
                "   (USER_ID VARCHAR(20)    NOT NULL,\n" +
                "    DATED   DATE           NOT NULL,\n" +
                "    LOGGER  VARCHAR(50)    NOT NULL,\n" +
                "    LEVEL   VARCHAR(10)    NOT NULL,\n" +
                "    MESSAGE VARCHAR(1000)  NOT NULL\n" +
                "   );";
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet getAllData(int countTop) {
        if(countTop == 0) countTop = 2000;
        String sql = "SELECT * FROM LOGS LIMIT " + countTop;
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = connect("log.db");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                System.out.println(rs.getString("USER_ID") +  "\t" +
//                        rs.getDate("DATED") + "\t" +
//                        rs.getString("LOGGER") + "\t" +
//                        rs.getString("LEVEL") + "\t" +
//                        rs.getString("MESSAGE"));
//            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static DefaultTableModel buildTableModel(int countTop) {

        ResultSet rs = getAllData(countTop);

        if(rs == null) return new DefaultTableModel();

        Vector<Vector<Object>> data = null;
        Vector<String> columnNames = null;
        ResultSetMetaData metaData;

        try {
            metaData = rs.getMetaData();
            columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new DefaultTableModel(data, columnNames);

    }

}