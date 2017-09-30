package mes;

import bean.MonthSalCount;
import bean.VehModelCount;
import com.google.gson.Gson;
import dbconnect.MESConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-9-20.
 */
public class GetMesData {

    //获取数量数据
    public String getMesCount(String sql , String column){
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        int coun  = 0 ;
        try {
            conn = MESConnect.getconnect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                coun = rs.getInt(column);
            }
            String str = coun+"";
            return str;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeconnect(rs,stmt,conn);
        }
    }

    //获取折线图数据
    public String getMesLineChart(String sql , String xcolumn , String ycolumn){
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        Gson gson = new Gson();
        ArrayList<MonthSalCount> jsArrayList = new ArrayList<MonthSalCount>();
        try {
            conn = MESConnect.getconnect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MonthSalCount monthSalobject = new MonthSalCount(rs.getInt(xcolumn)+"",rs.getInt(ycolumn));
                jsArrayList.add(monthSalobject);
            }
            String jsonstring = gson.toJson(jsArrayList);
            return jsonstring;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeconnect(rs,stmt,conn);
        }
    }

    //获取柱状图数据
    public String getMesBarChart(String sql , String xcolumn , String ycolumn){
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        Gson gson = new Gson();
        ArrayList<VehModelCount> veList  =  new ArrayList<VehModelCount>();
        try {
            conn = MESConnect.getconnect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                VehModelCount veobject = new VehModelCount(rs.getString(ycolumn),rs.getInt(xcolumn));
                veList.add(veobject);
            }
            String jsonstring = gson.toJson(veList);
            return jsonstring;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeconnect(rs,stmt,conn);
        }
    }

    //关闭数据库连接
    public void closeconnect(ResultSet rs,Statement stmt,Connection conn){
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       /*String str = new GetMesData().getMesZongTiaoCount("20170922");
        System.out.println(str);*/
    }
}
