package controller.recommendation;

import controller.link.*;
import dao.ArticleDAO;
import dao.RecommendationsDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateRecommendationTitle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }
        
        Integer recommendationId = Integer.parseInt(request.getParameter("recommendationId"));
        String title = request.getParameter("title");
        
        RecommendationsDAO recommendationdao = new RecommendationsDAO();
        recommendationdao.updateRecommendationTitle(recommendationId, title);
        session.setAttribute("message","Recommendation updated!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
