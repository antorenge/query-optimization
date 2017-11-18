# Test task

## Getting started
* Install postgresql and maven
* See db_boostrap folder for db setup instructions
* Run mvn test to see if everything appears alright

## Testing the server startup & simple search
> mvn test

## Running the local server
> mvn exec:java

## Quering the local server
> $ curl -v "http://localhost:12000/?merchant=8a3045ace8e164e896e2337fd9a12ca2&msisdn=373000028"

## I dont like prechosen component jetty, db driver or the database itself 
> You can swap anything you like except the dataset.

> If you choose alternative datastore it must be able to receive new records rapidly over time.

