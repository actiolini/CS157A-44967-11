package moviebuddy.servlet.provider.theatre;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.model.Theatre;
import moviebuddy.util.Validation;

@WebServlet("/TheatreLoadEdit")
public class TheatreLoadEditServlet extends HttpServlet {
    private static final long serialVersionUID = 3856058405158486366L;

    private static final String THEATRE_ID = "theatreIdEdit";
    private static final String NAME = "theatreNameEdit";
    private static final String ADDRESS = "theatreAddressEdit";
    private static final String CITY = "theatreCityEdit";
    private static final String STATE = "theatreStateEdit";
    private static final String COUNTRY = "theatreCountryEdit";
    private static final String ZIP = "theatreZipEdit";

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && role.equals("admin")) {
                int theatreId = Integer.parseInt(Validation.sanitize(request.getParameter("theatreId")));
                Theatre theatre = theatreDAO.getTheatreById(theatreId);
                if (theatre != null) {
                    session.setAttribute(THEATRE_ID, theatreId);
                    session.setAttribute(NAME, theatre.getTheatreName());
                    session.setAttribute(ADDRESS, theatre.getAddress());
                    session.setAttribute(CITY, theatre.getCity());
                    session.setAttribute(STATE, theatre.getState());
                    session.setAttribute(COUNTRY, theatre.getCountry());
                    session.setAttribute(ZIP, theatre.getZip());
                    response.sendRedirect("theatreedit.jsp");
                } else {
                    response.sendRedirect("managetheatre.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
