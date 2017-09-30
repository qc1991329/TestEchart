package servlet.mes;

import Util.GetQuerySql;
import mes.GetMesData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017-9-20.
 */
public class MesZongServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String progress = request.getParameter("progress");
        String date = request.getParameter("date");
        String sql = new GetQuerySql().getZongSql(progress,date);
        String str = null;
        PrintWriter out = response.getWriter();
        if(progress.equals("tiao")){
            response.setContentType("application/json; charset=UTF-8");
            str = new GetMesData().getMesBarChart(sql,"tiaoshi","basicmodel");
        }else if(progress.equals("tiaocount")){
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetMesData().getMesCount(sql,"tiaocount");
        }else if(progress.equals("pen")){
            response.setContentType("application/json; charset=UTF-8");
            str = new GetMesData().getMesBarChart(sql,"wan","basicmodel");
        }else if(progress.equals("pencount")){
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetMesData().getMesCount(sql,"pencount");
        }else if(progress.equals("dayin")){
            response.setContentType("application/json; charset=UTF-8");
            str = new GetMesData().getMesBarChart(sql,"dayin","BASICMODEL");
        }else if(progress.equals("incount")){
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetMesData().getMesCount(sql,"incount");
        }else if(progress.equals("dayout")){
            response.setContentType("application/json; charset=UTF-8");
            str = new GetMesData().getMesBarChart(sql,"dayout","BASICMODEL");
        }else if(progress.equals("outcount")){
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetMesData().getMesCount(sql,"outcount");
        }else if(progress.equals("monthout")){
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetMesData().getMesCount(sql,"monthout");
        }else if(progress.equals("monthdayout")){
            response.setContentType("application/json; charset=UTF-8");
            str = new GetMesData().getMesLineChart(sql,"dd","ddout");
        }else if(progress.equals("monthin")){
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetMesData().getMesCount(sql,"monthin");
        }else if(progress.equals("montdayhin")){
            response.setContentType("application/json; charset=UTF-8");
            str = new GetMesData().getMesLineChart(sql,"dd","ddin");
        }
        out.print(str);
        out.flush();
        out.close();

    }
}
