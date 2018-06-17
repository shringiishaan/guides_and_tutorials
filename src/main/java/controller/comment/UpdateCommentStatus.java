package controller.comment;

import dao.CommentDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/updatecommentstatus")
public class UpdateCommentStatus extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cidString = request.getParameter("commentId");
        String status = request.getParameter("status");
        
        HttpSession session = request.getSession(true);
        if(session.getAttribute("userId")==null || !(new UserDAO().validateAdminByUserId((Integer)session.getAttribute("userId")))) {
            response.sendRedirect("/error");
            return;
        }
        
        new CommentDAO().updateStatus(new Integer(cidString), status);
        request.getSession(true).setAttribute("message","Comment Updated!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }
}
