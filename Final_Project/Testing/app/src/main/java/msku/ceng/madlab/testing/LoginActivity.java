package msku.ceng.madlab.testing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import msku.ceng.madlab.testing.model.LoginRequest; // Modelini import et
import msku.ceng.madlab.testing.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Handles user authentication (Login).
 * Allows users to log in as either a 'Victim' or an 'Organization'.
 */

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private RadioGroup radioGroupRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        radioGroupRole = findViewById(R.id.radioGroupRole);

        TextView btnSignUp = findViewById(R.id.btnRegister);

        // Navigate to Registration Screen
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Trigger Login Process
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Determine the selected role (Victim vs Organization)
            int selectedId = radioGroupRole.getCheckedRadioButtonId();
            String role = (selectedId == R.id.rbOrganization) ? "Organization" : "Victim";


            performLogin(email, password, role);
        });
    }

    //Sends Login Request to the Backend via Retrofit.
    private void performLogin(String email, String password, String role) {
        LoginRequest request = new LoginRequest(email, password, role);

        Toast.makeText(this, "Verifying credentials...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().loginUser(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 200 OK
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    navigateBasedOnRole(role);
                } else {
                    // 401 Unauthorized
                    if (response.code() == 401) {
                        Toast.makeText(LoginActivity.this, "Invalid Email or Password!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection Error. Check Server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Navigates to the appropriate Home Screen based on the user's role.
     */
    private void navigateBasedOnRole(String role) {
        if (role.equals("Organization")) {
            Intent intent = new Intent(LoginActivity.this, OrganizationHomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
    }
}