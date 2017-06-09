package dao;

import entity.User;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yan on 2017/3/18.
 */
public class UserDao {
    private static String sql = "";
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    private static ResultSet rs = null;

    public UserDao() {
    }

    //	 判断用户是否匹配
    public static boolean isMatch(User user) throws SQLException {
        conn = new DBHelper().getConnection();
        sql = "select *from users where username=? and password=?;";

        stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPass());

        rs = stmt.executeQuery();

        if (rs.next()) {
            //登陆成功
            return true;
        }

        rs.close();
        stmt.close();
        conn.close();

        return false;
    }

    //注册
    public static String register(User user) throws SQLException {
        //连接数据库
        conn = new DBHelper().getConnection();
        sql = "select *from users where username=?;";
        //预编译
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
//        执行
        rs = stmt.executeQuery();
        if (rs.next()) {
            //用户名存在
            return "had";
        }
        sql = "insert into users values(?,?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPass());
        int flag = stmt.executeUpdate();
        if(flag>0){
            return "true";
        }
        rs.close();
        stmt.close();
        conn.close();
        return "false";
    }
}
