package dbconnect;

import bean.VehWorkingRate;
import bean.MonthSalCount;
import bean.VehModelCount;
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

	/*public String getVehtotal() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<Vehtotal> veList  =  new ArrayList<Vehtotal>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sqlvehtotsl =
					"select at_province,total from (select t.at_province,sum(t.at_total) as total from AREA_VEHICLE_TOTAL t where t.at_year = 2017 and t.at_province is not null  group by t.at_province order by total DESC) where rownum<=10";
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
	}*/

	//2017年型号销量前十
	public String getVehModelSalCount() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<VehModelCount> veList  =  new ArrayList<VehModelCount>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sql =
					"select vmi_name ,salcount from (select  m.vmi_id ,m.vmi_name,count(*) as salcount from vehicle_info t join vehicle_model_info m on t.vi_model = m.vmi_id\n" +
							"where t.vi_own_type = 3 and t.vi_is_deleted = 0 \n" +
							"and t.vi_sale_time between to_date('2017-1-01','yyyy-mm-dd') and to_date('2018-1-01','yyyy-mm-dd')\n" +
							"group by m.vmi_id,m.vmi_name\n" +
							"order by salcount DESC) where rownum <= 10";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				VehModelCount veobject = new VehModelCount(rs.getString("vmi_name"),rs.getInt("salcount"));
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


	/*public String getVehtotal() {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<WorkingRate> veList  =  new ArrayList<WorkingRate>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sqlvehtotsl = "WITH TEMP AS\n" +
					" (SELECT OOD_PROVINCE AS REGION,\n" +
					"         ONOFF.VI_ID,\n" +
					"         SUM(OOD_WORKHOURS) AS TOTALWORKHOURS\n" +
					"    FROM ON_OFF_DAY201706 ONOFF\n" +
					"   WHERE 1=1\n" +
					"     AND OOD_PROVINCE IS NOT NULL\n" +
					"     AND VI_OWN_TYPE = 3\n" +
					"     AND OOD_PROVINCE <> 'null'\n" +
					"   GROUP BY OOD_PROVINCE, ONOFF.VI_ID\n" +
					"   ),\n" +
					"VEHICLESTATS AS\n" +
					" (SELECT REGION,VI_TONNAGE_TYPE, VEHICLE.VI_ID, SUM(TOTALWORKHOURS) AS TOTALWORKHOURS\n" +
					"    FROM TEMP\n" +
					"    JOIN VEHICLE_INFO VEHICLE\n" +
					"      ON TEMP.VI_ID = VEHICLE.VI_ID\n" +
					"   WHERE 1 = 1\n" +
					"     AND VEHICLE.VI_IS_DELETED = 0\n" +
					"     AND VI_TERMINAL_MODEL IN\n" +
					"         (SELECT TMM_ID\n" +
					"            FROM TERMINAL_MODEL_INFO\n" +
					"           WHERE TERMINAL_MODEL_INFO.TMM_IS_RELAY = 0)\n" +
					"          AND VEHICLE.VI_SALE_TIME is not null\n" +
					"   GROUP BY REGION,VEHICLE.VI_TONNAGE_TYPE, VEHICLE.VI_ID)\n" +
					"  select PR_NAME,totalWorkHours,VEHICLECOUNT,trunc(rate,2) as rate from (SELECT PR_NAME,\n" +
					"       MAX(PR_CODE) PR_CODE,\n" +
					"          SUM(TOTALWORKHOURS) AS totalWorkHours,          \n" +
					"       COUNT(*) AS VEHICLECOUNT,\n" +
					"           SUM(TOTALWORKHOURS) /COUNT(*) AS  rate\n" +
					"  FROM   PROVINCE_INFO   \n" +
					"  LEFT JOIN VEHICLESTATS ON PR_NAME = REGION\n" +
					" WHERE 1=1\n" +
					"  AND pr_name<>'null'\n" +
					" GROUP BY PR_NAME\n" +
					" having SUM(TOTALWORKHOURS)>0  \n" +
					" ORDER BY totalWorkHours DESC) where rownum <= 10";
			rs = stmt.executeQuery(sqlvehtotsl);
			while (rs.next()) {
				WorkingRate workingRate = new WorkingRate(rs.getString("PR_NAME"),rs.getDouble("totalWorkHours")/1000,rs.getInt("VEHICLECOUNT")/10,rs.getDouble("rate"));
				veList.add(workingRate);
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
	
	public String getVehMonthSalCount(){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		ArrayList<MonthSalCount> jsArrayList = new ArrayList<MonthSalCount>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sql =
					"select to_number(mon) as mon, sum(salcount) as salcount  \n" +
							"from (select to_char(t.vi_sale_time,'mm') as mon , count(*) as salcount  \n" +
							"from vehicle_info t where t.vi_own_type = 3 and t.vi_is_deleted = 0 and t.vi_sale_time is not null and t.vi_sale_time between to_date('2017-1-01','yyyy-mm-dd') and to_date('2018-1-01','yyyy-mm-dd')\n" +
							"group by t.vi_sale_time \n" +
							"order by t.vi_sale_time)\n" +
							"group by mon\n" +
							"order by mon";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MonthSalCount monthSalobject = new MonthSalCount(rs.getInt("mon")+"月",rs.getInt("salcount"));
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
	public String getVehWorkingRate(){
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		Gson gson = new Gson();
		Calendar calendar=Calendar.getInstance();
		//获得当前时间的月份，月份从0开始所以结果要加1
		int month=calendar.get(Calendar.MONTH)+1;
		String strMonth = month>=10?month+"":"0"+month;
		ArrayList<VehWorkingRate> jsArrayList = new ArrayList<VehWorkingRate>();
		try {
			conn = GetConnect.getconnect();
			stmt = conn.createStatement();
			String sqlvehtotsl =
					"WITH TEMP AS\n" +
							" (SELECT OOD_PROVINCE AS REGION,\n" +
							"         ONOFF.VI_ID,\n" +
							"         SUM(OOD_WORKHOURS) AS TOTALWORKHOURS\n" +
							"    FROM ON_OFF_DAY2017"+strMonth+" ONOFF\n" +
							"   WHERE 1=1\n" +
							"     AND OOD_PROVINCE IS NOT NULL\n" +
							"     AND VI_OWN_TYPE = 3\n" +
							"     AND OOD_PROVINCE <> 'null'\n" +
							"   GROUP BY OOD_PROVINCE, ONOFF.VI_ID\n" +
							"   ),\n" +
							"VEHICLESTATS AS\n" +
							" (SELECT REGION,VI_TONNAGE_TYPE, VEHICLE.VI_ID, SUM(TOTALWORKHOURS) AS TOTALWORKHOURS\n" +
							"    FROM TEMP\n" +
							"    JOIN VEHICLE_INFO VEHICLE\n" +
							"      ON TEMP.VI_ID = VEHICLE.VI_ID\n" +
							"   WHERE 1 = 1\n" +
							"     AND VEHICLE.VI_IS_DELETED = 0\n" +
							"     AND VI_TERMINAL_MODEL IN\n" +
							"         (SELECT TMM_ID\n" +
							"            FROM TERMINAL_MODEL_INFO\n" +
							"           WHERE TERMINAL_MODEL_INFO.TMM_IS_RELAY = 0)\n" +
							"          AND VEHICLE.VI_SALE_TIME is not null\n" +
							"          AND VEHICLE.Vi_Own_Type = 3\n" +
							"   GROUP BY REGION,VEHICLE.VI_TONNAGE_TYPE, VEHICLE.VI_ID)\n" +
							"  select PR_NAME,totalWorkHours,VEHICLECOUNT,trunc(rate,2) as rate from (SELECT PR_NAME,\n" +
							"       MAX(PR_CODE) PR_CODE,\n" +
							"          SUM(TOTALWORKHOURS) AS totalWorkHours,          \n" +
							"       COUNT(*) AS VEHICLECOUNT,\n" +
							"           SUM(TOTALWORKHOURS) /COUNT(*) AS  rate\n" +
							"  FROM   PROVINCE_INFO   \n" +
							"  LEFT JOIN VEHICLESTATS ON PR_NAME = REGION\n" +
							" WHERE 1=1\n" +
							"  AND pr_name<>'null'\n" +
							" GROUP BY PR_NAME\n" +
							" having SUM(TOTALWORKHOURS)>0  \n" +
							" ORDER BY VEHICLECOUNT DESC)";
			rs = stmt.executeQuery(sqlvehtotsl);
			while (rs.next()) {
				VehWorkingRate workingRate = new VehWorkingRate(rs.getString("PR_NAME"),rs.getDouble("VEHICLECOUNT"));
				jsArrayList.add(workingRate);

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
