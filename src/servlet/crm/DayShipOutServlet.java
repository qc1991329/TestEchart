package servlet.crm;

import crm.GetCrmData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017-7-19.
 */
public class DayShipOutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        String vehgo = request.getParameter("vehgo");
        PrintWriter out = response.getWriter();
        String str = new GetCrmData().getCrmShipout(vehgo);
        out.print(str);
        out.flush();
        out.close();
    }
}
