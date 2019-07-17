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
	- Guice
	- Junit5
