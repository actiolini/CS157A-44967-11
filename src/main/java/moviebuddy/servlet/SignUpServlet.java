package moviebuddy.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import moviebuddy.dao.AuthenticateDAO;

@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 6851275245718964069L;
    private AuthenticateDAO authDAO;

    public void init() {
        authDAO = new AuthenticateDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userName = sanitize(request, "username");
            String email = sanitize(request, "email");
            String pass = sanitize(request, "password");
            String rePass = sanitize(request, "re-password");
            String message = authDAO.signUp(userName, email, pass, rePass);
            if (message.isEmpty()) {
                response.sendRedirect("home.jsp");
            } else {
                request.setAttribute("message", message);
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private String sanitize(HttpServletRequest request, String input) {
        return Jsoup.clean(request.getParameter(input), Whitelist.none());
    }
}
