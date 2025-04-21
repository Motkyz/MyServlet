package servlets;

import accounts.AccountService;
import accounts.UserProfile;
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
import java.util.Objects;

@WebServlet("/filelist")
public class FileListServlet extends HttpServlet {
    private static final String defaultPath = "C:\\Users\\Matvey\\IdeaProjects\\servlet\\users";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String username = session.getAttribute("username").toString();

        String path = req.getParameter("path");

        String userDirectory = defaultPath + "\\" + username;
        if (path == null || path.isEmpty() || !path.startsWith(userDirectory)) {
            path = userDirectory;
        }
        System.out.println(path);

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String parentDir = dir.getParent();
        if (Objects.equals(parentDir, defaultPath)) {
            parentDir = null;
        }

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

        String currentDir = path.replace(defaultPath, "").replace('\\','/');
        req.setAttribute("currentDir", currentDir);
        req.setAttribute("files", files);
        req.setAttribute("creationDate", creationDate);
        req.setAttribute("parentDir", parentDir);
        req.setAttribute("username", username);
        req.getRequestDispatcher("filelist.jsp").forward(req, resp);
    }
}
