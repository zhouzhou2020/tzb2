package servlet;

import dao.Json;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yan on 2017/3/18.
 */
@WebServlet(name = "uploadImagesServlet", urlPatterns = "/servlet/uploadImagesServlet")
public class uploadImagesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //允许所有域名访问
        PrintWriter pw = response.getWriter();
        String time = new Date().getTime() + "";
        /*创建上传文件夹*/
        String savePath = this.getServletContext().getRealPath("/uploadimages");
        System.out.println(savePath);
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() || !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }

        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                return;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            String filename = "";
            String username="";
            String id="";
            /*首先获取用户名，创建文件夹*/
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println(name+" : "+value);
                    if (name.equals("username")) {
                        username=value;
                    }
                    if(name.equals("id")){
                        id=value;
                    }
                }
            }
            if(!username.equals("")&&!id.equals("")){
                //服务器地址
//                String imageurl="http://localhost:8080/tzb2/uploadimages/"+username+"/"+id+"/";
                String imageurl="http://115.159.66.87:8080/tzb2/uploadimages/"+username+"/"+id+"/";

//                创建用户文件夹
                savePath = this.getServletContext().getRealPath("/uploadimages/"+username);
                System.out.println(savePath);
                file = new File(savePath);
                //判断上传文件的保存目录是否存在
                if (!file.exists() || !file.isDirectory()) {
                    System.out.println(savePath + "目录不存在，需要创建");
                    //创建目录
                    file.mkdir();
                }
                //创建任务文件夹
                savePath = this.getServletContext().getRealPath("/uploadimages/"+username+"/"+id);
                System.out.println(savePath);
                file = new File(savePath);
                //判断任务的保存目录是否存在
                if (!file.exists() || !file.isDirectory()) {
                    System.out.println(savePath + "目录不存在，需要创建");
                    //创建目录
                    file.mkdir();
                }
                ArrayList<String> imageurls=new ArrayList<>();
                for (FileItem item : list) {
                    //如果fileitem中封装的是文件数据
                    if (!item.isFormField()) {
                        //如果fileitem中封装的是上传文件
                        //得到上传的文件名称，
                        filename = item.getName();
                        System.out.println(filename);
                        if (filename == null || filename.trim().equals("")) {
                            continue;
                        }
                        //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                        //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                        if(filename.contains("\\")){
                            filename =filename.substring(filename.lastIndexOf("\\"));
                        }
                        String midurl=savePath+"/"+filename;
                        file=new File(midurl);
                        if (file.exists()) {
                            System.out.println(savePath + "图片已存在");
                            pw.print(Json.getJson("uploaded"));
                        }else{
                            //获取item中的上传文件的输入流
                            InputStream in = item.getInputStream();
                            //创建一个文件输出流
                            FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
                            //创建一个缓冲区
                            byte buffer[] = new byte[1024];
                            //判断输入流中的数据是否已经读完的标识
                            int len = 0;
                            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                            while ((len = in.read(buffer)) > 0) {
                                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                                out.write(buffer, 0, len);
                            }
                            //关闭输入流
                            in.close();
                            //关闭输出流
                            out.close();
                            //删除处理文件上传时生成的临时文件
                            item.delete();
                            imageurls.add(imageurl+filename);
                        }
                    }
                }
                imageurls.add(0,"true");
                pw.print(Json.getJson(imageurls));
            } else{
              pw.print(Json.getJson(false));
            }
        } catch (Exception e) {
            pw.print(Json.getJson(false));
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
