# payments

## Overview
- Simple REST api application to support money transfer requests
- Uses In-memmory datastore
- Ability to Create, get, list accounts and transfer money between accounts
- Validations
	- Duplicate Acount
	- Account Doesn't Exist
	- Insufficient Funds for transfer
	- Illegal transfer amount (< 0)
- Libraries
	- Jetty server
	- Jersey
	- swagger code-gen
	- Jackson
	- Junit5

## RUN
mvn clean install
./run.sh
createAccount <accountId1> <accountBalance>
createAccount <accountId2> <accountBalance2>
transfer <accountId1> <accountId2> <amount>
