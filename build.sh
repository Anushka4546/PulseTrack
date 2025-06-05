# Remove previous images & containers
docker compose down -v --remove-orphans
docker system prune -af

# Rebuild both projects locally

cd producer
mvn clean package -DskipTests

cd ../consumer
mvn clean package -DskipTests

# Build docker images freshly

docker compose build --no-cache

# Start everything
docker compose up
