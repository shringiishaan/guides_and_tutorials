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
import model.Article;
import model.Tutorial;
import model.User;

public class EditArticle extends HttpServlet {

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

        if(parts.length!=4 || !parts[0].isEmpty() || !parts[1].equals("EditArticle")) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
            return;
        }

        String tutorialIdentifier, articleIdentifier;
        tutorialIdentifier = parts[2];
        articleIdentifier = parts[3];
        request.setAttribute("tutorialIdentifier", tutorialIdentifier);
        request.setAttribute("articleIdentifier", articleIdentifier);
        request.getRequestDispatcher("/editArticle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String data = request.getParameter("data").trim();
        Integer id = Integer.parseInt(request.getParameter("id").trim());
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
        articledao.updateDataByArticleId(id, data);
        Article article = articledao.getArticleById(id);
        Tutorial tutorial = new TutorialDAO().getTutorialById(articledao.getTutorialIdByArticleId(id));
        session.setAttribute("message","Article updated successfully!");
        response.sendRedirect("/article/"+tutorial.getIdentifier()+"/"+article.getIdentifier());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
