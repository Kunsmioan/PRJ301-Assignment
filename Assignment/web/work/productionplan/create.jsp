<%-- 
    Document   : create
    Created on : Oct 16, 2024, 4:45:34 PM
    Author     : sonnt-local
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            form {
                width: 100%;
                max-width: 700px;
                padding: 20px;
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 8px;
            }

            h2 {
                text-align: center;
                color: #333;
            }

            label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }

            input[type="text"], input[type="date"], select {
                width: 100%;
                padding: 10px;
                margin: 8px 0;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 4px;
                background-color: #fff;
            }

            input[type="submit"] {
                background-color: #007bff;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 100%;
                font-size: 16px;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 20px;
                margin-bottom: 15px;
            }

            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: left;
            }

            th {
                background-color: #f4f4f4;
                font-weight: bold;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            #totalQuantity {
                font-weight: bold;
                color: #007bff;
                margin-left: 5px;
            }

            .form-section {
                margin-bottom: 20px;
            }

            .date-group {
                display: flex;
                justify-content: space-between;
                gap: 20px;
                align-items: center;
            }

            .date-group label {
                padding-top: 5px;
                font-weight: bold;
            }

            .date-group input {
                flex-grow: 1;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            .date-label {
                white-space: nowrap;
            }
        </style> 

        <script>
            function sumQuantities() {
                let total = 0;
                const quantities = document.querySelectorAll('.quantity');
                quantities.forEach(input => {
                    const value = parseInt(input.value) || 0;
                    total += value;
                });
                document.getElementById('totalQuantity').innerText = total;
                document.getElementById('hiddenTotalQuantity').value = total;
            }

            function calculateTotalQuantity() {
                sumQuantities();
                return true; // Allows form submission after total quantity calculation.
            }
        </script>

    </head>
    <body>


        <form action="create" method="POST" onsubmit="return calculateTotalQuantity()">
            <h2>Create New Plan</h2>

            <div class="form-section">
                <label for="name">Plan Title:</label>
                <input type="text" id="name" name="name" placeholder="Enter plan title" required/>
            </div>

            <div class="form-section">
                <div class="date-group">
                    <label class="date-label">From:</label>
                    <input type="date" id="from" name="from" required/>

                    <label class="date-label">To:</label>
                    <input type="date" id="to" name="to" required/>
                </div>
                <span id="dateError" style="color: red; display: none;">The "To" date must be later than the "From" date.</span>
            </div>

            <div class="form-section">
                <label for="workshop">Workshop:</label>
                <select id="workshop" name="did" required>
                    <c:forEach items="${requestScope.depts}" var="d">
                        <option value="${d.id}">${d.name}</option>
                    </c:forEach>
                </select>
            </div>

            <table>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Estimated Effort (per manhour)</th>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                    <tr>
                        <td>${p.name}<input type="hidden" value="${p.id}" name="pid"/></td>
                        <td><input type="text" name="quantity${p.id}" class="quantity" oninput="sumQuantities()"/></td>
                        <td><input type="text" name="estimate${p.id}" value="${p.estimate}" readonly/></td>
                    </tr>
                </c:forEach>
            </table>

            <div class="form-section">
                <strong>Total Quantity:</strong> <span id="totalQuantity">0</span>
                <input type="hidden" name="totalQuantity" id="hiddenTotalQuantity" value="0"/>
            </div>

            <input type="submit" value="Save"/>
        </form>

        <script>
            const fromDate = document.getElementById("from");
            const toDate = document.getElementById("to");
            const dateError = document.getElementById("dateError");

            function validateDates() {
                // Ensure "From" date is earlier than "To" date before form submission
                if (toDate.value && fromDate.value && toDate.value <= fromDate.value) {
                    dateError.style.display = "inline";
                    return false;  // Prevent form submission
                }
                dateError.style.display = "none";
                return true;
            }

            fromDate.addEventListener("change", function () {
                // Set "To" date minimum to "From" date
                toDate.min = fromDate.value;

                // Clear any error message if the date range becomes valid
                if (toDate.value && toDate.value > fromDate.value) {
                    dateError.style.display = "none";
                }
            });

            toDate.addEventListener("change", function () {
                // Validate date range on "To" date change
                if (toDate.value && toDate.value <= fromDate.value) {
                    dateError.style.display = "inline";
                } else {
                    dateError.style.display = "none";
                }
            });
        </script>
    </body>
</html>
