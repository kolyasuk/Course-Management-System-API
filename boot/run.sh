# $1 environment
echo "Running environment: $1";
sudo docker stop cms-api  2> /dev/null && docker rm cms-api  2> /dev/null;
sudo docker run --pull=always --restart always --name cms-api -d -e spring.profiles.active=$1 -p 8080:8080 dzioba/cms-api