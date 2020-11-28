package moviebuddy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;

import moviebuddy.dao.UserDAO;
import moviebuddy.model.User;
import moviebuddy.util.Validation;

@WebServlet("/FindRegisteredUser")
public class FindRegisteredUserServlet extends HttpServlet {
    private static final long serialVersionUID = 5105192454349691062L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = Validation.sanitize(request.getParameter("email"));
            User user = userDAO.getRegisteredUser(email);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            if (user == null) {
                out.print("");
            } else {
                out.print("Email is already registered");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
