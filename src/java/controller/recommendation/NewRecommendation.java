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

@WebServlet("/newrecommendation")
public class NewRecommendation extends HttpServlet {

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
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String link = request.getParameter("link");
        
        RecommendationDAO recommendationdao = new RecommendationDAO();
        recommendationdao.newRecommendation(articleId, title, link, type);
        session.setAttribute("message","Recommendation created!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
