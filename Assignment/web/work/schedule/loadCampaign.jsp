<%-- 
    Document   : loadCampaign
    Created on : Oct 28, 2024, 3:15:40 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Table</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
                text-align: center;
                font-family: Arial, sans-serif;
            }

            th, td {
                border: 1px solid #333;
                padding: 10px;
            }

            th {
                background-color: #E0E0E0;
            }

            .header, .subheader {
                background-color: #D3D3D3;
            }

            .header {
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <p><b>The detailed production plan is listed in the table below:</b></p>
        
        <table>
            <tr class="header">
                <th rowspan="2">ProductID</th>
                <th rowspan="2">Product Name</th>
                <!--fix this (done): list all day of plan campaign-->
                <c:forEach items="${requestScope.DSQ}" var ="Date">
                    <th colspan="3">${Date.date}</th>
                    </c:forEach>
                <th rowspan="2">Note</th>

            </tr>
            <tr class="subheader">
                <c:forEach items="${requestScope.DSQ}" var="DSQ">
                    <th>K1</th>
                    <th>K2</th>
                    <th>K3</th>
                    </c:forEach>
            </tr>

            <c:forEach items="${requestScope.listProduct}" var="product">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <c:forEach items="${requestScope.DSQ}" var="dateShiftData">
                        <c:set var="foundShift" value="false"/>
                        <c:forEach items="${dateShiftData.shifts}" var="shiftData">
                            <c:if test="${shiftData.productId == product.id}">
                                <td>${shiftData.quantity}</td>
                                <c:set var="foundShift" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${!foundShift}">
                            <td></td> <!-- Fill blank if no quantity found -->
                        </c:if>
                    </c:forEach>
                            <td></td>
                </tr>
            </c:forEach>

        </table>

    </body>
</html>
