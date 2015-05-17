# mckinsey

## Live Environment

[AppHarbor](https://appharbor.com)

- Email    : dev@rspective.pl

- Username : furryoctoninja

- App url  :	http://furryoctoninja.apphb.com/

- Repository Url: https://furryoctoninja@appharbor.com/furryoctoninja.git

- Deployment Url: https://support.appharbor.com/kb/getting-started/deploying-your-first-application-using-git

## Db Migrations

Run from Package Manager Console on Rspective.FurryOctoNinja.DataAccess project:

- To remove all data:

```
Update-Database -TargetMigration:$InitialDatabase 
```

- To move to latest version:

```
Update-Database
```

- To move to specific migration:

```
Update-Database -TargetMigration:"Migration name"
```

- To add new migraion:

```
Add-Migrarion "Name"
```


## Test Users:

*login  : password*

- bandro : furryninja2014

- polok  : furryninja2014

- pavel  : furryninja2014

- roger  : furryninja2014

- admin  : furryninja2014

## API

/api/survey - GET (same as: /api/survey/get)

/api/survey - POST (same as: /api/survey/post)

/api/survey/results - GET

/api/survey/users - GET

/api/user/login - POST (deprecated)

/api/auth/login - POST

- Request:

{
    login : "bandro" ("polok", "pavel", "roger", "admin"),
    password : ****,
    clientId : "El246n9cf1minYI0YGcBVQ8971fK8Gfp" ("p35iw0R6RO1730BSK432qswrZldwY0jR" for mobile!)
}

- Response:

400 - Bad Request, missing required parameters
401 - Unauthorized, there is no user or client

{
	Token: "...",
	Expiration: Date
	Roles: [ "Admin", "User" ]
}