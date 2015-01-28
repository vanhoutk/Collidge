package com.example.deirdremeehan.rpgdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Deirdre Meehan on 26/01/2015.
 */
public class BasicDialogFragment extends android.app.DialogFragment {

    private Context context;

    public void setContext(Context c){
        this.context = c;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles);

        builder.setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                openYes();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                openYes();
            }
        });


        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void openYes(){
        Intent intent = ((MainActivity)context).getIntent();
        startActivity(intent);
    }
}
