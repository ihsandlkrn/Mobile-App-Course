package msku.ceng.madlab.testing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

import msku.ceng.madlab.testing.model.AidRequest;



/**
 * Adapter for the RecyclerView that displays a list of aid requests.
 * This class is responsible for creating view holders and binding data to the views.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<AidRequest> requestList;
    private Context context;


    public RequestAdapter(Context context, List<AidRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        AidRequest request = requestList.get(position);

        // Combine the description and requested packages for display.
        String displayText = request.getDescription() + " " + request.getRequestedPackages().toString();
        holder.tvTitle.setText(displayText);

        // Change the text color based on the urgency of the request.
        if (request.getUrgency().equalsIgnoreCase("Critical")) {
            holder.tvTitle.setTextColor(context.getColor(R.color.urgent_red));
        } else {
            holder.tvTitle.setTextColor(context.getColor(R.color.text_primary));
        }

        // Set up the "DETAILS" button.
        holder.btnSendHelp.setText("DETAILS");
        holder.btnSendHelp.setOnClickListener(v -> {
            Intent intent = new Intent(context, RequestDetailActivity.class);

            intent.putExtra("request_data", request);
            context.startActivity(intent);
        });
    }
    // Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return requestList.size();
    }

     // Updates the list of requests and notifies the adapter to refresh the view.
    public void updateList(List<AidRequest> newList) {
        requestList = newList;
        notifyDataSetChanged();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        MaterialButton btnSendHelp;

        public RequestViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRequestTitle);
            btnSendHelp = itemView.findViewById(R.id.btnSendHelpItem);
        }
    }
}