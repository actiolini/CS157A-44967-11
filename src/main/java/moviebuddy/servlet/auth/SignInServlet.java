package moviebuddy.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import moviebuddy.dao.UserDAO;
import moviebuddy.model.User;
import moviebuddy.util.Passwords;
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
            User user = userDAO.signInCustomer(email, password);
            if (user != null) {
                // Sign in successfully
                HttpSession session = request.getSession();
                session.setAttribute("accountId", user.getAccountId());
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("zip", user.getZip());
                session.setAttribute("currentSession",
                        Passwords.applySHA256(session.getId() + request.getRemoteAddr()));
                response.sendRedirect("home.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("signinEmail", request.getParameter("email"));
                session.setAttribute("signinMessage", "Invalid email/password! Please try again.");
                response.sendRedirect("signin.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
