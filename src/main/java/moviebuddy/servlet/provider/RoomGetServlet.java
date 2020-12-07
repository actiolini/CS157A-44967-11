package moviebuddy.servlet.provider;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int theatreId = Integer.parseInt(request.getParameter("theatreId"));
            Theatre theatre = theatreDAO.getTheatreById(theatreId);
            List<Room> rooms = theatreDAO.listRooms(theatreId);
            HttpSession session = request.getSession();
            session.setAttribute(THEATRE_ID, theatreId);
            session.setAttribute(THEATRE_NAME, theatre.getTheatreName());
            session.setAttribute(ROOMS, rooms);
            response.sendRedirect("manageroom.jsp");
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("roomTheatreId") != null) {
                int theatreId = Integer.parseInt(session.getAttribute("roomTheatreId").toString());
                Theatre theatre = theatreDAO.getTheatreById(theatreId);
                List<Room> rooms = theatreDAO.listRooms(theatreId);
                session.setAttribute(THEATRE_NAME, theatre.getTheatreName());
                session.setAttribute(ROOMS, rooms);
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}