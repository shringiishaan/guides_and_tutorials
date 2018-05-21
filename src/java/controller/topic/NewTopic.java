package controller.topic;

import dao.TopicDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewTopic extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId"); 
        if(userId==null || !userdao.validateAdminByUserId((Integer)userId)) {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
        }
        
        String title = request.getParameter("title");
        TopicDAO topicdao = new TopicDAO();
        if(topicdao.verifyTopicTitle(title)) {
            session.setAttribute("error","Title already exists!");
            session.setAttribute("topicFormTitle",title);
            response.sendRedirect(request.getParameter("redirectURL"));
            return;
        }
        topicdao.createNewTopic(title.toLowerCase().replace(" ","-"),title);
        session.setAttribute("message","Topic created successfully");
        response.sendRedirect(request.getParameter("redirectURL"));
    }

    @Override
    public String getServletInfo() {
        return "Create new topic";
    }
}
