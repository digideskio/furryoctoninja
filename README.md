# mckinsey

## Live Environment

- Provider: [AppHarbor](https://appharbor.com)

- Email: dev@rspective.pl

- Username: furryoctoninja

- App url:	http://furryoctoninja.apphb.com/

- Repository Url: https://furryoctoninja@appharbor.com/furryoctoninja.git

- Deployment Url: https://support.appharbor.com/kb/getting-started/deploying-your-first-application-using-git

## Test Users:

*login: password*

- bandro: furryninja2014

- polok: furryninja2014

- pavel: furryninja2014

- roger: furryninja2014

- admin: furryninja2014

## Keys:

- Client "AngularJS": El246n9cf1minYI0YGcBVQ8971fK8Gfp

- Client "Android": p35iw0R6RO1730BSK432qswrZldwY0jR

## Headers

- Authorization

Schema

```
Authorization: Token ClientId:GeneratedToken
```

Sample

```
Authorization: Token p35iw0R6RO1730BSK432qswrZldwY0jR:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFeHBpcmF0aW9uIjoiXC9EYXRlKDE0MzQ2Njg0ODU2MDcpXC8iLCJDbGllbnQiOiJhbmRyb2lkbW9iaWxlIiwiVXNlcklkIjoxLCJSb2xlcyI6WyJVc2VyIl19.NOfH0mZP0qqIaXNvJTHGy4jubNBMkaG5phlN1t-AJ88
```

## API

- /api/survey - GET (same as: /api/survey/get)

- /api/survey - POST (same as: /api/survey/post)

- /api/survey/results - GET

- /api/survey/users - GET

- /api/auth/login - POST

Request:

```
POST /api/auth/login HTTP/1.1
Host: localhost:50753
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 15bcd829-1d98-82c8-ea14-30920b2221f9

{ "login" : "bandro", "password" : "furryninja2014", "clientId" : "p35iw0R6RO1730BSK432qswrZldwY0jR" }
```

Response:

```
400 - Bad Request, missing required payload
```

```
403 - Forbiden, user or client does not exists
```

```
200 - Success
{
    "Token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFeHBpcmF0aW9uIjoiXC9EYXRlKDE0MzQ2Njg0NDA1MjQpXC8iLCJDbGllbnQiOiJhbmRyb2lkbW9iaWxlIiwiVXNlcklkIjoxLCJSb2xlcyI6WyJVc2VyIl19.lunA5forY3VlaewaguFXr3nPfNKXQfXbPClY3p7pDyY",
    "Expiration": "2015-06-18T22:55:35.9596025Z",
    "Roles": [
        "User"
    ]
}
```

- /api/auth/refresh - POST

Request

```
POST /api/auth/refresh HTTP/1.1
Host: localhost:50753
Content-Type: application/json
Authorization: Token p35iw0R6RO1730BSK432qswrZldwY0jR:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFeHBpcmF0aW9uIjoiXC9EYXRlKDE0MzQ2Njg0NDA1MjQpXC8iLCJDbGllbnQiOiJhbmRyb2lkbW9iaWxlIiwiVXNlcklkIjoxLCJSb2xlcyI6WyJVc2VyIl19.lunA5forY3VlaewaguFXr3nPfNKXQfXbPClY3p7pDyY
Cache-Control: no-cache
Postman-Token: cc6add8a-2588-8d63-dcdd-7bdb56dde309
```

Response:

```
401 - Unauthorized
```

```
{
    "Token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFeHBpcmF0aW9uIjoiXC9EYXRlKDE0MzQ2Njg0ODU2MDcpXC8iLCJDbGllbnQiOiJhbmRyb2lkbW9iaWxlIiwiVXNlcklkIjoxLCJSb2xlcyI6WyJVc2VyIl19.NOfH0mZP0qqIaXNvJTHGy4jubNBMkaG5phlN1t-AJ88",
    "Expiration": "2015-06-18T23:01:25.6074447Z",
    "Roles": [
       "User"
    ]
}
```

- /api/user/current - GET

- /api/user/all - GET

- /api/user - POST (same as: /api/user/post) 

- /api/user - DELETE (same as: /api/user/delete) 

- /api/user - PUT (same as: /api/user/put) 

- /api/user - GET (same as: /api/user/get) 