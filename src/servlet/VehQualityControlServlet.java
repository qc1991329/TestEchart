package servlet;

import com.google.gson.Gson;
import dbconnect.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-19.
 */
public class VehQualityControlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String> map = new HashMap<String,String>();
        ArrayList<Map> list = new ArrayList<Map>();
        Gson gson = new Gson();
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strqc = new DBConnect().getVehQualityControl();
        String strss = new DBConnect().getStoreStock();
        map.put("strqc",strqc);
        map.put("strss",strss);
        list.add(map);
        String  json = gson.toJson(list);
        out.print(json);
        out.flush();
        out.close();
    }
}
