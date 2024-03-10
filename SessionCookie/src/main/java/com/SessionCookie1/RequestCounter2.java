package com.SessionCookie1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RequestCounter")
public class RequestCounter2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        Integer count = (Integer) session.getAttribute("count");
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        session.setAttribute("count", count);

        // Counting cookies
        int cookieCount = 0;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            cookieCount = cookies.length;
        }

        // Check if user is new or returning
        boolean isNewUser = true;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                isNewUser = false;
                break;
            }
        }

        if (isNewUser) {
            Cookie userCookie = new Cookie("user", "visited");
            userCookie.setMaxAge(60 * 60 * 24 * 365); // 1 year
            response.addCookie(userCookie);
        }

        response.getWriter().println("<html>");
        response.getWriter().println("<head><title>Request Counter</title></head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h2>Number of requests: " + count + "</h2>");
        response.getWriter().println("<h2>Number of cookies: " + cookieCount + "</h2>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
