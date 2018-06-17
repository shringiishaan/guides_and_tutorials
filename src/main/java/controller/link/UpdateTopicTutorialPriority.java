package controller.link;

import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/updatetopictutorialpriority")
public class UpdateTopicTutorialPriority extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        Integer topicId = Integer.parseInt(request.getParameter("topicId"));
        Integer tutorialId = Integer.parseInt(request.getParameter("tutorialId"));
        Integer priority = Integer.parseInt(request.getParameter("priority").trim());
        
        TutorialDAO tutorialdao = new TutorialDAO();
        tutorialdao.updatePriorityByTopicIdAndTutorialId(topicId, tutorialId, priority);
        session.setAttribute("message","Priority changed!");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
