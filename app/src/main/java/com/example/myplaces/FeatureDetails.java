package com.example.myplaces;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myplaces.MainActivity.EXTRA_TITLE;
import static com.example.myplaces.MainActivity.EXTRA_ADDRESS;
import static com.example.myplaces.MainActivity.EXTRA_PHONE;








public class FeatureDetails extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_features);

        Intent intent = getIntent();

        String title = intent.getStringExtra(EXTRA_TITLE);
        TextView titleView = findViewById(R.id.title);
        titleView.setText(title);

        String address = intent.getStringExtra(EXTRA_ADDRESS);
        TextView addressView = findViewById(R.id.add);
        addressView.setText(address);

        String phone = intent.getStringExtra(EXTRA_PHONE);
        TextView phoneView = findViewById(R.id.phone_no);
        phoneView.setText(phone);

        ImageView imageView = findViewById(R.id.imageView);
        Resources res = getResources();

        ImageView imageView2 = findViewById(R.id.imageView4);


        if(title.equals("Conrad Dublin"))
        {
            imageView.setImageDrawable(res.getDrawable(R.drawable.conrad));

            imageView2.setImageDrawable(res.getDrawable(R.drawable.startpointbit));
        }

        if(title.equals("Hilton Dublin"))
        {
            imageView.setImageDrawable(res.getDrawable(R.drawable.hilton));
            imageView2.setImageDrawable(res.getDrawable(R.drawable.petbit));

        }

        if(title.equals("Grand Canal Hotel"))
        {
            imageView.setImageDrawable(res.getDrawable(R.drawable.canal));
            imageView2.setImageDrawable(res.getDrawable(R.drawable.food));
        }

    }
}
