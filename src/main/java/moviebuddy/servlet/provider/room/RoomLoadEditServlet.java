package moviebuddy.servlet.provider.room;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.model.Room;
import moviebuddy.util.Validation;

@WebServlet("/RoomLoadEdit")
public class RoomLoadEditServlet extends HttpServlet {
    private static final long serialVersionUID = 8915502187614190878L;

    private static final String ROOM_ID = "roomIdEdit";
    private static final String ROOM_NUMBER = "roomNumberEdit";
    private static final String SECTIONS = "roomSectionsEdit";
    private static final String SEATS = "roomSeatsEdit";

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
                String roomNumber = Validation.sanitize(request.getParameter("roomNumber"));
                Room room = theatreDAO.getRoomById(theatreId, roomNumber);
                if (room != null) {
                    session.setAttribute(ROOM_ID, roomNumber);
                    session.setAttribute(ROOM_NUMBER, room.getRoomNumber());
                    session.setAttribute(SECTIONS, room.getNumberOfRows());
                    session.setAttribute(SEATS, room.getSeatsPerRow());
                    response.sendRedirect("roomedit.jsp");
                } else {
                    response.sendRedirect("manageroom.jsp");
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
