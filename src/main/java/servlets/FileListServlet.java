package servlets;

import accounts.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@WebServlet("/filelist")
public class FileListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (AccountService.getUserBySessionId(session.getId()) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
//        if (session == null || session.getAttribute("username") == null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }

        String path = req.getParameter("path");

        if (path == null || path.isEmpty()) {
            path = System.getProperty("user.home");
        }

        File dir = new File(path);

        String parentDir = dir.getParent();

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File dir) {
                return !(dir.isDirectory() && dir.listFiles() == null);
            }
        });

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && !f2.isDirectory()) {
                    return -1;
                } else if (!f1.isDirectory() && f2.isDirectory()) {
                    return 1;
                } else {
                    return f1.getName().compareTo(f2.getName());
                }
            }
        });

        System.out.println("Current path: " + path);
        System.out.println("Parent dir: " + parentDir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String creationDate = dateFormat.format(new Date());

        req.setAttribute("currentDir", path.replace('\\','/'));
        req.setAttribute("files", files);
        req.setAttribute("creationDate", creationDate);
        req.setAttribute("parentDir", parentDir);
        req.getRequestDispatcher("filelist.jsp").forward(req, resp);
    }
}
