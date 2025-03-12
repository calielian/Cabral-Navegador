#! /bin/bash

mvn clean package
cd target
java -jar cabralnavegador-1.0.jar
