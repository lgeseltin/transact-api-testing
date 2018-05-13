# API testing practice (Home assignment)

This project was made to learn and practise testing API with Java and Spring Boot

## Task Description

Create automated tests to check the described API.

## Technology stack

* Java 8
* Spring Boot
* TestNG (InteliJ Idea plugin [TestNG-J](http://plugins.jetbrains.com/plugin/137-testng-j) )
* Log4j

## Developer documentation (provided API)

### Base URL
```http://remote-url:8888```

### Register
Request

```
POST /v1/register

Body:

{ 
"email": "jane@trans.com", 
"phone": "+3712111111", 
"pwd": "111bbb", 
"birthDate": "1993-06-25T00:00:00.000Z", 
"description": "Lorem ipsum", 
  "address": { 
    "country": "LV", 
    "city": "Riga", 
    "state": "LV", 
    "zip": "LV-1063", 
    "street": "Ropazu 16" 
  } 
} 

```

Response:
```
200 OK

{
	"Result": true,
	"Details": "none"
}
```

### Authorize

Request:
```
POST /v1/authorize
 
Body:
 
{ 
  "login": "jane@trans.com", 
  "pwd": "111bbb" 
} 
```

Response:
```
200 OK

{ 
"Result": true, 
"Details": "AAABBBCCCDDDEEE==" 
} 

500 error: 
{ 
"Result": false, 
"Details": "Failed to authorize" 
} 
```

## Helpful Links

- [Spring Initializr](https://start.spring.io/)
- [Spring Documentation](https://spring.io/docs)
- [List of HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

## License
