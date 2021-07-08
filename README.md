# Wisercat-Test-Assignment

## Running the app with Docker

1. Navigate to folder filters/docker. Pull app and database images:
   ```
   docker pull ainsyyy/filters-springbootapp:latest
   docker pull ainsyyy/filters-postgresql:latest
   ```

1. Compose app and database:
   ```
   docker-compose up
   ```

1. Access application:

   ```
   http://localhost:8080/
   ```
