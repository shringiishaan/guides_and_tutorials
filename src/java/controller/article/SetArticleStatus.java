package controller.article;

import dao.ArticleDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetArticleStatus extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        String articleId = request.getParameter("articleId").trim();
        String status = request.getParameter("status").trim();
        
        ArticleDAO articledao = new ArticleDAO();
        articledao.updateStatusByArticleId(articleId, status);
        session.setAttribute("message","Status changed!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new article";
    }
}