package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ygaps.travelapp.API.AddStopPointRequest;
import com.ygaps.travelapp.API.MessageResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.utils.StopPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddStopPointActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int REQUEST_LOCATION_CODE = 99;
    private static int RESULT_STOP_POINT_INFO = 1;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Marker currentLocationMarker = null;
    private EditText locationSearch;
    private double latitude, longitude;
    private ArrayList<StopPoint> stopPoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop_point);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        locationSearch = findViewById(R.id.stopPointLocationSearch);

        locationSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Searching
                    String searchString = locationSearch.getText().toString();
                    search(searchString);

                    return true;
                }
                return false;
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.stopPointMap);
        mapFragment.getMapAsync(this);

//        Button saveLocationBtn = findViewById(R.id.saveLocationBtn);
//        saveLocationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Move back to create tour screen
//                Intent intent = new Intent();
//                intent.putExtra("LAT", latitude);
//                intent.putExtra("LONG", longitude);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (currentLocationMarker != null)
                        currentLocationMarker.remove();

                    latitude = latLng.latitude;
                    longitude = latLng.longitude;
                    locationSearch.setText("(" + latLng.latitude + '-' + latLng.longitude + ')');
                    currentLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Chosen coordinate")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    // Move to AddStopPoint screen
                    Intent intent = new Intent(getApplication(), StopPointInfoActivity.class);
                    intent.putExtra("LAT", latitude);
                    intent.putExtra("LONG", longitude);
                    startActivityForResult(intent, RESULT_STOP_POINT_INFO);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMap != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mMap.clear();

            // add markers from database to the map

        }
    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    private void search(String searchString) {
        Geocoder geocoder = new Geocoder(AddStopPointActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e("Error", "search: IOException " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d("searching", "search: " + address);

            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            if (currentLocationMarker != null)
                currentLocationMarker.remove();
            currentLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Chosen coordinate")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;

        } else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_STOP_POINT_INFO && resultCode == RESULT_OK && data != null) {
            StopPoint newStopPoint = (StopPoint) data.getSerializableExtra("NEW_STOP_POINT");
//            Log.d("NEW_STOP_POINT", "onActivityResult: " + newStopPoint.toString());
            stopPoints.add(newStopPoint);
            long tourId = getIntent().getLongExtra("TOUR_ID", -1);
            int[] deleteId = new int[0];

            AddStopPointRequest request = new AddStopPointRequest(String.valueOf(tourId), stopPoints, deleteId);
//            Log.d("NEW_STOP_POINT", "onActivityResult: " + request.toString());

            if (addStopPoint(request))
                onResume();
            else onResume();
        }

    }

    public boolean addStopPoint(AddStopPointRequest request) {
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        // Get token
        String token = sharedPreferences.getString("token", "");

        Call<MessageResponse> call = client.addStopPoint(token, request);
        final boolean[] result = {false};
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                MessageResponse message = response.body();

                if (message != null && message.equals("Successful")) {
                    Toast.makeText(getApplicationContext(), "Add stop point successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), AddStopPointActivity.class);
                    startActivity(intent);
                    result[0] = true;
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }
        });

        return result[0];
    }
}
