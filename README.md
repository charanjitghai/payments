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
	- lombok

## RUN
1) source start.sh

## TEST
1) createAccount accountId1 accountBalance1
2) createAccount accountId2 accountBalance2
3) transfer accountId1 accountId2 amount
4) getAccount accountId1
5) getAccount accountId2
6) getAllAccounts

