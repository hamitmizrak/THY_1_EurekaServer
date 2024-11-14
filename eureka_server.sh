#!/bin/bash

echo -e "Eureka Server End Point"

#Countdown execute yaptım
chmod +x countdown.sh

eureka_server_curl(){
   ./countdown.sh
  curl http://localhost:8761/
   ./countdown.sh
  curl http://localhost:8761/eureka/apps
   ./countdown.sh
  curl http://localhost:8761/eureka/apps/address-service
   ./countdown.sh
  curl http://localhost:8761/actuator/health
}

eureka_server_curl