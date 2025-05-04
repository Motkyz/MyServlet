package servlets;


import accounts.AccountService;
import accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        String email = req.getParameter("e-mail");

        UserProfile userProfile = null;
        try {
            userProfile = AccountService.getUserByLogin(name);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            doGet(req, resp);
            return;
        }

        if (name == null || name.isEmpty() || pass == null || pass.isEmpty() || email == null || email.isEmpty()) {
            doGet(req, resp);
            return;
        }

        if (userProfile != null) {
            doGet(req, resp);
            return;
        }

        UserProfile newUser = new UserProfile(name, pass, email);
        try {
            AccountService.addNewUser(newUser);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            doGet(req, resp);
            return;
        }
        session.setAttribute("username", newUser.getLogin());

        resp.sendRedirect(req.getContextPath() + "/filelist");
    }
}
