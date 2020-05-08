### Weather Information

## How it works

The application built with Spring boot, MySQL including CRUD operations, authentication.

## Getting started

* Clone this repository
* Import > Existing Maven Project
* Open MySQL > Create Schema > with the name of `spring-rest-weather`

The entry point address of the backend API is at  http://localhost:8090/api

## Security

Integration with Spring Security and jwt token.

## API List

Postman: https://www.getpostman.com/collections/72b0ec27b3cc38edc81f

* Login

`POST Method/JSON`

```
localhost:8090/login

```
```
{
	“username”: ”root”,
	“password”: “root”
}

```

`You can get Bearer token from response’s header section when login successfully.`

* User List

`GET Method`


```
localhost:8090/api/users
```
#User Section

* Add User

`POST Method/JSON`

```
localhost:8090/api/user

```

```
{
	“username”: ”test”,
	“password”: “test”
	“email”: test@test.com
}

```

* Update User

`PATCH (Method/JSON)`

```
localhost:8090/api/user

```

```
{
	“id”:”3a984ff5-a2a7-4ec3-a074-d4bd4d9a7a2a”
	“username”: ”test”,
	“password”: “test”
	“email”: test@test.com
}

```

* Delete User

`DELETE Method`

```
localhost:8090/api/user/{id}

```

# City Section

* City List

`GET Method`


```
localhost:8090/api/cities

```

#User Section

* Add City

`POST Method/JSON`

```
localhost:8090/api/city
```

```
{
	“cityName” : “New York”
}

```

* Update User

`PATCH Method/JSON`

```
localhost:8090/api/city

```

```
{
	“id”:”3a984ff5-a2a7-4ec3-a074-d4bd4d9a7a2a”
	“cityName” : “Istanbul”
}

```

* Delete User

`DELETE Method`

```
localhost:8090/api/city/{id}

```

# Weather Section

* Get Weather Information

`GET Method`


```
localhost:8090/api/weaher/{cityName}

```

# Report Section

`GET Method`


```
localhost:8090/api/report/{userId}?cityId={cityId}?status={status}


```



userId: Required
cityId: Optional
status: Optional

> Example:localhost:8090/api/report/fa3327e1-61f8-42fe-b8b4-e840c01f8b2e?cityId=5c0ca7fa-9d9c-481a-86b0-3980ffb48db5?status=SUCCESS


