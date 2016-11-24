package com.algonquincollege.dale0106.finaldoorsopenottawa;

/**
 * Created by crisdalessio on 2016-11-23.
 */


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class AboutDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String mystring = getResources().getString(R.string.author);
        // here we are converting the int into a string to send to builder

        //TODO pro-tip: cascading messages
        builder.setTitle(R.string.action_about)
                .setMessage(mystring)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
