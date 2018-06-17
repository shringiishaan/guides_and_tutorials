package controller.topic;

import dao.TopicDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/newtopic")
public class NewTopic extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId"); 
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error").forward(request, response);
        }
        
        String title = request.getParameter("title");
        
        TopicDAO topicdao = new TopicDAO();
        if(topicdao.verifyTopicTitle(title)) {
            session.setAttribute("error","Title already exists!");
            response.sendRedirect("/error");
            return;
        }
        String key = title.toLowerCase().replace(" ","-");
        topicdao.createNewTopic(key,title);
        session.setAttribute("message","Topic created successfully");
        response.sendRedirect("/managetopic?tid="+topicdao.getTopicByTitle(title).getId());
    }

    @Override
    public String getServletInfo() {
        return "Create new topic";
    }
}
