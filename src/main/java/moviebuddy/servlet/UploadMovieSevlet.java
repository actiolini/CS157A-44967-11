package moviebuddy.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.InputStream;

import moviebuddy.dao.UploadMovieDAO;

@WebServlet("/upload")
@MultipartConfig
public class UploadMovieSevlet extends HttpServlet {
    private static final long serialVersionUID = 3223494789974884818L;
    private UploadMovieDAO uploadMovieDAO;

    public void init() {
        uploadMovieDAO = new UploadMovieDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part posterPart = request.getPart("uploadfile");
        try {
            InputStream poster = posterPart.getInputStream();
            uploadMovieDAO.upload(poster);
            request.setAttribute("message", "Upload Successfully!");
            RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
            rd.forward(request, response);
        } catch (IOException e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
