<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            

        </style>
    </head>
    <body>
        <table border="1" style="width: 60%; border-collapse: collapse;">
            <caption><h2>LIST OF EMPLOYEES</h2></caption>
            <thead>
                <tr>
                    <th style="padding: 15px; font-size: 18px;">Employee ID</th>
                    <th style="padding: 15px; font-size: 18px;">Name</th>
                    <th style="padding: 15px; font-size: 18px;">Gender</th>
                    <th style="padding: 15px; font-size: 18px;">Address</th>
                    <th style="padding: 15px; font-size: 18px;">Date of Birth</th>
                    <th style="padding: 15px; font-size: 18px;">Role ID</th>
                    <th style="padding: 15px; font-size: 18px;">Department ID</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td style="padding: 10px; font-size: 16px;">${employee.id}</td>
                        <td style="padding: 10px; font-size: 16px;">${employee.name}</td>
                        <td style="padding: 10px; font-size: 16px;">${employee.gender ? 'Male' : 'Female'}</td>
                        <td style="padding: 10px; font-size: 16px;">${employee.address}</td>
                        <td style="padding: 10px; font-size: 16px;">${employee.date}</td>
                        <td style="padding: 10px; font-size: 16px;">${employee.role.id}</td>
                        <td style="padding: 10px; font-size: 16px;">${employee.department.id}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>



