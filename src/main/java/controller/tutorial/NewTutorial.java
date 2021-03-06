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

@WebServlet("/newtutorial")
public class NewTutorial extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        UserDAO userdao = new UserDAO();
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        String title = request.getParameter("title").trim();
        TutorialDAO tutorialdao = new TutorialDAO();
        if(tutorialdao.verifyTutorialTitle(title)) {
            session.setAttribute("error","Title already exists!");
            response.sendRedirect("/error");
        }
        else {
            String key = title.toLowerCase().replace(" ","-");
            tutorialdao.createNewTutorial(key,title);
            System.out.println(title);
            System.out.println(key);
            session.setAttribute("message","Tutorial created successfully");
            response.sendRedirect("/managetutorial?tid="+tutorialdao.getIdByTutorialKey(key));
        }
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
