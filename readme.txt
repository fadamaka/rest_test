Unprotected endpoints:

POST: /sign-up
	Send JSON to register a user:

	{
    		"name": "testuser",
    		"email": "user@test.com",
    		"pw": "12345"
	}

POST: /login
	Send JSON to log in

	{
    		"email": "user@test.com",
    		"pw": "paprika"
	}

	on successful login JWT comes back in the header.

Protected endpoints:

Valid JWT must be sent in the header of the request:
	Authorization: Bearer <proper JWT>

POST: /products
	Creates a new product.
	{
		"code": 123456,
        	"name": "test product",
        	"description": "test desc",
        	"price": "121233"
    	}

GET: /products
	Returns with a list of products.
	Optional parameters:
	-size,page for pagination
	-code for filtering

GET: /products/{productCode}
	Returns a single product matching the product code,
	with a list of ratings related to said product.

PUT: /products/{productCode}
	Enables the updating of products.
	{
        	"name": "test product",
        	"description": "test desc",
        	"price": "121233"
    	}

POST: /rate/{productCode}
	Enables the rating of products.
	{
		"rating": "5"
	}

GET: /ratings/{productCode}
	Returns with a list of ratings related to the product code.

GET: /ratings/avg/{productCode}
	Returns with the avarage rating of a product.