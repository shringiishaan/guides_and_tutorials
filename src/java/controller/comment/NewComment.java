package controller.comment;

import dao.CommentDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

public class NewComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String message = request.getParameter("message").trim();
        Integer articleId = Integer.parseInt(request.getParameter("articleId"));
        
        UserDAO userdao = new UserDAO();
        CommentDAO commentdao = new CommentDAO();
        
        Integer priority = commentdao.getMaximumCommentPriorityByArticleId(articleId);
        priority++;
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        User user=null;
        if(userId !=null) {
            user = userdao.getUserById((Integer)userId);
        }
        commentdao.createNewComment(user==null?null:user.getId(), message, articleId, priority);
        session.setAttribute("message","Comment posted!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }
}
