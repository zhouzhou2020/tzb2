package servlet;

import com.google.gson.Gson;
import dao.Json;
import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by Yan on 2017/3/19.
 */
@WebServlet(name = "registerServlet", urlPatterns = "/servlet/registerServlet")
public class registerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //允许所有域名访问
        Gson gson=new Gson();
        User user=gson.fromJson(request.getReader(), User.class);
        PrintWriter pw=response.getWriter();
        try {
            pw.print(Json.getJson(UserDao.register(user)));
        } catch (SQLException e) {
            pw.print(Json.getJson("false"));
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
