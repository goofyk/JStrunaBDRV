package UMainPack;

import java.sql.*;
import java.time.LocalDate;

public class UBDRV {

    UBDRV(){
//        System.out.println("Create obj!");
    }

    public void test() {
//        String connectionUrl = "jdbc:sqlserver://SPB99-AR-SQL01;databaseName=GPNA-BDRV_TEST;user=bdrv_asutp_struna;password=Hy7KzH!47rnc";
        String connectionUrl = "jdbc:sqlserver://DESKTOP-J9E3M3L\\SQLEXPRESS;databaseName=GPNA_BDRV_TEST_SHRM;user=sa;password=2";
        Connection connection = this.getConnection(connectionUrl);
        if(connection == null) return;
        this.callProcedure(connection);
    }

    public Connection getConnection(String connectionUrl) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connection is open!");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void callProcedure(Connection connection){
        CallableStatement cs = null;
        try {
            //for (int i = 1; i <= 1; i++){
            ResultSet rs = null;
            cs = connection.prepareCall("{call dbo.Sp_msr_value_send(?,?,?,?)}");
            cs.setInt(1, 19);
            cs.setInt(2, 457);
            cs.setFloat(3, (float)345555.555);
            LocalDate localDate = LocalDate.now();
            System.out.println(localDate);
            cs.setDate(4, Date.valueOf(localDate));
            cs.execute();
            //}
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
