## Setup

### Mysql setup using docker

```shell
docker run --name mysql-server -e MYSQL_ROOT_PASSWORD=secret@123 -p 3300:3306 -d mysql:latest

mysql -h 127.0.0.1 -P 3300 -u root -p secret@123

```

#### TO Learn

1. type-safe SQL queries
2. JPQL
3. 

### Flow

Get list of city on based on location: city
select city and get all movies happening in the cities
get details of movie
book Ticket button -> get all theatre list with timing information for that movie

Add a movie
Update a movie details
inactivate a movie and activate

Add a theatre
Add screen to a theatre
add seats to a screen

Add a show
inactivate a show

get details of all shows in a theatre

select theatre -> get seat list -> search can be empty or full
select seat and do payment -> login is needed here.
after payment success get ticket details for that user

1. Ensure that no two show get same screen at a time. we need to check if a screen is empty at that time.
2.

UserController: Manages user-related operations.
MovieController: Manages movie-related operations.
TheatreController: Manages theatre-related operations.
ShowController: Manages show-related operations.
BookingController: Manages booking and ticketing operations.
PaymentController: Manages payment-related operations.
