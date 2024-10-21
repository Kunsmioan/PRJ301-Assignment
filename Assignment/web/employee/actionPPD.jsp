<%-- 
    Document   : actionPPD
    Created on : Oct 21, 2024, 10:37:49 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Hello Mr. ${sessionScope.account.username}</h2>
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/employee/create">Create New Employee</a> <!-- Link to feature URL -->
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/work/productionplan/loadPlansPPD">Work plan (Production Planning Director)</a> <!-- Link to feature URL -->
            </li>
            <li>
                <a href="${pageContext.request.contextPath}${feature.url}">${feature.name}</a> <!-- Link to feature URL -->
            </li>
            <li>
                <a href="${pageContext.request.contextPath}${feature.url}">${feature.name}</a> <!-- Link to feature URL -->
            </li>
        </ul>
    </body>
</html>
