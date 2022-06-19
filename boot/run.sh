# $1 environment
docker stop cms-api  2> /dev/null && docker rm cms-api  2> /dev/null
docker run --name cms-api -d -e env=$1 -p 8080:8080 dzioba/cms-api