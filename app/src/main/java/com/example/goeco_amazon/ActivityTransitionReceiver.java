package com.example.goeco_amazon;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityTransitionReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "activity_transition_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "ActivityTransitionReceiver";

    // Static map to store activity durations across receiver instances
    private static final Map<String, ActivityStats> activityStats = new HashMap<>();
    private static String currentActivity = null;
    private static long lastTransitionTime = 0;

    // Inner class to track statistics for each activity type
    private static class ActivityStats {
        int occurrences = 0;
        long totalDuration = 0;
        long lastStartTime = 0;

        void addOccurrence() {
            occurrences++;
        }

        void startActivity() {
            lastStartTime = System.currentTimeMillis();
            addOccurrence();
        }

        void endActivity() {
            if (lastStartTime > 0) {
                totalDuration += System.currentTimeMillis() - lastStartTime;
                lastStartTime = 0;
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("ActivityTest", "Receiver got intent: " + intent.getAction());

            if (!ActivityTransitionResult.hasResult(intent)) {
                Log.d(TAG, "No activity transition result");
                return;
            }

            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            if (result != null) {
                for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                    String activityName = getActivityName(event.getActivityType());
                    String transitionName = getTransitionName(event.getTransitionType());
                    long timeInMillis = System.currentTimeMillis();

                    // Update activity statistics
                    updateActivityStats(activityName, event.getTransitionType(), timeInMillis);

                    String timestamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                            .format(new Date(timeInMillis));

                    Log.d(TAG, "Activity: " + activityName + ", Transition: " + transitionName +
                            ", Time: " + timestamp);

                    // Show notification
                    showNotification(context, "Activity Detection",
                            "Detected " + activityName + " (" + transitionName + ") at " + timestamp);

                    // Send detailed update to MovementActivity
                    sendActivityUpdate(context, activityName, transitionName, timestamp, timeInMillis);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing activity transition: " + e.getMessage(), e);
        }
    }

    private void showNotification(Context context, String title, String content) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        try {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            } else {
                Log.w(TAG, "Notification permission not granted");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Security exception when showing notification: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Error showing notification: " + e.getMessage());
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Activity Transitions";
            String descriptionText = "Shows detected activity transitions";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void updateActivityStats(String activityName, int transitionType, long timeInMillis) {
        ActivityStats stats = activityStats.computeIfAbsent(activityName, k -> new ActivityStats());

        if (transitionType == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
            stats.startActivity();
            if (currentActivity != null && !currentActivity.equals(activityName)) {
                // End previous activity
                ActivityStats prevStats = activityStats.get(currentActivity);
                if (prevStats != null) {
                    prevStats.endActivity();
                }
            }
            currentActivity = activityName;
            lastTransitionTime = timeInMillis;
        } else if (transitionType == ActivityTransition.ACTIVITY_TRANSITION_EXIT) {
            stats.endActivity();
            if (currentActivity != null && currentActivity.equals(activityName)) {
                currentActivity = null;
            }
        }
    }
    private void sendActivityUpdate(Context context, String activityName, String transitionName,String timestamp, long timeInMillis) {
        Intent updateIntent = new Intent("com.example.moveapp.ACTIVITY_UPDATE");
        updateIntent.putExtra("activity", activityName);
        updateIntent.putExtra("transition", transitionName);
        updateIntent.putExtra("timestamp", timestamp);
        updateIntent.putExtra("timeInMillis", timeInMillis);

        // Add statistics
        ActivityStats stats = activityStats.get(activityName);
        if (stats != null) {
            updateIntent.putExtra("occurrences", stats.occurrences);
            updateIntent.putExtra("totalDuration", stats.totalDuration);
        }

        // Calculate and add overall statistics
        Map<String, Long> durationMap = new HashMap<>();
        Map<String, Integer> occurrenceMap = new HashMap<>();
        for (Map.Entry<String, ActivityStats> entry : activityStats.entrySet()) {
            durationMap.put(entry.getKey(), entry.getValue().totalDuration);
            occurrenceMap.put(entry.getKey(), entry.getValue().occurrences);
        }

        // Convert maps to arrays for intent extras
        updateIntent.putExtra("allActivities", durationMap.keySet().toArray(new String[0]));
        updateIntent.putExtra("allDurations", durationMap.values().toArray(new Long[0]));
        updateIntent.putExtra("allOccurrences", occurrenceMap.values().toArray(new Integer[0]));

        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);
    }


    private String getActivityName(int type) {
        switch (type) {
            case DetectedActivity.IN_VEHICLE:
                return "car";
            case DetectedActivity.ON_BICYCLE:
                return "cycling";
            case DetectedActivity.ON_FOOT:
                return "On Foot";
            case DetectedActivity.RUNNING:
                return "running";
            case DetectedActivity.STILL:
                return "still";
            case DetectedActivity.TILTING:
                return "Tilting";
            case DetectedActivity.WALKING:
                return "walking";
            case DetectedActivity.UNKNOWN:
                return "Unknown";
            default:
                return "Unrecognized(" + type + ")";
        }
    }

    private String getTransitionName(int transitionType) {
        switch (transitionType) {
            case ActivityTransition.ACTIVITY_TRANSITION_ENTER:
                return "Started";
            case ActivityTransition.ACTIVITY_TRANSITION_EXIT:
                return "Stopped";
            default:
                return "Unknown Transition(" + transitionType + ")";
        }
    }
}
