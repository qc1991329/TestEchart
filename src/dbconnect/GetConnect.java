package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016-12-15.
 */
public  class GetConnect {
    private static  String dataurl = "jdbc:oracle:thin:@10.1.128.245:1521/xzywdb.com";
    private  static  String user = "XUZHONG";
    private  static  String password = "XUZHONG";

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
