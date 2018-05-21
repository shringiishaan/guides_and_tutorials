package controller.util;

import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        if(userdao.validateUser(email, password)) {
            session.removeAttribute("loginFormEmail");
            User user = userdao.getUserByEmail(email);
            session.setAttribute("userId", user.getId());
            session.setAttribute("message", "Login successful!");
            response.sendRedirect(request.getParameter("redirectURL"));
        }
        else {
            session.setAttribute("error", "Invalid Email or Password");
            session.setAttribute("loginFormEmail", email);
            request.getRequestDispatcher(request.getParameter("redirectURL")).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
