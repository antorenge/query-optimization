# Test task

## Getting started
* Install postgresql (9+) and maven
* See db_bootstrap folder for db setup instructions
* Run gradle test to see if everything appears alright

## Testing the server startup & simple search
> gradle test

## Build project
> gradle build

## Running the local server
> gradle execute

## Querying the local server
> $ curl -v "http://localhost:12000/?merchant=8a3045ace8e164e896e2337fd9a12ca2&msisdn=373000028"

## Performance optimization

1. Optimizing database queries

Create an index without locking out writes to the payments table for the merchant and msisdn fields.

> CREATE INDEX CONCURRENTLY merchant_msisdn_index ON PAYMENTS (merchant_uuid, msisdn);

2. Catching database queries

[Cacheonix](https://www.cacheonix.org/index.htm) library is used to support caching the results of data queries so as to provide significant improvement of the application performance.
