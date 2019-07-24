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


## Sample Session
➜  payments git:(master) source start.sh

starting application
[2] 90326
appending output to nohup.out

➜  payments git:(master) getAllAccounts

curl -i -X GET 'localhost:8080/v1/accounts/'
HTTP/1.1 200 OK
Date: Wed, 24 Jul 2019 04:49:20 GMT
Content-Type: application/json
Content-Length: 2
Server: Jetty(9.4.z-SNAPSHOT)

[]%                                                                                                                  

➜  payments git:(master) createAccount 1 100

curl -i -X POST 'localhost:8080/v1/accounts/' -H 'Content-type: application/json' -d '{"id": "1", "balance": "100"}'
HTTP/1.1 200 OK
Date: Wed, 24 Jul 2019 04:49:25 GMT
Content-Type: application/json
Content-Length: 24
Server: Jetty(9.4.z-SNAPSHOT)

{"id":"1","balance":100}%                                                                                            

➜  payments git:(master) createAccount 2 300

curl -i -X POST 'localhost:8080/v1/accounts/' -H 'Content-type: application/json' -d '{"id": "2", "balance": "300"}'
HTTP/1.1 200 OK
Date: Wed, 24 Jul 2019 04:49:34 GMT
Content-Type: application/json
Content-Length: 24
Server: Jetty(9.4.z-SNAPSHOT)

{"id":"2","balance":300}%                                                                                            

➜  payments git:(master) transfer 1 2 30

curl -i -X POST 'localhost:8080/v1/transfers/' -H 'Content-type: application/json' -d '{"from": "1", "to": "2", "amount": "30"}'
HTTP/1.1 200 OK
Date: Wed, 24 Jul 2019 04:49:41 GMT
Content-Type: application/json
Content-Length: 54
Server: Jetty(9.4.z-SNAPSHOT)

{"from":"1","fromBalance":70,"to":"2","toBalance":330}%                                                              

➜  payments git:(master) transfer 1 3 100

curl -i -X POST 'localhost:8080/v1/transfers/' -H 'Content-type: application/json' -d '{"from": "1", "to": "3", "amount": "100"}'
HTTP/1.1 404 Not Found
Date: Wed, 24 Jul 2019 04:49:47 GMT
Content-Type: application/json
Content-Length: 31
Server: Jetty(9.4.z-SNAPSHOT)

Account with id 3 doesn't exist%                                                                                     

➜  payments git:(master) getAccount 1

curl -i -X GET 'localhost:8080/v1/accounts/1'
HTTP/1.1 200 OK
Date: Wed, 24 Jul 2019 04:49:56 GMT
Content-Type: application/json
Content-Length: 23
Server: Jetty(9.4.z-SNAPSHOT)

{"id":"1","balance":70}%                                                                                            

➜  payments git:(master) getAllAccounts

curl -i -X GET 'localhost:8080/v1/accounts/'
HTTP/1.1 200 OK
Date: Wed, 24 Jul 2019 04:50:00 GMT
Content-Type: application/json
Content-Length: 50
Server: Jetty(9.4.z-SNAPSHOT)

[{"id":"1","balance":70},{"id":"2","balance":330}]%
