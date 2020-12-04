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
                user = userDAO.signInStaff(staffId, password);
            }

            HttpSession session = request.getSession();
            if (user != null) {
                // Sign in successfully
                session.setAttribute("accountId", user.getAccountId());
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("zip", user.getZip());
                session.setAttribute("buddyPoints", user.getBuddyPoints());
                session.setAttribute("autoRenew", user.getAutoRenew());
                session.setAttribute("endDate", user.getEndDate());
                session.setAttribute("staffId", user.getStaffId());
                session.setAttribute("role", user.getRole());
                session.setAttribute("currentSession",
                        Passwords.applySHA256(session.getId() + request.getRemoteAddr()));
                response.sendRedirect("home.jsp");
            } else {
                session.setAttribute("signinStaffId", request.getParameter("staffId"));
                session.setAttribute("signinMessage", "Invalid staff ID/password! Please try again.");
                response.sendRedirect("staffsignin.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
