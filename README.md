# Payment Service
#### 1. Get token - POST request

###### URL
/token/{service_name}/{method_name}

###### Headers
content-type: application/json;charset=utf-8

###### Request Body
date_from - string

date_to - string

###### Responses
1. Success

   token - string
   
   status_code - number(201)
2. Failure

   status_code - number(404)
   
   status_message - string("Invalid method")
