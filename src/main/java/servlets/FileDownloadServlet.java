package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet("/filedownload")
public class FileDownloadServlet extends HttpServlet {
    private static final String defaultPath = "C:\\Users\\Matvey\\IdeaProjects\\servlet\\users";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

        String username = (String) session.getAttribute("username");
        String userDirectory = defaultPath + "\\" + username;

        String downloadedFilePath = URLDecoder.decode(req.getParameter("fileDownloadPath"), "UTF-8");

        if (downloadedFilePath == null || !downloadedFilePath.startsWith(userDirectory)) {
            return;
        }

        File file = new File(downloadedFilePath);
        String fileName = file.getName();
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");

        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" +  encodedFileName + "\"");

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = resp.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
