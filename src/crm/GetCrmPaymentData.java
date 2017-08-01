package crm;

import bean.CrmMonthPayment;
import com.google.gson.Gson;
import dbconnect.CRMConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetCrmPaymentData {

	//昨日回款额
	public String getCrmPayment() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		double coun  = 0 ;
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
				sql ="select Convert(decimal(18,2),sum(new_amount)/10000) as coun from new_ord_billing t \n" +
						"where datediff(dd,t.new_billingdate,getDate())=1 and t.new_approvalstatus='3'\n" +
						"and t.new_status='4'  and  t.new_bill_confirmcode is not null and t.new_organizationid ='819C5898-9051-E511-95A0-288023ADD5FB'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				coun = rs.getDouble("coun");
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

	//当日型号发车量统计
	/*public String getVehModelSalCount(String vehgo) {
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
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type!=4 and datediff(dd,t.new_outtime,getdate())=0 and pm.new_category=1\n" +
						"group by t.new_vinmodelid,t.new_vinmodelidname\n" +
						"order by coun DESC";
			}else{
				sql ="select\tt.new_vinmodelidname,count(*) coun\n" +
						"from\tnew_vin_shipin t join new_vin_productmodel pm on t.new_vinmodelid = pm.new_vin_productmodelid\n" +
						"where\tt.new_formstatus=2 and t.statecode=0 and t.new_type=1 and datediff(dd,t.new_entrytime,getdate())=0 and pm.new_category=1\n" +
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
	}*/

	//当月每日回款额折线图
	public String getCrmMonthPayment(){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		Gson gson = new Gson();
		ArrayList<CrmMonthPayment> jsArrayList = new ArrayList<CrmMonthPayment>();
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
				sql ="select DATEPART(dd, new_billingdate) as dd,Convert(decimal(18,2),sum(new_amount)/10000) as coun from new_ord_billing t \n" +
						"where datediff(mm,t.new_billingdate,getDate())=1 and t.new_approvalstatus='3'\n" +
						"and t.new_status='4'  and  t.new_bill_confirmcode is not null and t.new_organizationid ='819C5898-9051-E511-95A0-288023ADD5FB'\n" +
						"group by DATEPART(dd, new_billingdate)\n" +
						"order by dd";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CrmMonthPayment monthPayment = new CrmMonthPayment(rs.getString("dd"),rs.getDouble("coun"));
				jsArrayList.add(monthPayment);
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


	//当年每月回款额线图
	public String getCrmYearPayment(){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		Gson gson = new Gson();
		ArrayList<CrmMonthPayment> jsArrayList = new ArrayList<CrmMonthPayment>();
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
			sql ="select DATEPART(mm, new_billingdate) as mm,Convert(decimal(18,2),sum(new_amount)/10000) as coun\n" +
					"from new_ord_billing t \n" +
					"where datediff(yy,t.new_billingdate,getDate())=0 and t.new_approvalstatus='3'\n" +
					"and t.new_status='4'  and  t.new_bill_confirmcode is not null and t.new_organizationid ='819C5898-9051-E511-95A0-288023ADD5FB'\n" +
					"group by DATEPART(mm, new_billingdate)\n" +
					"order by DATEPART(mm, new_billingdate)";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CrmMonthPayment monthPayment = new CrmMonthPayment(rs.getString("mm"),rs.getDouble("coun"));
				jsArrayList.add(monthPayment);
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
//		String str = new GetCrmPaymentData().getCrmMonthPayment();
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
