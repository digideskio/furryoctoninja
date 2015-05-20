# front-end

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

TEST