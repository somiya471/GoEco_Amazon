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



## Usage

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
