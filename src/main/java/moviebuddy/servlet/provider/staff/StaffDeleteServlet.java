package moviebuddy.servlet.provider.staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.UserDAO;
import moviebuddy.model.User;
import moviebuddy.util.Validation;

@WebServlet("/StaffDelete")
public class StaffDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = -8552363830506676929L;

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
                String staffId = Validation.sanitize(request.getParameter("staffId"));
                User staff = userDAO.getProviderByStaffId(staffId);
                String errorMessage = "";
                if (staff != null && !staff.getRole().equals("faculty") && role.equals("manager")) {
                    errorMessage = "Unauthorized deletion";
                }
                if (errorMessage.isEmpty()) {
                    errorMessage = userDAO.deleteProvider(staffId);
                }
                if (!errorMessage.isEmpty()) {
                    session.setAttribute("errorMessage", errorMessage);
                }
                response.sendRedirect("managestaff.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
