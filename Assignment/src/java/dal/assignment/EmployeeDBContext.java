package dal.assignment;

import dal.DBContext;
import entity.accesscontrol.Role;
import entity.assignment.Department;
import entity.shedule.Employee;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDBContext extends DBContext<Employee> {

   public static void main(String[] args) {
        EmployeeDBContext employeeDB = new EmployeeDBContext();

        // Test parameters for search method
        Integer id = 23;            // No specific ID filter
        String name = "Tran Thi W";         // Search for employees with names similar to 'John'
        Boolean gender = false;        // Male employees
        String address = "Hai Duong";        // No specific address filter
        Date from = Date.valueOf("1990-01-01"); // From this date of birth
        Date to = Date.valueOf("2000-12-31");   // To this date of birth
        Integer did = 2;           // No specific department filter

        // Perform search
        ArrayList<Employee> employees = employeeDB.search(id, name, gender, address, from, to, did);

        // Print out the search results
        System.out.println("Search Results:");
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getId() +
                               ", Name: " + employee.getName() +
                               ", Gender: " + (employee.isGender() ? "Male" : "Female") +
                               ", Address: " + employee.getAddress() +
                               ", DOB: " + employee.getDate() +
                               ", Department ID: " + (employee.getDepartment() != null ? employee.getDepartment().getId() : "N/A") +
                               ", Department Name: " + (employee.getDepartment() != null ? employee.getDepartment().getName() : "N/A"));
        }
    }
    public ArrayList<Employee> search(Integer id, String name, Boolean gender, String address, Date from, Date to, Integer did) {
        String sql = "SELECT e.EmployeeID,e.EmployeeName,e.gender,e.address,e.dob,d.DepartmentID,d.DepartmentName FROM Employee e \n"
                + "	INNER JOIN Department d ON e.DepartmentID = d.DepartmentID\n"
                + "WHERE (1=1)";

        ArrayList<Employee> emps = new ArrayList<>();
        ArrayList<Object> paramValues = new ArrayList<>();
        if (id != null) {
            sql += " AND e.EmployeeID = ?";
            paramValues.add(id);
        }

        if (name != null) {
            sql += " AND e.EmployeeName LIKE '%'+?+'%'";
            paramValues.add(name);
        }

        if (gender != null) {
            sql += " AND e.gender = ?";
            paramValues.add(gender);
        }

        if (address != null) {
            sql += " AND e.[address] LIKE '%'+?+'%'";
            paramValues.add(address);
        }
        if (from != null) {
            sql += " AND e.dob >= ?";
            paramValues.add(from);
        }

        if (to != null) {
            sql += " AND e.dob <= ?";
            paramValues.add(to);
        }

        if (did != null) {
            sql += " AND d.DepartmentID = ?";
            paramValues.add(did);
        }

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < paramValues.size(); i++) {
                stm.setObject((i + 1), paramValues.get(i));
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("EmployeeID"));
                e.setName(rs.getNString("EmployeeName"));
                e.setGender(rs.getBoolean("gender"));
                e.setDate(rs.getDate("dob"));
                e.setAddress(rs.getString("address"));

                Department d = new Department();
                d.setId(rs.getInt("DepartmentID"));
                d.setName(rs.getString("DepartmentName"));
                e.setDepartment(d);

                emps.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(dal.EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(dal.EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emps;
    }

    @Override
    public void insert(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Employee entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    


    @Override
    public ArrayList<Employee> list() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        
        PreparedStatement command = null;
        try {
            String query = "SELECT EmployeeID, EmployeeName, gender, address, dob, RoleID, DepartmentID FROM Employee";
            command = connection.prepareStatement(query);
            ResultSet rs = command.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();

                employee.setId(rs.getInt("EmployeeID"));
                employee.setName(rs.getString("EmployeeName"));

                // Assuming Role is a separate entity
                Role role = new Role();
                role.setId(rs.getInt("RoleID"));
                employee.setRole(role);

                employee.setGender(rs.getBoolean("gender")); // Adjust this if necessary

                employee.setDate(rs.getDate("dob"));
                employee.setAddress(rs.getString("address"));

                // Assuming Department is a separate entity
                Department department = new Department();
                department.setId(rs.getInt("DepartmentID"));
                employee.setDepartment(department);

                employeeList.add(employee);
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

        return employeeList;
    }

    @Override
    public Employee get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
