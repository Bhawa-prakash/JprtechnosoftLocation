package com.sourcey.materiallogindemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sourcey.materiallogindemo.Model.AddTripResponse;
import com.sourcey.materiallogindemo.Model.TripResponse;
import com.sourcey.materiallogindemo.Retrofit.RestClient;
import com.sourcey.materiallogindemo.Utils.LocationPrefs;
import com.sourcey.materiallogindemo.Utils.Utils;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationActivity extends AppCompatActivity {


    private static final String TAG = LocationActivity.class.getSimpleName();

    @BindView(R.id.location_result)
    TextView txtLocationResult;

    @BindView(R.id.updated_on)
    TextView txtUpdatedOn;

    @BindView(R.id.btn_start_location_updates)
    Button btnStartUpdates;

    @BindView(R.id.btn_stop_location_updates)
    Button btnStopUpdates;

    /*@BindView(R.id.btnShow1)
    Button showdata;*/


    private String mLastUpdateTime;


    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;


    private Boolean mRequestingLocationUpdates;
   // private TripResponse tripResponse;
    private int visit_id;
    private String userid;
    private String lattitude;
    private String longitude;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
       /* Realm.init(this);*/
        lattitude = getIntent().getStringExtra("Lattitude");
        longitude = getIntent().getStringExtra("Longitude");
        id = getIntent().getStringExtra("ID");

        userid = LocationPrefs.getString(getApplicationContext(), "loginId");
        visit_id = LocationPrefs.getInt(getApplicationContext(), "userid", 0);
        if (visit_id < 1) {
            visit_id++;
        }


        init();
        /* getTripLocation();*/
        restoreValuesFromBundle(savedInstanceState);

       /* if (getIntent() != null) {
            String Lattitude = getIntent().getStringExtra("Lattitude");
            String Longitude = getIntent().getStringExtra("Longitude");
            String id = getIntent().getStringExtra("ID");
        }*/
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                distanceFromLocation( mCurrentLocation);
            }
        };

        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }

        }

        /* updateLocationOnServer(1, tripResponse.getTripDetail().get(0).getId());*/
    }


    private void updateLocationOnServer(int status, String id) {

        if (mCurrentLocation != null) {

            String visiid = String.valueOf(visit_id);
            String tripid = id;

            String userid = LocationPrefs.getString(getApplicationContext(), "loginId");
            String lattitude = String.valueOf(mCurrentLocation.getLatitude());
            String longitude = String.valueOf(mCurrentLocation.getLongitude());

            RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), lattitude);
            RequestBody statusbody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(status));
            RequestBody longit = RequestBody.create(MediaType.parse("text/plain"), longitude);
            RequestBody userid1 = RequestBody.create(MediaType.parse("text/plain"), userid);
            RequestBody tripid1 = RequestBody.create(MediaType.parse("text/plain"), tripid);
            RequestBody visitid = RequestBody.create(MediaType.parse("text/plain"), visiid);


            Utils.showProgressDialog(this);
            RestClient.AddUser(userid1, tripid1, visitid, lat, longit, statusbody, new Callback<AddTripResponse>() {
                @Override
                public void onResponse(Call<AddTripResponse> call, Response<AddTripResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            /*Utils.displayToast(getApplicationContext(), "Successfuly Add");*/


                            Log.d("MY LOCATION DATA", response.body().getMessage());

                        } else {
                            Log.d("MY LOCATION DATA", response.body().getMessage());
                            /*Toast.makeText(LocationActivity.this, "Failed AddData", Toast.LENGTH_SHORT).show();*/
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddTripResponse> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Utils.displayToast(LocationActivity.this, "Unable to AddData, please try again later");

                }
           /* txtLocationResult.setText(
                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
                            "Lng: " + mCurrentLocation.getLongitude()
            );*/


                /*  *//*Realm realm = Realm.getDefaultInstance();
            final String lattitude = String.valueOf(mCurrentLocation.getLatitude());
            final String longitude = String.valueOf(mCurrentLocation.getLongitude());
            final String time = String.valueOf(mCurrentLocation.getTime());


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    GpsTracker gps = new GpsTracker();
                    gps.setLattitude(lattitude);
                    gps.setLongitude(longitude);
                    gps.setTime(time);
                    realm.insertOrUpdate(gps);


                }*//*
            });*/


           /* // giving a blink animation on TextView
            txtLocationResult.setAlpha(0);
            txtLocationResult.animate().alpha(1).setDuration(300);

            // location last updated time
          *//*  txtUpdatedOn.setText("Last updated on: " + mLastUpdateTime);*//*
        }*/

      /*  toggleButtons();
    }

*/
            });

        }


    }

   /* private void getTripLocation() {

        Utils.showProgressDialog(this);
        RestClient.tripRespons(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {

                Utils.dismissProgressDialog();

                if (response.body() != null) {
                    if (response.body() != null) {


                        tripResponse = response.body();

                    } else {
                        Toast.makeText(LocationActivity.this, "Failed AddData", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(LocationActivity.this, "Unable to AddData, please try again later");

            }
        });

    }*/


    private void distanceFromLocation(Location mCurrentLocation) {


        if (mCurrentLocation != null && !TextUtils.isEmpty(lattitude) && !TextUtils.isEmpty(longitude)) {
            LatLng myLat = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            Location triplocation = new Location("Two");
            triplocation.setLatitude(Double.parseDouble(lattitude));
            triplocation.setLongitude(Double.parseDouble(longitude));

            float distanceBetweenLocation = mCurrentLocation.distanceTo(triplocation);

            if (distanceBetweenLocation < 50) {

                updateLocationOnServer(1, id);
                Toast.makeText(getApplicationContext(), "Trip tracking started", Toast.LENGTH_SHORT).show();

            } else {
                updateLocationOnServer(2, id);
                LocationPrefs.putInt(getApplicationContext(), userid, visit_id + 1);
                visit_id++;


                Toast.makeText(getApplicationContext(), "Trip tracking end:" + visit_id, Toast.LENGTH_SHORT).show();
            }

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    private void toggleButtons() {
        if (mRequestingLocationUpdates) {
            btnStartUpdates.setEnabled(false);
            btnStopUpdates.setEnabled(true);
        } else {
            btnStartUpdates.setEnabled(true);
            btnStopUpdates.setEnabled(false);
        }
    }


    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "GpsTracker settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {

                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(LocationActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "GpsTracker settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(LocationActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }


                    }
                });
    }

    @OnClick(R.id.btn_start_location_updates)
    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @OnClick(R.id.btn_stop_location_updates)
    public void stopLocationButtonClick() {
        mRequestingLocationUpdates = false;
        stopLocationUpdates();
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "GpsTracker updates stopped!", Toast.LENGTH_SHORT).show();
                        toggleButtons();
                    }
                });
    }

    @OnClick(R.id.btn_get_last_location)
    public void showLastKnownLocation() {
        if (mCurrentLocation != null) {
            Toast.makeText(getApplicationContext(), "Lat: " + mCurrentLocation.getLatitude()
                    + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
        }
    }

    /* @OnClick(R.id.btnShow1)
     public void showdata() {

         Realm realm = Realm.getDefaultInstance();
         realm.executeTransaction(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {

                 RealmResults<GpsTracker> realmResults = realm.where(GpsTracker.class).findAll();
                 if ((!realmResults.isEmpty())) {
                     for (GpsTracker gpsTracker : realmResults) {
                         Log.d("Location Data", gpsTracker.getLattitude() + " " + gpsTracker.getLongitude() + " " + gpsTracker.getTime());
                         Toast.makeText(LocationActivity.this, gpsTracker.getLattitude() + " " + gpsTracker.getLongitude() + " " + gpsTracker.getTime(), Toast.LENGTH_SHORT).show();
                     }

                     Toast.makeText(LocationActivity.this, "Check Your Logcat", Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(LocationActivity.this, "No Data", Toast.LENGTH_SHORT).show();

                 }
             }
         });
     }
 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }


    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {

            stopLocationUpdates();
        }
    }
}

/*
public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        if (status == 0) {
            lat1 = location.getLatitude();
            lon1 = location.getLongitude();
        } else if ((status % 2) != 0) {
            lat2 = location.getLatitude();
            lon2 = location.getLongitude();
            distance += distanceBetweenTwoPoint(lat1, lon1, lat2, lon2);
        } else if ((status % 2) == 0) {
            lat1 = location.getLatitude();
            lon1 = location.getLongitude();
            distance += distanceBetweenTwoPoint(lat2, lon2, lat1, lon1);
        }
        status++;
        UIhandler.postDelayed(sendUpdatesToUI, 0);
    }
*/
