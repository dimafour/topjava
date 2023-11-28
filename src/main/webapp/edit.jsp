<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Title</title>
</head>
<body>
<section>
    <form method="post" id="form" onsubmit="validateForm()" action="meals" enctype="application/x-www-form-urlencoded">
        <script>
            function validateForm() {
                if (document.getElementById("description").value.trim() === "") {
                    alert("Описание не может быть пустым. Изменения не будут сохранены");
                }
            }
        </script>
        <input type="hidden" name="id" value="${meal.id}" required>
                <label>
                    Описание:
                    <input type="text" name="description" id="description" size=50 value="${meal.description}"><br/>
                    Время:
                    <input type="datetime-local" name="datetime" size=50 value="${meal.dateTime}"><br/>
                    Количество калорий:
                    <input type="number" min="0" name="calories" size=50 value="${meal.calories}"><br/>
                </label>
        <button type="submit" id="submit" onclick="history.back()">Сохранить</button>
        <button type="reset" onclick="history.back()">Отмена</button>
    </form>
</section>

</body>
</html>
