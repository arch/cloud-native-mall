# Running Mall

create a Docker network

```bash
docker network create mall-network
```

## Running Catalog Service

```bash
docker run -d \
    --name catalog-servie \
    -network mall-networ \
    -p 9001:9001 \
    -e SPRING_DATASOURCE_URL=jdbc:postgresql://mall-posgres:5432/mall_catalog \
    -e MALL_TESTDATA_ENABLED=true \
    catalog-service
```

## Running a PostgreSQL Database

Run PostgreSQL as a Docker container

```bash
docker run -d \
    --name mall-postgres \
    --network mall-network \
    -e POSTGRES_USER=mall \
    -e POSTGRES_PASSWORD=p@ssword \
    -e POSTGRES_DB=mall_catalog \
    -p 5432:5432 \
    postgres:16.3-alpine
```

### Container Commands

| Docker Command	               | Description       |
|:------------------------------|:-----------------:|
| `docker stop mall-postgres`   | Stop container.   |
| `docker start mall-postgres`  | Start container.  |
| `docker remove mall-postgres` | Remove container. |

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

From within the PSQL console, you can also fetch all the data stored in the `book` table.

```bash
select * from book;
```

# Cleanup

```bash
docker rm -f catalog-service mall-postres
```