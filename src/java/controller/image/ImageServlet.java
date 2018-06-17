package controller.image;

import com.mysql.jdbc.StringUtils;
import dao.ImageDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Image;
import javax.servlet.annotation.WebServlet;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = requestedPath.split("/");

        if(parts.length!=4 || !parts[0].isEmpty() || !parts[1].equals("image")) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        String k1 = parts[2];
        String k2 = parts[3];
        
        if(StringUtils.isNullOrEmpty(k1) || StringUtils.isNullOrEmpty(k2)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        ImageDAO imagedao = new ImageDAO();
        Image image = imagedao.getImageByKeywords(k1,k2);
        
        if(image==null) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        response.setContentType("image/jpeg");  
        ServletOutputStream servletOutStream = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        
        try {
            servletOutStream = response.getOutputStream();  
            inputStream = image.getData().getBinaryStream();  

            bufferedInputStream = new BufferedInputStream(inputStream);  
            bufferedOutputStream = new BufferedOutputStream(servletOutStream);  

            int ch =0;
            while((ch=bufferedInputStream.read())!=-1)  
            {  
                bufferedOutputStream.write(ch);  
            }  
        }
        catch(IOException | SQLException e) {
            request.getRequestDispatcher("/Error").forward(request, response);
        }
        finally {
            if(bufferedInputStream!=null) {
                bufferedInputStream.close();
            }
            if(inputStream!=null) {
                inputStream.close();  
            }
            if(bufferedOutputStream!=null) {
                bufferedOutputStream.close();  
            }
            if(servletOutStream!=null) {
                servletOutStream.close();  
            }
        }
    }
}
