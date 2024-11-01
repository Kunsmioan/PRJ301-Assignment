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

</body>
</html>
