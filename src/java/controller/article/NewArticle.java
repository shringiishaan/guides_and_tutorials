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

public class NewArticle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String title = request.getParameter("title").trim();
        String data = request.getParameter("data");
        
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        if(userId==null) {
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }
        User user = userdao.getUserById((Integer)userId);
        if(!user.getType().equals("admin")) {
            request.getRequestDispatcher("/Error").forward(request, response);
            return;
        }
        
        ArticleDAO articledao = new ArticleDAO();
        
        if(articledao.verifyArticleTitle(title)) {
            session.setAttribute("error","Title already exists!");
            session.setAttribute("articleFormTitle",title);
            session.setAttribute("articleFormData",data);
            response.sendRedirect(request.getParameter("redirectURL"));
            return;
        }
        
        String key = title.replace(" ","-").replace("/","").replaceAll("[^a-zA-Z/-]", "").toLowerCase();
        if(data==null || data.trim().isEmpty()) {
            data = null;
        }
        articledao.createNewArticle(key, title, user.getId(), "unlinked", data);
        
        session.removeAttribute("articleFormTitle");
        session.removeAttribute("articleFormData");
        session.setAttribute("message","Article created successfully");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
