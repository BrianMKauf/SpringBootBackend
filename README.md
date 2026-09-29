# SpringBootBackend

Kotlin Spring Boot API.

Frontend: https://github.com/BrianMKauf/ReactFrontend

## Local API

JDK 17+

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

- http://localhost:8080/api/health
- http://localhost:8080/api/hello?name=Brian

## Local Postgres (your Mac)

GitHub does not run Postgres on your desktop. This repo includes `docker-compose.yml` so you start the same database locally.

### One-time: install Docker Desktop

1. https://www.docker.com/products/docker-desktop/
2. Download **Mac with Apple Silicon** or **Mac with Intel**, depending on your machine
3. Open Docker Desktop and wait until it says running
4. Confirm:

```bash
docker version
```

### Start Postgres

From this repo:

```bash
docker compose up -d
```

That starts Postgres 16 on `localhost:5432`.

- database: `app`
- user: `postgres`
- password: `dev`

Stop:

```bash
docker compose down
```

Data stays in a Docker volume until you run `docker compose down -v`.

The API does not read/write the database yet. When it does, local settings will be:

```
DATABASE_URL=jdbc:postgresql://localhost:5432/app
DATABASE_USER=postgres
DATABASE_PASSWORD=dev
```

## Dev database (Neon, not Render)

Render free Postgres is deleted after 30 days. Use Neon for the Render **dev** API.

### Create a Neon account

1. Open https://console.neon.tech
2. Sign up with **GitHub** (same account is fine)
3. Create a project, name it `SpringBootBackend-BK-dev`
4. Region close to Ohio if listed (your Render API is in Ohio)
5. After the project exists, open **Dashboard** → **Connection details**
6. Copy the connection string (URI). It looks like:
   `postgresql://user:password@ep-....neon.tech/neondb?sslmode=require`

### Give the string to Render

On **SpringBootBackend-BK-dev** → **Environment**:

- `DATABASE_URL` = that Neon URI

Do not commit the password to GitHub.

## Tests

```bash
./mvnw test
```

GitHub Actions still runs unit tests. Smoke tests hit the live Render API. Postgres in CI can be added when the API starts using a database.
