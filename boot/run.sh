docker stop cms-api
docker rm cms-api
docker run --name cms-api -d -p 8080:8080 dzioba/cms-api