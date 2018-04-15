package controller;

import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

public class NewTutorial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newTutorial.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title").trim();
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        User user = null;
        if(userId!=null && userdao.getUserById((Integer)userId).getType().equals("admin")) {
            TutorialDAO tutorialdao = new TutorialDAO();
            if(tutorialdao.verifyTutorialTitle(title)) {
                session.setAttribute("error","Title already exists!");
                session.setAttribute("tutorialFormTitle",title);
                request.getRequestDispatcher("/newTutorial.jsp").forward(request, response);
            }
            else {
                tutorialdao.createNewTutorial(title);
                session.setAttribute("message","Tutorial created successfully");
                response.sendRedirect("/");
            }
        }
        else {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
