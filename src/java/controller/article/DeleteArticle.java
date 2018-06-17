package controller.article;

import dao.ArticleDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;


@WebServlet("/deletearticle")
public class DeleteArticle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        UserDAO userdao = new UserDAO();
        Object userId = session.getAttribute("userId");
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }

        Integer articleId = Integer.parseInt(request.getParameter("articleId"));        
        ArticleDAO articledao = new ArticleDAO();
        articledao.removeTutorialArticleLinksByArticleId(articleId);
        articledao.deleteByArticleId(articleId);
        
        session.setAttribute("message", "Article Deleted!");
        response.sendRedirect("/managearticle");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
