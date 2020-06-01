package com.example.myplaces;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import org.json.JSONException;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private ImageView mInfo;
    private Marker[] mMarker = new Marker[3];

    public static final String EXTRA_ID = "place_id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_RATING = "rating";
    public static final String EXTRA_PETS = "pets";
    public static final String EXTRA_BREAKFAST = "breakfast";




    private String clayton_id = "ChIJSSljmLwOZ0gRaC2ayOrrGp0";
    private String ballsbridge_id = "ChIJjW_gw8AOZ0gRfF6L8WrY290";
    private String uppercross_id = "ChIJQQYt-wIMZ0gRgbCbEcC5ITg";

    private static final PatternItem DOT = new Dot();
    private static final float PATTERN_GAP_LENGTH_PX =10 ;
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // Create a new Places client instance
    private  GoogleMap mgoogleMap;

    private  GeoJsonLayer mLayer;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static LatLng CLAYTON = new LatLng(53.331311, -6.249483);
    private static LatLng BALLSBRIDGE = new LatLng(53.333157, -6.234377);
    private static LatLng UPPERCORSS = new LatLng(53.322289, -6.265619);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        Intent detailIntent = new Intent(MainActivity.this,DisplayDetails.class);

        mInfo = (ImageView) findViewById(R.id.place_info);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mgoogleMap = googleMap;
        layer();



        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMarker[0].isInfoWindowShown()) {
                    mMarker[0].hideInfoWindow();
                    mMarker[1].showInfoWindow();
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(BALLSBRIDGE));
                } else if (mMarker[1].isInfoWindowShown()) {
                    mMarker[1].hideInfoWindow();
                    mMarker[2].showInfoWindow();
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(UPPERCORSS));

                } else if (mMarker[2].isInfoWindowShown()) {
                    mMarker[2].hideInfoWindow();
                    mMarker[0].showInfoWindow();
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(CLAYTON));
                } else if (!mMarker[0].isInfoWindowShown() && !mMarker[1].isInfoWindowShown() && !mMarker[2].isInfoWindowShown()) {
                    mMarker[0].showInfoWindow();
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(CLAYTON));
                }

            }
        });

        Marker claytonMarker = mgoogleMap.addMarker(new MarkerOptions().position(CLAYTON).title("Clayton Hotel Burlington Road"));
        claytonMarker.showInfoWindow();
        mMarker[0] = claytonMarker;


        Marker ballsbridgeMarker = mgoogleMap.addMarker(new MarkerOptions().position(BALLSBRIDGE).title("Ballsbridge Hotel"));
        mMarker[1] = ballsbridgeMarker;


        Marker uppercrossMarker = mgoogleMap.addMarker(new MarkerOptions().position(UPPERCORSS).title("Uppercross House Hotel"));
        mMarker[2] = uppercrossMarker;

        //polyline to represent connects between locations
        //create polyline obk=ject and add points
        Polyline polyline = mgoogleMap.addPolyline(new PolylineOptions()
                .clickable(false).add(
                        UPPERCORSS,
                        CLAYTON,
                        BALLSBRIDGE
                ));

        polyline.setColor(0xFFFF0000);
        polyline.setPattern(PATTERN_POLYLINE_DOTTED);

        GroundOverlayOptions claytonOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.clayton))
                .position(CLAYTON, 500f, 500f);

        GroundOverlayOptions uppercrossOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.uppercross))
                .position(UPPERCORSS, 500f, 500f);

        GroundOverlayOptions ballsbridgeOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.ballsbridge))
                .position(BALLSBRIDGE, 500f, 500f);

        mgoogleMap.addGroundOverlay(claytonOverlay);
        mgoogleMap.addGroundOverlay(uppercrossOverlay);
        mgoogleMap.addGroundOverlay(ballsbridgeOverlay);




        mgoogleMap.setMinZoomPreference(13.7f);
        mgoogleMap.setMaxZoomPreference(20.0f);
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(CLAYTON));

        mgoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(mMarker[0])) {
                    Intent detailIntent = new Intent(MainActivity.this, DisplayDetails.class);
                    detailIntent.putExtra(EXTRA_ID, clayton_id);
                    startActivity(detailIntent);
                    Log.d("MARKER", mMarker[0].getTitle());
                    return true;
                }
                else if (marker.equals(mMarker[1]))
                {
                    Intent detailIntent = new Intent(MainActivity.this, DisplayDetails.class);
                    detailIntent.putExtra(EXTRA_ID, ballsbridge_id);
                    startActivity(detailIntent);
                    Log.d("MARKER", mMarker[1].getTitle());
                    return true;
                }
                else if (marker.equals(mMarker[2]))
                {
                    Intent detailIntent = new Intent(MainActivity.this, DisplayDetails.class);
                    detailIntent.putExtra(EXTRA_ID, uppercross_id);
                    startActivity(detailIntent);
                    Log.d("MARKER", mMarker[2].getTitle());
                    return true;
                }
                Intent detailIntent = new Intent(MainActivity.this, DisplayDetails.class);
                startActivity(detailIntent);
                return false;
            }
        });

    }

    public void layer()
    {

        try {
            mLayer = new GeoJsonLayer(mgoogleMap, R.raw.hotels, getApplicationContext());

            GeoJsonPointStyle style1 = new GeoJsonPointStyle();

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.startpointbit);
            BitmapDescriptor image = BitmapDescriptorFactory.fromBitmap(bm);

            style1.setIcon(image);

            GeoJsonPointStyle style2 = new GeoJsonPointStyle();

            Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.petbit);
            BitmapDescriptor image2 = BitmapDescriptorFactory.fromBitmap(bm2);

            style2.setIcon(image2);

            GeoJsonPointStyle style3 = new GeoJsonPointStyle();

            Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.food);
            BitmapDescriptor image3 = BitmapDescriptorFactory.fromBitmap(bm3);

            style3.setIcon(image3);

            for (GeoJsonFeature feature : mLayer.getFeatures()) {
                if (feature.getProperty("title").equals("Conrad Dublin")) {
                    feature.setPointStyle(style1);
                }

                if (feature.getProperty("title").equals("Hilton Dublin")) {
                    feature.setPointStyle(style2);
                }

                if (feature.getProperty("title").equals("Grand Canal Hotel")) {
                    feature.setPointStyle(style3);
                }

            }

            mLayer.setOnFeatureClickListener(new GeoJsonLayer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {

                    Intent intent = new Intent(MainActivity.this, FeatureDetails.class);
                    if (feature.getProperty("title").equals("Conrad Dublin")) {

                        intent.putExtra(EXTRA_TITLE,feature.getProperty("title"));
                        intent.putExtra(EXTRA_ADDRESS,feature.getProperty("address"));
                        intent.putExtra(EXTRA_PHONE,feature.getProperty("phone"));
                        startActivity(intent);

                    }

                    if (feature.getProperty("title").equals("Hilton Dublin")) {
                        intent.putExtra(EXTRA_TITLE,feature.getProperty("title"));
                        intent.putExtra(EXTRA_ADDRESS,feature.getProperty("address"));
                        intent.putExtra(EXTRA_PHONE,feature.getProperty("phone"));
                        startActivity(intent);
                    }

                    if (feature.getProperty("title").equals("Grand Canal Hotel")) {
                        intent.putExtra(EXTRA_TITLE,feature.getProperty("title"));
                        intent.putExtra(EXTRA_ADDRESS,feature.getProperty("address"));
                        intent.putExtra(EXTRA_PHONE,feature.getProperty("phone"));
                        startActivity(intent);
                    }

                }

            });

            mLayer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}


