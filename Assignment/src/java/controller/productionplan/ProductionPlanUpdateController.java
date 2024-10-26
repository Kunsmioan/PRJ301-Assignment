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

        //validate name to uppercase first letter
        Validation validation = new Validation();
        plan.setName(validation.nameValid(request.getParameter("name")));

        //get another attributes from form
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
        List<PlanCampaign> existCampaigns = dbPC.listByPlanId(id_raw);  // Assume you have a method to fetch campaigns by plan ID

        // Get product IDs (pids) from the form
        String[] pids = request.getParameterValues("pid");

        for (String pid : pids) {
            Product product = new Product();
            product.setId(Integer.parseInt(pid));

            // Initialize quantity and estimate from request parameters
            String raw_quantity = request.getParameter("quantity" + pid);
            String raw_estimate = request.getParameter("estimate" + pid);

            //check quantity and estimate
            int quantity;
            if (raw_quantity != null && !raw_quantity.isEmpty()) {
                quantity = Integer.parseInt(raw_quantity);
            } else {
                quantity = 0;
            }

            float estimate;
            if (raw_estimate != null && !raw_estimate.isEmpty()) {
                estimate = Float.parseFloat(raw_estimate);
            } else {
                estimate = 0;
            }

            // Find or create a new PlanCampaign for the product
            PlanCampaign existCampaign = null;
            for (PlanCampaign c : existCampaigns) {
                if (c.getProduct().getId() == product.getId()) {
                    existCampaign = c;  // Found the existing campaign
                    break;
                }
            }

            // If no exist campaign was found, create a new one
            if (existCampaign == null) {
                existCampaign = new PlanCampaign();
                existCampaign.setProduct(product);  // Set the product for the campaign
                existCampaign.setPlan(plan);  // Associate the campaign with the plan
            }

            // Set the quantity and estimate for the campaign
            existCampaign.setQuantity(quantity);
            existCampaign.setEstimate(estimate);

            // Check if both quantity and estimate are greater than 0
            if (quantity >= 0 && estimate >= 0) {
                dbPC.updateQAE(existCampaign); // Update only quantity and estimate
            }
        }

        // Update the plan
        db.update(plan);

        // Redirect after updated successfully 
        response.sendRedirect("../productionplan/loadPlansPPD");
    }

}
