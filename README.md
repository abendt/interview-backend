![Travis](https://travis-ci.org/abendt/interview-backend.svg?branch=master)

To start the service:
`./gradlew bootRun`

To manually test the service:
1. List all products with their master data
- `curl http://localhost:8080/products`

2. Show single product with master data and all available prices
- http://localhost:8080/products/{id}
- example: `curl http://localhost:8080/products/b867525e-53f8-4864-8990-5f13a5dd9d14`

3. Show single product price for one product and specific unit
- http://localhost:8080/products/{id}/prices/{unit}
- example: `curl http://localhost:8080/products/b867525e-53f8-4864-8990-5f13a5dd9d14/prices/PACKAGE`

