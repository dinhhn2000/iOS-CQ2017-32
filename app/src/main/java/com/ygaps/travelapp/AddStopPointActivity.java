package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
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
import com.ygaps.travelapp.API.Requests.AddStopPointRequest;
import com.ygaps.travelapp.API.Requests.SuggestedStopPointRequest;
import com.ygaps.travelapp.API.Responses.MessageResponse;
import com.ygaps.travelapp.API.Responses.SuggestedStopPointResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.Responses.getTourInfoResponse;
import com.ygaps.travelapp.utils.Coordinate;
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
        GoogleMap.OnMarkerClickListener,
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
    private ArrayList<StopPoint> suggestedStopPoints = new ArrayList<>();

    private PopupWindow mPopupWindow;

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
            mMap.setOnMarkerClickListener(this);

            getStopPoints();

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (currentLocationMarker != null)
                        currentLocationMarker.remove();

                    latitude = latLng.latitude;
                    longitude = latLng.longitude;
//                    locationSearch.setText("(" + latLng.latitude + '-' + latLng.longitude + ')');
                    currentLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Chosen coordinate")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    popupStopPoint(false, false);
                }
            });
        }

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMap != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mMap.clear();

            // add  markers from database to the map
            // Personal stop points
            for (int i = 0; i < stopPoints.size(); i++) {
                LatLng latLng = new LatLng(stopPoints.get(i).getLat(), stopPoints.get(i).getLong());
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(stopPoints.get(i).getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            }

            // Suggested stop points
            int count = 0;
            for (int i = 0; i < suggestedStopPoints.size(); i++) {
                if (count > 10) break;
                LatLng latLng = new LatLng(suggestedStopPoints.get(i).getLat(), suggestedStopPoints.get(i).getLong());
                if (findPersonalTourByLatLong(latLng) == null) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(suggestedStopPoints.get(i).getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    count++;
                }
            }

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
//        Toast.makeText(getApplicationContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
        latitude = latLng.latitude;
        longitude = latLng.longitude;

        boolean isSuggested = true;
        if (findPersonalTourByLatLong(latLng) != null)
            isSuggested = false;

        popupStopPoint(isSuggested, true);
        return false;
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

            popupStopPoint(false, false);

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

        getSuggestedStopPoints();

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
            ArrayList<StopPoint> setStopPoints = new ArrayList<>();
            setStopPoints.add(newStopPoint);
            long tourId = getIntent().getLongExtra("TOUR_ID", -1);
            int[] deleteId = new int[0];

            AddStopPointRequest request = new AddStopPointRequest(String.valueOf(tourId), setStopPoints, deleteId);
//            Log.d("NEW_STOP_POINT", "onActivityResult: " + request.toString());

            setStopPoint(request);
        }

    }

    public boolean setStopPoint(AddStopPointRequest request) {
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        // Get token
        String token = sharedPreferences.getString("token", "");

        Call<MessageResponse> call = client.setStopPoint(token, request);
        final boolean[] result = {false};
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                MessageResponse message = response.body();

                if (message != null && message.getMessage().equals("Successful")) {
                    Toast.makeText(getApplicationContext(), "Add stop point successful", Toast.LENGTH_SHORT).show();
                    result[0] = true;
                    getStopPoints();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }
        });

        return result[0];
    }

    public boolean getStopPoints() {
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        // Get token
        String token = sharedPreferences.getString("token", "");

        long tourId = getIntent().getLongExtra("TOUR_ID", -1);
//        Log.d("Tour_info_tour_id", "getStopPoints: " + tourId + '/' + token);
        Call<getTourInfoResponse> call = client.getTour(token, tourId);
        final boolean[] result = {false};
        call.enqueue(new Callback<getTourInfoResponse>() {
            @Override
            public void onResponse(Call<getTourInfoResponse> call, Response<getTourInfoResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                getTourInfoResponse res = response.body();

                if (res != null) {
//                    Toast.makeText(getApplicationContext(), "Get tour info successful", Toast.LENGTH_SHORT).show();
//                    Log.d("Tour_info", "onResponse: "+ res.toString());
                    stopPoints = res.getStopPoints();
                    result[0] = true;
                    onResume();
                }
            }

            @Override
            public void onFailure(Call<getTourInfoResponse> call, Throwable t) {
            }
        });

        return result[0];
    }

    public boolean getSuggestedStopPoints() {
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        // Get token
        String token = sharedPreferences.getString("token", "");

        SuggestedStopPointRequest request = new SuggestedStopPointRequest(true, new Coordinate(latitude, longitude));
        Call<SuggestedStopPointResponse> call = client.getSuggestedStopPoint(token, request);

        final boolean[] result = {false};
        call.enqueue(new Callback<SuggestedStopPointResponse>() {
            @Override
            public void onResponse(Call<SuggestedStopPointResponse> call, Response<SuggestedStopPointResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                SuggestedStopPointResponse suggestedStopPointResponse = response.body();

                if (suggestedStopPointResponse != null) {
//                    Toast.makeText(getApplicationContext(), "Get tour info successful", Toast.LENGTH_SHORT).show();
//                    Log.d("Tour_info", "onResponse: "+ res.toString());
                    suggestedStopPoints = suggestedStopPointResponse.getStopPoints();
                    result[0] = true;
                    onResume();
                }
            }

            @Override
            public void onFailure(Call<SuggestedStopPointResponse> call, Throwable t) {
            }
        });

        return result[0];
    }

    public boolean deleteStopPoint(int id) {
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        // Get token
        String token = sharedPreferences.getString("token", "");

        long tourId = getIntent().getLongExtra("TOUR_ID", -1);

        AddStopPointRequest request = new AddStopPointRequest(String.valueOf(tourId),new ArrayList<StopPoint>(0), new int[]{id});

        Call<MessageResponse> call = client.setStopPoint(token, request);
        final boolean[] result = {false};
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                MessageResponse message = response.body();

                if (message != null && message.getMessage().equals("Successful")) {
                    Toast.makeText(getApplicationContext(), "Delete successful", Toast.LENGTH_SHORT).show();
                    result[0] = true;
                    getStopPoints();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }
        });

        return result[0];
    }

    public void popupStopPoint(final boolean isSuggested, final boolean isUpdate) {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.stop_point_popup, null);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        mPopupWindow.setAnimationStyle(R.style.Animation);
        mPopupWindow.setFocusable(true);

        TextView latPopup = customView.findViewById(R.id.stopPointLatTV);
        TextView longPopup = customView.findViewById(R.id.stopPointLongTV);

        latPopup.setText(String.valueOf(latitude));
        longPopup.setText(String.valueOf(longitude));

        ImageButton closeBtn = customView.findViewById(R.id.closeStopPointPopup);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        Button createStopPointBtn = customView.findViewById(R.id.createStopPointBtn);
        createStopPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to AddStopPoint screen
