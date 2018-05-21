package controller.tutorial;

import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewTutorial extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        String title = request.getParameter("title").trim();
        TutorialDAO tutorialdao = new TutorialDAO();
        if(tutorialdao.verifyTutorialTitle(title)) {
            session.setAttribute("error","Title already exists!");
            session.setAttribute("tutorialFormTitle",title);
            response.sendRedirect(request.getParameter("redirectURL"));
        }
        else {
            tutorialdao.createNewTutorial(title.toLowerCase().replace(" ","-"),title);
            session.setAttribute("message","Tutorial created successfully");
            response.sendRedirect(request.getParameter("redirectURL"));
        }
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
