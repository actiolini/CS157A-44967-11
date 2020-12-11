package moviebuddy.servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import moviebuddy.dao.TicketDAO;
import moviebuddy.model.Ticket;

@WebServlet("/CheckOut")
public class CheckOutServerlet extends HttpServlet {
    private static final long serialVersionUID = 0;
    private TicketDAO ticketDAO;

    public void init() {
        ticketDAO = new TicketDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int scheduleId = Integer.parseInt(request.getParameter("scheduleid"));
            int theatreId = Integer.parseInt(request.getParameter("theatreid"));
            String[] seatNumber = request.getParameterValues("seatnumber");
            List<Ticket> tickets = new ArrayList<Ticket>();
            for(String seat : seatNumber){
                Ticket ticket = ticketDAO.getReceiptInfo(scheduleId,seat,theatreId);
                tickets.add(ticket);
            }
            request.setAttribute("tickets", tickets);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
