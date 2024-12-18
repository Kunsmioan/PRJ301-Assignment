/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.assignment;

import dal.DBContext;
import entity.accesscontrol.User;
import entity.assignment.Department;
import entity.assignment.Plan;
import entity.assignment.PlanCampaign;
import entity.assignment.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import validatation.Validation;

/**
 *
 * @author sonnt-local
 */
public class PlanDBContext extends DBContext<Plan> {

//    public static void main(String[] args) {
//        PlanDBContext dbContext = new PlanDBContext(); // Create PlanDBContext instance
//
//        // Step 1: Create a dummy Plan object and set its properties
//        PlanDBContext db = new PlanDBContext();
//        Plan dummyPlan = db.get(30);
//
//        System.out.println(dummyPlan.getName());
//        PlanCampaignDBContext pc = new PlanCampaignDBContext();
//        for (PlanCampaign p : pc.list()) {
//            if (p.getPlan().getId() == (30)) {
//                System.out.println(p.getQuantity() + ", " + p.getEstimate());
//            }
//        }
//        System.out.println(dummyPlan.getCampains());
//
//        try {
//            // Step 3: Insert the dummy plan into the database
//            dbContext.insert(dummyPlan);
//            System.out.println("Dummy plan inserted successfully!");
//
//            // Step 4: Fetch the inserted plan to verify it was added
//            Plan fetchedPlan = dbContext.get(dummyPlan.getId());
//            if (fetchedPlan != null) {
//                System.out.println("Fetched plan: " + fetchedPlan.getName());
//            } else {
//                System.out.println("No plan found with the given ID.");
//            }
//
//            // Step 5: Delete the dummy plan
//            dbContext.delete(dummyPlan);
//            System.out.println("Dummy plan deleted successfully!");
//
//            // Step 6: Verify deletion
//            Plan deletedPlan = dbContext.get(dummyPlan.getId());
//            if (deletedPlan == null) {
//                System.out.println("Plan deleted successfully!");
//            } else {
//                System.out.println("Failed to delete the plan. It still exists in the database.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error during plan processing: " + e.getMessage());
//        }
//    }
    public static void main(String[] args) {
        // Create an instance of PlanDBContext
        PlanDBContext planDBContext = new PlanDBContext();

        // Retrieve plans for a specific user
        String userName = "admin2"; // Example username
        ArrayList<Plan> userPlans = planDBContext.getPlansByUser(userName);

        // Check if any plans were retrieved
        if (userPlans.isEmpty()) {
            System.out.println("No plans found for user: " + userName);
        } else {
            // Print out the plans and their associated campaigns
            for (Plan plan : userPlans) {
                System.out.println("Plan ID: " + plan.getId() + ", Name: " + plan.getName()
                        + ", Start: " + plan.getStart() + ", End: " + plan.getEnd()
                        + ", Department: " + plan.getDept().getName()
                        + ", Manager: " + plan.getDept().getUser().getDisplayname());
                for (PlanCampaign campaign : plan.getCampains()) {
                    System.out.println("  - Campaign ID: " + campaign.getId()
                            + ", Product ID: " + campaign.getProductId()
                            + ", Product Name: " + campaign.getProduct().getName()
                            + ", Quantity: " + campaign.getQuantity()
                            + ", Estimate: " + campaign.getEstimate());
                }
            }
        }
    }

