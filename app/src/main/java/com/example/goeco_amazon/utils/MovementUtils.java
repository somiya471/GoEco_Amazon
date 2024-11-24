package com.example.goeco_amazon.utils;


import com.example.goeco_amazon.service.MovementClassifier;

public class MovementUtils {

    // Detect movement type based on acceleration and rotation values
    public static MovementClassifier.MovementType detectMovementType(float[] accelerometerReading, float[] gyroscopeReading) {
        // Calculate magnitude of acceleration
        float accelerationMagnitude = (float) Math.sqrt(
                accelerometerReading[0] * accelerometerReading[0] +
                        accelerometerReading[1] * accelerometerReading[1] +
                        accelerometerReading[2] * accelerometerReading[2]
        );

        // Calculate rotation rate from gyroscope
        float rotationMagnitude = (float) Math.sqrt(
                gyroscopeReading[0] * gyroscopeReading[0] +
                        gyroscopeReading[1] * gyroscopeReading[1] +
                        gyroscopeReading[2] * gyroscopeReading[2]
        );

        // Implement movement type detection logic
        if (accelerationMagnitude < 1.0) {
            return MovementClassifier.MovementType.STATIONARY;
        } else if (accelerationMagnitude > 1.0 && accelerationMagnitude < 10.0 && rotationMagnitude < 1.0) {
            return MovementClassifier.MovementType.WALKING;
        } else if (accelerationMagnitude > 10.0 && rotationMagnitude > 1.0 && rotationMagnitude < 3.0) {
            return MovementClassifier.MovementType.CYCLING;
        } else if (accelerationMagnitude > 15.0 && rotationMagnitude > 3.0) {
            return MovementClassifier.MovementType.DRIVING;
        }

        // Default to stationary if no match
        return MovementClassifier.MovementType.STATIONARY;
    }
}

