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
import java.util.List;
import validatation.Validation;

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
        PlanCampaignDBContext dbPlanCampaign = new PlanCampaignDBContext();
        String id = req.getParameter("id");
        int id_raw = Integer.parseInt(id);

        req.setAttribute("plan", dbPlan.get(id_raw));
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDepts.get("WS"));
        req.setAttribute("campaign", dbPlanCampaign.listByPlanId(id_raw));
        req.setAttribute("plandept", dbPlan.get(id_raw).getDept());

        req.getRequestDispatcher("../productionplan/update.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        PlanDBContext db = new PlanDBContext();
        PlanCampaignDBContext dbPC = new PlanCampaignDBContext();

        String id = request.getParameter("id");
        int id_raw = Integer.parseInt(id);
        Plan plan = db.get(id_raw);
        
        //validate name to uppercase
        Validation validation = new Validation();
        plan.setName(validation.nameValid(request.getParameter("name")));
        
        plan.setStart(Date.valueOf(request.getParameter("from")));
        plan.setEnd(Date.valueOf(request.getParameter("to")));
        Department d = new Department();
        d.setId(Integer.parseInt(request.getParameter("did")));
        plan.setDept(d);

        // Get the plan's total quantity from the form
        String rawPlanQuantity = request.getParameter("totalQuantity");  // Plan-level quantity
        int planQuantity = (rawPlanQuantity != null && rawPlanQuantity.length() > 0) ? Integer.parseInt(rawPlanQuantity) : 0;
        plan.setQuantity(planQuantity);  // Set the plan-level quantity

        // Retrieve the existing campaigns for this plan from the database
        List<PlanCampaign> existingCampaigns = dbPC.listByPlanId(id_raw);  // Assume you have a method to fetch campaigns by plan ID

        // Get product IDs (pids) from the form
        String[] pids = request.getParameterValues("pid");

        for (String pid : pids) {
            Product product = new Product();
            product.setId(Integer.parseInt(pid));

            // Initialize quantity and estimate from request parameters
            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_estimate = request.getParameter("estimate" + pid);

            int quantity = (raw_quantity == null || raw_quantity.length() == 0) ? 0 : Integer.parseInt(raw_quantity);
            float estimate = (raw_estimate == null || raw_estimate.length() == 0) ? 0 : Float.parseFloat(raw_estimate) ;

            // Find or create a new PlanCampaign for the product
            PlanCampaign existingCampaign = null;
            for (PlanCampaign c : existingCampaigns) {
                if (c.getProduct().getId() == product.getId()) {
                    existingCampaign = c;  // Found the existing campaign
                    break;
                }
            }

            // If no existing campaign was found, create a new one
            if (existingCampaign == null) {
                existingCampaign = new PlanCampaign();
                existingCampaign.setProduct(product);  // Set the product for the campaign
                existingCampaign.setPlan(plan);  // Associate the campaign with the plan
            }

            // Set the quantity and estimate for the campaign
            existingCampaign.setQuantity(quantity);
            existingCampaign.setEstimate(estimate);

            // Check if both quantity and estimate are greater than 0
            if (quantity >= 0 && estimate >= 0) {
                dbPC.updateQAE(existingCampaign); // Update in the database
            }
        }

        // Update the plan details in the database
        db.update(plan);

        // Redirect after successful update
        response.sendRedirect("../productionplan/loadPlansPPD");
// Continue with your existing logic to insert the plan
//            response.getWriter().println("Your plan does not have any products / campaigns");
    }

}
