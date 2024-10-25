/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.assignment;

import dal.DBContext;
import entity.assignment.Plan;
import entity.assignment.PlanCampaign;
import entity.assignment.Product;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PlanCampaignDBContext extends DBContext<PlanCampaign> {

    public static void main(String[] args) {
        PlanCampaignDBContext db = new PlanCampaignDBContext();

        // Fetch the list of PlanCampaigns for the given Plan ID
        List<PlanCampaign> pc = db.listByPlanId(30);

        // Print original values
        System.out.println("Original Values:");
        for (PlanCampaign campaign : pc) {
            System.out.println("Product ID: " + campaign.getProduct().getId() + ", Quantity: " + campaign.getQuantity());
        }

        // Update the quantity for each PlanCampaign and call updateQAE
        for (PlanCampaign campaign : pc) {
            campaign.setQuantity(10); // Set new quantity (for example, 10)
            db.updateQAE(campaign); // Update in the database
        }

        // Fetch again to check if updates took place
        List<PlanCampaign> updatedPC = db.listByPlanId(30);

        // Print updated values
        System.out.println("Updated Values:");
        for (PlanCampaign campaign : updatedPC) {
            System.out.println("Product ID: " + campaign.getProduct().getId() + ", Quantity: " + campaign.getQuantity());
        }
    }

    public void updateQAE(PlanCampaign campaign) {
        PreparedStatement command = null;
        try {
            String sql = "UPDATE [dbo].[PlanCampain] "
                    + "SET [Quantity] = ?, [Estimate] = ? "
                    + "WHERE [PlanCampnID] = ?";

            command = connection.prepareStatement(sql);

            // Set the updated values for Quantity and Estimate
            command.setInt(1, campaign.getQuantity());
            command.setFloat(2, campaign.getEstimate());

            // Set the PlanCampnID to identify which campaign to update
            command.setInt(3, campaign.getId());

            // Execute the update command
            command.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<PlanCampaign> listByPlanId(int planId) {
        ArrayList<PlanCampaign> campaigns = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "SELECT [PlanCampnID], [PlanID], [ProductID], [Quantity], [Estimate] "
                    + "FROM [dbo].[PlanCampain] WHERE [PlanID] = ?";

            command = connection.prepareStatement(sql);
            command.setInt(1, planId); // Set the plan ID for filtering
            ResultSet rs = command.executeQuery();

            while (rs.next()) {
                PlanCampaign campaign = new PlanCampaign();

                // Set the ID
                campaign.setId(rs.getInt("PlanCampnID"));

                // Create a Plan object and set its ID
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                campaign.setPlan(plan);

                // Create a Product object and set its ID
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                campaign.setProduct(product);

                // Set other fields
                campaign.setQuantity(rs.getInt("Quantity"));
                campaign.setEstimate(rs.getFloat("Estimate"));

                // Add to the list
                campaigns.add(campaign);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return campaigns;
    }

    @Override
    public void insert(PlanCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(PlanCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(PlanCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<PlanCampaign> list() {
        ArrayList<PlanCampaign> campaigns = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "SELECT [PlanCampnID], [PlanID], [ProductID], [Quantity], [Estimate] "
                    + "FROM [dbo].[PlanCampain]";

            command = connection.prepareStatement(sql);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                PlanCampaign campaign = new PlanCampaign();

                // Set the ID
                campaign.setId(rs.getInt("PlanCampnID"));

                // Create a Plan object and set its ID
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                campaign.setPlan(plan);

                // Create a Product object and set its ID
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                campaign.setProduct(product);

                // Set other fields
                campaign.setQuantity(rs.getInt("Quantity"));
                campaign.setEstimate(rs.getFloat("Estimate"));

                // Add to the list
                campaigns.add(campaign);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return campaigns;
    }

    @Override
    public PlanCampaign get(int PlanID) {
        PlanCampaign campaign = null; // Initialize to null
        PreparedStatement command = null;
        try {
            String sql = "SELECT [PlanCampnID], [PlanID], [ProductID], [Quantity], [Estimate] "
                    + "FROM [dbo].[PlanCampain] WHERE [PlanID] = ?";

            command = connection.prepareStatement(sql);
            command.setInt(1, PlanID); // Set the PlanID parameter

            ResultSet rs = command.executeQuery();
            if (rs.next()) { // Use if instead of while for a single record
                campaign = new PlanCampaign();

                // Set the ID
                campaign.setId(rs.getInt("PlanCampnID"));

                // Create a Plan object and set its ID
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                campaign.setPlan(plan);

                // Create a Product object and set its ID
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                campaign.setProduct(product);

                // Set other fields
                campaign.setQuantity(rs.getInt("Quantity"));
                campaign.setEstimate(rs.getFloat("Estimate"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return campaign; // Return the found campaign or null
    }

}
