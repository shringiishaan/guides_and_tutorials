package controller;

import dao.CategoryDAO;
import dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

public class NewCategory extends HttpServlet {

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
        request.getRequestDispatcher("/newCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name").trim();
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession(true);
        Object userId = session.getAttribute("userId");
        User user = null;
        if(userId!=null && userdao.getUserById((Integer)userId).getType().equals("admin")) {
            CategoryDAO categorydao = new CategoryDAO();
            if(categorydao.verifyCategoryName(name)) {
                session.setAttribute("error","Name already exists!");
                session.setAttribute("categoryFormName",name);
                request.getRequestDispatcher("/newCategory.jsp").forward(request, response);
            }
            else {
                categorydao.createNewCategory(name);
                session.setAttribute("message","Category created successfully");
                response.sendRedirect("/");
            }
        }
        else {
            request.getRequestDispatcher("/error404.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Create new category";
    }
}
