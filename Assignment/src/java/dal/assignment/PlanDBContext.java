/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.assignment;

import dal.DBContext;
import entity.assignment.Department;
import entity.assignment.Plan;
import entity.assignment.PlanCampain;
import entity.assignment.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author sonnt-local
 */
public class PlanDBContext extends DBContext<Plan> {

    public static void main(String[] args) {
        // Create a dummy Plan object
        Plan dummyPlan = new Plan();
        dummyPlan.setName("Dummy Plan");
        dummyPlan.setStart(Date.valueOf("2024-01-01"));
        dummyPlan.setEnd(Date.valueOf("2024-12-31"));

        // Create a Department object and set its ID
        Department dummyDept = new Department();
        dummyDept.setId(1); // Assuming this department exists
        dummyPlan.setDept(dummyDept);

        // Create a dummy campaign
        PlanCampain dummyCampain = new PlanCampain();
        Product dummyProduct = new Product();
        dummyProduct.setId(1); // Assuming this product exists
        dummyCampain.setProduct(dummyProduct);
        dummyCampain.setQuantity(10); // Example quantity
        dummyCampain.setEstimate(100.0f); // Example estimate
        dummyPlan.getCampains().add(dummyCampain);

        // Insert the dummy plan into the database
        PlanDBContext dbContext = new PlanDBContext();
        try {
            dbContext.insert(dummyPlan);
            System.out.println("Dummy plan inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Print any error that occurs during insertion
            System.out.println("Failed to insert dummy plan: " + e.getMessage());
        }
        PlanDBContext planDBContext = new PlanDBContext();

        planDBContext.delete(dummyPlan);

        // Step 4: Insert the dummy plan into the database
    try {
        // Step 5: Delete the dummy plan
        dbContext.delete(dummyPlan);
        System.out.println("Dummy plan deleted successfully!");

        // Step 6: Verify if the plan is deleted by trying to fetch it
        Plan deletedPlan = dbContext.get(dummyPlan.getId()); // Use get method to fetch plan by ID
        if (deletedPlan == null) {
            System.out.println("Plan deleted successfully!");
        } else {
            System.out.println("Failed to delete the plan. It still exists in the database.");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Print any error that occurs during insertion or deletion
        System.out.println("Error during plan processing: " + e.getMessage());
    }
    }

    @Override
    public void insert(Plan plan) {
        try {
            // Insert Plan first and get the generated PlanID
            String insertPlanSQL = "INSERT INTO [Plan] (PlanName, StartDate, EndDate, Quantity, DepartmentID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertPlanSQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, plan.getName());
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

            // Now insert PlanCampain(s)
            String insertCampaignSQL = "INSERT INTO PlanCampain (PlanID, ProductID, Quantity, Estimate) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(insertCampaignSQL);

            for (PlanCampain campaign : plan.getCampains()) {
                ps.setInt(1, plan.getId());  // Use the generated PlanID
                ps.setInt(2, campaign.getProduct().getId());
                ps.setInt(3, campaign.getQuantity());
                ps.setFloat(4, campaign.getEstimate());
                ps.executeUpdate();
            }

            System.out.println("Dummy plan inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    @Override
    public Plan get(int id) {
        PreparedStatement command = null;
        try {
            String sql = "SELECT PlanID, PlanName, StartDate, EndDate, Quantity, DepartmentID FROM [Plan] WHERE PlanID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create a Plan object and set its attributes from the result set
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                plan.setQuantity(rs.getInt("Quantity"));

                // Create and set Department object based on DepartmentID
                Department dept = new Department();
                dept.setId(rs.getInt("DepartmentID"));
                plan.setDept(dept);

                // You can also add logic to fetch associated PlanCampain objects, if needed
                return plan;
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
        return null;
    }

}
