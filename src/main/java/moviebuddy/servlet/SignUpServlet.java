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
            String userName = Validation.sanitize(request.getParameter("userName"));
            String email = Validation.sanitize(request.getParameter("email"));
            String password = Validation.sanitize(request.getParameter("password"));
            String rePassword = Validation.sanitize(request.getParameter("rePassword"));

            String invalidUserName = Validation.validateUserName(userName);
            String invalidEmail = Validation.validateEmail(email);
            if (invalidEmail.isEmpty() && userDAO.getRegisterdUser(email) != null) {
                invalidEmail = "Email is already registered";
            }
            String invalidPassword = Validation.validatePassword(password);
            String invalidRePassword = Validation.validateRePassword(password, rePassword);

            String message = invalidUserName + invalidEmail + invalidPassword + invalidRePassword;
            if (message.isEmpty() && userDAO.signUp(userName, email, password, rePassword)) {
                response.sendRedirect("home.jsp");
            } else {
                // request.setAttribute("", );
                // request.setAttribute("", );
                // request.setAttribute("", );
                // request.setAttribute("", );
                request.setAttribute("userNameError", invalidUserName);
                request.setAttribute("emailError", invalidEmail);
                request.setAttribute("passwordError", invalidPassword);
                request.setAttribute("rePasswordError", invalidRePassword);
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
