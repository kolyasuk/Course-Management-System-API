# CMS-API

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for
development and testing purposes.

### Prerequisites

For building and running the application you need:

```
JDK 11
```
```
MySQL 8.0
```
```
Gradle 6.3
```
### Setup

* Clone the project from Bitbucket repository
* Setup such environment variables in the configuration properties:
  "SPRING_PROFILE=[profile]",
  where [profile] is needed profile for using (dev, prod, test)
* To build jar file run such command:
  "gradle clean build"
* To run jar file run such command:
 "java -jar -DSPRING_PROFILE=[profile] CMS-API-1.0-SNAPSHOT.jar",
  where [profile] is needed profile for using (dev, prod, test)

## DEPLOYMENT: CI/CD
There is deployed Jenkins by a link: [34.141.86.60:8080](http://34.141.86.60:8080).

To deploy new code select **build docker image** pipeline and choose a branch:
* **develop** - to redeploy testing server
* **master** - to redeploy production server

**If there were changes in any configuration file, you need to update them in build folders of jenkins by such paths:
<em>/var/lib/jenkins/workspace/[pipeline folder name]/src/main/resources/app_config/</em>,
where [pipeline folder name] could be as <em>build_docker_image_master</em>**

## DEPLOYMENT: Infrastructure
**Testing server:**
- The testing server is deployed using compose where DB is part of it.
- To run in use bash file <em>run-test.sh</em> located in <em>/boot/</em> folder
- As the result, the file <em>run-test.sh</em> runs <em>docker-compose.yml</em> file located in <em>/src/main/docker/</em> folder

**Production server:**
- The is MySql 8.0 production DB hosted on Google cloud
- To run in use bash file <em>run.sh</em> located in <em>/boot/</em> folder
- As the result, the file <em>runt.sh</em> runs <em>Dockerfile</em> file located in <em>/src/main/docker/</em> folder

## DEPLOYMENT: Cloud
Google Cloud is used for the production server

### Property files

All properties are in application-[profile].properties file.
There are three property files for each profile:

* application-dev.properties - is used for the development process
* application-prod.properties - is used for production environment
* application-test.properties - is used for testing on testing server
* application-testing.properties - is used for running tests
