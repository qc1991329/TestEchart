package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016-12-15.
 * MES数据库连接
 */
public  class MESConnect {
    //MES数据库连接
    private static  String dataurl = "jdbc:oracle:thin:@10.8.0.33:1521/MES15";
    private  static  String user = "MES";
    private  static  String password = "MES";

    static  {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getconnect(){
        try {
            Connection con = DriverManager.getConnection(dataurl,user,password);
            return con;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }


}
