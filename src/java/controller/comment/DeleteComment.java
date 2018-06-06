package controller.comment;

import dao.CommentDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        UserDAO userdao = new UserDAO();
        Object userId = session.getAttribute("userId");
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }
        
        Integer commentId = Integer.parseInt(request.getParameter("commentId"));
        CommentDAO commentdao = new CommentDAO();
        commentdao.deleteByCommentId(commentId);
        session.setAttribute("message", "Comment Deleted");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
