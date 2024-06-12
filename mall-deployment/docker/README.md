# Use Docker Compose to manage Spring Boot containers

## pwd: docker-compose.yml

```bash
cd /mall-deployment/docker
```

## Run all services at sametime
```bash
docker-compose up -d
```

## Stop all services at sametime
```bash
docker-compose down
```

# Only running PostgreSQL Database
```bash
docker-compose up -d mall-postgres
```

### Database Commands

Start an interactive PSQL console:

```bash
docker exec -it mall-postgres psql -U mall -d mall_catalog
```

| PSQL Command	           | Description                                    |
|:------------------------|:-----------------------------------------------|
| `\list`                 | List all databases.                            |
| `\connect mall_catalog` | Connect to specific database.                  |
| `\dt`                   | List all tables.                               |
| `\d book`               | Show the `book` table schema.                  |
| `\quit`                 | Quit interactive psql console.                 |