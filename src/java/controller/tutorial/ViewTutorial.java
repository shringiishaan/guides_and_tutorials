package controller.tutorial;

import dao.ArticleDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Article;

public class ViewTutorial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = requestedPath.split("/");

        if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("Tutorial")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }

        String tutorialId = parts[2];
        ArticleDAO articledao = new ArticleDAO();
        Article article = articledao.getMaximumPriorityArticleByTutorialIdAndStatus(tutorialId,"final",false);
        response.sendRedirect("/Article/"+article.getId());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
