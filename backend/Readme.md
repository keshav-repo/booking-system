## Setup

### Mysql setup using docker

```shell
docker run --name mysql-server -e MYSQL_ROOT_PASSWORD=secret@123 -p 3300:3306 -d mysql:latest

mysql -h 127.0.0.1 -P 3306 -u root -p secret@123

```

#### TO Learn

1. type-safe SQL queries
2. JPQL
3.

### Flow

Get list of city on based on location: city
select city and get all movies happening in the cities
get details of movie
book Ticket button -> get all theatre list with timing information
select theatre -> get seat list -> search can be empty or full
select seat and do payment -> login is needed here.
after payment success get ticket details for that user
