package com.example.goeco_amazon.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.goeco_amazon.ActivityTransitionReceiver;
import com.example.goeco_amazon.MainActivity;
import com.example.goeco_amazon.R;
import com.example.goeco_amazon.adapters.MetricsRecyclerAdapter;
import com.example.goeco_amazon.interfaces.MetricsCardOnClick;
import com.example.goeco_amazon.models.ActivitySession;
import com.example.goeco_amazon.models.ActivitySummary;
import com.example.goeco_amazon.models.SoloMetricsData;
import com.example.goeco_amazon.models.UpdateDelivery;
import com.example.goeco_amazon.models.UpdateUser;
import com.example.goeco_amazon.models.UserLogin;
import com.example.goeco_amazon.responsemodels.LoginResponse;
import com.example.goeco_amazon.responsemodels.MetricsData;
import com.example.goeco_amazon.responsemodels.SoloMetricsResponse;
import com.example.goeco_amazon.responsemodels.UpdateDeliveryResponse;
import com.example.goeco_amazon.responsemodels.UpdateUserResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.GetMetricsViewModel;
import com.example.goeco_amazon.viewmodels.LoginUserViewModel;
import com.example.goeco_amazon.viewmodels.SoloMetricsViewModel;
import com.example.goeco_amazon.viewmodels.UpdateDeliveryViewModel;
import com.example.goeco_amazon.viewmodels.UserUpdateViewModel;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovementActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ACTIVITY_RECOGNITION = 100;
    private static final String TAG = "MainActivity";

    private TextView activityTextView,pinCodeDisplay;
    Button receiveButton;
    private TextView summaryTextView,userlocation,pickuplocation,distance;
    private Button startStopButton;
    SoloMetricsViewModel viewModel;

    UserUpdateViewModel userUpdateViewModel;
    UpdateDeliveryViewModel updateDeliveryViewModel;
    double lat,lon;
    private boolean isTracking = false;
    private ActivitySession currentSession;
    private List<ActivitySession> activitySessions;
    private long trackingStartTime;
    private String predominantMovement;
    private double latitude = 70.6754, longitude = 50.6785;
    private LocationManager locationManager;
    double distancecal = 0.0;
    LoginManager loginManager;
    String deliveryid;
    int ecopoints;
    double carbonsaved,caloriesburned;

    private ActivityRecognitionClient client;
    private PendingIntent pendingIntent;

    private Map<String, ActivityStats> activityStatsMap = new HashMap<>();

    // Inner class to store activity statistics
    private static class ActivityStats {
        int occurrences;
        long totalDuration;
        long lastStartTime;

        ActivityStats() {
            this.occurrences = 0;
            this.totalDuration = 0;
            this.lastStartTime = 0;
        }

        void update(int newOccurrences, long newDuration) {
            this.occurrences = newOccurrences;
            this.totalDuration = newDuration;
        }
    }

    // Update the activity update receiver
    private final BroadcastReceiver activityUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String activity = intent.getStringExtra("activity");
            String transition = intent.getStringExtra("transition");
            String timestamp = intent.getStringExtra("timestamp");
            long timeInMillis = intent.getLongExtra("timeInMillis", System.currentTimeMillis());

            // Get statistics for current activity
            int occurrences = intent.getIntExtra("occurrences", 0);
            long totalDuration = intent.getLongExtra("totalDuration", 0);

            // Update activity stats map
            ActivityStats stats = activityStatsMap.computeIfAbsent(activity, k -> new ActivityStats());
            stats.update(occurrences, totalDuration);

            // Get overall statistics
            String[] allActivities = intent.getStringArrayExtra("allActivities");
            Long[] allDurations = (Long[]) intent.getSerializableExtra("allDurations");
            Integer[] allOccurrences = (Integer[]) intent.getSerializableExtra("allOccurrences");

            if (allActivities != null && allDurations != null && allOccurrences != null) {
                for (int i = 0; i < allActivities.length; i++) {
                    String activityType = allActivities[i];
                    ActivityStats activityStats = activityStatsMap.computeIfAbsent(
                            activityType, k -> new ActivityStats());
                    activityStats.update(allOccurrences[i], allDurations[i]);
                }
            }

            if (activity != null) {
                updateActivityUI(activity + " (" + transition + ") at " + timestamp);

                if (isTracking) {
                    if ("Started".equals(transition)) {
                        // End previous session if exists
                        if (currentSession != null) {
                            currentSession.setEndTime(timeInMillis);
                            activitySessions.add(currentSession);
                        }
                        // Start new session
                        currentSession = new ActivitySession(activity, timeInMillis);
                    } else if ("Stopped".equals(transition)) {
                        if (currentSession != null && currentSession.getActivityType().equals(activity)) {
                            currentSession.setEndTime(timeInMillis);
                            activitySessions.add(currentSession);
                            currentSession = null;
                        }
                    }
                    // Update summary in real-time
                    updateActivitySummary();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movement);

        activityTextView = findViewById(R.id.activityTextView);
        summaryTextView = findViewById(R.id.summaryTextView);
        startStopButton = findViewById(R.id.startStopButton);
        activitySessions = new ArrayList<>();
        receiveButton = findViewById(R.id.receiveButton);
        pinCodeDisplay = findViewById(R.id.pinCodeDisplay);
        userlocation = findViewById(R.id.userlocation);
        pickuplocation = findViewById(R.id.pickuplocation);
        loginManager = new LoginManager(this);
        distance = findViewById(R.id.distance);

        lat = getIntent().getDoubleExtra("latitude",0.0);
        lon = getIntent().getDoubleExtra("longtitude",0.0);
        deliveryid = getIntent().getStringExtra("id");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Request Location Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLocation();
            userlocation.setText("User - Latitude: "+loginManager.getLatitude()+" Longitude: "+loginManager.getLongitude());
            pickuplocation.setText("Pickup - Latitude: "+lat+" Longitude: "+lon);


        }
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel = new ViewModelProvider(MovementActivity.this).get(SoloMetricsViewModel.class);
                viewModel.getUserLiveData().observe(MovementActivity.this, new Observer<SoloMetricsResponse>() {
                    @Override
                    public void onChanged(SoloMetricsResponse response) {
                        if (response!=null) {
                                    ecopoints = response.getEcoPoints();
                                    carbonsaved = response.getCarbon_saved();
                                    caloriesburned = response.getCalories_burned();
                                    distancecal = response.getDistance();
                                    distance.setText("Distance: "+distancecal);
                                    loginManager.setDistance(distancecal);
                                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                                    btnupdate(timeFormat.format(new Date()),ecopoints,carbonsaved,caloriesburned,deliveryid,loginManager.getmode());
                                    btnuserupdate(loginManager.getid(),ecopoints,carbonsaved,caloriesburned);

                                }

                    }
                });
                SoloMetricsData s = new SoloMetricsData(loginManager.getLatitude(),loginManager.getLongitude(),lat,lon,loginManager.getWeight(),loginManager.getmode());
                viewModel.solometrics(MovementActivity.this.getApplication(),s);


            }
        });


        startStopButton.setOnClickListener(v -> {
            if (!isTracking) {
                startTracking();
            } else {
                stopTracking();
                pinCodeDisplay.setText("678123");
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                activityUpdateReceiver,
                new IntentFilter("com.example.moveapp.ACTIVITY_UPDATE")
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void getLocation() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    loginManager.setLatitude(latitude);
                    loginManager.setLongitude(longitude);
                    Toast.makeText(MovementActivity.this,latitude+""+longitude,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(MovementActivity.this, "Enable GPS for location", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(this)
                    .unregisterReceiver(activityUpdateReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Receiver not registered", e);
        }
    }
    private static final String[] REQUIRED_PERMISSIONS = new String[] {
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.POST_NOTIFICATIONS
    };

    private boolean hasRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            for (String permission : REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(this, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    private void startTracking() {
        if (!hasRequiredPermissions()) {
            requestActivityRecognitionPermission();
            return;
        }

        isTracking = true;
        trackingStartTime = System.currentTimeMillis();
        activitySessions.clear();
        activityStatsMap.clear(); // Clear previous statistics
        currentSession = null;
        startStopButton.setText("Stop Tracking");
        startActivityRecognition();
    }

    private void updateActivitySummary() {
        if (isTracking) {
            displayActivitySummary();
        }
    }


    private void stopTracking() {
        if (!hasRequiredPermissions()) {
            Log.e(TAG, "Required permissions not granted");
            Toast.makeText(this, "Required permissions not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (client != null && pendingIntent != null) {
                Task<Void> task = client.removeActivityTransitionUpdates(pendingIntent);
                task.addOnSuccessListener(unused -> {
                    Log.d(TAG, "Activity tracking stopped");
                    isTracking = false;
                    startStopButton.setText("Start Tracking");

                    // End any ongoing session
                    if (currentSession != null) {
                        currentSession.setEndTime(System.currentTimeMillis());
                        activitySessions.add(currentSession);
                        currentSession = null;
                    }

                    displayActivitySummary();
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to stop activity tracking", e);
                    handleActivityRecognitionError(e);
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Security exception while stopping tracking: " + e.getMessage());
            handleActivityRecognitionError(e);
        } catch (Exception e) {
            Log.e(TAG, "Error stopping activity recognition", e);
            handleActivityRecognitionError(e);
        }
    }
    private void btnupdate(String receivetime,int ecopoints, double carbonsaved,double caloriesburned,String id,String mode) {

        UpdateDelivery user = new UpdateDelivery("delivered",receivetime,ecopoints,carbonsaved,caloriesburned,mode);
        initViewModel();
        updateDeliveryViewModel.updatedelivery(this.getApplication(),user,id);
    }

    private void initViewModel(){
        updateDeliveryViewModel = new ViewModelProvider(this).get(UpdateDeliveryViewModel.class);
        updateDeliveryViewModel.getUserLiveData().observe(this, new Observer<UpdateDeliveryResponse>() {
            @Override
            public void onChanged(UpdateDeliveryResponse userResponse) {
                if (userResponse == null) {
                    Toast.makeText(MovementActivity.this, "Register Failure", Toast.LENGTH_SHORT).show();
                } else {
//                    loginManager.settoken(userResponse.getToken());
                    Toast.makeText(MovementActivity.this, "Login Successful"+loginManager.getid(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnuserupdate(String id,int eco,double carbon,double calorie) {

        UpdateUser user = new UpdateUser(id,eco,carbon,calorie);
        initViewModel2();
        userUpdateViewModel.updatedelivery(this.getApplication(),user);
    }

    private void initViewModel2(){
        userUpdateViewModel = new ViewModelProvider(this).get(UserUpdateViewModel.class);
        userUpdateViewModel.getUserLiveData().observe(this, new Observer<UpdateUserResponse>() {
            @Override
            public void onChanged(UpdateUserResponse userResponse) {
                if (userResponse == null) {
                    Toast.makeText(MovementActivity.this, "Register Failure", Toast.LENGTH_SHORT).show();
                } else {
//                    loginManager.settoken(userResponse.getToken());
                    Toast.makeText(MovementActivity.this, "Login Successful"+loginManager.getid(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MovementActivity.this,MainActivity.class);
                    startActivity(i);
                }

            }
        });
    }
    private String analyzePredominantActivity() {
        String predominantActivity = "Unknown";
        double highestScore = 0;

        for (Map.Entry<String, ActivityStats> entry : activityStatsMap.entrySet()) {
            ActivityStats stats = entry.getValue();
            // Calculate score based on both duration and occurrences
            double durationScore = stats.totalDuration / 60000.0; // Convert to minutes
            double occurrenceScore = stats.occurrences * 10; // Weight occurrences
            double totalScore = durationScore + occurrenceScore;

            if (totalScore > highestScore) {
                highestScore = totalScore;
                predominantActivity = entry.getKey();
            }
        }
        loginManager = new LoginManager(this);

        // Update the mode in LoginManager
        if (loginManager != null) {
            loginManager.setmode(predominantActivity);
        }

        return predominantActivity;
    }
    private static class MovementScore {
        private final String movementType;
        private int occurrences;
        private double durationScore;

        public MovementScore(String movementType) {
            this.movementType = movementType;
            this.occurrences = 0;
            this.durationScore = 0;
        }

        public void incrementOccurrence() {
            occurrences++;
        }

        public void addDurationScore(double minutes) {
            durationScore += minutes;
        }

        public double getTotalScore() {
            // Weight occurrences and duration equally
            return (occurrences * 10) + durationScore; // 10 points per occurrence plus 1 point per minute
        }

        public String getMovementType() {
            return movementType;
        }
    }
    private void displayActivitySummary() {
        StringBuilder summaryText = new StringBuilder();
        summaryText.append("Session Summary:\n\n");

        // Sort activities by duration
        List<Map.Entry<String, ActivityStats>> sortedActivities = new ArrayList<>(activityStatsMap.entrySet());
        sortedActivities.sort((a, b) -> Long.compare(b.getValue().totalDuration, a.getValue().totalDuration));

        // Display detailed statistics for each activity
        for (Map.Entry<String, ActivityStats> entry : sortedActivities) {
            String activityType = entry.getKey();
            ActivityStats stats = entry.getValue();
            double durationMinutes = stats.totalDuration / 60000.0; // Convert to minutes

            summaryText.append(activityType).append(":\n");
            summaryText.append("- Occurrences: ").append(stats.occurrences).append("\n");
            summaryText.append(String.format("- Total Duration: %.2f minutes\n", durationMinutes));
            summaryText.append(String.format("- Average Duration: %.2f minutes per occurrence\n",
                    stats.occurrences > 0 ? durationMinutes / stats.occurrences : 0));
            summaryText.append("\n");
        }

        // Find predominant activity based on both duration and occurrences
        String predominantActivity = analyzePredominantActivity();
        summaryText.append("\nPredominant Activity: ").append(predominantActivity);

        summaryTextView.setText(summaryText.toString());
    }

    private void requestActivityRecognitionPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            List<String> permissionsToRequest = new ArrayList<>();

            for (String permission : REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(this, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }

            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(
                        this,
                        permissionsToRequest.toArray(new String[0]),
                        REQUEST_CODE_ACTIVITY_RECOGNITION
                );
            } else {
                startActivityRecognition();
            }
        } else {
            startActivityRecognition();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                REQUEST_CODE_ACTIVITY_RECOGNITION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ACTIVITY_RECOGNITION) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                startActivityRecognition();
            } else {
                Toast.makeText(this,
                        "Required permissions not granted",
                        Toast.LENGTH_SHORT).show();
                isTracking = false;
                startStopButton.setText("Start Tracking");
            }
        }
    }

    private void startActivityRecognition() {
        if (!hasRequiredPermissions()) {
            Log.e(TAG, "Required permissions not granted");
            Toast.makeText(this, "Required permissions not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Log.d(TAG, "Starting activity recognition");
            client = ActivityRecognition.getClient(this);

            Intent intent = new Intent(this, ActivityTransitionReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(
                    this, 0, intent,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                            ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE
                            : PendingIntent.FLAG_UPDATE_CURRENT
            );

            // Create transitions list with explicit permission check
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {

                List<ActivityTransition> transitions = new ArrayList<>();
                transitions.add(new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
                transitions.add(new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
                transitions.add(new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

                ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

                Task<Void> task = client.requestActivityTransitionUpdates(request, pendingIntent);

                task.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Activity updates registered successfully");
                        Toast.makeText(MovementActivity.this,
                                "Activity tracking started",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to register for activity updates", e);
                        handleActivityRecognitionError(e);
                    }
                });
            } else {
                throw new SecurityException("Activity recognition permission not granted");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Security exception: " + e.getMessage());
            handleActivityRecognitionError(e);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error: " + e.getMessage());
            handleActivityRecognitionError(e);
        }
    }
    private void handleActivityRecognitionError(Exception error) {
        String errorMessage = error instanceof SecurityException
                ? "Permission denied for activity recognition"
                : "Failed to start activity recognition: " + error.getMessage();
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void updateActivityUI(final String activity) {
        runOnUiThread(() -> activityTextView.setText("Current Activity: " + activity));
    }

}