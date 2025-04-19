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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("e-mail");

        if (AccountService.getUserByLogin(username) == null){
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
        else{
            UserProfile userProfile = AccountService.getUserByLogin(username);
            AccountService.addSession(session.getId(), userProfile);
            resp.sendRedirect(req.getContextPath() + "/filelist");
            return;
        }

        UserProfile profile = new UserProfile(username, password, email);
        AccountService.addNewUser(profile);
        AccountService.addSession(req.getSession().getId(), profile);

        resp.sendRedirect(req.getContextPath() + "/filelist");
    }
}
