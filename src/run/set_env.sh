#!/bin/bash

function getAllAccounts {
    cmd="curl -i -X GET 'localhost:8080/v1/accounts/'"
    echo $cmd
    eval $cmd
}

function getAccount {
    cmd="curl -i -X GET 'localhost:8080/v1/accounts/$1'"
    echo $cmd
    eval $cmd
}

function createAccount {
	cmd="curl -i -X POST 'localhost:8080/v1/accounts/' -H 'Content-type: application/json' -d '{\"id\": \"$1\", \"balance\": \"$2\"}'"
    echo $cmd
    eval $cmd
}

function transfer {
	cmd="curl -i -X POST 'localhost:8080/v1/transfers/' -H 'Content-type: application/json' -d '{\"from\": \"$1\", \"to\": \"$2\", \"amount\": \"$3\"}'"
    echo $cmd
    eval $cmd
}
