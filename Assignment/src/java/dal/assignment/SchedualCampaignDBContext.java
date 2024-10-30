/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.assignment;

import dal.DBContext;
import entity.assignment.DateShiftData;
import entity.assignment.Plan;
import entity.assignment.PlanCampaign;
import entity.assignment.SchedualCampaign;
import entity.assignment.ShiftData;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class SchedualCampaignDBContext extends DBContext<SchedualCampaign> {

    public static void main(String[] args) {
        SchedualCampaignDBContext db = new SchedualCampaignDBContext();

        // Retrieve the list of date and shift data
        List<DateShiftData> dateShiftDataList = db.listDSQ();

        // Iterate over each DateShiftData entry
        for (DateShiftData dateShiftData : dateShiftDataList) {
            // Print the date
            System.out.println("Date: " + dateShiftData.getDate());

            // Iterate over each ShiftData entry for the current date
            for (ShiftData shiftData : dateShiftData.getShifts()) {
                System.out.println("  Shift: " + shiftData.getShift() + ", Quantity: " + shiftData.getQuantity());
            }
            System.out.println(); // Blank line for separation between dates
        }

    }

   public List<DateShiftData> listDSQ() {
    List<DateShiftData> dateShiftDataList = new ArrayList<>();
    PreparedStatement command = null;
    try {
        String sql = "SELECT p.ProductName, p.ProductID, sc.[Date], sc.Shift, sc.Quantity " +
                     "FROM [dbo].[SchedualCampaign] sc " +
                     "INNER JOIN [dbo].[PlanCampain] pc ON sc.PlanCampnID = pc.PlanCampnID " +
                     "INNER JOIN [dbo].[Product] p ON p.ProductID = pc.ProductID ";

        command = connection.prepareStatement(sql);
        ResultSet rs = command.executeQuery();

            Map<Date, DateShiftData> dateShiftMap = new LinkedHashMap<>();

        while (rs.next()) {
            Date date = rs.getDate("Date");
            int shift = rs.getInt("Shift");
            int quantity = rs.getInt("Quantity");
            String productName = rs.getString("ProductName");
            int productId = rs.getInt("ProductID");

            // Get or create the DateShiftData object for the current date
            DateShiftData currentDateData = dateShiftMap.get(date);
            if (currentDateData == null) {
                currentDateData = new DateShiftData(date);
                dateShiftMap.put(date, currentDateData);
            }

            // Add shift data for each product
            currentDateData.addShift(new ShiftData(productId, productName, shift, quantity));
        }

        // Convert the map values to a list
        dateShiftDataList.addAll(dateShiftMap.values());

    } catch (SQLException ex) {
        Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (command != null) {
                command.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return dateShiftDataList;
}


    @Override
    public void insert(SchedualCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(SchedualCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(SchedualCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SchedualCampaign> list() {
        ArrayList<SchedualCampaign> SchedualCampaigns = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "select sc.ScID, pc.PlanCampnID, \n"
                    + "pc.PlanID, sc.Date, sc.Shift, sc.Quantity \n"
                    + "from [dbo].[SchedualCampaign] sc join [dbo].[PlanCampain] pc \n"
                    + "on sc.PlanCampnID = pc.PlanCampnID";

            command = connection.prepareStatement(sql);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                SchedualCampaign sc = new SchedualCampaign();

                sc.setId(rs.getInt("ScID"));

                PlanCampaign plancampn = new PlanCampaign();
                plancampn.setId(rs.getInt("PlanCampnID"));
                sc.setPlancampn(plancampn);

                // Create a Plan object and set its ID
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                sc.setPlan(plan);

                sc.setDate(rs.getDate("Date"));
                sc.setShift(rs.getInt("Shift"));
                sc.setQuantity(rs.getInt("Quantity"));

                SchedualCampaigns.add(sc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return SchedualCampaigns;
    }

    @Override
    public SchedualCampaign get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
