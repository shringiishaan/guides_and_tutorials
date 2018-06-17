package controller.util;

import dao.ArticleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Article;
import javax.servlet.annotation.WebServlet;

@WebServlet("/search")
public class SearchResultsJSON extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String query = request.getParameter("s");
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        
        if(query==null || query.isEmpty()) {
            writer.append("{\"results\":[]}");
            return;
        }
        
        ArticleDAO articledao = new ArticleDAO();
        List<Article> articles = articledao.getArticlesBySearchQuery(query);
        
        if(articles == null || articles.isEmpty()) {
            writer.append("{\"results\":[]}");
            return;
        }
        
        writer.append("{\"results\":[");
        for(int i=0; i<articles.size(); i++) {
            writer.append("{");
            writer.append("\"title\":\"").append(articles.get(i).getTitle()).append("\",");
            writer.append("\"link\":\"/article/").append(articles.get(i).getKey()).append("\",");
            writer.append("\"description\":\"").append(articles.get(i).getDescription()).append("\"");
            writer.append("}");
            if(i!=articles.size()-1) {
                writer.append(",");
            }
        }
        writer.append("]}");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
