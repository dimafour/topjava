<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<link rel="stylesheet" href="css/style.css">
<style> table, th, td {
    border: 1px solid black;
}
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<section>
    <table>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="<c:if test="${meal.excess}"> excess </c:if>
                        <c:if test="${!meal.excess}"> noexcess </c:if>>">
                <td>${meal.date} ${meal.time}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><img src="img/delete.png" height="30" alt="delete"></td>
                <td><img src="img/edit.png" height="30" alt="edit">
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>