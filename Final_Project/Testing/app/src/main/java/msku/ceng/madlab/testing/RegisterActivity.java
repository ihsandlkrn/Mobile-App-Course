package msku.ceng.madlab.testing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import msku.ceng.madlab.testing.model.RegisterRequest;
import msku.ceng.madlab.testing.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for New User Registration.
 * Implements strict validation logic for Data Integrity.
 */
public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPhone, etPassword, etConfirmPass;
    private TextInputLayout tilName, tilEmail, tilPhone, tilPassword, tilConfirmPass;
    private RadioGroup rgRole;
    private MaterialButton btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        // Change hint text based on role selection
        rgRole.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbRegOrg) {
                tilName.setHint("Organization Name");
            } else {
                tilName.setHint("Full Name");
            }
        });

        btnRegister.setOnClickListener(v -> performRegistration());

        tvGoToLogin.setOnClickListener(v -> {
            finish(); // Go back to Login Activity
        });
    }

    private void initViews() {
        // TextInputEditTexts
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etRegEmail);
        etPhone = findViewById(R.id.etRegPhone);
        etPassword = findViewById(R.id.etRegPassword);
        etConfirmPass = findViewById(R.id.etRegConfirmPassword);

        // TextInputLayouts (Required for showing error messages under the box)
        tilName = findViewById(R.id.tilName);


        rgRole = findViewById(R.id.rgRegisterRole);
        btnRegister = findViewById(R.id.btnDoRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);
    }

    private void performRegistration() {
        // Reset previous errors
        etName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPass.setError(null);

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPass = etConfirmPass.getText().toString().trim();

        boolean isValid = true;

        // 1. Name Validation
        if (name.isEmpty()) {
            etName.setError("Name cannot be empty");
            isValid = false;
        }

        // 2. Email Validation (Must be a valid email format)
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            isValid = false;
        }

        // 3. Phone Validation (Must be at least 10 digits)
        if (phone.isEmpty() || phone.length() < 10 || !Patterns.PHONE.matcher(phone).matches()) {
            etPhone.setError("Please enter a valid phone number (min 10 digits)");
            isValid = false;
        }

        // 4. Password Validation (Strict Policy)
        if (!isValidPassword(password)) {
            etPassword.setError("Password must be at least 8 chars, contain 1 Number & 1 Uppercase letter.");
            isValid = false;
        }

        // 5. Confirm Password
        if (!password.equals(confirmPass)) {
            etConfirmPass.setError("Passwords do not match!");
            isValid = false;
        }

        // Stop if any validation failed
        if (!isValid) return;

        // If everything is correct, proceed to backend
        String role = (rgRole.getCheckedRadioButtonId() == R.id.rbRegOrg) ? "Organization" : "Victim";

        RegisterRequest request = new RegisterRequest(name, email, phone, password, role);
        sendRegisterRequest(request);
    }


    private boolean isValidPassword(String password) {
        // Regex explanation:
        // (?=.*[0-9])       : Contains at least one digit
        // (?=.*[A-Z])       : Contains at least one uppercase letter
        // (?=\\S+$)         : No whitespace allowed
        // .{8,}             : At least 8 characters
        Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}$");
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    private void sendRegisterRequest(RegisterRequest request) {
        Toast.makeText(this, "Creating account...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().registerUser(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful! Please Login.", Toast.LENGTH_LONG).show();
                    finish();
                } else if (response.code() == 409) {
                    // Conflict: Email already exists
                    Toast.makeText(RegisterActivity.this, "This email is already registered!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connection Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}