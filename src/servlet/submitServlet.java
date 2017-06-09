package servlet;

import com.google.gson.Gson;
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
 * Created by Yan on 2017/3/18.
 */
@WebServlet(name = "submitServlet", urlPatterns = "/servlet/submitServlet")
public class submitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //允许所有域名访问
        Gson gson = new Gson();
        Task task = gson.fromJson(request.getReader(), Task.class);
        PrintWriter pw=response.getWriter();
        try {
            boolean flag= TaskDao.upload(task);
            pw.print(flag);
        } catch (SQLException e) {
            pw.print(false);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
