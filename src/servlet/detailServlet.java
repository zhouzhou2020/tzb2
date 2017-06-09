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

/**
 * Created by Yan on 2017/3/22.
 */
@WebServlet(name = "detailServlet", urlPatterns = "/servlet/detailServlet")
public class detailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //允许所有域名访问
        Gson gson = new Gson();
        String[] req = gson.fromJson(request.getReader(), String[].class);
        System.out.println(req);
        PrintWriter pw = response.getWriter();
        try {
            Task task = TaskDao.getTask(req[0], req[1]);
            pw.print(Json.getJson(task));
        } catch (SQLException e) {
            pw.print(Json.getJson("false"));
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
