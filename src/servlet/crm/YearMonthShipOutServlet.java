package servlet.crm;

import crm.GetCrmData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017-7-27.
 */
public class YearMonthShipOutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        String vehgo = request.getParameter("vehgo");
        String date = request.getParameter("date");
        PrintWriter out = response.getWriter();
        String json = new GetCrmData().getYearMonthShipOut(vehgo,date);
        out.print(json);
        out.flush();
        out.close();
    }
}
