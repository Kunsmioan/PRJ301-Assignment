/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.schedule;

import controller.accesscontrol.BaseRBACController;
import dal.assignment.PlanDBContext;
import entity.accesscontrol.User;
import entity.assignment.Plan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class WorkshopController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        // Get the username directly from the account parameter
        String username = account.getUsername(); // Assuming 'User' has a getUsername() method

        // Retrieve plans for the specified user
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.getPlansByUser(username);

        // Set plans as a request attribute to be accessed in JSP
        request.setAttribute("plans", plans);

        // Pass the account details to the JSP if needed for display
        request.setAttribute("account", account);

        request.getRequestDispatcher("../schedule/workshop.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
