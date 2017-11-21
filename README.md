# Test task

## Getting started
* Install postgresql (9+) and maven
* See db_bootstrap folder for db setup instructions
* Run mvn test to see if everything appears alright

## Testing the server startup & simple search
> mvn test

## Running the local server
> mvn exec:java

## Querying the local server
> $ curl -v "http://localhost:12000/?merchant=8a3045ace8e164e896e2337fd9a12ca2&msisdn=373000028"

## I dont like pre-chosen component jetty, db driver or the database itself 
> You can swap anything you like except the data set.

> If you choose alternative data store it must be able to receive new records rapidly over time.
