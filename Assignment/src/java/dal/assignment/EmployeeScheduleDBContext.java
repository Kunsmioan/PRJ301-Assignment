/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.assignment;

import dal.DBContext;
import entity.accesscontrol.Role;
import entity.assignment.Department;
import entity.assignment.Product;
import entity.shedule.Attendence;
import entity.shedule.Employee;
import entity.shedule.SchedualCampaign;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class EmployeeScheduleDBContext extends DBContext<Employee>{
    public static void main(String[] args) {
        EmployeeScheduleDBContext dbContext = new EmployeeScheduleDBContext();
        List<Employee> employees = dbContext.getAttendence(1,  Date.valueOf("2024-10-01"));

            // Display the retrieved data
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Role ID: " + employee.getRole().getId());
                System.out.println("Product ID: " + employee.getProduct().getId());
                System.out.println("Product Name: " + employee.getProduct().getName());
                System.out.println("Quantity: " + employee.getAttendence().getQuantity());
                System.out.println("Alpha: " + employee.getAttendence().getAlpha());
                System.out.println("Department ID: " + employee.getDepartment().getId());
                System.out.println("Date: " + employee.getSc().getDate());
                System.out.println("----------------------------------");
            }

    }
    
    public ArrayList<Employee> getAttendence(int departmentID, Date date) {
        ArrayList<Employee> employeeList = new ArrayList<>();
        String query = """
            SELECT e.EmployeeID, e.EmployeeName, e.RoleID, p.ProductID, p.ProductName,
                   a.Quantity, a.Alpha, e.DepartmentID, sc.Date
            FROM Attendence a
            INNER JOIN SchedualEmployee se ON a.SchEmpID = se.SchEmpID
            INNER JOIN Employee e ON e.EmployeeID = se.EmployeeID
            INNER JOIN SchedualCampaign sc ON sc.ScID = se.ScID
            INNER JOIN PlanCampain pc ON pc.PlanCampnID = sc.PlanCampnID
            INNER JOIN Product p ON p.ProductID = pc.ProductID
            WHERE e.DepartmentID = ? and sc.Date = ?
            ORDER BY e.EmployeeID
            """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, departmentID);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                
                employee.setId(rs.getInt("EmployeeID"));
                employee.setName(rs.getString("EmployeeName"));
                
                // Assuming Role, Department, and Product are separate entities
                Role role = new Role();
                role.setId(rs.getInt("RoleID"));
                employee.setRole(role);

                Department department = new Department();
                department.setId(rs.getInt("DepartmentID"));
                employee.setDepartment(department);

                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getString("ProductName"));
                employee.setProduct(product);
                
                // Set other fields like Quantity, Alpha, and Date
                Attendence attendence = new Attendence();
                attendence.setQuantity(rs.getInt("Quantity"));
                attendence.setAlpha(rs.getFloat("Alpha"));
                employee.setAttendence(attendence);

                SchedualCampaign sc = new SchedualCampaign();
                sc.setDate(rs.getDate("Date"));
                employee.setSc(sc);

                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    @Override
    public void insert(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Employee> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
