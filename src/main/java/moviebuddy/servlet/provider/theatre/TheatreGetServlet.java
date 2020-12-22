package moviebuddy.servlet.provider.theatre;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.model.Theatre;
import moviebuddy.util.S;

@WebServlet("/TheatreGet")
public class TheatreGetServlet extends HttpServlet {
    private static final long serialVersionUID = -4869640868654901643L;

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Theatre> theatres = new LinkedList<>();
            HttpSession session = request.getSession();
            Object role = session.getAttribute(S.ROLE);

            // Retrieve list of theatres for admin
            if (role != null && role.equals(S.ADMIN)) {
                theatres = theatreDAO.listTheatres();
            }

            // Retieve theatre information for manager
            if (role != null && role.equals(S.MANAGER)) {
                String employTheatreId = "";
                Object employIdObj = session.getAttribute(S.EMPLOY_THEATRE_ID);
                if (employIdObj != null) {
                    employTheatreId = employIdObj.toString();
                }
                Theatre theatre = theatreDAO.getTheatreById(employTheatreId);
                theatres.add(theatre);
            }

            session.setAttribute(S.THEATRE_LIST, theatres);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(S.ERROR_PAGE);
        }
    }
}
