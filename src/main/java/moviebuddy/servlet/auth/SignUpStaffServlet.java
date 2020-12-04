package moviebuddy.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import moviebuddy.dao.UserDAO;
import moviebuddy.util.Passwords;
import moviebuddy.util.Validation;

@WebServlet("/SignUpStaff")
public class SignUpStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 6851275245718964069L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int staffId = -1;

            // Sanitize user inputs
            String role = Validation.sanitize(request.getParameter("role"));
            String userName = Validation.sanitize(request.getParameter("userName"));
            String email = Validation.sanitize(request.getParameter("email"));
            String password = Validation.sanitize(request.getParameter("password"));

            // Validate user inputs
            String invalidRole = Validation.validateRole(role);
            String invalidUserName = Validation.validateUserName(userName);
            String invalidEmail = Validation.validateEmail(email);
            if (invalidEmail.isEmpty() && userDAO.getRegisteredUser(email) != null) {
                invalidEmail = "Email is already registered\n";
            }
            String invalidPassword = Validation.validatePassword(password);
            String message = invalidRole + invalidUserName + invalidEmail + invalidPassword;
            if (message.isEmpty()) {
                staffId = userDAO.createStaff(role, userName, email, password);
            }
            HttpSession session = request.getSession();
            if (staffId > -1) {
                session.setAttribute("signupStaffId", staffId);
                session.setAttribute("currentSession",
                        Passwords.applySHA256(session.getId() + request.getRemoteAddr()));
            } else {
                session.setAttribute("signupRole", request.getParameter("role"));
                session.setAttribute("signupUserName", request.getParameter("userName"));
                session.setAttribute("signupEmail", request.getParameter("email"));
                session.setAttribute("roleError", invalidRole);
                session.setAttribute("userNameError", invalidUserName);
                session.setAttribute("emailError", invalidEmail);
                session.setAttribute("passwordError", invalidPassword);
            }
            response.sendRedirect("staffsignup.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