//                    Log.d("Stop_point_long_click", "onMapLongClick: " + latitude + '/' + longitude);
                Intent intent = new Intent(getApplication(), StopPointInfoActivity.class);
                intent.putExtra("LAT", latitude);
                intent.putExtra("LONG", longitude);
                intent.putExtra("IS_UPDATE", isUpdate);
                startActivityForResult(intent, RESULT_STOP_POINT_INFO);
            }
        });

        Button deleteStopPointBtn = customView.findViewById(R.id.deleteStopPointBtn);
        if (isSuggested) deleteStopPointBtn.setEnabled(false);
        deleteStopPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopPoint stopPoint = findPersonalTourByLatLong(new LatLng(latitude, longitude));
                if(stopPoint != null){
                    deleteStopPoint(stopPoint.getId());
                }
                else
                    Toast.makeText(getApplicationContext(), "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        ConstraintLayout layout = findViewById(R.id.addStopPoint);
        mPopupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
    }

    public StopPoint findPersonalTourByLatLong(LatLng latLng) {
        for (int i = 0; i < stopPoints.size(); i++) {
            if (stopPoints.get(i).getLat() == latLng.latitude && stopPoints.get(i).getLong() == latLng.longitude) {
                return stopPoints.get(i);
            }
        }
        return null;
    }
}
