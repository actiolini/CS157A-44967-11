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

@WebServlet("/TheatreEdit")
public class TheatreEditServlet extends HttpServlet {
    private static final long serialVersionUID = 986833348812078854L;

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
                if (request.getParameter("action").equals("save")) {
                    int theatreId = Integer.parseInt(Validation.sanitize(request.getParameter("theatreId")));
                    String theatreName = Validation.sanitize(request.getParameter("theatreName"));
                    String address = Validation.sanitize(request.getParameter("address"));
                    String city = Validation.sanitize(request.getParameter("city"));
                    String state = Validation.sanitize(request.getParameter("state"));
                    String country = Validation.sanitize(request.getParameter("country"));
                    String zip = Validation.sanitize(request.getParameter("zip"));
                    String errorMessage = "";
                    Theatre theatre = theatreDAO.getTheatreByName(theatreName);
                    if (theatre != null && theatre.getId() != theatreId) {
                        errorMessage = "Theatre name already existed";
                    }
                    if (errorMessage.isEmpty()) {
                        errorMessage = theatreDAO.updateTheatre(theatreId, theatreName, address, city, state, country,
                                zip);
                    }
                    if (errorMessage.isEmpty()) {
                        response.sendRedirect("managetheatre.jsp");
                    } else {
                        session.setAttribute("errorMessage", errorMessage);
                        session.setAttribute(NAME, theatreName);
                        session.setAttribute(ADDRESS, address);
                        session.setAttribute(CITY, city);
                        session.setAttribute(STATE, state);
                        session.setAttribute(COUNTRY, country);
                        session.setAttribute(ZIP, zip);
                        response.sendRedirect("theatreedit.jsp");
                    }
                }
                if (request.getParameter("action").equals("cancel")) {
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
