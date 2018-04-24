package controller;

import dao.ArticleDAO;
import dao.TutorialDAO;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Article;
import model.Tutorial;
import model.User;

public class MoveArticle extends HttpServlet {

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
        String requestedPath = request.getRequestURI().substring(request.getContextPath().length());
        String[] parts = requestedPath.split("/");

        if(parts.length!=3 || !parts[0].isEmpty() || !parts[1].equals("EditArticle")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }

        String tutorialIdentifier;
        tutorialIdentifier = parts[2];
        request.setAttribute("tutorialIdentifier", tutorialIdentifier);
        request.getRequestDispatcher("/moveArticle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer tutorialId = Integer.parseInt(request.getParameter("tutorialId").trim());
        Integer targetArticleId = Integer.parseInt(request.getParameter("targetArticleId").trim());
        Integer preArticleId = Integer.parseInt(request.getParameter("preArticleId").trim());
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
        ArticleDAO articledao = new ArticleDAO();
        Integer displayIndex = articledao.getArticleById(preArticleId).getDisplayIndex();
        displayIndex++;
        articledao.incrementDisplayIndexForArticlesByTutorialId(tutorialId, displayIndex);
        articledao.updateDisplayIndexByArticleId(targetArticleId, displayIndex);
        session.setAttribute("message","Article moved successfully!");
        response.sendRedirect("/article/"+new TutorialDAO().getTutorialById(tutorialId).getIdentifier());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
