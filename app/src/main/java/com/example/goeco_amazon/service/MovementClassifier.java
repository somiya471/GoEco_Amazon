package com.example.goeco_amazon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovementClassifier {
    // Enum for movement types
    public enum MovementType {
        STATIONARY,
        WALKING,
        CYCLING,
        E_BIKE,
        DRIVING
    }

    // Movement classification parameters
    private static class MovementParameters {
        float minSpeed;
        float maxSpeed;
        float accelerationThreshold;

        MovementParameters(float minSpeed, float maxSpeed, float accelerationThreshold) {
            this.minSpeed = minSpeed;
            this.maxSpeed = maxSpeed;
            this.accelerationThreshold = accelerationThreshold;
        }
    }

    // Predefined speed and acceleration thresholds for different movement types
    private static final Map<MovementType, MovementParameters> MOVEMENT_THRESHOLDS = new HashMap<>() {{
        put(MovementType.WALKING, new MovementParameters(0.5f, 6.5f, 2.0f));
        put(MovementType.CYCLING, new MovementParameters(6.5f, 25.0f, 3.0f));
        put(MovementType.E_BIKE, new MovementParameters(15.0f, 35.0f, 4.0f));
        put(MovementType.DRIVING, new MovementParameters(25.0f, 180.0f, 5.0f));
    }};

    // Data point for tracking movement characteristics
    public static class MovementDataPoint {
        public float speed;
        public float[] acceleration;
        public long timestamp;
        public double latitude;
        public double longitude;

        public MovementDataPoint(float speed, float[] acceleration, long timestamp, double latitude, double longitude) {
            this.speed = speed;
            this.acceleration = acceleration;
            this.timestamp = timestamp;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    /**
     * Classify the primary mode of transportation for the entire journey
     *
     * @param movementDataPoints List of movement data points throughout the journey
     * @return The most likely primary mode of transportation
     */
    public MovementType classifyJourneyMovement(List<MovementDataPoint> movementDataPoints) {
        if (movementDataPoints == null || movementDataPoints.isEmpty()) {
            return MovementType.STATIONARY;
        }

        // Count of times each movement type is detected
        Map<MovementType, Integer> movementTypeCount = new HashMap<>();

        // Analyze each data point
        for (MovementDataPoint dataPoint : movementDataPoints) {
            MovementType detectedType = detectMovementType(dataPoint);
            movementTypeCount.put(
                    detectedType,
                    movementTypeCount.getOrDefault(detectedType, 0) + 1
            );
        }

        // Determine primary movement type
        return determinePredominantMovementType(movementTypeCount, movementDataPoints.size());
    }

    /**
     * Detect movement type for a single data point
     */
    private MovementType detectMovementType(MovementDataPoint dataPoint) {
        // Calculate acceleration magnitude
        float accelerationMagnitude = calculateAccelerationMagnitude(dataPoint.acceleration);

        // Check each movement type's parameters
        for (Map.Entry<MovementType, MovementParameters> entry : MOVEMENT_THRESHOLDS.entrySet()) {
            MovementType type = entry.getKey();
            MovementParameters params = entry.getValue();

            // Check if speed and acceleration match the movement type
            if (dataPoint.speed >= params.minSpeed &&
                    dataPoint.speed <= params.maxSpeed &&
                    accelerationMagnitude <= params.accelerationThreshold) {
                return type;
            }
        }

        // Default to stationary if no match
        return MovementType.STATIONARY;
    }

    /**
     * Calculate the magnitude of acceleration
     */
    private float calculateAccelerationMagnitude(float[] acceleration) {
        return (float) Math.sqrt(
                acceleration[0] * acceleration[0] +
                        acceleration[1] * acceleration[1] +
                        acceleration[2] * acceleration[2]
        );
    }

    /**
     * Determine the predominant movement type based on count and total data points
     */
    private MovementType determinePredominantMovementType(
            Map<MovementType, Integer> movementTypeCount,
            int totalDataPoints
    ) {
        // If no movement detected, return stationary
        if (movementTypeCount.isEmpty()) {
            return MovementType.STATIONARY;
        }

        // Find the movement type with the highest count
        MovementType primaryMovement = MovementType.STATIONARY;
        float highestPercentage = 0;

        for (Map.Entry<MovementType, Integer> entry : movementTypeCount.entrySet()) {
            float percentage = (entry.getValue() * 100f) / totalDataPoints;

            if (percentage > highestPercentage) {
                highestPercentage = percentage;
                primaryMovement = entry.getKey();
            }
        }

        // Optional: Add a threshold to ensure reliability
        return (highestPercentage > 30) ? primaryMovement : MovementType.STATIONARY;
    }

    /**
     * Comprehensive method to collect and classify movement data
     */
    public static class MovementTracker {
        private List<MovementDataPoint> dataPoints = new ArrayList<>();
        private double lastLatitude;
        private double lastLongitude;

        // Add a data point during journey
        public void addDataPoint(float speed, float[] acceleration, long timestamp, double latitude, double longitude) {
            lastLatitude = latitude;
            lastLongitude = longitude;
            dataPoints.add(new MovementDataPoint(speed, acceleration, timestamp, latitude, longitude));
        }

        // Get the primary movement type at the end of journey
        public MovementType getFinalMovementType() {
            MovementClassifier classifier = new MovementClassifier();
            return classifier.classifyJourneyMovement(dataPoints);
        }

        // Get the last known latitude
        public double getLastLatitude() {
            return lastLatitude;
        }

        // Get the last known longitude
        public double getLastLongitude() {
            return lastLongitude;
        }

        // Clear collected data
        public void reset() {
            dataPoints.clear();
        }
    }
}
