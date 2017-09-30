package crm;

import bean.MonthSalCount;
import bean.VehModelCount;
import com.google.gson.Gson;
import dbconnect.CRMConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetCrmData {

	//当日发车量(出入库)
	public String getCrmShipout( String vehgo , String date) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		int coun  = 0 ;
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
			if(vehgo.equals("out")){
				sql ="select count(*) as coun \n" +
						"from new_vin_shipout t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where t.new_formstatus=2 and t.statecode=0 and t.new_type!=4 and datediff(dd,t.new_outtime,'" +
						date +
						"')=0 and pm.new_category=1";
			}else{
				sql ="select count(*) as coun \n" +
						"from new_vin_shipin t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where t.new_formstatus=2 and t.statecode=0 and t.new_type=1 and datediff(dd,t.new_entrytime,'"+
						date+
						"')=0 and pm.new_category=1";
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				coun = rs.getInt("coun");
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

	//当日型号发车量统计(出入库)
	public String getVehModelSalCount(String vehgo , String date) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		Gson gson = new Gson();
		ArrayList<VehModelCount> veList  =  new ArrayList<VehModelCount>();
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
			if(vehgo.equals("out")){
				sql ="select\tt.new_vinmodelidname,count(*) coun\n" +
						"from\tnew_vin_shipout t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type!=4 and datediff(dd,t.new_outtime,'" +
						date +
						"')=0 and pm.new_category=1\n" +
						"group by t.new_vinmodelid,t.new_vinmodelidname\n" +
						"order by coun DESC";
			}else{
				sql ="select\tt.new_vinmodelidname,count(*) coun\n" +
						"from\tnew_vin_shipin t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type=1 and datediff(dd,t.new_entrytime,'"+
						date+
						"')=0 and pm.new_category=1\n" +
						"group by t.new_vinmodelid,t.new_vinmodelidname\n" +
						"order by coun DESC";
			}

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				VehModelCount veobject = new VehModelCount(rs.getString("new_vinmodelidname"),rs.getInt("coun"));
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

	//当月每日发车量折线图(出入库)
	public String getVehMonthSalCount(String vehgo , String date){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		Gson gson = new Gson();
		ArrayList<MonthSalCount> jsArrayList = new ArrayList<MonthSalCount>();
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
			if(vehgo.equals("out")){
				sql ="select\tDATEPART(dd, t.new_outtime) as dd , count(*) as mfache\n" +
						"from\tnew_vin_shipout t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type!=4 and datediff(mm,t.new_outtime,'" +
						date +
						"')=0 and pm.new_category=1\n" +
						"group by DATEPART(dd, t.new_outtime)\n" +
						"order by dd";
			}else{
				sql ="select\tDATEPART(dd, t.new_entrytime) as dd , count(*) as mfache\n" +
						"from\tnew_vin_shipin t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type=1 and datediff(mm,t.new_entrytime,'"+
						date+
						"')=0 and pm.new_category=1\n" +
						"group by DATEPART(dd, t.new_entrytime)\n" +
						"order by dd";
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MonthSalCount monthSalobject = new MonthSalCount(rs.getInt("dd")+"",rs.getInt("mfache"));
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


	//当年每月发车量折线图(出入库)
	public String getYearMonthShipOut(String vehgo , String date){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		Gson gson = new Gson();
		ArrayList<MonthSalCount> jsArrayList = new ArrayList<MonthSalCount>();
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
			if(vehgo.equals("out")){
				sql ="select\tDATEPART(mm, t.new_outtime) as mm , count(*) as yfache\n" +
						"from\tnew_vin_shipout t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type!=4 and datediff(yy,t.new_outtime,'" +
						date +
						"')=0 and pm.new_category=1\n" +
						"group by DATEPART(mm, t.new_outtime)\n" +
						"order by mm";
			}else{
				sql ="select\tDATEPART(mm, t.new_entrytime) as mm , count(*) as yfache\n" +
						"from\tnew_vin_shipin t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type=1 and datediff(yy,t.new_entrytime,'"+
						date+
						"')=0 and pm.new_category=1\n" +
						"group by DATEPART(mm, t.new_entrytime)\n" +
						"order by mm";
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MonthSalCount monthSalobject = new MonthSalCount(rs.getInt("mm")+"月",rs.getInt("yfache"));
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
//		String str = new GetCrmData().getCrmShipout();
//		System.out.println(str);
		/*boolean issuccess = false;
		try {
			issuccess = new DBConnect().sendsms("15805212701");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(issuccess)
			System.out.println("验证短信发送成功！");
		else
			System.out.println("发送失败！");*/
	}

}
