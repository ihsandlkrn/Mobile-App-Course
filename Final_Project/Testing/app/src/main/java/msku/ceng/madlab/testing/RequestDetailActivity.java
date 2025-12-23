package msku.ceng.madlab.testing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import msku.ceng.madlab.testing.model.AidRequest;
/**
 * Activity to display full details of a specific Aid Request.
 * Allows launching Google Maps for navigation to the victim's location.
 */
public class RequestDetailActivity extends AppCompatActivity {

    private TextView tvUrgency, tvDescription, tvPackages, tvAddress;
    private MaterialButton btnOpenMap;
    private AidRequest currentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        initViews();

        // Retrieve the Serializable object passed from Adapter

        if (getIntent().getSerializableExtra("request_data") != null) {
            currentRequest = (AidRequest) getIntent().getSerializableExtra("request_data");
            populateData();
        }

        // 2. Map Button Click Listener
        btnOpenMap.setOnClickListener(v -> openMapLocation());
    }

    private void initViews() {
        tvUrgency = findViewById(R.id.tvDetailUrgency);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvPackages = findViewById(R.id.tvDetailPackages);
        tvAddress = findViewById(R.id.tvDetailAddress);
        btnOpenMap = findViewById(R.id.btnOpenMap);
    }

    private void populateData() {
        if (currentRequest == null) return;

        tvDescription.setText(currentRequest.getDescription());
        tvAddress.setText(currentRequest.getAddress() + "\n(" + currentRequest.getLocation() + ")");
        tvPackages.setText(currentRequest.getRequestedPackages().toString());
        tvUrgency.setText(currentRequest.getUrgency().toUpperCase());

        // Set Color for Urgency
        if (currentRequest.getUrgency().equalsIgnoreCase("Critical")) {
            tvUrgency.setTextColor(getColor(R.color.urgent_red));
        } else {
            tvUrgency.setTextColor(getColor(R.color.primary_green)); // Or standard color
        }
    }
    /**
     * Creates an Intent to open external Map application (Google Maps)
     * using the coordinates stored in the request.
     */
    private void openMapLocation() {
        if (currentRequest == null || currentRequest.getLocation() == null) {
            Toast.makeText(this, "No location data available", Toast.LENGTH_SHORT).show();
            return;
        }


        // URI for geo-location
        String geoUri = "geo:0,0?q=" + currentRequest.getLocation() + "(Aid Request)";

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        mapIntent.setPackage("com.google.android.apps.maps"); // Try to open specifically in Google Maps

        // Verify if there is an app to handle this intent
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // If Google Maps is not installed, try opening in browser or standard map handler
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(browserIntent);
        }
    }
}