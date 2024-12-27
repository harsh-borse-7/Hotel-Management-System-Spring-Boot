# Hotel Management System - Spring Boot

A Spring Boot-based Hotel Management System designed for managing hotels, their details, and associated menu items. This system allows you to handle hotel data, fetch related menu items, and calculate their prices based on hotel-specific multipliers.

## Features
- **Hotel Management**: Manage hotel details, including hotel name, location, and a list of associated menu items.
- **Menu Management**: Store menu items with attributes like name, base price, and description.
- **Dynamic Price Calculation**: Fetch hotel details along with menu items where prices are dynamically calculated based on the hotel’s multiplier.
- **Error Handling**: Handle missing menu items gracefully and provide meaningful error messages or placeholder data.
- **Custom Responses**: Return hotel details and associated menu items as part of the response, including updated pricing.

## Tech Stack
- **Java**: Programming language used for building the application.
- **Spring Boot**: Framework for developing the backend of the hotel management system.
- **MySQL**: Relational database for storing hotel and menu item data.
- **Maven/Gradle**: Dependency management tools for the project.
- **Postman**: Tool for testing the APIs and validating responses.

## Project Structure
The project follows a typical layered architecture in Spring Boot:

1. **Model Layer**: Defines the entities for the hotel and menu items.
2. **Controller Layer**: Manages the API endpoints and user requests.
3. **Service Layer**: Contains business logic such as fetching hotel and menu data and calculating prices.
4. **Repository Layer**: Interacts with the database to retrieve and persist hotel and menu data.

## Core Functionality

### 1. **Hotel Management**
Each hotel is represented by the following attributes:
- `hotelID`: Unique identifier for the hotel.
- `hotelName`: Name of the hotel.
- `location`: Location of the hotel.
- `multiplier`: Price multiplier specific to the hotel.
- `itemIDs`: Comma-separated list of menu item IDs associated with the hotel.

### 2. **Menu Management**
Menu items are stored as entities with the following attributes:
- `itemID`: Unique identifier for the menu item.
- `itemName`: Name of the menu item.
- `basePrice`: Base price of the menu item.
- `description`: A description of the menu item.

### 3. **Fetching Hotels with Menu Items**
When fetching hotel details, the following steps occur:
- The `itemIDs` from the hotel entity are split into individual IDs.
- The corresponding menu items are retrieved from the database using these IDs.
- The price of each menu item is calculated by multiplying its `basePrice` by the hotel’s `multiplier`.
- The response includes both the hotel details and updated prices for the associated menu items.

