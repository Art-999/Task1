package com.example.arturmusayelyan.task1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int PERMISSION_REQUEST = 45;
    //usefull https://developers.google.com/maps/documentation/android-api/start
    //https://stackoverflow.com/questions/12668551/share-location-with-share-intent-activity
    //https://stackoverflow.com/questions/22036033/how-to-share-the-location-in-mapv2-in-android

    private GoogleMap mMap;
    private EditText geoLocate_et;
    private Button geoLocate_btn;
    private GoogleApiClient googleApiClient;
    private Marker marker;

    private boolean locationBull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect !!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            initMap();
        }


    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geoLocate_btn = findViewById(R.id.goto_location_btn);
        geoLocate_et = findViewById(R.id.goto_location_et);
        geoLocate_btn.setOnClickListener(this);

        locationBull = false;

    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //markeri masna
        if (mMap != null) {
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    LatLng latLng = marker.getPosition();
                    List<Address> list = null;
                    try {
                        list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = list.get(0);
                    marker.setTitle(address.getLocality());
                    marker.showInfoWindow();
                }
            });

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.info_window, null);
                    TextView tvLocality = view.findViewById(R.id.info_window_tv_locality);
                    TextView tvLatitude = view.findViewById(R.id.info_window_tv_latitude);
                    TextView tvLongitude = view.findViewById(R.id.info_window_tv_longitude);
                    TextView tvSnippet = view.findViewById(R.id.info_window_tv_snippet);
                    Button btnShare = view.findViewById(R.id.info_window_btn_share);

                    LatLng latLng = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLatitude.setText("Latitude: " + latLng.latitude);
                    tvLongitude.setText("Longitude: " + latLng.longitude);
                    tvSnippet.setText(marker.getSnippet());
//                    btnShare.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("Artur", "Share button");
//                        }
//                    });
                    return view;
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Log.d("Artur", "Share button");
                    if (marker != null) {
                        LatLng latLng = marker.getPosition();
                        Double latitude = latLng.latitude;
                        Double longitude = latLng.longitude;

                        Intent intent = new Intent(MapsActivity.this, ChatActivity.class);
                        intent.putExtra("latitude", latitude.toString());
                        intent.putExtra("longitude", longitude.toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//                        Bitmap bitmap=Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
//                        Canvas canvas=new Canvas(bitmap);
//                        Log.d("Artur",canvas.toString());

                        setResult(RESULT_OK,intent);
                        finish();
                    } else {
                        Toast.makeText(MapsActivity.this, "Put marker for sharing location", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID)

//        try {
//            goToLocationZoom(40.178613, 44.512654, 15);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//            }
//
//        mMap.setMyLocationEnabled(true);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.d("Artur", "Location button click");

                Location currentLocation = mMap.getMyLocation();
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    List<Address> addressList = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                    if (addressList != null) {
                        Address currentAddress = addressList.get(0);
                        setMarker(currentAddress.getLocality(), currentAddress.getLatitude(), currentAddress.getLongitude());
                        locationBull = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
    }

    private void goToLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
        mMap.moveCamera(update);
    }

    public void goToLocationZoom(double lat, double lng, int zoom) throws IOException {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocation(lat, lng, 1);
        Address address = list.get(0);
        setMarker(address.getLocality(), address.getLatitude(), address.getLongitude());
    }


    @SuppressLint("MissingPermission")
    public void geoLocate() {
        String location = geoLocate_et.getText().toString();
        if (!(location.equals(""))) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addressList = geocoder.getFromLocationName(location, 1);
                if (addressList != null) {
                    Address address = addressList.get(0);
                    String locality = address.getLocality();
                    if (locality != null) {
                        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
                    }

                    double lat = address.getLatitude();
                    double lng = address.getLongitude();
                    goToLocationZoom(lat, lng, 15);

                    setMarker(locality, lat, lng);// avelacnum e marker
                    mMap.setMyLocationEnabled(true);
                    locationBull = true;

                } else {
                    Toast.makeText(this, "Google map cant find your address", Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            geoLocate_et.setText("");
            hideKeyboard(this);
        }

    }

    private void setMarker(String locality, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .title(locality)
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)) //kam fromResourse(R.mipmap....)
                .position(new LatLng(lat, lng))
                .snippet("I am Here");
        marker = mMap.addMarker(markerOptions);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_location_btn:
                geoLocate();
                break;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    LocationRequest LocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest = LocationRequest.create();
        LocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST);
            return;

        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, LocationRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(MapsActivity.this, "Please access location", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(this, "Cant get current location", Toast.LENGTH_LONG).show();
        } else if (!locationBull) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(update);

            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList != null) {
                Address address = addressList.get(0);
                String locality = address.getLocality();
                setMarker(locality, latLng.latitude, latLng.longitude);
            } else {
                Toast.makeText(this, "Google map cant find your address", Toast.LENGTH_LONG).show();
            }
        }
    }
    //    @Override
//    protected void onResume() {
//        super.onResume();
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//    }
//    private void setUpMapIfNeeded(){
//        if(mMap==null){
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//        }
//        if(mMap!=null){
//            setUpMap();
//        }
//    }
}
