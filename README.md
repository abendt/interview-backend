Please implement a service that reads in the provided xml and json files and exposes the information via a RESTful API.

File structure:
- products.xml contains master data for all products.
- prices.json contains price information for all products

The API should be designed in a way, that it allows the following use cases:
1. List all products with their master data
2. Show one product with master data and prices
3. Show only the current price of a product filtered by unit

Out of scope:
- Persistence of data
- Authentication/Authorization