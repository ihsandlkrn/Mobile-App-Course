package msku.ceng.madlab.testing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
/**
 * A reusable DialogFragment to confirm user logout.
 * Demonstrates the usage of Fragments in the project.
 */
public class LogoutDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Navigate back to Login Screen and clear back stack
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialog, id) -> dismiss());
        return builder.create();
    }
}