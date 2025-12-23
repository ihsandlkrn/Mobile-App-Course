package msku.ceng.madlab.testing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import msku.ceng.madlab.testing.model.AidRequest;
import msku.ceng.madlab.testing.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Activity responsible for creating Aid Requests.
 * Uses Google Play Services (FusedLocationProviderClient) to fetch GPS coordinates.
 */
public class RequestAidActivity extends AppCompatActivity {

    private MaterialCardView cardFood, cardWarmth, cardHealth, cardHygiene;
    private MaterialButton btnRefreshLocation, btnSubmit;
    private TextView tvLocationCoords, tvAddress;
    private RadioGroup radioGroupUrgency;
    private TextInputEditText etDescription;
    private List<String> selectedPackages = new ArrayList<>();


    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_aid);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        initViews();
        setupPackageSelection();

        btnRefreshLocation.setOnClickListener(v -> getRealLocation());
        btnSubmit.setOnClickListener(v -> submitRequest());
    }

    private void getRealLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        tvAddress.setText("Getting GPS signal...");


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            double lat = location.getLatitude();
                            double lng = location.getLongitude();

                            tvLocationCoords.setText(String.format("%.4f, %.4f", lat, lng));
                            tvAddress.setText("Location Verified via Satellite");
                            Toast.makeText(RequestAidActivity.this, "GPS Location Updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RequestAidActivity.this, "Could not retrieve location. Try opening Maps.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getRealLocation();
        }
    }


    private void initViews() {

        cardFood = findViewById(R.id.cardFood);
        cardWarmth = findViewById(R.id.cardWarmth);
        cardHealth = findViewById(R.id.cardHealth);
        cardHygiene = findViewById(R.id.cardHygiene);
        btnRefreshLocation = findViewById(R.id.btnRefreshLocation);
        btnSubmit = findViewById(R.id.btnSubmitRequest);
        tvLocationCoords = findViewById(R.id.tvLocationCoords);
        tvAddress = findViewById(R.id.tvAddress);
        radioGroupUrgency = findViewById(R.id.radioGroupUrgency);
        etDescription = findViewById(R.id.etDescription);
    }

    private void setupPackageSelection() {
        View.OnClickListener cardClickListener = view -> {
            MaterialCardView card = (MaterialCardView) view;
            String packageType = "";
            if (card == cardFood) packageType = "Food Pack";
            else if (card == cardWarmth) packageType = "Warmth Pack";
            else if (card == cardHealth) packageType = "Health Pack";
            else if (card == cardHygiene) packageType = "Hygiene Pack";

            if (card.isChecked()) {
                card.setChecked(false);
                selectedPackages.remove(packageType);
                card.setStrokeWidth(0);
            } else {
                card.setChecked(true);
                selectedPackages.add(packageType);
                card.setStrokeWidth(6);
                card.setStrokeColor(getColor(R.color.primary_green));
            }
        };
        cardFood.setOnClickListener(cardClickListener);
        cardWarmth.setOnClickListener(cardClickListener);
        cardHealth.setOnClickListener(cardClickListener);
        cardHygiene.setOnClickListener(cardClickListener);
    }

    private void submitRequest() {
        int selectedId = radioGroupUrgency.getCheckedRadioButtonId();
        String urgency = "Medium";
        if (selectedId == R.id.rbLow) urgency = "Low";
        else if (selectedId == R.id.rbHigh) urgency = "Critical";

        if (selectedPackages.isEmpty()) {
            Toast.makeText(this, "Please select at least one aid package.", Toast.LENGTH_LONG).show();
            return;
        }

        String locationCoords = tvLocationCoords.getText().toString();
        String addressText = tvAddress.getText().toString();
        String descriptionText = etDescription.getText().toString();

        AidRequest newRequest = new AidRequest(descriptionText, urgency, locationCoords, selectedPackages, addressText);

        ApiClient.getApiService().submitAidRequest(newRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RequestAidActivity.this, "Request Received by Center!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(RequestAidActivity.this, "Server Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RequestAidActivity.this, "Connection Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}