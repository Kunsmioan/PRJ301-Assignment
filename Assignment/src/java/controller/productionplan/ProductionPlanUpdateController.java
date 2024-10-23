/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import dal.assignment.DepartmentDBContext;
import dal.assignment.PlanCampaignDBContext;
import dal.assignment.PlanDBContext;
import dal.assignment.ProductDBContext;
import entity.accesscontrol.User;
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
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class ProductionPlanUpdateController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        PlanDBContext dbPlan = new PlanDBContext();
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();
        PlanCampaignDBContext dbPlanCampaign =  new PlanCampaignDBContext();
        String id = req.getParameter("id");
        int id_raw = Integer.parseInt(id);

        req.setAttribute("plan", dbPlan.get(id_raw));
//        req.setAttribute("plancampaign", dbPlan.get(id_raw).getCampains());
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDepts.get("WS"));
        req.setAttribute("campaign", dbPlanCampaign.get(id_raw).getProducts());
         req.setAttribute("plandept", dbPlan.get(id_raw).getDept());

        req.getRequestDispatcher("../productionplan/update.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        String id = request.getParameter("id");
        int id_raw = Integer.parseInt(id);
        Plan plan = db.get(id_raw);
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

        // Add new campaigns from the request
        String[] pids = request.getParameterValues("pid");
        for (String pid : pids) {
            PlanCampaign c = new PlanCampaign();

            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan); // Set existing plan to the campaign

            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_estimate = request.getParameter("estimate" + pid);

            int quantity = (raw_quantity != null && raw_quantity.length() > 0) ? Integer.parseInt(raw_quantity) : 0;
            float estimate = (raw_estimate != null && raw_estimate.length() > 0) ? Float.parseFloat(raw_estimate) : 0;

            c.setQuantity(quantity);
            c.setEstimate(estimate);

            // Add only if both quantity and estimate are valid
            if (quantity > 0 && estimate > 0) {
                plan.getCampains().add(c);
            }
        }

        // Update the existing plan in the database
        db.update(plan); // Implement the update method to handle this logic
        response.sendRedirect("../productionplan/loadPlansPPD");
    }

  
}



