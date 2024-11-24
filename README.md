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
- 📱 QR code/PIN verification for pickup
- 🗺️ Community drop-off point mapping

### Admin Panel
- 📈 Dashboard for data visualization
- 📊 User engagement metrics
- 🌍 Environmental impact tracking
- 📋 Delivery center management
- 📉 Analytics and reporting

## Architecture

The application consists of three main components:
1. Android Mobile App (User Interface)
2. Backend Server (API Services)
3. Admin Dashboard
4. Shared MongoDB Database

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
- AWS DocumentDB (for scaling)

### Cloud & DevOps
- AWS EKS
- AWS Athena
- AWS QuickSight
- Docker

## Components

### 1. Android APK
Location: `/mobile-app`
```bash
# Build APK
./gradlew assembleDebug
```

### 2. Backend Server
Location: `/backend`
```bash
# Install dependencies
npm install

# Start server
npm start
```

### 3. Admin Dashboard
Location: `/admin`
```bash
# Install dependencies
npm install

# Start admin panel
npm start
```

## Installation
The application should now be running on http://localhost:3000.
- For Backend Visit: http://localhost:3000

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

1. Open the application by downloading the zip file and opening the unzipped file in android studio and press run
2. First you will be able to view the login page. (You have to enter your username and password)
![image](https://github.com/user-attachments/assets/2f72e743-f028-4d62-b2b6-af3fc306d433)
4. For new users, you will need to register in the application first before logging in. Enter your username, password, weight. (You will be asked to provide access to your location)
![image](https://github.com/user-attachments/assets/02e35ab4-e11d-4647-8733-7c2684297fbc)
5. Now, you will be able to view the mainpage with three navigations ( home, delivery, profile)
   - Home page: You will be able to view a list of product items that you can buy;
     ![image](https://github.com/user-attachments/assets/89c5088d-2c6b-44d0-8bfe-8cf5bb76e7e2)
     ![image](https://github.com/user-attachments/assets/2b844c8a-2cbb-4c15-b71d-21878a38f351)


   - Now on clicking the buy button u will redirected to confirmation page, you will be asked to fill details, choose delivery options, based on users location a list of pickup points will be displayed from which they can choose, further based on the pickup point distance is calculated between both the cordinates and accordingly in next slide various mode of transport is shown along with details (carbon saved,calorie burned, ecopoints),further on choosing the mode final all details are viewed to user along with delivery date and timeslot then user can confirm delivery.
     ![image](https://github.com/user-attachments/assets/2ebdffcd-2005-44a4-8294-1028ec8f19e1)
![image](https://github.com/user-attachments/assets/3c908595-3021-40a9-8f0c-28f32aed8ec0)

   - Delivery page: You will be able to view the list of delivery or orders u have made so far, so there for each delivery u have the details and u have two buttons (cancel and start) the start button is enabled only on delivery date and time slot given.
     ![image](https://github.com/user-attachments/assets/387a719c-c27c-4e28-9a09-4cbd28761e8b)

   - Once enabled u can click on it, first it will once again track user location to confirm the previous details, based on that again it will display the user location and already chosen pickup point location details and distance between. There will be three buttons, for start tracking button it will start tracking user movement throughout the journey and for stop tracking it will consider all the data tracked so far and take an aggregate of it and predict the final movement. then for receive button the pin code is generated which can be cross checked and further delivery is success and data is changed according to current data( ecopoints, carbon saved, calorie burned)
   - Profile page: you will be able to view ur profile and leaderboard which displays the top 3 users based on descending ecopoints and also your position in the leaderboard
     ![image](https://github.com/user-attachments/assets/4980cbc3-57a6-4175-8c72-89a6af78b68a)



### Running the Backend Server
```bash
cd backend
npm start
```

### Running the Admin Panel
```bash
cd admin
npm start
```

### Installing the Mobile App
1. Build the APK using Android Studio
2. Install on Android device
3. Configure server endpoints in app settings

## Success Metrics

Track the following metrics to measure app success:

- 📈 User adoption rate
- 🌱 Total carbon emissions saved
- 🏃‍♂️ Cumulative calories burned by users
- ⭐ User engagement and satisfaction
- 📊 Community pickup point utilization
