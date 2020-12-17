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

@WebServlet("/SignInStaff")
public class SignInStaffServlet extends HttpServlet {
    private static final long serialVersionUID = -7713354808955052674L;

    private static final String ACCOUNT_ID = "accountId";
    private static final String USER_NAME = "userName";
    private static final String EMAIL = "email";
    private static final String ZIP = "zip";
    private static final String BUDDY_POINTS = "buddyPoints";
    private static final String AUTO_RENEW = "autoRenew";
    private static final String END_DATE = "endDate";
    private static final String STAFF_ID = "staffId";
    private static final String ROLE = "role";
    private static final String THEATRE_ID = "employTheatreId";
    private static final String STAFF_ID_INPUT = "signinStaffId";

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = null;
            // Sanitize user inputs
            String staffId = Validation.sanitize(request.getParameter("staffId"));
            String password = Validation.sanitize(request.getParameter("password"));
            String invalidStaffId = Validation.validateStaffId(staffId);
            if (invalidStaffId.isEmpty()) {
                user = userDAO.signInProvider(staffId, password);
            }

            HttpSession session = request.getSession();
            if (user != null) {
                // Sign in successfully
                session.setAttribute(ACCOUNT_ID, user.getAccountId());
                session.setAttribute(USER_NAME, user.getUserName());
                session.setAttribute(EMAIL, user.getEmail());
                session.setAttribute(ZIP, user.getZip());
                session.setAttribute(BUDDY_POINTS, user.getBuddyPoints());
                session.setAttribute(AUTO_RENEW, user.getAutoRenew());
                session.setAttribute(END_DATE, user.getEndDate());
                session.setAttribute(STAFF_ID, user.getStaffId());
                session.setAttribute(ROLE, user.getRole());
                session.setAttribute(THEATRE_ID, user.getTheatre_id());
                session.setAttribute("currentSession",
                        Passwords.applySHA256(session.getId() + request.getRemoteAddr()));
                response.sendRedirect("home.jsp");
            } else {
                session.setAttribute(STAFF_ID_INPUT, request.getParameter("staffId"));
                session.setAttribute("errorMessage", "Invalid staff ID/password! Please try again.");
                response.sendRedirect("staffsignin.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
