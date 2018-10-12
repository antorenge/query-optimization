# Query Optimization

## Getting started
* Install postgresql (9+) and maven
* See db_bootstrap folder for db setup instructions
* Run gradle test to see if everything appears alright

## Test project
`gradle test`

## Build project
`gradle build`

## Running the local server
`gradle execute`

## Querying the local server

### Perform search query
`$ curl -v "http://localhost:12000/?merchant=92b43b9cbe987973c39bf44a7a67b352&operator=operator-3&msisdn=373000002"`

### Perform report query
`$ curl -v "http://localhost:12000/report/?merchant=849961a24d5dc6d961da43e550a2f0b9&start_date=2018-10-10&end_date=2018-10-10"`

## Performance optimization

1. Optimizing database queries

Create an index without locking out writes to the payments table for the merchant and msisdn fields.

`CREATE INDEX CONCURRENTLY merchant_msisdn_index ON PAYMENTS (merchant_uuid, msisdn);`

2. Catching database queries

[Cacheonix](https://www.cacheonix.org/index.htm) library is used to support caching the results of data queries.
