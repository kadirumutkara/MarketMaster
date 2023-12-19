# MarketMaster

## Project Description
MarketMaster is a dynamic project focused on creating stock exchanges and adding stocks to these exchanges. It features a robust backend with seven different APIs, each designed to manage stock exchanges and stocks efficiently. Detailed examples of API usage are provided in a Postman JSON file for ease of understanding and use.

## Features and Endpoints
The application offers several features, implemented through the following endpoints:

- **List One StockExchange with All Stocks:** `GET api/stock-exchange/{name}`
- **Create a Stock:** `POST api/stock`
- **Add a Stock to the Stock Exchange:** `POST api/stock-exchange/{name}`
- **Delete a Stock from the Stock Exchange:** `DELETE api/stock-exchange/{name}/{stockId}`
- **Update the Price of a Stock:** `PUT api/stock/{id}`
- **Delete a Stock from the System:** `DELETE api/stock/{id}`
## Project Rules and Logic

The development of the MarketMaster project adhered to specific business rules to ensure its functionality aligns with real-world stock exchange operations. These rules are:

1. **Multiple Stocks in a Stock Exchange:** Each stock exchange within the MarketMaster application can list multiple stocks, reflecting the diversity and breadth of a real-world stock market.

2. **Live Status of Stock Exchanges:** A stock exchange is considered 'live' in the market only if it has five or more stocks listed. This rule ensures that only active and sufficiently diverse stock exchanges are marked as operational (`liveInMarket` flag is set to `true`). Stock exchanges with fewer than five stocks are not considered live and have the `liveInMarket` flag set to `false`.

3. **Stock Listing Across Exchanges:** A particular stock can be listed on multiple stock exchanges. Regardless of the number of exchanges a stock is listed on, all its properties (such as name, current price, description, etc.) remain the same across all these exchanges. This feature demonstrates the uniformity and consistency of stock information, akin to real-world scenarios where a stock retains its identity across different markets.

By adhering to these rules, MarketMaster provides a realistic simulation of stock exchange mechanics, offering a robust platform for managing stocks and exchanges.


## Initial Setup
The `DatabaseLoader` class populates the H2 in-memory database with predefined data on startup. The application utilizes three primary tables: `STOCK`, `STOCK_EXCHANGE`, and the mapping table `STOCK_EXCHANGE_STOCK`.

## Security and Authentication
Spring Security is integrated for access control. Authentication is required to access the endpoints, with the following user credentials:

- **Username:** admin
- **Password:** admin

Access the H2 database at `http://localhost:8080/h2-console` using these credentials:

- **Username:** username
- **Password:** 1234
## Spring Security Configuration

In the MarketMaster project, Spring Security is implemented to ensure secure access to the application's functionalities. The security configuration is defined in the `WebSecurityConfig` class, which sets up the necessary security constraints.

### Key Features of Security Configuration:

1. **User Authentication:** The application defines two in-memory user roles: 'USER' and 'ADMIN'. These roles are crucial for accessing various endpoints of the application. The `userDetailsService` bean creates these user accounts with encrypted passwords, ensuring secure access.

2. **Form Login and HTTP Basic Authentication:** The security configuration supports both form-based login and HTTP basic authentication. This provides flexibility in how users can authenticate themselves when interacting with the application.

3. **CSRF and Frame Options:** To facilitate the use of the H2 database console, CSRF protection is disabled, and frame options are set to allow the use of frames. This is necessary for accessing the H2 console through the browser.

4. **Whitelist for H2 Console:** A crucial aspect of the security configuration is the whitelist defined for accessing the H2 database console. The whitelist (`AUTH_WHITELIST`) includes the path patterns for the H2 console (`/h2-console/**`). This ensures that the H2 console can be accessed without authentication, facilitating easy database management during development and testing phases.


## API Testing and Verification
Six comprehensive tests have been written to verify the business logic of the utilized endpoints. These tests ensure that each component of the application functions as expected, providing an additional layer of reliability and robustness.

## Exception Handling and Error Codes
MarketMaster employs a sophisticated exception handling mechanism to manage business logic and runtime exceptions efficiently. This approach ensures robust error handling and user-friendly error messages. Key components of this system include:

### Custom Exception Classes
#### ApplicationException: Used for general runtime errors, extending from RuntimeException.
#### BusinessException: Specific to business logic violations, inheriting from ApplicationException.
Defined Error Codes
The application defines a set of error codes to categorize and identify different error scenarios. Some key error codes include:


10. **STOCK_PRICE_MUST_BE_NUMBER**
11. **STOCK_PRICE_MUST_NOT_BE_NEGATIVE**
12. **STOCK_NOT_FOUND**
13. **STOCK_PRICE_CANNOT_BE_NULL**
14. **STOCK_NAME_REQUIRED**
15. **STOCK_EXCHANGE_NOT_FOUND**
16. **STOCK_ALREADY_EXISTS_IN_EXCHANGE**
17. **STOCK_NOT_IN_EXCHANGE**
101.**GENERAL ERROR**

These error codes facilitate easier debugging and user communication regarding issues encountered during application usage.

### Global Exception Handler
The GlobalExceptionHandler class, annotated with @RestControllerAdvice, is responsible for handling exceptions globally. This class captures exceptions thrown across the application and returns a consistent error response format, improving the API's reliability and maintainability.

## Logging Strategy
Logging plays a crucial role in monitoring and debugging the application. MarketMaster utilizes the SLF4J logging framework to log important information, errors, and exceptions. This strategy aids in tracking application flow and identifying issues promptly. Key aspects of our logging approach include:

### Informative Log Messages: 
Logs provide detailed information about application operations, such as the start and completion of significant processes.
### Error Logging:
In case of exceptions or unexpected behavior, error logs capture essential details for troubleshooting.
### Consistent Logging Across Services:
All service classes implement logging consistently, ensuring uniformity in log messages and error reporting.

## Postman Collection Import Guide

The `MarketMaster.Api.json` file included in this repository is a Postman collection containing examples of all the API requests. To import and use this collection in Postman, follow these steps:

1. **Open Postman:** Launch the Postman application on your computer.

2. **Import Collection:** Click the `Import` button at the top left corner of the Postman interface.

3. **Choose File:** In the import dialog, select the `Upload Files` tab and browse to the location of the `MarketMaster.Api.json` file. Select the file and click `Open`.

4. **Complete Import:** After the file is uploaded, review the details and click the `Import` button to add the collection to your Postman workspace.

5. **Access and Use Collection:** Once imported, the `MarketMaster` collection will be visible in the left-hand sidebar of Postman. You can now access and use the API requests for testing and interaction with the application.

This collection serves as a comprehensive guide to understand and test the application's functionalities, providing practical examples for each endpoint.
