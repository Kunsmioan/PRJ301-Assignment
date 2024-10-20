<%-- 
    Document   : loadPlan
    Created on : Oct 18, 2024, 2:23:44 AM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Plans</title>
        <style>
            table {
                width: 90%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
        <script>
            function removePlan(id)
            {
                var result = confirm("Are you want to delete id:" + id + "?");
                if (result)
                {
                    document.getElementById("frmRemovePlan$" + id).submit();
                }
            }
            function updatePlan(id)
            {
                var result = confirm("Are you want to update id:" + id + "?");
                if (result)
                {
                    window.location.href = "update?id=" + id;

                }
            }
        </script>
    </head>
    <body>

        <h2>List of plans:</h2>
        <table>
            <thead>
                <tr>
                    <th>Plan ID</th>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Quantity</th>
                    <th>Department ID</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.plans}" var="plan">
                    <tr>
                        <td>${plan.id}</td>
                        <td>${plan.name}</td>
                        <td>${plan.start}</td>
                        <td>${plan.end}</td>
                        <td>${plan.quantity}</td>
                        <td>${plan.dept.id}</td>
                        <td>
                            <a href="javascript:updatePlan(${plan.id});">Edit</a>
                            <input type="button" value="Remove" onclick="removePlan(${plan.id})"/>
                            <form id="frmRemovePlan${plan.id}" action="delete" method="POST">
                                <input type="hidden" name="id" value="${e.id}"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
