package msku.ceng.madlab.testing;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

/**
 * Main Dashboard for Victims.
 * Provides quick access to emergency aid request.
 * Implements Fragment usage via LogoutDialogFragment.
 */
public class HomeActivity extends AppCompatActivity {

    private MaterialCardView btnEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnEmergency = findViewById(R.id.btnEmergency);

        // Launches the Request Aid Activity
        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RequestAidActivity.class);
                startActivity(intent);
            }
        });

        // Shows a Logout Confirmation Fragment instead of closing the app immediately.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                LogoutDialogFragment dialog = new LogoutDialogFragment();
                dialog.show(getSupportFragmentManager(), "LogoutDialog");
            }
        });

    }


}
