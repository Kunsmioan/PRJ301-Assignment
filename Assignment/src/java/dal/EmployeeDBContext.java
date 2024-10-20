/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.accesscontrol.Feature;
import entity.accesscontrol.Role;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EmployeeDBContext extends DBContext {
    public static void main(String[] args) {
        String testUsername = "admin4";
        EmployeeDBContext dao = new EmployeeDBContext();
        List<Feature> features = dao.getListFeature(testUsername);

        // Print out the features to verify the output
        if (features.isEmpty()) {
            System.out.println("No features found for user: " + testUsername);
        } else {
            System.out.println("Features for user " + testUsername + ":");
            for (Feature feature : features) {
                System.out.println("Feature ID: " + feature.getId()
                        + ", Name: " + feature.getName()
                        + ", URL: " + feature.getUrl());
            }
        }
    }
    

    public List<Feature> getListFeature(String username) {
        String sql = "SELECT u.UserName, r.RoleID, r.RoleName, f.FeatureID, f.FeatureName, f.url \n"
                + "FROM [User] u\n"
                + "INNER JOIN UserRole ur ON ur.username = u.username\n"
                + "INNER JOIN [Role] r ON r.RoleID = ur.RoleID\n"
                + "INNER JOIN RoleFeature rf ON r.RoleID = rf.RoleID\n"
                + "INNER JOIN Feature f ON f.FeatureID = rf.FeatureID\n"
                + "WHERE u.username = ?\n"
                + "ORDER BY r.RoleID, f.FeatureID ASC;";
        PreparedStatement stm = null;
        List<Feature> list = new ArrayList<>();
       try {
        stm = connection.prepareStatement(sql);
        stm.setString(1, username);
        ResultSet rs = stm.executeQuery();

        Role c_role = new Role();
        c_role.setId(-1); // Initialize with a non-existing ID
        
        while (rs.next()) {
            // Create a new Feature object for each row
            Feature feature = new Feature();
            feature.setId(rs.getInt("FeatureID"));  // Retrieve feature ID
            feature.setName(rs.getString("FeatureName"));  // Retrieve feature name
            feature.setUrl(rs.getString("url"));  // Retrieve the feature URL

            // Add the feature to the list
            list.add(feature);
        }
        
    } catch (Exception e) {
        e.printStackTrace(); // Log or handle the exception as needed
    } finally {
        // Close resources
        if (stm != null) {
            try { stm.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    return list ;
}

@Override
public void insert(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
public void update(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
public void delete(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
public ArrayList list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
public Object get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
