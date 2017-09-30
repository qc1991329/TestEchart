package servlet;

import auth.AuthHelper;
import com.alibaba.fastjson.JSON;
import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import user.UserHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017-9-19.
 */
public class GetUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String corpId = request.getParameter("corpid");
        System.out.println("code:"+code+" corpid:"+corpId);

        try {
            response.setContentType("text/html; charset=utf-8");

            String accessToken = AuthHelper.getAccessToken();
            System.out.println("access token:"+accessToken);
            CorpUserBaseInfo user = UserHelper.getUserInfo(accessToken, code);
            String userJson = JSON.toJSONString(user);
            response.getWriter().append(userJson);
            System.out.println("userjson:"+userJson);

        } catch ( Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.getWriter().append(e.getMessage());
        }
    }
}
