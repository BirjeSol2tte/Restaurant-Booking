# Restaurant Booking System

A full-stack restaurant reservation system that allows customers to view a restaurant floor plan, receive intelligent table recommendations, make reservations, and manage their bookings.

The application includes a **visual floor plan**, **smart recommendation logic**, **reservation lookup**, and **Dish of the Day integration based on the reservation date**.

---

# Setup

## Requirements

* Java **21+**
* Node.js **18+**
* PostgreSQL **14+**
* Maven (or use the included Maven wrapper)

---

## 1. Clone the Repository

```bash
git clone https://github.com/BirjeSol2tte/Restaurant-Booking.git
cd restaurant-booking
```

---

## 2. Setup PostgreSQL

Create the database:

```sql
CREATE DATABASE restaurant_booking;
```

Update backend configuration if necessary:

`backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/restaurant_booking
    username: postgres
    password: your_password
```

---

## 3. Run Backend

Navigate to the backend folder:

```bash
cd backend
```

Start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

Backend runs at:

```
http://localhost:8080
```

---

## 4. Run Frontend

Navigate to the frontend folder:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Run the development server:

```bash
npm run dev
```

Frontend runs at:

```
http://localhost:5173
```

---

# Features

## Visual Restaurant Floor Plan

The system displays a visual layout of the restaurant including multiple seating areas:

* Main Hall
* Quiet Area
* Patio
* Party Room
* Private Room
* Reception Area

Tables are positioned using coordinates and displayed on a floor plan to simulate a real restaurant layout.

---

## Smart Table Recommendation

The system recommends the best table based on:

* group size
* reservation time
* table capacity
* table availability
* selected area preference
* seating efficiency

The recommendation algorithm prioritizes tables that minimize unused seats while respecting zone preferences.

---

## Reservation System

Customers can:

* create reservations
* receive a unique reservation code
* look up their reservation
* cancel reservations
* update meal choices after booking

Reservations include:

* table assignment
* start time
* end time
* optional dish of the day

---

## Dish of the Day

The restaurant offers a rotating **Dish of the Day**.

Important behavior:

* The dish is determined by the **reservation date**, not the current date.
* Customers can include or remove the dish when creating or managing reservations.

Example dishes:

* Salmon Soup
* Chicken Pasta
* Mushroom Risotto
* Beef Stew
* Grilled Salmon
* BBQ Pork Ribs
* Roast Chicken

---

## Reservation Lookup

Customers can manage reservations using their reservation code.

Available actions:

* view reservation details
* see assigned table and area
* see dish of the day selection
* update dish choice
* cancel reservation

---

## Menu Page

The menu page integrates with **TheMealDB API** to display additional dishes.

This allows the system to present a dynamic menu without maintaining a large internal menu database.

---

# Architecture

The backend follows a layered architecture:

```
Controller → Service → Repository → Database
```

### Controllers

Handle HTTP requests and return API responses.

### Services

Contain the core business logic such as reservation validation and recommendation scoring.

### Repositories

Use Spring Data JPA to interact with PostgreSQL.

### Database

Stores restaurant tables and reservations.

---

# Technology Stack

## Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Maven

## Frontend

* React
* Vite
* Axios
* CSS

## External API

* TheMealDB

---

# Recommendation Algorithm

The recommendation engine works as follows:

1. Filter tables by **capacity**
2. Remove tables with **overlapping reservations**
3. Apply optional **zone filters**
4. Score remaining tables using:

   * seating efficiency
   * zone suitability
   * group size compatibility
5. Return the highest scoring table

This ensures small groups are not assigned unnecessarily large tables while large groups are directed to suitable areas.

---

# Design Decisions

Several design decisions were made to keep the system realistic while remaining manageable for a single project:

* Reservation codes were used instead of full user authentication to simplify reservation lookup.
* Table coordinates are stored in the database to support a visual floor plan.
* Dish of the Day is tied to reservation date to ensure consistency.
* PostgreSQL was chosen instead of in-memory databases to better simulate production conditions.

---

# Future Improvements

Potential future improvements include:

* administrator floor plan editor
* user accounts and authentication
* table merging for large groups
* improved menu categorization
* reservation editing (time changes)
* real-time availability updates

---

# Author

**Birje Solätte**

Restaurant Booking System
Created as a full-stack internship project.
