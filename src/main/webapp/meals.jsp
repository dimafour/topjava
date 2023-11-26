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
            <tr class="${meal.excess ? "excess" : "noexcess"}">
                <td>${meal.date} ${meal.time}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=delete"><img src="img/delete.png" height="30" alt="delete"></a></td>
                <td><a href="meals?id=${meal.id}&action=edit"><img src="img/edit.png" height="30" alt="edit"></a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="meals?action=add"><img src="img/add.png" height="30" alt="add"></a><br/><br/>
    <a href="meals?action=clear"><img src="img/clear.png" height="30" alt="clear"></a>
</section>
</body>
</html>