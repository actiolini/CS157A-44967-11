package moviebuddy.servlet.provider.staff;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.dao.UserDAO;
import moviebuddy.model.Theatre;
import moviebuddy.model.User;
import moviebuddy.util.Validation;

@WebServlet("/StaffGet")
public class StaffGetServlet extends HttpServlet {
    private static final long serialVersionUID = 5635940102912001720L;

    private static final String SELECTED_THEATRE_ID = "selectTheatreId";
    private static final String ADMINS = "adminUserList";

    private static final String THEATRE_NAME = "staffTheatreName";
    private static final String STAFFS = "staffUserList";

    private UserDAO userDAO;
    private TheatreDAO theatreDAO;

    public void init() {
        userDAO = new UserDAO();
        theatreDAO = new TheatreDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && (role.equals("admin") || role.equals("manager"))) {
                String theatreId = "";
                if (role.equals("admin")) {
                    List<User> admins = userDAO.listAdminUser();
                    request.setAttribute(ADMINS, admins);
                    if (session.getAttribute(SELECTED_THEATRE_ID) == null) {
                        List<Theatre> theatres = theatreDAO.listTheatres();
                        if (!theatres.isEmpty()) {
                            session.setAttribute(SELECTED_THEATRE_ID, theatres.get(0).getId());
                        } else {
                            session.setAttribute(SELECTED_THEATRE_ID, "");
                        }
                    }
                    theatreId = session.getAttribute(SELECTED_THEATRE_ID).toString();
                }
                if (role.equals("manager")) {
                    theatreId = session.getAttribute("employTheatreId").toString();
                }
                Theatre theatre = theatreDAO.getTheatreById(theatreId);
                List<User> staffs = userDAO.listStaffByTheatreId(theatreId);
                request.setAttribute(THEATRE_NAME, theatre.getTheatreName());
                request.setAttribute(STAFFS, staffs);
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && role.equals("admin")) {
                String theatreId = Validation.sanitize(request.getParameter("selectTheatreOption"));
                session.setAttribute(SELECTED_THEATRE_ID, theatreId);
                response.sendRedirect("managestaff.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}