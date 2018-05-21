package controller.image;

import dao.ImageDAO;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

public class UploadImage extends HttpServlet {

    private static final long serialVersionUID = -337920480578240289L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            HttpSession session = request.getSession();
            
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            String keyword1=null, keyword2=null, redirectURL=null;
            InputStream inputStream = null;
            Integer fileSize=null;
            
            List<FileItem> fileItems =  upload.parseRequest(new ServletRequestContext(request));
            for(FileItem fileItem : fileItems) {
                if(fileItem.getFieldName().equals("redirectURL")) {
                    redirectURL = fileItem.getString();
                }
                else if(fileItem.getFieldName().equals("keyword1")) {
                    keyword1 = fileItem.getString();
                }
                else if(fileItem.getFieldName().equals("keyword2")) {
                    keyword2 = fileItem.getString();
                }
                else if(fileItem.getFieldName().equals("image")) {
                    inputStream = fileItem.getInputStream();
                    fileSize = Integer.parseInt(Long.toString(fileItem.getSize()));
                }
            }
            
            if (inputStream != null) {
                ImageDAO imagedao = new ImageDAO();
                imagedao.createNewImage(keyword1, keyword2, inputStream, fileSize);
                response.sendRedirect(redirectURL);
            }
            else {
                session.setAttribute("message", "File empty!");
                response.sendRedirect(redirectURL);
            }
        } 
        catch (IOException | NumberFormatException | FileUploadException ex) {
            Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
