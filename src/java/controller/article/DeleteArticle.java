package controller.article;

import dao.ArticleDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteArticle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        UserDAO userdao = new UserDAO();
        Object userId = session.getAttribute("userId");
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }

        String articleId = request.getParameter("articleId");
        
        ArticleDAO articledao = new ArticleDAO();
        
        articledao.removeAllLinksByArticleId(articleId);
        articledao.deleteByArticleId(articleId);
        
        session.setAttribute("message", "Article Deleted!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
