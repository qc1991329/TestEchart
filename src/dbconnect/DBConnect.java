package dbconnect;

import bean.CityWorkingTime;
import bean.JangSuVehTotal;
import bean.Vehtotal;
import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class DBConnect {

	public String getVehtotal() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<Vehtotal> veList  =  new ArrayList<Vehtotal>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sqlvehtotsl = 
					"select at_province,total from (select t.at_province,sum(t.at_total) as total from AREA_VEHICLE_TOTAL t where t.at_year = 2014 and t.at_province is not null  group by t.at_province order by total DESC) where rownum<=10";
			rs = stmt.executeQuery(sqlvehtotsl);
			while (rs.next()) {
				Vehtotal veobject = new Vehtotal(rs.getString("AT_PROVINCE"),rs.getInt("total"));
				String str = veobject.getProvince();
				if(!(str==null) && !(str.equals("null"))){
					veList.add(veobject);
				}
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
	
	public String getJangsuVehTotal(){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<JangSuVehTotal> jsArrayList = new ArrayList<JangSuVehTotal>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sqlvehtotsl = 
					"select t.at_year,t.at_province,sum(t.at_total) as total from AREA_VEHICLE_TOTAL t where t.at_province = '江苏' group by t.at_year,t.at_province order by t.at_year";
			rs = stmt.executeQuery(sqlvehtotsl);
			while (rs.next()) {
				JangSuVehTotal jangSuVehTotal = new JangSuVehTotal(rs.getInt("at_year"),rs.getInt("total"));
				jsArrayList.add(jangSuVehTotal);
			
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
	public String getWokingtime(){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<CityWorkingTime> jsArrayList = new ArrayList<CityWorkingTime>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sqlvehtotsl =
					"select t.at_city ,sum(t.at_total) as worktime from AREA_WORK_TOTAL t where t.at_province ='江苏' and t.at_year = 2014 group by t.at_city having sum(t.at_total)!=0 order by worktime DESC";
			rs = stmt.executeQuery(sqlvehtotsl);
			while (rs.next()) {
				CityWorkingTime workingTime = new CityWorkingTime(rs.getString("at_city"),rs.getDouble("worktime"));
				jsArrayList.add(workingTime);

			}
			String jsonstring = gson.toJson(jsArrayList);
			return jsonstring;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeconnect(rs,stmt,conn);
		}

	}

	//阿里大于短信验证接口
	public boolean sendsms(String mobile){
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String time = hour+":"+minute+":"+second;
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey = "23550438";
		String secret = "efd64ee5b4101eb45aab398b276a049f";
		TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");
		req.setSmsFreeSignName("XCMG测试");
		req.setSmsParamString("{\"code\":"+"\""+time+"\",\"product\":\"徐工起重在线\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_30210012");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			System.out.println("请求成功,发送手机号码："+mobile);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return rsp.isSuccess();
	}

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
//		String str = new DBConnect().getVehtotal();
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
