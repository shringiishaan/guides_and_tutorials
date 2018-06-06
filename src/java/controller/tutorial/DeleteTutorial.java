package controller.tutorial;

import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteTutorial extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        UserDAO userdao = new UserDAO();
        Object userId = session.getAttribute("userId");
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }

        Integer tutorialId = Integer.parseInt(request.getParameter("tutorialId"));
        TutorialDAO tutorialdao = new TutorialDAO();
        
        tutorialdao.removeAllLinksByTutorialId(tutorialId);
        tutorialdao.deleteByTutorialId(tutorialId);
        
        session.setAttribute("message", "Tutorial deleted!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
