package dao;

import com.google.gson.Gson;

/**
 * Created by Yan on 2017/3/11.
 */
public class Json {
    public static Gson gson = new Gson();
    public static String getJson(Object so){
        String res=gson.toJson(so);
        return res;
    }
}
