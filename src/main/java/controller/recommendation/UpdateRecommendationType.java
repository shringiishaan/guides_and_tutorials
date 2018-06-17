package controller.recommendation;

import dao.RecommendationDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/updaterecommendationtype")
public class UpdateRecommendationType extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        Integer recommendationId = Integer.parseInt(request.getParameter("recommendationId"));
        String type = request.getParameter("type");
        
        RecommendationDAO recommendationdao = new RecommendationDAO();
        recommendationdao.updateRecommendationType(recommendationId, type);
        session.setAttribute("message","Recommendation updated!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
