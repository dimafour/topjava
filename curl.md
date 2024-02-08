<h2> MealRestController: </h2> <br>
<b>getAll</b> curl http://localhost:8080/topjava/rest/meals <br>
<b>delete</b> curl -X DELETE http://localhost:8080/topjava/rest/meals/100004 <br>
<b>get</b> curl http://localhost:8080/topjava/rest/meals/100005 <br>
<b>update</b> curl -X PUT http://localhost:8080/topjava/rest/meals/100009 
        -H "Content-type:application/json" 
        -d {\"id\":100009,\"dateTime\":\"2020-01-31T22:22:22\",\"description\":\"dinnerUpdate\",\"calories\":666} <br>
<b>create</b> curl -X POST http://localhost:8080/topjava/rest/meals 
    -H "Content-type:application/json" 
    -d {\"id\":null,\"dateTime\":\"2023-02-08T11:11:11\",\"description\":\"newOne\",\"calories\":888} <br>
<b>filter</b> curl -G -d "startDate=2020-01-30" -d "endDate=2020-01-30" -d "startTime=10:00" 
                        -d "endTime=15:00" http://localhost:8080/topjava/rest/meals/filter