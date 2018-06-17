package controller.tutorial;

import dao.ArticleDAO;
import dao.TutorialDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Article;
import javax.servlet.annotation.WebServlet;

@WebServlet("/viewtutorial/*")
public class ViewTutorial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = requestedPath.split("/");

        if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("tutorial")) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }

        String tutorialKey = parts[2];
        ArticleDAO articledao = new ArticleDAO();
        Integer tutorialId = new TutorialDAO().getIdByTutorialKey(tutorialKey);
        Article article = articledao.getMaximumPriorityArticleByTutorialIdAndStatus(tutorialId,"final",false);
        response.sendRedirect("/article/"+article.getKey());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
