package moviebuddy.servlet.provider.ticketprice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.model.Theatre;
import moviebuddy.model.TicketPrice;
import moviebuddy.util.Validation;

@WebServlet("/TicketPriceGet")
public class TicketPriceGetServlet extends HttpServlet {
    private static final long serialVersionUID = -1637661127728707360L;

    private static final String THEATRE_ID = "ticketPriceTheatreId";
    private static final String THEATRE_NAME = "ticketPriceTheatreName";
    private static final String TICKET_PRICES = "ticketPriceList";

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
                String theatreId = Validation.sanitize(request.getParameter("theatreId"));
                Theatre theatre = theatreDAO.getTheatreById(theatreId);
                List<TicketPrice> ticketPrices = theatreDAO.listTicketPrices(theatreId);
                session.setAttribute(THEATRE_ID, theatreId);
                session.setAttribute(THEATRE_NAME, theatre.getTheatreName());
                session.setAttribute(TICKET_PRICES, ticketPrices);
                response.sendRedirect("ticketprice.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute(THEATRE_ID) != null) {
                String theatreId = Validation.sanitize(session.getAttribute(THEATRE_ID).toString());
                List<TicketPrice> ticketPrices = theatreDAO.listTicketPrices(theatreId);
                session.setAttribute(TICKET_PRICES, ticketPrices);
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
