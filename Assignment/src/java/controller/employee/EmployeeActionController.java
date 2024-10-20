/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.employee;

import dal.EmployeeDBContext;
import entity.accesscontrol.Feature;
import entity.accesscontrol.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EmployeeActionController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //use doGet instead of doPost
        User account = (User) req.getSession().getAttribute("account");

        EmployeeDBContext db = new EmployeeDBContext();
        List<Feature> features = db.getListFeature(account.getUsername());

        req.setAttribute("features", features);
        req.getRequestDispatcher("../employee/action.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
