/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import dal.assignment.DepartmentDBContext;
import dal.assignment.PlanDBContext;
import dal.assignment.ProductDBContext;
import entity.assignment.Department;
import entity.assignment.Plan;
import entity.assignment.PlanCampaign;
import entity.assignment.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import validatation.Validation;

/**
 *
 * @author sonnt-local hand-some
 */
public class ProductionPlanCreateController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();

        request.setAttribute("products", dbProduct.list());
        request.setAttribute("depts", dbDepts.get("WS"));

        request.getRequestDispatcher("../productionplan/create.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Plan plan = new Plan();
        //validate name to uppercase
        Validation validation = new Validation();
        plan.setName(validation.nameValid(request.getParameter("name")));
        plan.setName(request.getParameter("name"));
        plan.setStart(Date.valueOf(request.getParameter("from")));
        plan.setEnd(Date.valueOf(request.getParameter("to")));
        Department d = new Department();
        d.setId(Integer.parseInt(request.getParameter("did")));
        plan.setDept(d);
        
        // Get the plan's total quantity from the form
        String rawPlanQuantity = request.getParameter("totalQuantity");  // Plan-level quantity
        int planQuantity = (rawPlanQuantity != null && rawPlanQuantity.length() > 0) ? Integer.parseInt(rawPlanQuantity) : 0;
        plan.setQuantity(planQuantity);  // Set the plan-level quantity

        // Assuming this is part of your doPost method
        String[] pids = request.getParameterValues("pid");

        for (String pid : pids) {
            PlanCampaign c = new PlanCampaign();

            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan);

            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_estimate = request.getParameter("estimate" + pid);

            int quantity = (raw_quantity == null || raw_quantity.length() == 0) ? 0 :Integer.parseInt(raw_quantity);
            float estimate = (raw_estimate == null || raw_estimate.length() == 0) ? 0 : Float.parseFloat(raw_estimate);

            c.setQuantity(quantity);
            c.setEstimate(estimate);

            // Check if both quantity and estimate are greater than 0
            if (quantity >= 0 && estimate >= 0) {
                plan.getCampains().add(c);
            }
        }

// Continue with your existing logic to insert the plan
        if (plan.getCampains().size() > 0) {
            // Insert
            PlanDBContext db = new PlanDBContext();
            db.insert(plan);
            response.sendRedirect("../productionplan/loadPlansPPD");
        } else {
            response.getWriter().println("Your plan does not have any products / campaigns");
        }

    }

}
