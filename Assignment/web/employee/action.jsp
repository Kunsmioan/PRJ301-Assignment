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
        <ul>
            <c:forEach items="${requestScope.features}" var="feature" >
                <c:choose>
                    <c:when test="${account.username == 'admin4' 
                                    && (feature.id == 1 || feature.id == 4 
                                    || feature.id == 6 || feature.id == 7 
                                    || feature.id == 8 || feature.id == 9
                                    )}">
                        <li>
                            <a href="${pageContext.request.contextPath}${feature.url}">${feature.name}</a>
                        </li>
                    </c:when>

                    <c:when test="${(account.username == 'admin1' 
                                    || account.username == 'admin2' 
                                    || account.username == 'admin3') 
                                    && (feature.id == 5 || feature.id == 6 
                                    || feature.id == 7 || feature.id == 9
                                    || feature.id == 10)}">
                        <li>
                            <a href="${pageContext.request.contextPath}${feature.url}">${feature.name}</a>
                        </li>
                    </c:when>
                </c:choose>

            </c:forEach>
        </ul>
        <c:if test="${empty requestScope.features}">
            <p>No features available for this user.</p>
        </c:if>
    </body>


</html>
