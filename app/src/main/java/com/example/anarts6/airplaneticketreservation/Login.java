package com.example.anarts6.airplaneticketreservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;

/**
 * Created by anarts6 on 5/12/16.
 */
public class Login extends DialogFragment {
    private String username;
    private String password;
    public Login(){

    }
    public Dialog onCreateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.login, null))
                .setPositiveButton(R.string.common_signin_button_text, new
                        DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //sign in the user...
                            }
                        })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Login.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
