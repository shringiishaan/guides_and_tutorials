package controller.feedback;

import dao.FeedbackDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/updatefeedbackstatus")
public class UpdateFeedbackStatus extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String fidString = request.getParameter("feedbackId");
        String status = request.getParameter("status");
        
        HttpSession session = request.getSession(true);
        if(session.getAttribute("userId")==null || !(new UserDAO().validateAdminByUserId((Integer)session.getAttribute("userId")))) {
            response.sendRedirect("/logout");
            return;
        }
        
        new FeedbackDAO().updateFeedbackStatus(new Integer(fidString), status);
        request.getSession(true).setAttribute("message","Feedback Updated!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }
}
