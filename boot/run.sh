docker stop cms-api || true && docker rm cms-api || true
docker run --name cms-api -d -p 8080:8080 dzioba/cms-api