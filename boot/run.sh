# $1 environment
# $2 u
# $3 p
echo "Running environment: $1";
docker login -u $2 -p $3
sudo docker stop cms-api  2> /dev/null && docker rm cms-api  2> /dev/null;
sudo docker stop jenkins-cms-api-1  2> /dev/null && docker rm jenkins-cms-api-1  2> /dev/null;
sudo docker run --pull=always --restart always --name cms-api -d -e SPRING_PROFILE=$1 -p 8080:8080 dzioba/cms-api