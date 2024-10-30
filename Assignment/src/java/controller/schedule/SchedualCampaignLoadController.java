/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.schedule;

import controller.accesscontrol.BaseRBACController;
import dal.assignment.ProductDBContext;
import dal.assignment.SchedualCampaignDBContext;
import entity.accesscontrol.User;
import entity.assignment.DateShiftData;
import entity.assignment.Product;
import entity.assignment.SchedualCampaign;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Admin
 */
public class SchedualCampaignLoadController extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        SchedualCampaignDBContext db = new SchedualCampaignDBContext();
        ProductDBContext dbproduct = new ProductDBContext();
        List<Product> listProduct = dbproduct.list();
        req.setAttribute("listProduct", listProduct);

        //create a map contain date link include its shifts and quantities
        List<DateShiftData> DSQ = db.listDSQ();
        req.setAttribute("DSQ", DSQ);
        req.getRequestDispatcher("../schedule/loadCampaign.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {

    }

}
