package moviebuddy.servlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import moviebuddy.dao.UserDAO;
import moviebuddy.util.Validation;

@WebServlet("/SignUpStaff")
public class SignUpStaffServlet extends HttpServlet {
    private static final long serialVersionUID = 6851275245718964069L;

    private static final String ROLE = "signupStaffRole";
    private static final String LOCATION = "signupStaffTheatreLocation";
    private static final String USERNAME = "signupStaffUserName";
    private static final String EMAIL = "signupStaffEmail";
    private static final String ROLE_ERROR = "roleError";
    private static final String LOCATION_ERROR = "theatreLocationError";
    private static final String USERNAME_ERROR = "userNameError";
    private static final String EMAIL_ERROR = "emailError";
    private static final String PASSWORD_ERROR = "passwordError";

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && (role.equals("admin") || role.equals("manager"))) {
                // Sanitize user inputs
                String roleInput = Validation.sanitize(request.getParameter("role"));
                String theatreLocation = Validation.sanitize(request.getParameter("theatreLocation"));
                String userName = Validation.sanitize(request.getParameter("userName"));
                String email = Validation.sanitize(request.getParameter("email"));
                String password = Validation.sanitize(request.getParameter("password"));

                // Validate user inputs
                String invalidRole = Validation.validateRole(roleInput);
                String invalidLocation = "";
                if (invalidRole.isEmpty() && !roleInput.equals("admin")) {
                    invalidLocation = Validation.validateTheatreLocation(theatreLocation);
                }
                String invalidUserName = Validation.validateUserName(userName);
                String invalidEmail = Validation.validateEmail(email);
                if (invalidEmail.isEmpty() && userDAO.getRegisteredUser(email) != null) {
                    invalidEmail = "Email is already registered\n";
                }
                String invalidPassword = Validation.validatePassword(password);
                String invalid = invalidRole + invalidLocation + invalidUserName + invalidEmail + invalidPassword;

                String errorMessage = "";
                if (invalid.isEmpty() && role.equals("admin")) {
                    errorMessage = userDAO.signUpProvider(roleInput, theatreLocation, userName, email, password);
                }
                if (invalid.isEmpty() && role.equals("manager")
                        && !(roleInput.equals("admin") || roleInput.equals("manager"))) {
                    String employTheatreId = session.getAttribute("employTheatreId").toString();
                    if (employTheatreId.equals(theatreLocation)) {
                        errorMessage = userDAO.signUpProvider(roleInput, theatreLocation, userName, email, password);
                    } else {
                        errorMessage = "Unable to add staff to a different theatre\n";
                    }
                }
                if (errorMessage.isEmpty()) {
                    response.sendRedirect("managestaff.jsp");
                } else {
                    session.setAttribute(ROLE, request.getParameter("role"));
                    session.setAttribute(LOCATION, request.getParameter("theatreLocation"));
                    session.setAttribute(USERNAME, request.getParameter("userName"));
                    session.setAttribute(EMAIL, request.getParameter("email"));
                    session.setAttribute(ROLE_ERROR, invalidRole);
                    session.setAttribute(LOCATION_ERROR, invalidLocation);
                    session.setAttribute(USERNAME_ERROR, invalidUserName);
                    session.setAttribute(EMAIL_ERROR, invalidEmail);
                    session.setAttribute(PASSWORD_ERROR, invalidPassword);
                    session.setAttribute("errorMessage", errorMessage);
                    response.sendRedirect("staffsignup.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
