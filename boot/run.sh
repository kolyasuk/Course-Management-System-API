# $1 environment
echo "Running environment: $1";
docker stop cms-api  2> /dev/null && docker rm cms-api  2> /dev/null;
docker run --pull=always --restart always --name cms-api -d -e spring.profiles.active=$1 -p 8080:8080 dzioba/cms-api