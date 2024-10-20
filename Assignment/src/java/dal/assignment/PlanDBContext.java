/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.assignment;

import dal.DBContext;
import dal.UserDBContext;
import entity.accesscontrol.Feature;
import entity.accesscontrol.Role;
import entity.accesscontrol.User;
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
        // Create a dummy user (assume this user exists in your database)
        User user = new User();
        user.setUsername("admin4"); // Use a valid username

        // Fetch user roles and permissions
        UserDBContext userDbContext = new UserDBContext();
        ArrayList<Role> roles = userDbContext.getRoles(user.getUsername());
        user.setRoles(roles);

        // Check if user is authorized to delete
        String deleteUrl = "work/productionplan/create"; // Adjust this URL based on your servlet mapping
        boolean isAuthorized = false;

        for (Role role : user.getRoles()) {
            for (Feature feature : role.getFeatures()) {
                if (feature.getUrl().equals(deleteUrl)) {
                    isAuthorized = true;
                    break;
                }
            }
            if (isAuthorized) {
                break;
            }
        }

        if (!isAuthorized) {
            System.out.println("User is not authorized to perform delete operation.");
            return; // Stop further processing
        }

        // Create a dummy plan object to delete
        Plan planToDelete = new Plan();
        planToDelete.setId(1); // Use a valid plan ID that you want to delete

        // Instantiate PlanDBContext and perform delete operation
        PlanDBContext planDbContext = new PlanDBContext();
        try {
            planDbContext.delete(planToDelete);
            System.out.println("Plan deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete the plan: " + e.getMessage());
        }

        // Verify if the plan is deleted
        Plan deletedPlan = planDbContext.get(planToDelete.getId());
        if (deletedPlan == null) {
            System.out.println("Plan deleted successfully!");
        } else {
            System.out.println("Failed to delete the plan. It may still exist.");
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
            Logger.getLogger(PlanDBContext.class  

.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PlanDBContext.class  

.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                command.close();
                connection.close();

} catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class  

.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PlanDBContext.class  

.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

}
