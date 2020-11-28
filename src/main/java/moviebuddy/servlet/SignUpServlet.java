package moviebuddy.servlet;

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

@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 6851275245718964069L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Sanitize user inputs
            String userName = Validation.sanitize(request.getParameter("userName"));
            String email = Validation.sanitize(request.getParameter("email"));
            String password = Validation.sanitize(request.getParameter("password"));
            String rePassword = Validation.sanitize(request.getParameter("rePassword"));

            // Validate user inputs
            String invalidUserName = Validation.validateUserName(userName);
            String invalidEmail = Validation.validateEmail(email);
            if (invalidEmail.isEmpty() && userDAO.getRegisteredUser(email) != null) {
                invalidEmail = "Email is already registered";
            }
            String invalidPassword = Validation.validatePassword(password);
            String invalidRePassword = Validation.validateRePassword(password, rePassword);

            String message = invalidUserName + invalidEmail + invalidPassword + invalidRePassword;
            if (message.isEmpty() && userDAO.signUp(userName, email, password)) {
                // Sign up successfully
                User user = userDAO.signIn(email, password);
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
                session.setAttribute("signupUserName", request.getParameter("userName"));
                session.setAttribute("signupEmail", request.getParameter("email"));
                session.setAttribute("userNameError", invalidUserName);
                session.setAttribute("emailError", invalidEmail);
                session.setAttribute("passwordError", invalidPassword);
                session.setAttribute("rePasswordError", invalidRePassword);
                response.sendRedirect("signin.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
