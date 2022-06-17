#!/bin/bash

fileLocation=input.txt

while [ "$1" !=  "" ]; do
	case $1 in
		-fl | fileLocation ) shift
							fileLocation=$1
	    *) 
		exit 1
		esac
	shift
done

java -cp ./costCalculator-1.0-SNAPSHOT.jar co.aisle.costCalculator.GenerateReceipt $fileLocation