<%-- 
    Document   : action
    Created on : Oct 19, 2024, 2:51:47 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            h2{
                color:green;
            }
        </style>
    </head>

    <body>
        <h2>Hello Mr. ${sessionScope.account.username}</h2>
        <h3>Action:</h3>
        <ul>
            <c:forEach items="${requestScope.features}" var="feature">
                <li>
                    <a href="${pageContext.request.contextPath}${feature.url}">${feature.name}</a> <!-- Link to feature URL -->
                </li>
            </c:forEach>
        </ul>
    <c:if test="${empty requestScope.features}">
        <p>No features available for this user.</p>
    </c:if>
</body>


</html>
