package moviebuddy.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import moviebuddy.dao.UserDAO;
import moviebuddy.util.Validation;

@WebServlet("/SignIn")
public class SignInServlet extends HttpServlet {
    private static final long serialVersionUID = 4660290895566468329L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Sanitize user inputs
            String email = Validation.sanitize(request.getParameter("email"));
            String password = Validation.sanitize(request.getParameter("password"));
            if (userDAO.signIn(email, password)) {
                // Sign in successfully

                // Session will be added here
                response.sendRedirect("home.jsp");
            } else {
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("password", request.getParameter("password"));
                request.setAttribute("message", "Invalid email/password! Please try again.");
                RequestDispatcher rd = request.getRequestDispatcher("signin.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
