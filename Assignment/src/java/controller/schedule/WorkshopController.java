/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.schedule;

import controller.accesscontrol.BaseRBACController;
import dal.assignment.EmployeeScheduleDBContext;
import dal.assignment.PlanCampaignDBContext;
import dal.assignment.PlanDBContext;
import dal.assignment.SchedualCampaignDBContext;
import entity.accesscontrol.User;
import entity.assignment.DateShiftData;
import entity.assignment.Plan;
import entity.shedule.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.sql.Date;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class WorkshopController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest request, HttpServletResponse response, User account) throws ServletException, IOException {
        // Get the username directly from the account parameter
        String username = account.getUsername(); // Assuming 'User' has a getUsername() method

        // Retrieve plans for the specified user
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.getPlansByUser(username);

        // plan
        request.setAttribute("plans", plans);

        // Account
        request.setAttribute("account", account);

        //Schedule employee
        EmployeeScheduleDBContext esdb = new EmployeeScheduleDBContext();
        SchedualCampaignDBContext scdb = new SchedualCampaignDBContext();
        ArrayList<DateShiftData> dateShifts = scdb.listDSQ();
        Map<Date, ArrayList<Employee>> listByDate = new TreeMap<>();

        for (DateShiftData dateShiftData : dateShifts) {
            Date date = dateShiftData.getDate();
            ArrayList<Employee> listES = esdb.getAttendence(account.getDepartment().getId(), (java.sql.Date) date);
            listByDate.put(date, listES); // Store the list of employees for this date
        }

        request.setAttribute("listByDate", listByDate);

        request.getRequestDispatcher("../schedule/workshop.jsp").forward(request, response);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
