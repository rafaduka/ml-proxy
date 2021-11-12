# ML Proxy

Proxy developed for the Mercado Livre technical challenge.
On each folder you'll find the source code for every component developed for the challenge.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

https://www.docker.com/get-started

### Usage

#### Development Profile

Run in the command line:
```
docker-compose up
```

Test the proxy:
```
curl http://localhost:8080/categories/MLA5725
```

Open statistics dashboard at http://localhost:4200

#### Local Profile

Run in the command line:
```
mvn spring-boot:run -Dspring.profiles.active="local"
```

Test the proxy:
```
curl http://localhost:8080/categories/MLA5725
```

Open statistics dashboard at http://localhost:4200

### Tests

#### Local profile

(It is necessary to have MongoDB and Redis running)

Run in the command line:
```
mvn '-Dtest=ProxyApplicationTests' test
```

## Solution diagram

![alt text](docs/images/macro-flow.png)


### Rate Limit Algorithm
We count requests from each sender using multiple fixed time windows 1/60th the size of our rate limit’s time window.

For example, if we have an hourly rate limit, we increment counters specific to the current Unix minute timestamp and 
calculate the sum of all counters in the past hour when we receive a new request.

![alt text](docs/images/slidewindow.png)

### Strategy Rate Limit Distributed

We divide the limit load for each instance according to the total available.
With the formula below

![alt text](docs/images/ratelimit-instance-strategy.png)


## Built With

* [Spring Boot](https://spring.io/) - The backend framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Zuul](https://github.com/Netflix/zuul) - Gateway service
* [Mongo](https://www.mongodb.com/) - MongoDB

## Roadmap
- [x] Dockerizado
- [x] RateLimiter Proxy
- [x] API Statistics
- [ ] Swagger
- [ ]
- [x] Readme 

## Author

Rafael Horácio :octocat:  
rafaduka@gmail.com

## License
[MIT](https://choosealicense.com/licenses/mit/)