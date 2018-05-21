package controller.link;

import dao.ArticleDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetTutorialArticlePriority extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        String tutorialId = request.getParameter("tutorialId").trim();
        String articleId = request.getParameter("articleId").trim();
        Integer priority = Integer.parseInt(request.getParameter("priority").trim());
        
        ArticleDAO articledao = new ArticleDAO();
        articledao.updatePriorityByTutorialIdAndArticleId(tutorialId, articleId, priority);
        session.setAttribute("message","Priority changed!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new article";
    }
}
