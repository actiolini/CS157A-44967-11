// package moviebuddy.servlet;

// import javax.servlet.annotation.WebServlet;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

// import java.io.IOException;
// import java.util.List;

// import moviebuddy.dao.ReceiptDAO;
// import moviebuddy.model.Receipt;
// import moviebuddy.dao.UserDAO;
// import moviebuddy.model.User;
// import moviebuddy.util.Passwords;
// import moviebuddy.util.Validation;

// @WebServlet("")
// public class GetReceiptsServerlet extends HttpServlet {
//     private static final long serialVersionUID = 4366896761698484912L;
//     private static final String RECEIPTS = "receiptList";

//     private ReceiptDAO receiptDAO;

//     public void init() {
//         receiptDAO = new ReceiptDAO();
//     }

//     protected void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         try {
//             HttpSession session = request.getSession();
//             String accountID = session.getAttribute("accountId").toString();
//             List<Receipt> receipts = receiptDAO.listReceipts(accountID);
//             request.setAttribute(RECEIPTS, receipts);
//         } catch (Exception e) {
//             response.sendRedirect("error.jsp");
//             e.printStackTrace();
//         }
//     }
// }
