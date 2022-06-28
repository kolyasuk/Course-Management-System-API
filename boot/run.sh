# $1 environment
echo "Running environment: $1";
docker stop cms-api  2> /dev/null && docker rm cms-api  2> /dev/null;
docker run --name cms-api -d -e App.config.file=/home/dzioba99/cms-api/config/application-$1.properties env=$1 -p 8080:8080 dzioba/cms-api;