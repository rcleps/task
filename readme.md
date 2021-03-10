# Miro task

## Run
```
./mvnw spring-boot:run
```
## Swagger-UI

Swagger url: http://localhost:8090/swagger-ui/index.html
Swagger docs: /api-docs

## REST endpoints
Root URL: http://localhost:8090

## Create
```
curl -X POST "http://localhost:8090/widget/" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"coordinatorX\":1,\"coordinatorY\":1,\"indexZ\":1,\"width\":10,\"height\":100}"
```

## Update
```
curl -X PUT "http://localhost:8090/widget/id" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"coordinatorX\":13,\"coordinatorY\":13,\"indexZ\":2,\"width\":100,\"height\":10}"
```
## Delete
```
curl -X DELETE "http://localhost:8090/widget/id" -H  "accept: */*"
```

## Get by Id
```
curl -X GET "http://localhost:8090/widget/id" -H  "accept: application/json"
```

## Get by Page
```
curl -X GET "http://localhost:8090/widget/id" -H  "accept: application/json"
```

## Get by All
```
curl -X GET "http://localhost:8090/widget/all" -H  "accept: application/json"
```
