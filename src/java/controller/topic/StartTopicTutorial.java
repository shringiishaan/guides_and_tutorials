package controller.topic;

import dao.ArticleDAO;
import dao.TopicDAO;
import dao.TutorialDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartTopicTutorial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = requestedPath.split("/");

        if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("StartTopicTutorial")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        TopicDAO topicdao = new TopicDAO();
        if(!topicdao.verifyTopicId(parts[2])) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        response.sendRedirect("/Article/"+new ArticleDAO().getMaximumPriorityArticleByTutorialIdAndStatus(new TutorialDAO().getMaximumPriorityTutorialByTopicId(parts[2]).getId(), "final", false).getId());
    }

    @Override
    public String getServletInfo() {
        return "Create new topic";
    }
}
