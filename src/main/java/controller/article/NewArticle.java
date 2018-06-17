package controller.article;

import dao.ArticleDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import javax.servlet.annotation.WebServlet;
import model.Article;


@WebServlet("/newarticle")
public class NewArticle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String title = request.getParameter("title").trim();
        
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        if(userId==null) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        User user = userdao.getUserById((Integer)userId);
        if(!user.getType().equals("admin")) {
            request.getRequestDispatcher("/error").forward(request, response);
            return;
        }
        
        ArticleDAO articledao = new ArticleDAO();
        Article tempArticle = null;
        tempArticle = articledao.getArticleByTitle(title, false);
        if(tempArticle!=null) {
            session.setAttribute("error","Title already exists!");
            response.sendRedirect("/managearticle?aid="+tempArticle.getId());
            return;
        }
        
        String key = title.replace(" ","-").replace("/","").replaceAll("[^a-zA-Z/-]", "").toLowerCase();
        articledao.createNewArticle(key, title, user.getId());
        session.setAttribute("message","Article created successfully");
        response.sendRedirect("/managearticle?aid="+articledao.getIdByArticleKey(key));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
