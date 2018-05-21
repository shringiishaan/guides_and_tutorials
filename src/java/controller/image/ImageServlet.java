package controller.image;

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

public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = requestedPath.split("/");

        if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("Image")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        Integer imageId;
        try {
            imageId = Integer.parseInt(parts[2]);
        }
        catch(NumberFormatException e) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        ImageDAO imagedao = new ImageDAO();
        Image image = imagedao.getImageById(imageId);
        
        if(image==null) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
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
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
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
