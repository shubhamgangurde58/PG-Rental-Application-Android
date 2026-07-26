#  PG Rental Application (Android)

# About the Project

The **PG Rental Application** is an Android application developed as part of my MCA learning journey to simplify the process of finding and managing Paying Guest (PG) accommodations. The application provides separate modules for **PG Owners** and **Students**, allowing owners to manage their PG listings while students can explore available accommodations, make bookings, and share their reviews.

This project helped me gain hands-on experience in Android application development using **Java, XML, SQLite**, and Android components such as Activities, RecyclerView, Intents, and custom adapters.

# Features

# Student Module

* Student Registration
* Student Login
* Browse Available PGs
* View PG Details
* Book a PG
* Submit Reviews
* View Booking Information

# Owner Module

* Owner Registration
* Owner Login
* Add New PG
* Update PG Details
* Manage PG Listings
* View Student Bookings

# Application Features

* User Authentication
* SQLite Database Integration
* RecyclerView for Dynamic Lists
* Intent-Based Navigation
* Custom UI with XML Layouts
* Interactive User Experience

# Technologies Used

| Technology     | Purpose                 |
| -------------- | ----------------------- |
| Java           | Application Logic       |
| XML            | User Interface Design   |
| Android Studio | Development Environment |
| SQLite         | Local Database          |
| RecyclerView   | Display Dynamic Data    |
| Intents        | Activity Navigation     |
| Gradle         | Build Automation        |

# Application Architecture

User
   │
   ▼
Android Activities
   │
   ▼
DAO Layer
   │
   ▼
SQLite Database

The application follows a modular structure where Activities handle the user interface, DAO classes manage database operations, and SQLite stores application data.

# Project Structure

PG-Rental-Application
│
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   ├── activities
│   │   │   │   ├── adapters
│   │   │   │   ├── dao
│   │   │   │   ├── database
│   │   │   │   ├── models
│   │   │   │   └── utilities
│   │   │   ├── res
│   │   │   │   ├── layout
│   │   │   │   ├── drawable
│   │   │   │   ├── values
│   │   │   │   └── mipmap
│   │   │   └── AndroidManifest.xml
│
├── gradle
├── build.gradle
├── settings.gradle
└── README.md

# Application Workflow

# Student Workflow

1. Register a new account.
2. Login to the application.
3. Browse available PG accommodations.
4. View detailed PG information.
5. Book a suitable PG.
6. Submit reviews after booking.

# Owner Workflow

1. Register as a PG Owner.
2. Login to the application.
3. Add new PG details.
4. Manage existing PG listings.
5. View booking requests.

# Android Concepts Covered

* Android Activities
* XML Layout Design
* Intents
* RecyclerView
* SQLite Database
* DAO Pattern
* CRUD Operations
* Form Validation
* Event Handling
* Object-Oriented Programming (OOP)

# How to Run

1. Clone this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync all dependencies.
4. Build the project.
5. Run the application on an Android Emulator or Physical Device.

# Learning Outcomes

Through this project, I gained practical experience in:

* Android Application Development
* Java Programming
* SQLite Database Management
* CRUD Operations
* RecyclerView Implementation
* Android Navigation using Intents
* User Authentication
* Mobile UI Design
* Project Organization

# Future Enhancements

* Firebase Authentication
* Cloud Database Integration
* Google Maps for PG Location
* Online Payment Gateway
* Image Upload Support
* Push Notifications
* Favorite PG List
* Search & Filter Options
* Dark Mode Support
* REST API Integration using Spring Boot

# Author

**Shubham Santosh Gangurde**

**MCA Student**
Aspiring Java Full Stack Developer

**GitHub:** https://github.com/shubhamgangurde58

# Note

This project was developed as part of my MCA learning journey to explore Android application development using Java and SQLite. It demonstrates my understanding of mobile application architecture, local database management, and Android UI development.

I plan to continue enhancing this project by integrating modern Android development practices and backend services.

 **If you found this project useful, please consider giving it a Star on GitHub.**
