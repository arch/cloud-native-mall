# Running a PostgreSQL Database

Run PostgreSQL as a Docker container

```bash
docker run -d \
    --name mall-postgres \
    -e POSTGRES_USER=mall \
    -e POSTGRES_PASSWORD=p@ssword \
    -e POSTGRES_DB=mall_catalog \
    -p 5432:5432 \
    postgres:14.10
```

## Container Commands

| Docker Command	               | Description       |
|:------------------------------|:-----------------:|
| `docker stop mall-postgres`   | Stop container.   |
| `docker start mall-postgres`  | Start container.  |
| `docker remove mall-postgres` | Remove container. |

## Database Commands

Start an interactive PSQL console:

```bash
docker exec -it mall-postgres psql -U mall -d mall_catalog
```


| PSQL Command	              | Description                                    |
|:---------------------------|:-----------------------------------------------|
| `\list`                    | List all databases.                            |
| `\connect polardb_catalog` | Connect to specific database.                  |
| `\dt`                      | List all tables.                               |
| `\d book`                  | Show the `book` table schema.                  |
| `\quit`                    | Quit interactive psql console.                 |

From within the PSQL console, you can also fetch all the data stored in the `book` table.

```bash
select * from book;
```