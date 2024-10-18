/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.accesscontrol.Feature;
import entity.accesscontrol.Role;
import entity.accesscontrol.User;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sonnt-local
 */
public class UserDBContext extends DBContext<User> {
    public static void main(String[] args) {
        // Assuming you have a UserDBContext class that provides a connection to the database
        UserDBContext userDB = new UserDBContext();  // Initialize the context (you may need to modify this part depending on your actual connection setup)

        // Test username and password
        String testUsername = "admin1";
        String testPassword = "123";

        // Call the get method to retrieve the user
        User user = userDB.get(testUsername, testPassword);

        // Check if user is returned and display results
        if (user != null) {
            System.out.println("User found: ");
            System.out.println("Username: " + user.getUsername());
        } else {
            System.out.println("No user found with the provided credentials.");
        }
    }

    public ArrayList<Role> getRoles(String username) {
        String sql = "SELECT r.rid,r.rname,f.fid,f.fname,f.url FROM [User] u \n"
                + "	INNER JOIN UserRole ur ON ur.username = u.username\n"
                + "	INNER JOIN [Role] r ON r.rid = ur.rid\n"
                + "	INNER JOIN RoleFeature rf ON r.rid = rf.rid\n"
                + "	INNER JOIN Feature f ON f.fid = rf.fid\n"
                + "WHERE u.username = ?\n"
                + "ORDER BY r.rid, f.fid ASC";
        
        PreparedStatement stm = null;
        ArrayList<Role> roles = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            Role c_role = new Role();
            c_role.setId(-1);
            while(rs.next())
            {
                int rid = rs.getInt("rid");
                if(rid != c_role.getId())
                {
                    c_role = new Role();
                    c_role.setId(rid);
                    c_role.setName(rs.getString("rname"));
                    roles.add(c_role);
                }
                
                Feature f = new Feature();
                f.setId(rs.getInt("fid"));
                f.setName(rs.getString("fname"));
                f.setUrl(rs.getString("url"));
                c_role.getFeatures().add(f);
                f.setRoles(roles);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return roles;
    }

    public User get(String username, String password) {
        //encoding username / password
        String sql = "SELECT username, password FROM [User] \n"
                + "WHERE username = ? AND [password] = ?";
        PreparedStatement stm = null;
        User user = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(username);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    @Override
    public void insert(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
