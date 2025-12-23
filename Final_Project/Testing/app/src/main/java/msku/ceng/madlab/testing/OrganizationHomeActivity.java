package msku.ceng.madlab.testing;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;
import msku.ceng.madlab.testing.model.AidRequest;
import msku.ceng.madlab.testing.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Dashboard for Organization Users.
 * Features:
 * 1. Fetches all aid requests from Backend.
 * 2. Displays data in a RecyclerView.
 * 3. Provides filtering via ChipGroup.
 */
public class OrganizationHomeActivity extends AppCompatActivity {

    private RecyclerView rvRequests;
    private RequestAdapter adapter;
    private List<AidRequest> apiDataList = new ArrayList<>();
    private ChipGroup chipGroupFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_home);

        rvRequests = findViewById(R.id.rvRequests);
        chipGroupFilters = findViewById(R.id.chipGroupFilters);

        rvRequests.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter with Empty List first
        adapter = new RequestAdapter(this, apiDataList);
        rvRequests.setAdapter(adapter);

        fetchRequestsFromBackend();
        // Handle Filter Logic (Chips)
        chipGroupFilters.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == View.NO_ID) {

                adapter.updateList(apiDataList);
            } else {
                filterList(checkedId);
            }
        });
        // Handle Back Press with Fragment
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                LogoutDialogFragment dialog = new LogoutDialogFragment();
                dialog.show(getSupportFragmentManager(), "LogoutDialog");
            }
        });
    }

    /**
     * Makes an asynchronous network call to get the list of requests.
     */
    private void fetchRequestsFromBackend() {
        Toast.makeText(this, "Loading requests...", Toast.LENGTH_SHORT).show();

        ApiClient.getApiService().getAllRequests().enqueue(new Callback<List<AidRequest>>() {
            @Override
            public void onResponse(Call<List<AidRequest>> call, Response<List<AidRequest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    apiDataList = response.body();

                    adapter.updateList(apiDataList);
                } else {
                    Toast.makeText(OrganizationHomeActivity.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AidRequest>> call, Throwable t) {
                Toast.makeText(OrganizationHomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * Filters the local list based on selected aid package keyword.
     */
    private void filterList(int checkedId) {
        List<AidRequest> filtered = new ArrayList<>();
        String filterKeyword = "";

        if (checkedId == R.id.chipFood) filterKeyword = "Food";
        else if (checkedId == R.id.chipHealth) filterKeyword = "Health";
        else if (checkedId == R.id.chipWarmth) filterKeyword = "Warmth";
        else if (checkedId == R.id.chipAll) {
            adapter.updateList(apiDataList);
            return;
        }

        for (AidRequest aid : apiDataList) {
            if (aid.getRequestedPackages().toString().contains(filterKeyword)) {
                filtered.add(aid);
            }
        }
        adapter.updateList(filtered);
    }
}