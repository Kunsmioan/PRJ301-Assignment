<%-- 
    Document   : filter
    Created on : Oct 2, 2024, 4:32:05 PM
    Author     : sonnt-local
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        /* Set up a flex container to align form and table */
        .container {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin: 20px;
        }

        /* Style the form */
        .form-container {
            width: 30%;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-right: 20px;
        }

        /* Style for the form groups */
        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #333;
        }

        /* Style form inputs */
        input[type="text"],
        input[type="date"],
        select {
            justify-content: space-between;
            width: 90%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="radio"] {
            margin-right: 5px;
        }

        /* Style the submit button */
        .submit-btn {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        /* Style the table */
        table {
            width: 55%;
            border-collapse: collapse;
            background-color: #f9f9f9;
        }

        table, th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        /* Adjust the Date input width within the Date of Birth group */
        .form-group div input[type="date"] {
            width: 45%;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h2>Search Employees</h2>
            <form action="search" method="GET">
                <div class="form-group">
                    <label for="id">ID:</label>
                    <input type="text" name="id" value="${param.id}" id="id"/>
                </div>
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" name="name" value="${param.name}" id="name"/>
                </div>
                <div class="form-group">
                    <label>Gender:</label>
                    <div class="gender-group">
                        <input type="radio" name="gender" value="male" ${param.gender ne null && param.gender eq "male" ? "checked=\"checked\"" : ""}/> Male
                        <input type="radio" name="gender" value="female" ${param.gender ne null && param.gender eq "female" ? "checked=\"checked\"" : ""}/> Female
                        <input type="radio" name="gender" value="both" ${param.gender eq null or param.gender eq "both" ? "checked=\"checked\"" : ""}/> Both
                    </div>
                </div>
                <div class="form-group">
                    <label>Date of Birth:</label>
                    <div>
                        From: <input type="date" name="from" value="${param.from}" style="width: 25%;"/>
                        To: <input type="date" name="to" value="${param.to}" style="width: 25%;"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" name="address" value="${param.address}" id="address"/>
                </div>
                <div class="form-group">
                    <label for="department">Department:</label>
                    <select name="did" id="department">
                        <option value="-1">-------------------------------------ALL-------------------------------------</option>
                        <c:forEach items="${requestScope.depts}" var="d">
                            <option ${param.did ne null && param.did eq d.id ? "selected=\"selected\"" : ""} value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="submit" value="Search" class="submit-btn"/>
            </form>
        </div>

        <!-- Table to display results -->
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Gender</th>
                <th>DOB</th>
                <th>Address</th>
                <th>Department</th>
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
                <tr>
                    <td>${e.id}</td>
                    <td>${e.name}</td>
                    <td>${e.gender ? "Male" : "Female"}</td>
                    <td>${e.date}</td>
                    <td>${e.address}</td>
                    <td>${e.department.id}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
