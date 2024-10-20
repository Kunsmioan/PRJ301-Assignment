/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import dal.assignment.PlanDBContext;
import entity.assignment.Plan;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProductionPlanLoadPlansPPDController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        List<Plan> plans = db.list();

        // Set the plans as a request attribute
        request.setAttribute("plans", plans);
        request.getRequestDispatcher("../productionplan/loadPlansPPD.jsp").forward(request, response);
    }

}
