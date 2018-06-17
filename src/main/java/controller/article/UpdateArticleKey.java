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


@WebServlet("/updatearticlekey")
public class UpdateArticleKey extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        Integer articleId = Integer.parseInt(request.getParameter("articleId"));
        String key = request.getParameter("key");
        
        ArticleDAO articledao = new ArticleDAO();
        articledao.updateKeyByArticleId(articleId, key);
        session.setAttribute("message","Article key updated!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new article";
    }
}