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
        HttpSession session = req.getSession();
//        if (session.getAttribute("user") == null) {
//            resp.sendRedirect("/login");
//        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("pass");
        String email = req.getParameter("email");

        if (AccountService.getUserByLogin(username) == null){
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

        UserProfile profile = new UserProfile(username, password, email);
        AccountService.addNewUser(profile); //TODO
        AccountService.addSession(req.getSession().getId(), profile);
        HttpSession session = req.getSession();
        System.out.println("УСПЕХ");
        resp.sendRedirect(req.getContextPath() + "/filelist");
        //req.getRequestDispatcher("filelist.jsp").forward(req, resp);
    }
}
