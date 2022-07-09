# $1 environment
# $2 u
# $3 p
echo "Running environment: $1";
docker login -u $2 -p $3
PROFILE_NAME=$1 docker compose up -d