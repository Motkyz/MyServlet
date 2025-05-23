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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/files");
            return;
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserProfile userProfile = null;
        try {
            userProfile = AccountService.getUserByLogin(username);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            doGet(req, resp);
            return;
        }

        if (userProfile == null){
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
        else if (userProfile.getPass().equals(password)){
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/filelist");
        }
        else{
            doGet(req, resp);
        }
    }
}
