package servlet;

import dbconnect.DBConnect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016-12-14.
 */
public class Sendsms extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8");
        String mobile = request.getParameter("mobile");
        PrintWriter out = response.getWriter();
        boolean issuucess = new DBConnect().sendsms(mobile);
        out.print(issuucess);
        out.flush();
        out.close();
    }
}
