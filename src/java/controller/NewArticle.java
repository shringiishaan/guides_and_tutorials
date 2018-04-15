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
import model.User;

public class NewArticle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newArticle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title").trim();
        Integer tutorialId = Integer.parseInt(request.getParameter("tutorialId").trim());
        String data = request.getParameter("data").trim();
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
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
        
        TutorialDAO tutorialdao = new TutorialDAO();
        ArticleDAO articledao = new ArticleDAO();
        
        if(!tutorialdao.verifyTutorialId(tutorialId)) {
            session.setAttribute("error","Tutorial not found");
            session.setAttribute("articleFormTitle",title);
            session.setAttribute("articleFormData",data);
            request.getRequestDispatcher("/newArticle.jsp").forward(request, response);
        }
        else if(articledao.verifyArticleTitleAndTutorialId(tutorialId, title)) {
            session.setAttribute("error","Title already exists in the selected tutorial!");
            session.setAttribute("articleFormTitle",title);
            session.setAttribute("articleFormData",data);
            request.getRequestDispatcher("/newArticle.jsp").forward(request, response);
        }
        else {
            Integer displayIndex = articledao.getLastDisplayIndexByTutorialId(tutorialId);
            System.out.println(displayIndex);
            if(displayIndex==null)
                displayIndex = 0;
            displayIndex++;
            articledao.createNewArticle(title, tutorialId, user.getId(), displayIndex, data);
            session.removeAttribute("articleFormTitle");
            session.removeAttribute("articleFormData");
            session.setAttribute("message","Article created successfully");
            response.sendRedirect("/");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
