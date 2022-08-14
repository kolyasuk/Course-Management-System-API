# $1 environment
# $2 u
# $3 p
echo "Running environment: $1";
pwd
docker login -u $2 -p $3
sudo docker stop cms-api  2> /dev/null && docker rm cms-api  2> /dev/null
sudo docker stop jenkins-cms-api-1  2> /dev/null && docker rm jenkins-cms-api-1  2> /dev/null;
docker compose down
docker compose pull
PROFILE_NAME=$1 docker compose up -d