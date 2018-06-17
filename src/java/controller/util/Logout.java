package controller.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/logout")
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.removeAttribute("userId");
        session.invalidate();
        if(request.getParameter("redirectURL")!=null) {
            response.sendRedirect(request.getParameter("redirectURL"));
        }
        else {
            response.sendRedirect("/");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
