package servlet;

import com.google.gson.Gson;
import dao.Json;
import dao.TaskDao;
import entity.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Yan on 2017/3/19.
 */
@WebServlet(name = "allTasksServlet", urlPatterns = "/servlet/allTasksServlet")
public class allTasksServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //允许所有域名访问
        Gson gson=new Gson();
        String num=gson.fromJson(request.getReader(),String.class);
        int number=Integer.parseInt(num);
        PrintWriter pw=response.getWriter();
        try {
            ArrayList<Task> tasks=TaskDao.getAllTasks(number);
            pw.print(Json.getJson(tasks));
        } catch (SQLException e) {
            pw.print(Json.getJson("false"));
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
