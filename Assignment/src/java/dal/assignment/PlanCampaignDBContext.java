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

/**
 *
 * @author Admin
 */
public class PlanCampaignDBContext extends DBContext<PlanCampaign> {

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
