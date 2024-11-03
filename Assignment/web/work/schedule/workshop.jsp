<%-- 
    Document   : workshop
    Created on : Oct 31, 2024, 9:22:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Production Plan for October and November 2024</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            h1 {
                text-align: center;
            }
            .info {
                margin-bottom: 20px;
                text-align: center;
            }
            table {
                width: 50%;
                margin: 0 auto;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <h1>Production Plan for October and November 2024</h1>

        <p><strong>Workshop Manager:</strong> ${sessionScope.account.displayname}</p> 
        <p><strong>Department: </strong> ${sessionScope.account.department.id}</p> 
        <c:forEach var="plan" items="${plans}">
            <div>
                <p><strong>Plan ID:</strong> ${plan.id}</p> 
                <p><strong>Workshop:</strong> ${plan.dept.name}</p>
                <p><strong>General Plan:</strong> ${plan.name}</p>
            </div>

            <table class="info">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Quantity (unit: piece)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="campaign" items="${plan.campains}">
                        <tr>
                            <td>${campaign.product.id}</td>
                            <td>${campaign.product.name}</td> 
                            <td>${campaign.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:forEach>
        <br>
        <br>
        <br>

        <table border="1px" style="width: 80%; border-collapse: collapse;">
            <caption style="font-size: 1.5em; font-weight: bold;">Attendance at workshop No. 01 – October 1, 2024 – Production shift No. 1</caption>
            <thead>
                <tr>
                    <th style="padding: 15px;">Employee ID</th>
                    <th style="padding: 15px;">Employee Name</th>
                    <th style="padding: 15px;">Product ID</th>
                    <th style="padding: 15px;">Product Name</th>
                    <th style="padding: 15px;">Quantity</th>
                    <th style="padding: 15px;">Alpha</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty listByDate}">
                    <c:forEach var="entry" items="${listByDate.entrySet()}">
                        <c:set var="date" value="${entry.key}" />
                        <c:set var="employees" value="${entry.value}" />

                        <!-- Print the date as a title -->
                        <tr>
                            <td colspan="6" style="font-weight: bold; font-size: 1.2em; padding: 10px; text-align: center;">Attendance for Date: ${date}</td>
                        </tr>

                        <c:forEach var="employee" items="${employees}">
                            <c:set var="attendence" value="${employee.attendence}" />
                            <c:set var="product" value="${employee.product}" />
                            <tr>
                                <td style="padding: 10px;">${employee.id}</td>
                                <td style="padding: 10px;">${employee.name}</td>
                                <td style="padding: 10px;">${product.id}</td>
                                <td style="padding: 10px;">${product.name}</td>
                                <td style="padding: 10px;">${attendence.quantity}</td>
                                <td style="padding: 10px;">${attendence.alpha}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:if>
                <c:if test="${empty listByDate}">
                    <tr>
                        <td colspan="6" style="padding: 10px; text-align: center;">No attendance data found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>



    </body>
</html>
