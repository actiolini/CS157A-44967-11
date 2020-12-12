package moviebuddy.servlet.provider.room;

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
import moviebuddy.model.Room;
import moviebuddy.util.Validation;

@WebServlet("/RoomGet")
public class RoomGetServlet extends HttpServlet {
    private static final long serialVersionUID = 7128033020470518231L;

    private static final String THEATRE_ID = "roomTheatreId";
    private static final String THEATRE_NAME = "roomTheatreName";
    private static final String ROOMS = "roomList";

    private TheatreDAO theatreDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute(THEATRE_ID) != null) {
                String theatreId = session.getAttribute(THEATRE_ID).toString();
                List<Room> rooms = theatreDAO.listRooms(theatreId);
                request.setAttribute(ROOMS, rooms);
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
            String theatreId = Validation.sanitize(request.getParameter("theatreId"));
            Theatre theatre = theatreDAO.getTheatreById(theatreId);
            session.setAttribute(THEATRE_ID, theatreId);
            session.setAttribute(THEATRE_NAME, theatre.getTheatreName());
            response.sendRedirect("manageroom.jsp");

        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
