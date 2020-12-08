package moviebuddy.servlet.provider.ticketprice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.util.Validation;

@WebServlet("/TicketPriceDelete")
public class TicketPriceDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = -4687243445163545649L;

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int theatreId = Integer.parseInt(Validation.sanitize(request.getParameter("theatreId")));
            String startTime = Validation.sanitize(request.getParameter("startTime"));
            String errorMessage = theatreDAO.deleteTicketPrice(theatreId, startTime);
            if (!errorMessage.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", errorMessage);
            }
            response.sendRedirect("ticketprice.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
