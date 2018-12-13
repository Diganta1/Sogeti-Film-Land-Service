# Sogeti-Film-Land-Service
Build a Restful web application using spring  boot for Film land User

# Pre-requisites
This project is to show web application using the postman and real project developed in Spring Test Suite (STS 3.9.6), 
Java 8
Maven dependancies
STS

# How to run
For a basic usage of STS, please clone the github project from repository and build the project . 
Provide JDK in class path so it can compile (secret key- hash algorithm- MD5) and spring boot required as well.
Once build success kindly run the main package(com.sogeti.filmLand.filmLandSogetiRunner.java).

As soon as application start up , Open a parallel service hosting Swagger in same port(http://localhost:7092/swagger-ui.html#/service-controller) to check the varios service controller.

Recomemnded to open Postman to transact each request.(CRUD operation REST- spring based CRUD)

# Data Base  (H2) 
Here we are using the h2 java inline database and preloaded few tables durig application run time

 LOGIN_DATABASE   (Password will be stored on 128-bits (32 caracters) "hash" format . Authorization Key will be (Combination of emailid                    and user name ) 
 AVAILABLE_SERVICES 
 SUBSCRIBED_SERVICES
 
 # Scheduler 
 
 Used Spring spring scheduler to run and log if the start date of subsciber service is more than a month.
 
 
# Specific End points and arguments has been noted below:(Its for reference and all negetive schenario has been handled and binded in response body with http error code)

1.localhost:7092/rest/jwt/signup   

Json Request Body: 
{
	"email_id": "Diganta.mohapatra2@gmail.com",
	"name": "ganesh",
	"password": "1234"
}

Json Response Body:
{
    "status": "200 OK",
    "message": "User has been Successfully Registered"
}

2.localhost:7092/rest/jwt/login

Json Request Body:
{
	"email_id": "Diganta.mohapatra@gmail.com",
	"password": "5678"
}

Json Response Body:
{
    "status": "200 OK",
    "message": "Login Successfull"
}

3.localhost:7092/rest/jwt/details?name=Diganta  (GET Request)

Headers: apiKey     (Diganta.mohapatra@gmail.com_Diganta)

Response Body:
{
    "availableServices": [
        {
            "name": "Netherlandse Films",
            "availableContent": "8",
            "price": "5.0"
        },
        {
            "name": "Netherlandse Series",
            "availableContent": "25",
            "price": "8.0"
        }
    ],
    "subscribedServices": [
        {
            "name": "Netherlandse Films",
            "subscribedContent": "1",
            "price": "5.0",
            "subscribedDate": "2018-11-08T23:00:00.000+0000"
        },
        {
            "name": "Netherlandse Series",
            "subscribedContent": "1",
            "price": "8.0",
            "subscribedDate": "2018-11-08T23:00:00.000+0000"
        }
    ]
}

4.localhost:7092/rest/jwt/subscribe  (POST)

Headers: apiKey     (Diganta.mohapatra@gmail.com_Diganta)
Json Request Body:
{
"name":"Netherlandse Roaming1"
}

Json Response Body:
{
    "status": "Failed409 CONFLICT",
    "message": "Requested Subscrition is already subscribed"
}

5.localhost:7092/rest/jwt/shareSubscribe (POST)

Headers: apiKey     (Diganta.mohapatra@gmail.com_Diganta)
Json Request Body:
{
	"friend": "Sumitabha",
	"serviceName":"Netherlandse Roaming1"
}

Json Response Body:

{
    "status": "Success200 OK",
    "message": " Successfully shared the item to Sumitabha"
}



