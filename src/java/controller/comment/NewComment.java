package controller.comment;

import com.mysql.jdbc.StringUtils;
import dao.CommentDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import javax.servlet.annotation.WebServlet;

@WebServlet("/newcomment")
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
        
        String ownerKey = "anonymous";
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        User user=null;
        if(userId !=null && userdao.validateAdminByUserId((Integer)userId)) {
            ownerKey = "admin";
        }
        else if(request.getParameter("ownerKey")!=null) {
            ownerKey = request.getParameter("ownerKey");
            if(StringUtils.isEmptyOrWhitespaceOnly(ownerKey) || ownerKey.equals("admin")) {
                ownerKey = "anonymous";
            }
        }
        commentdao.createNewComment(ownerKey, "new", message, articleId, priority);
        session.setAttribute("message","New comment posted!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }
}
