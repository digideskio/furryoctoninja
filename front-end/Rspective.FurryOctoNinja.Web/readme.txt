-> Test envoirentment has been prepared, and application has beeen deployed :
http://furryoctoninja.apphb.com/

-> How to log in? 
Please use one of the test user we have prepared:

bandro	: furryninja2014
polok	: furryninja2014
pavel	: furryninja2014
roger	: furryninja2014
admin	: furryninja2014

-> Database: 
We use EF Code First Migrations. Test database has been also prepared, but below three quick steps how to prepare them:

1. Remove all data:
Update-Database -TargetMigration:$InitialDatabase 

2. Add migration:
Add-Migration "Initial"

3. Update
Update-Database

-> Push notifications:
We use OneSignal as a provider, and this application uses our instance of OneSignal (user: dev@rspective.pl)

-> API:
Both: web and android apps uses the same API, but they use different Client Ids:

Client "AngularJS": El246n9cf1minYI0YGcBVQ8971fK8Gfp
Client "Android": p35iw0R6RO1730BSK432qswrZldwY0jR

Methods:

/api/survey - GET (same as: /api/survey/get)
/api/survey - POST (same as: /api/survey/post)
/api/survey/notify - POST
/api/survey/reset - POST
/api/survey/results - GET
/api/survey/users - GET
/api/survey/validate - POST
/api/survey/save - POST
/api/auth/login - POST
/api/user/current - GET
/api/user/all - GET
/api/user - POST (same as: /api/user/post)
/api/user - DELETE (same as: /api/user/delete)
/api/user - PUT (same as: /api/user/put)
/api/user - GET (same as: /api/user/get)

Sample request:

POST /api/auth/login HTTP/1.1
Host: localhost:50753
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 15bcd829-1d98-82c8-ea14-30920b2221f9

{ "login" : "bandro", "password" : "furryninja2014", "clientId" : "p35iw0R6RO1730BSK432qswrZldwY0jR" }

Sample response:

400 - Bad Request, missing required payload
403 - Forbiden, user or client does not exists
200 - Success
{
    "Token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFeHBpcmF0aW9uIjoiXC9EYXRlKDE0MzQ2Njg0NDA1MjQpXC8iLCJDbGllbnQiOiJhbmRyb2lkbW9iaWxlIiwiVXNlcklkIjoxLCJSb2xlcyI6WyJVc2VyIl19.lunA5forY3VlaewaguFXr3nPfNKXQfXbPClY3p7pDyY",
    "Expiration": "2015-06-18T22:55:35.9596025Z",
    "Roles": [
        "User"
    ]
}

-> Questions?
In case of any questions don't hesitate to contact us at dev@rspective.pl :)