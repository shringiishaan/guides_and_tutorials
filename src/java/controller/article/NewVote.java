package controller.article;

import dao.VoteDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;


@WebServlet("/newvote")
public class NewVote extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String articleIdString = request.getParameter("articleId");
        String type = request.getParameter("type");
        if(articleIdString==null || type==null) {
            return;
        }
        
        Integer articleId = Integer.parseInt(articleIdString);
        HttpSession session = request.getSession();
        if(session.getAttribute("vote-"+type+articleId)==null) {
            VoteDAO articlevotesdao = new VoteDAO();
            if(!articlevotesdao.verifyArticleVoteType(articleId, type)) {
                articlevotesdao.newArticleVoteType(articleId, type);
            }
            articlevotesdao.incrementVoteForArticle(articleId,type);
            session.setAttribute("vote-"+type+articleId,true);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
