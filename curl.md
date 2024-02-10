### *Примеры запросов для отображения и редактирования еды пользователя*: 

* #### **Список еды залогиненного пользователя**
`curl http://localhost:8080/topjava/rest/meals` 

* #### **Удалить еду (в примере id = 100004)**

`curl -X DELETE http://localhost:8080/topjava/rest/meals/100004`

* #### **Запрос еды (например id = 100005)**

`curl http://localhost:8080/topjava/rest/meals/100005`

* #### **Обновить еду (id = 100009)**

`curl -X PUT http://localhost:8080/topjava/rest/meals/100009 -H "Content-type:application/json" -d {\"id\":100009,\"dateTime\":\"2020-01-31T22:22:22\",\"description\":\"dinnerUpdate\",\"calories\":666}`

* #### **Создать новую еду**

`curl -X POST http://localhost:8080/topjava/rest/meals -H "Content-type:application/json" -d {\"id\":null,\"dateTime\":\"2023-02-08T11:11:11\",\"description\":\"newOne\",\"calories\":888}`

* #### **Фильтр еды пользователя по дате и времени**

`curl -G -d "startDate=2020-01-30" -d "endDate=2020-01-30" -d "startTime=10:00" -d "endTime=15:00" http://localhost:8080/topjava/rest/meals/filter`