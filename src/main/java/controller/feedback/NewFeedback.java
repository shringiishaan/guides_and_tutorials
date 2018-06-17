package controller.feedback;

import dao.FeedbackDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/newfeedback")
public class NewFeedback extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String data = request.getParameter("data");
        
        String ownerKey = "anonymous";
        if(request.getParameter("ownerKey")!=null) {
            ownerKey = request.getParameter("ownerKey");
        }
        
        new FeedbackDAO().newFeedback(ownerKey, "new", data);
        response.sendRedirect(request.getParameter("redirectURL"));
    }
}