    public ArrayList<Plan> getPlansByUser(String userName) {
    ArrayList<Plan> plans = new ArrayList<>();
    Map<Integer, Plan> planMap = new HashMap<>(); // Map to track plans by ID
    PreparedStatement command = null;
    
    try {
        String sql = "SELECT d.DepartmentID, d.DepartmentName, u.DisplayName, u.UserName, "
                   + "p.PlanID, p.PlanName, p.StartDate, p.EndDate, "
                   + "pc.PlanCampnID, pc.ProductID, pc.Quantity, pro.ProductName, pc.Estimate "
                   + "FROM [User] u "
                   + "INNER JOIN Department d ON u.UserName = d.UserName "
                   + "INNER JOIN [Plan] p ON p.DepartmentID = d.DepartmentID "
                   + "INNER JOIN PlanCampain pc ON p.PlanID = pc.PlanID "
                   + "INNER JOIN Product pro ON pro.ProductID = pc.ProductID "
                   + "WHERE u.UserName = ?";

        command = connection.prepareStatement(sql);
        command.setString(1, userName);
        ResultSet rs = command.executeQuery();
        
        while (rs.next()) {
            // Create and populate User object
            User user = new User();
            user.setUsername(rs.getString("UserName"));
            user.setDisplayname(rs.getString("DisplayName"));

            // Create and populate Department object
            Department department = new Department();
            department.setId(rs.getInt("DepartmentID"));
            department.setName(rs.getString("DepartmentName"));
            department.setUser(user);

            // Create or retrieve the Plan object
            int planId = rs.getInt("PlanID");
            Plan plan = planMap.get(planId);
            if (plan == null) {
                plan = new Plan();
                plan.setId(planId);
                plan.setName(rs.getString("PlanName"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                plan.setDept(department);
                
                planMap.put(planId, plan);
                plans.add(plan);
            }

            // Create and populate PlanCampaign object
            PlanCampaign planCampaign = new PlanCampaign();
            planCampaign.setId(rs.getInt("PlanCampnID"));
            planCampaign.setQuantity(rs.getInt("Quantity"));
            planCampaign.setEstimate(rs.getFloat("Estimate"));
            planCampaign.setProductId(rs.getInt("ProductID"));

            // Create and set Product object, if applicable
            Product product = new Product();
            product.setId(planCampaign.getProductId());
            product.setName(rs.getString("ProductName"));
            planCampaign.setProduct(product);

            // Add PlanCampaign to the Plan's list of campaigns
            plan.getCampains().add(planCampaign);
        }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }
                if (connection != null) {
                    connection.close(); // Close only if it's not managed by a pool
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
    }

    @Override
    public Plan get(int id) {
        PreparedStatement command = null;
        ResultSet rs = null; // Declare ResultSet outside to close it in the finally block
        try {
            String sql = "SELECT PlanID, PlanName, StartDate, EndDate, Quantity, DepartmentID FROM [Plan] WHERE PlanID = ?";
            command = connection.prepareStatement(sql);
            command.setInt(1, id);
            rs = command.executeQuery();

            if (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                plan.setQuantity(rs.getInt("Quantity"));

                Department dept = new Department();
                dept.setId(rs.getInt("DepartmentID"));
                plan.setDept(dept);

                return plan;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, "Get failed", ex);
        }
        return null;
    }

    @Override
    public void insert(Plan plan) {
        try {
            // Insert Plan first and get the generated PlanID
            String insertPlanSQL = "INSERT INTO [Plan] (PlanName, StartDate, EndDate, Quantity, DepartmentID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertPlanSQL, Statement.RETURN_GENERATED_KEYS);

            Validation valid = new Validation();
            ps.setString(1, valid.nameValid(plan.getName()));
            ps.setDate(2, plan.getStart());
            ps.setDate(3, plan.getEnd());
            ps.setInt(4, plan.getQuantity());
            ps.setInt(5, plan.getDept().getId());

            ps.executeUpdate();

            // Get the generated PlanID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedPlanID = rs.getInt(1);
                plan.setId(generatedPlanID);
            }

            // Now insert PlanCampaign(s)
            String insertCampaignSQL = "INSERT INTO PlanCampain (PlanID, ProductID, Quantity, Estimate) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(insertCampaignSQL);

            for (PlanCampaign campaign : plan.getCampains()) {
                ps.setInt(1, plan.getId());  // Use the generated PlanID
                ps.setInt(2, campaign.getProduct().getId());
                ps.setInt(3, campaign.getQuantity());
                ps.setFloat(4, campaign.getEstimate());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Plan plan) {
        try {
            String updatePlanSQL = "UPDATE [dbo].[Plan]\n"
                    + "   SET [PlanName] = ?\n"
                    + "      ,[StartDate] = ?\n"
                    + "      ,[EndDate] = ?\n"
                    + "      ,[Quantity] = ?\n"
                    + "      ,[DepartmentID] = ?\n"
                    + " WHERE PlanID = ?";
            PreparedStatement ps = null;

            ps = connection.prepareStatement(updatePlanSQL);
            Validation valid = new Validation();
            ps.setString(1, valid.nameValid(plan.getName()));
            ps.setDate(2, plan.getStart());
            ps.setDate(3, plan.getEnd());
            ps.setInt(4, plan.getQuantity());
            ps.setInt(5, plan.getDept().getId());
            ps.setInt(6, plan.getId());
            ps.executeUpdate(); // Execute the update

            // Now update PlanCampaign entries associated with the Plan
            String updatePlanCampaignSQL = "UPDATE [dbo].[PlanCampain]\n"
                    + "   SET [Quantity] = ?,\n"
                    + "       [Estimate] = ?\n"
                    + " WHERE PlanCampnID = ? AND PlanID = ?";

            for (PlanCampaign campaign : plan.getCampains()) {
                ps = connection.prepareStatement(updatePlanCampaignSQL);
                ps.setInt(1, campaign.getQuantity());
                ps.setFloat(2, campaign.getEstimate());
                ps.setInt(3, campaign.getId()); // Assuming PlanCampaign has a unique ID
                ps.setInt(4, plan.getId()); // Use PlanID to ensure the campaign belongs to this plan
                ps.executeUpdate(); // Execute the update for each PlanCampaign
                ps.close(); // Close the statement for each update
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void delete(Plan entity) {
        String deleteCampaignSQL = "DELETE FROM PlanCampain WHERE PlanID = ?";
        String deletePlanSQL = "DELETE FROM [Plan] WHERE PlanID = ?";
        PreparedStatement stm_update = null;
        try {

            stm_update = connection.prepareStatement(deleteCampaignSQL);
            stm_update.setInt(1, entity.getId());
            stm_update.executeUpdate();

            // Now, delete the Plan
            stm_update = connection.prepareStatement(deletePlanSQL);
            stm_update.setInt(1, entity.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "SELECT [PlanID]\n"
                    + "      ,[PlanName]\n"
                    + "      ,[StartDate]\n"
                    + "      ,[EndDate]\n"
                    + "      ,[Quantity]\n"
                    + "      ,[DepartmentID]\n"
                    + "  FROM [dbo].[Plan]";

            command = connection.prepareStatement(sql);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                Plan p = new Plan();
                //fill blank 
                // Extract values from ResultSet and set to the Plan object
                p.setId(rs.getInt("PlanID"));  // Assuming Plan has a setId method
                p.setName(rs.getString("PlanName"));  // Assuming Plan has a setName method
                p.setStart(rs.getDate("StartDate"));  // Assuming Plan has a setStart (java.sql.Date) method
                p.setEnd(rs.getDate("EndDate"));  // Assuming Plan has a setEnd (java.sql.Date) method
                p.setQuantity(rs.getInt("Quantity"));  // Assuming Plan has a setQuantity method

                // Assuming you have a Department object in Plan with a setDept method
                Department d = new Department();
                d.setId(rs.getInt("DepartmentID"));  // Assuming Department has a setId method
                p.setDept(d);  // Setting the department for the plan

                // Add to the list
                plans.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                command.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
    }

}
