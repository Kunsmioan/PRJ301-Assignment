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
    </head>
    <body>
        <form action="create" method="POST" onsubmit="return calculateTotalQuantity()">
            Plan title: <input type="text" name="name"/> <br/>
            From: <input type="date" name="from"/> To: <input type="date" name="to"/> <br/>
            Workshop: <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}">${d.name}</option>
                </c:forEach>
            </select> <br/>

            <table border="1px">
                <tr>
                    <td>Product</td>
                    <td>Quantity</td>
                    <td>Estimate</td>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                    <tr>
                        <td>${p.name}<input type="hidden" value="${p.id}" name="pid"/></td>
                        <td><input type="text" name="quantity${p.id}" class="quantity" oninput="sumQuantities()"/></td>
                        <td><input type="text" name="estimate${p.id}"/></td>
                    </tr>
                </c:forEach>
            </table>

            <!-- Display total quantity here -->
            Total Quantity: <span id="totalQuantity">0</span>
            <input type="hidden" name="totalQuantity" id="hiddenTotalQuantity" value="0"/>
            <br/>
            <input type="submit" value="Save"/>
        </form>

        <script>
            // Function to sum quantities as the user enters values
            function sumQuantities() {
                let total = 0;
                // Get all inputs with the class 'quantity'
                let quantityInputs = document.querySelectorAll('.quantity');

                // Loop through each input and add its value to total
                quantityInputs.forEach(function (input) {
                    let value = parseInt(input.value);
                    if (!isNaN(value)) {
                        total += value;
                    }
                });

                // Display the total in the span and set it in the hidden field
                document.getElementById('totalQuantity').textContent = total;
                document.getElementById('hiddenTotalQuantity').value = total;
            }

            // Function called on form submission to ensure total quantity is updated
            function calculateTotalQuantity() {
                sumQuantities();  // To ensure the total is updated before submission
                return true;  // Allow form submission
            }
        </script>

    </body>
</html>
