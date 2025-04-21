package servlets;


import accounts.AccountService;
import accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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

        UserProfile userProfile = AccountService.getUserByLogin(name);
        if (name == null || name.isEmpty() || pass == null || pass.isEmpty() || email == null || email.isEmpty()) {
            doGet(req, resp);
            return;
        }
        if (userProfile != null) {
            doGet(req, resp);
            return;
        }

        UserProfile newUser = new UserProfile(name, pass, email);
        AccountService.addNewUser(newUser);
        session.setAttribute("username", newUser.getLogin());

        resp.sendRedirect(req.getContextPath() + "/filelist");
    }
}
