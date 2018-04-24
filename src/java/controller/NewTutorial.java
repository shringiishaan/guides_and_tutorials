package controller;

import dao.ArticleDAO;
import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Tutorial;
import model.User;

public class NewTutorial extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserDAO userdao = new UserDAO();
        Object userId = session.getAttribute("userId");
        if(userId==null) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        User user = userdao.getUserById((Integer)userId);
        if(!user.getType().equals("admin")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/newTutorial.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title").trim();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        if(userId==null) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        UserDAO userdao = new UserDAO();
        User user = userdao.getUserById((Integer)userId);
        if(user==null || !user.getType().equals("admin")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }
        
        TutorialDAO tutorialdao = new TutorialDAO();
        if(tutorialdao.verifyTutorialTitle(title)) {
            session.setAttribute("error","Title already exists!");
            session.setAttribute("tutorialFormTitle",title);
            request.getRequestDispatcher("/newTutorial.jsp").forward(request, response);
        }
        else {
            tutorialdao.createNewTutorial(title);
            Tutorial tutorial = tutorialdao.getTutorialByTitle(title);
            ArticleDAO articledao = new ArticleDAO();
            articledao.createNewArticleInTutorial("Overview",tutorial.getId(),user.getId(),1);
            session.setAttribute("message","Tutorial created successfully");
            response.sendRedirect("/");
        }
    }

    @Override
    public String getServletInfo() {
        return "Create new tutorial";
    }
}
