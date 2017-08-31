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
	public String getCrmPayment(String date) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		String sql = "";
		double coun  = 0 ;
		try {
			conn = CRMConnect.getconnect();
			stmt = conn.createStatement();
				sql ="select Convert(decimal(18,2),sum(new_amount)/10000) as coun from new_ord_billing t \n" +
						"where datediff(dd,t.new_billingdate,'"+
						date+
						"')=0 and t.new_approvalstatus='3'\n" +
						"and t.new_status='4' and  t.new_bill_confirmcode is not null and t.new_organizationidname ='徐工重型'";
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


	//当月每日回款额折线图
	public String getCrmMonthPayment(String date){
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
						"where datediff(mm,t.new_billingdate,'"+
						date+
						"')=0 and t.new_approvalstatus='3'\n" +
						"and t.new_status='4'  and  t.new_bill_confirmcode is not null and t.new_organizationidname ='徐工重型'\n" +
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
	public String getCrmYearPayment(String date){
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
					"where datediff(yy,t.new_billingdate,'"+
					date+
					"')=0 and t.new_approvalstatus='3'\n" +
					"and t.new_status='4'  and  t.new_bill_confirmcode is not null and t.new_organizationidname ='徐工重型'\n" +
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
