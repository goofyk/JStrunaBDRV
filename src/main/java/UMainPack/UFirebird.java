package UMainPack;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import java.lang.*;


public class UFirebird {

    public static void main(String args[]){

        try {
            Connection connection = getConnection();
            if(connection == null) {
                System.out.println("Don't get connection!");
                return;
            }

            Statement stmt = connection.createStatement();
            String sql = "SELECT FIRST 2 rd.P_VALUE FROM GET_LAST_CONF('12-SEP-2018 00:00:00', '0') AS RD WHERE NOT UPPER(RD.P_NAME) CONTAINING 'USER_NAME'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String P_VALUE  = rs.getString("P_VALUE");
                byte[] P_VALUE_b  = rs.getBytes("P_VALUE");
                String P_VALUE_b_s = new String(P_VALUE_b);
//                byte ptext[] = P_VALUE.getBytes("UTF8");
//                String newString = new String(ptext, "UTF8");

                System.out.println("P_VALUE: " + P_VALUE);
                System.out.println("P_VALUE_b: " + P_VALUE_b);
                System.out.println("P_VALUE_b_String: " + P_VALUE_b.toString());
                System.out.println("P_VALUE_b_String: " + P_VALUE_b_s);
//                System.out.println("newString: " + newString);
            }

            rs.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){

        try{

            Properties props = new Properties();
            props.setProperty("user", "SYSDBA");
            props.setProperty("password", "masterkey");
            props.setProperty("encoding", "ASCII");

            Connection connection = DriverManager.getConnection(
                    "jdbc:firebirdsql://localhost:3050/D:/ULTRA/STRUNA/ARM.fdb",
                    props);

            return connection;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

}
