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

public class SiteMap extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" " +
                        "xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\" " +
                        "xmlns:video=\"http://www.google.com/schemas/sitemap-video/1.1\">");
                ArticleDAO articledao = new ArticleDAO();
                List<Article> allArticles = articledao.getAllArticles(false);
                for(Article article : allArticles) {
                    writer.append("<url>");
                        writer.append("<loc>http://www.cstutorials.tech/article/").append(article.getKey()).append("</loc>");
                    writer.append("</url>");
                }
        writer.append("</urlset>");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
