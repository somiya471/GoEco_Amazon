# GoSolo - Amazon GoEco App 🌱

A sustainable e-commerce delivery solution that empowers users to make eco-friendly delivery choices while promoting health and community engagement.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Components](#components)
- [Installation](#installation)
- [Usage](#usage)
- [Success Metrics](#success-metrics)

## Overview

GoEco App is an innovative platform that transforms e-commerce deliveries by introducing sustainable delivery options. Users can select from various eco-friendly delivery methods, including community drop-off points, flexible pickup options, and green last-mile delivery modes like cycling or walking. The app gamifies the sustainable delivery experience by offering EcoPoints and rewards while tracking both environmental impact and health metrics.

## Features

### User App
- 🗺️ Location-based sustainable delivery options
- 📊 Real-time carbon footprint tracking
- 🏃‍♂️ Health metrics (calories burned) for active delivery choices
- 🎮 Gamification with EcoPoints and leaderboards
- 🗺️ Community drop-off point mapping

### Admin Panel
- 📈 Dashboard for data visualization
- 📊 User engagement metrics
- 🌍 Environmental impact tracking
- 📉 Analytics and reporting

## Architecture

The application consists of three main components:
1. Android Mobile App (User Interface)
2. Backend Server (API Services)
3. Admin Dashboard
4. MongoDB Database

## Tech Stack

### Mobile App
- Java (Android)
- Android SDK

### Backend
- Node.js
- Express.js
- Carbon Footprint API Integration

### Database
- MongoDB


## Installation

### Clone the repository
git clone 

### Navigate to the project directory
cd backend

### Install dependencies
npm install

### Run the app
npm run start

 ### Running the Backend Server
```bash
cd backend
npm run start
```

### Installing the Mobile App
1. Build the APK using Android Studio
2. Install on Android device or emulator
3. For running the app on emulator using local server - change base url to - 10.0.2.2
4. For running the app on actual device using local server - ensure that both are using same network - change base url to network ip address


## API
These are the available API's in the application:

- POST /register
- POST /login
- POST /addpickup
- POST /addproduct
- GET /products
- PUT /user/update
- POST /add/delivery
- GET /deliveries/:userId
- GET /getprofile/:id
- POST /delivery
- PUT /delivery/:id
- GET /getmetrics
- GET /nearby-pickups/:lat/:lon
- GET /leaderboard



## Usage

Firstly, download the zip file, extract it, and open the unzipped folder backend in VScode and run the server.
To use the application, open the unzipped folder in Android Studio or you can git clone. Press **Run** to launch the app.  

1. **Login and Registration**  
   - On opening, the login page appears where you can enter your username and password.  
   - New users must register by providing a username, password, and weight, and granting location access.  

2. **Main Page Navigation**  
   The main page has three sections: **Home**, **Delivery**, and **Profile**.  
   - **Home Page**: View a list of products.  
     - Click **Buy** to go to the confirmation page.  
     - Fill in details, choose delivery options, and select a pickup point based on your location.  
     - The app calculates the distance and shows transport modes with metrics like carbon saved, calories burned, and eco-points.  
     - After confirming the transport mode, view final delivery details, date, and time slot before confirming the order.  
   - **Delivery Page**: View all past orders and their details.  
     - Orders have two options: **Cancel** and **Start**. The **Start** button activates on the delivery date.  
     - Once started, your location is verified, and tracking begins. You can **Start Tracking**, **Stop Tracking**, or **Receive** the delivery using a PIN code for successful completion.  
   - **Profile Page**: View your profile and the leaderboard, which shows the top three users by eco-points and your rank.  

This app provides an eco-friendly shopping experience with detailed metrics and real-time tracking.

## Success Metrics

Track the following metrics to measure app success:

- 📈 User adoption rate
- 🌱 Total carbon emissions saved
- 🏃‍♂️ Cumulative calories burned by users
- ⭐ User engagement and satisfaction
- 📊 Community pickup point utilization
