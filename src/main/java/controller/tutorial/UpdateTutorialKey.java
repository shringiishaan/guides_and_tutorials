package controller.tutorial;

import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/updatetutorialkey")
public class UpdateTutorialKey extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        Integer tutorialId = Integer.parseInt(request.getParameter("tutorialId"));
        String key = request.getParameter("key").trim();
        
        TutorialDAO tutorialdao = new TutorialDAO();
        tutorialdao.updateKeyByTutorialId(tutorialId, key);
        session.setAttribute("message","Key changed!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
