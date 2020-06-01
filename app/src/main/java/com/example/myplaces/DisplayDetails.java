package com.example.myplaces;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;


import java.util.Arrays;
import java.util.List;

import static com.example.myplaces.MainActivity.EXTRA_ID;

public class DisplayDetails  extends AppCompatActivity {

    // Create a new Places client instance
    private PlacesClient mPlacesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_details);

        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);

        getPlace();


    }

    private void getPlace(){

        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_ID);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,Place.Field.PHONE_NUMBER,Place.Field.PHOTO_METADATAS);

        // Construct a request object, passing the place ID and fields array.
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(id, placeFields);

        Task<FetchPlaceResponse> fetchPlaceResponse = mPlacesClient.fetchPlace(request);

        fetchPlaceResponse.addOnCompleteListener(this,
                new OnCompleteListener<FetchPlaceResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FetchPlaceResponse> task) {

                        if(task.isSuccessful()){
                            FetchPlaceResponse response = task.getResult();

                            Intent intent = getIntent();
                            String id = intent.getStringExtra(EXTRA_ID);

                            Place p = response.getPlace();
                            Resources res = getResources();
                            ImageView imageView = findViewById(R.id.mainImage);
                            TextView name = findViewById(R.id.title1);
                            if(id.equals("ChIJSSljmLwOZ0gRaC2ayOrrGp0"))
                            {
                                imageView.setImageDrawable(res.getDrawable(R.drawable.clayton));

                                name.setText("Clayton Hotel");
                            }

                            if(id.equals("ChIJjW_gw8AOZ0gRfF6L8WrY290"))
                            {
                                imageView.setImageDrawable(res.getDrawable(R.drawable.ballsbridge));
                                name.setText("Ballsbridge Hotel");

                            }

                            if(id.equals("ChIJQQYt-wIMZ0gRgbCbEcC5ITg"))
                            {
                                imageView.setImageDrawable(res.getDrawable(R.drawable.uppercross));
                                name.setText("Uppercross Hotel");

                            }

                            TextView address = findViewById(R.id.address);
                            address.setText(p.getAddress());

                            TextView phone = findViewById(R.id.phone);
                            phone.setText(p.getPhoneNumber());





                            Log.d("Place found: ", response.getPlace().getName());
                        } else{
                            Exception exception = task.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                Log.e("Place not found: ","error");
                            }
                        }
                    }
                }
        );

    }
}