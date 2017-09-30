package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016-12-15.
 * CRM数据库连接
 */
public  class CRMConnect {
    //集团内网CRM数据库
    private static  String dataurl = "jdbc:sqlserver://10.1.1.83:1433;DatabaseName=XZZX_MSCRM";
    private  static  String user = "xzzxsa";
    private  static  String password = "xcmgcrmAdmin";

    static  {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

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
