package servlet.crm;


import crm.GetCrmPaymentData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017-8-1.
 */
public class CrmPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String payment = request.getParameter("payment");
        String date = request.getParameter("date");
        if(payment.equals("day")){
            String str = null;
            PrintWriter out = response.getWriter();
            response.setContentType("text/plain; charset=UTF-8");
            str = new GetCrmPaymentData().getCrmPayment(date);
            out.print(str);
            out.flush();
            out.close();
        }else if(payment.equals("month")){
            String str = null;
            PrintWriter out = response.getWriter();
            response.setContentType("application/json; charset=UTF-8");
            str = new GetCrmPaymentData().getCrmMonthPayment(date);
            out.print(str);
            out.flush();
            out.close();
        }else if(payment.equals("year")){
            String str = null;
            PrintWriter out = response.getWriter();
            response.setContentType("application/json; charset=UTF-8");
            str = new GetCrmPaymentData().getCrmYearPayment(date);
            out.print(str);
            out.flush();
            out.close();
        }

    }
}
