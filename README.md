# Hotel Management System - Spring Boot

A Spring Boot-based Hotel Management System designed for managing hotels, their details, and associated foodItem items. This system allows you to handle hotel data, fetch related foodItem items, and calculate their prices based on hotel-specific multipliers.

## Features
- **Hotel Management**: Manage hotel details, including hotel name, location, and a list of associated foodItem items.
- **FoodItem Management**: Store foodItem items with attributes like name, base price, and description.
- **Error Handling**: Handle missing foodItem items gracefully and provide meaningful error messages or placeholder data.

## Tech Stack
- **Java**: Programming language used for building the application.
- **Spring Boot**: Framework for developing the backend of the hotel management system.
- **MySQL**: Relational database for storing hotel and foodItem item data.
- **Maven/Gradle**: Dependency management tools for the project.
- **Postman**: Tool for testing the APIs and validating responses.

## Project Structure
The project follows a typical layered architecture in Spring Boot:

1. **Model Layer**: Defines the entities for the hotel and foodItem items.
2. **Controller Layer**: Manages the API endpoints and user requests.
3. **Service Layer**: Contains business logic such as fetching hotel and foodItem data and calculating prices.
4. **Repository Layer**: Interacts with the database to retrieve and persist hotel and foodItem data.

## Core Functionality

### 1. **Hotel Management**
Each hotel is represented by the following attributes:
- `hotelId`: Unique identifier for the hotel.
- `hotelName`: Name of the hotel.
- `location`: Location of the hotel.
- `foodItems` : One to many mapping with food items

### 2. **Menu Management**
Menu items are stored as entities with the following attributes:
- `itemID`: Unique identifier for the foodItem item.
- `itemName`: Name of the foodItem item.
- `description`: A description of the foodItem item.


