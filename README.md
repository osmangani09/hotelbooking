# hotelbooking

This is a Demo project.<br/>
Problem:
After the contagious pandemic is over, Panoramic Hotel is now accepting booking of its only
presidential suite. You are going to design and develop a back-end service to provide the REST APIs to
manage the booking of the presidential suite. Each booking allows up to 3 people for up to 3 days. <br/>
Solution: Provide REST APIs for
1. Booking the presidential suite. The user provides the following:
  <br/> •Email, first name and last name of the principal guest
  <br/> •Number of people, including the principal guest
  <br/> •Check-in and check-out dates
<br/>Upon success, the system returns the following:
  <br/> •A unique reservation identifier
  <br/> •How to retrieve and cancel the reservation
2. Retrieving a reservation with its identifier
3. Cancelling a reservation with its identifier

## Endpoints details
So basically I expose three rest API as follows: </br>
1. For reservation of presidential suit<br>
Endpoint: /booking <br>
Sample request: <br>
```
curl --location --request POST 'localhost:8080/booking' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "osmangoni14@gmail.com",
"firstName":"Osman",
"lastName":"Gani",
"guestCount":3,
"startDate":"2023-01-18",
"endDate":"2023-01-20"
}'
```
2. For query about reservation of presidential suit <br> 
Endpoint: /booking/{id} <br>
Sample request: <br>
``` 
curl --location --request GET 'localhost:8080/booking/ef07c50c-61ff-494b-937a-a01e60a082ef' 
```
3. For cancel reservation of presidential suit<br> 
Endpoint: /booking/{id} <br>
Sample Request: <br>
```
curl --location --request PUT 'localhost:8080/booking/85aec0bc-445f-434c-b51f-14762ae08cbf'
```

## Language and Frameworks Storage:
Java: 17 <br>
Spring-boot: 3.0.1 <br>
build-tool: maven (3.6.3) <br>
storage: in-memory H2 database <br>

## Run project
To build a Spring Boot project with Maven, you can use the following command:
 <br>
```mvn clean install``` <br>
if you want to run the project directly using spring-boot plugin, you can use the following command: <br>
```mvn spring-boot:run ``` <br>
To run tests in a Spring Boot project with Maven, you can use the following command:
<br>
```mvn test``` <br>
to run specific test class or method you can use the following command : <br>
```mvn -Dtest=TestClassName#testMethod test``` <br>


### Concurrency Handle
If two people request for booking the presidential suit at the same time for same date then traditional concurrency problem arise. 
we can handle concurrency problem in several way. 
1. Use Locking mechanism in the Database. we can use Database Optimistic Lock or Pessimistic Lock
2. Use Cache for lock like redis.
3. Use a message queue and push all request in queue, then serially process the request.
In this project we use Database Pessimistic lock to handle concurrency. 