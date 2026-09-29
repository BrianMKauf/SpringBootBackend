# SpringBootBackend

Kotlin Spring Boot API.

Paired frontend repo: https://github.com/BrianMKauf/ReactFrontend

## Local

JDK 17+

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

- http://localhost:8080/api/health
- http://localhost:8080/api/hello?name=Brian

## Render (dev)

Create a **Web Service** from this repo.

Suggested service name: `SpringBootBackend-dev-BK`

- Language: Docker
- Dockerfile: `./Dockerfile`
- Instance: Free

Environment:

- `CORS_ORIGINS` = the ReactFrontend-dev-BK URL, e.g. `https://reactfrontend-dev-bk.onrender.com`
  (no trailing slash; comma-separate if you also allow localhost)

Render assigns the public hostname. After the first deploy, put that API URL into the frontend as `VITE_API_URL`.
