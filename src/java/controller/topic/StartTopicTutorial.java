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
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }
        
        Integer topicId = Integer.parseInt(parts[2]);
        
        TopicDAO topicdao = new TopicDAO();
        if(!topicdao.verifyTopicId(topicId)) {
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }
        
        response.sendRedirect("/article/"+new ArticleDAO().getMaximumPriorityArticleByTutorialIdAndStatus(new TutorialDAO().getMaximumPriorityTutorialByTopicId(topicId).getId(),"final", false).getKey());
    }

    @Override
    public String getServletInfo() {
        return "Create new topic";
    }
}
