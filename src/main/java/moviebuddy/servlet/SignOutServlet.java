package moviebuddy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;

@WebServlet("/SignOut")
public class SignOutServlet extends HttpServlet {
    private static final long serialVersionUID = -5845132156063049133L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Iterator<String> attributes = session.getAttributeNames().asIterator();
        while (attributes.hasNext()) {
            session.removeAttribute(attributes.next());
        }
        session.invalidate();
        response.sendRedirect("home.jsp");
    }
}
