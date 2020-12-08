package moviebuddy.servlet.provider.theatre;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.util.Validation;

@WebServlet("/TheatreCreate")
public class TheatreCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 5383246290874129363L;

    private static final String NAME = "theatreNameUpload";
    private static final String ADDRESS = "theatreAddressUpload";
    private static final String CITY = "theatreCityUpload";
    private static final String STATE = "theatreStateUpload";
    private static final String COUNTRY = "theatreCountryUpload";
    private static final String ZIP = "theatreZipUpload";

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String theatreName = Validation.sanitize(request.getParameter("theatreName"));
            String address = Validation.sanitize(request.getParameter("address"));
            String city = Validation.sanitize(request.getParameter("city"));
            String state = Validation.sanitize(request.getParameter("state"));
            String country = Validation.sanitize(request.getParameter("country"));
            String zip = Validation.sanitize(request.getParameter("zip"));
            String errorMessage = "";
            if (theatreDAO.getTheatreByName(theatreName) != null) {
                errorMessage = "Theatre name already existed";
            }
            if (errorMessage.isEmpty()) {
                errorMessage = theatreDAO.createTheatre(theatreName, address, city, state, country, zip);
            }
            if (errorMessage.isEmpty()) {
                response.sendRedirect("managetheatre.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", errorMessage);
                session.setAttribute(NAME, theatreName);
                session.setAttribute(ADDRESS, address);
                session.setAttribute(CITY, city);
                session.setAttribute(STATE, state);
                session.setAttribute(COUNTRY, country);
                session.setAttribute(ZIP, zip);
                response.sendRedirect("theatrecreate.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
