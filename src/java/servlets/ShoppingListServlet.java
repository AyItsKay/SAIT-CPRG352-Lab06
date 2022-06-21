package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShoppingListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        if ( action != null && action.equals("logout") ) { 
            session.invalidate();
            response.sendRedirect("ShoppingList");
        }
        else if ( session.getAttribute("username") == null ) {
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
            return;
        }    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
       
        if ( action.equals("register") && !request.getParameter("username").equals("") ) {
            session.setAttribute("username", request.getParameter("username"));
            session.setAttribute("items", new ArrayList<String>());
        } else if ( action.equals("add") ) {
            ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
            items.add(request.getParameter("newItem"));
            session.setAttribute("items", items);
        } else if ( action.equals("delete") ) {
            String item = request.getParameter("item");
            if ( item != null ) {
                ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
                items.remove(item);
                session.setAttribute("items", items);
            }
        }
        
        response.sendRedirect("ShoppingList");       
    }
}