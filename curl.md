### *MealRestController cURL commands*: 
* ### **getAll**
`curl http://localhost:8080/topjava/rest/meals` 

* ### **delete**
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100004`

* ### **get**
`curl http://localhost:8080/topjava/rest/meals/100005`

* ### **update**
`curl -X PUT http://localhost:8080/topjava/rest/meals/100009 -H "Content-type:application/json" -d {\"id\":100009,\"dateTime\":\"2020-01-31T22:22:22\",\"description\":\"dinnerUpdate\",\"calories\":666}`

* ### **create**
`curl -X POST http://localhost:8080/topjava/rest/meals -H "Content-type:application/json" -d {\"id\":null,\"dateTime\":\"2023-02-08T11:11:11\",\"description\":\"newOne\",\"calories\":888}
`
* ### **filter**
`curl -G -d "startDate=2020-01-30" -d "endDate=2020-01-30" -d "startTime=10:00" -d "endTime=15:00" http://localhost:8080/topjava/rest/meals/filter`