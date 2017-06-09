package dao;

import entity.Task;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yan on 2017/3/18.
 */
public class TaskDao {
    private static String sql = "";
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    private static ResultSet rs = null;

    /*上传*/
    public static boolean upload(Task task) throws SQLException {
        conn = new DBHelper().getConnection();
        sql = "insert into tasks values(?,?,?,?,?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, task.getCreateMan());
        stmt.setString(2, task.getCreateDate());
        stmt.setString(3, task.getBeiZhu());
        stmt.setString(4, task.getDownUrl());
        stmt.setString(5, task.getTaskName());
        String[] imagelist = task.getUpUrl();
        StringBuffer imageurls = new StringBuffer();
        for (String imageurl : imagelist) {
            imageurls.append(imageurl + ",");
        }
        stmt.setString(6, imageurls.substring(0, imageurls.length() - 1).toString());
        int flag = stmt.executeUpdate();
        stmt.close();
        conn.close();
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    //获取
    public static ArrayList<Task> getTasks(String user) throws SQLException {
        ArrayList<Task> tasks=new ArrayList<>();
        conn = new DBHelper().getConnection();
        sql = "select *from tasks where createman='"+ user+"' order by createdate desc";
        stmt = conn.prepareStatement(sql);
        rs=stmt.executeQuery();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        while(rs.next()){
            Task task=new Task();
            task.setBeiZhu(rs.getString("beizhu"));
            task.setCreateMan(rs.getString("createman"));
            long time=Long.parseLong(rs.getString("createdate"));
            String date = sdf.format(new Date(time));
            task.setId(time+"");
            task.setCreateDate(date);
            task.setTaskName(rs.getString("taskname"));
            task.setDownUrl(rs.getString("downurl"));
            String imageurlstring=rs.getString("imageurls");
            task.setUpUrl(imageurlstring.split(","));
            tasks.add(task);
        }
        return tasks;
    }
    //获取
    public static Task getTask(String user,String id) throws SQLException {
        Task task=null;
        conn = new DBHelper().getConnection();
        sql = "select *from tasks where createman='"+ user+"' and createdate='"+id+"'";
        stmt = conn.prepareStatement(sql);
        rs=stmt.executeQuery();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        if(rs.next()){
            task=new Task();
            task.setBeiZhu(rs.getString("beizhu"));
            task.setCreateMan(rs.getString("createman"));
            long time=Long.parseLong(rs.getString("createdate"));
            String date = sdf.format(new Date(time));
            task.setId(time+"");
            task.setCreateDate(date);
            task.setTaskName(rs.getString("taskname"));
            task.setDownUrl(rs.getString("downurl"));
            String imageurlstring=rs.getString("imageurls");
            task.setUpUrl(imageurlstring.split(","));
        }
        return task;
    }
    //获取
    public static ArrayList<Task> getAllTasks(int number) throws SQLException {
        ArrayList<Task> tasks=new ArrayList<>();
        conn = new DBHelper().getConnection();
        sql = "select *from tasks order by createdate desc,createman DESC ";
        if(number>0){
            sql=sql+" limit 0,"+number;
        }
        stmt = conn.prepareStatement(sql);
        rs=stmt.executeQuery();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        while(rs.next()){
            Task task=new Task();
            task.setBeiZhu(rs.getString("beizhu"));
            task.setCreateMan(rs.getString("createman"));
            long time=Long.parseLong(rs.getString("createdate"));
            String date = sdf.format(new Date(time));
            task.setCreateDate(date);
            task.setId(time+"");
            task.setTaskName(rs.getString("taskname"));
            task.setDownUrl(rs.getString("downurl"));
            String imageurlstring=rs.getString("imageurls");
            task.setUpUrl(imageurlstring.split(","));
            tasks.add(task);
        }
        return tasks;
    }
}
