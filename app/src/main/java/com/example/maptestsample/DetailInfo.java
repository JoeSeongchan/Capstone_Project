package com.example.maptestsample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DetailInfo extends DialogFragment {


    private static String title_s = "TITLE";
    private static String phone_s = "PHONE";
    private static String address1_s = "ADDRESS1";
    private static String address2_s = "ADDRESS2";


    public static DetailInfo newInstance(String Title_D, String Phonenum_D, String Address1_D, String Address2_D){
        //Log.d("WANTSLEEP","????\n"+"Place.name: "+Title_D+" Phone num: "+Phonenum_D+" Address1: " +Address1_D+" Address2: "+Address2_D+"\n");

        title_s = Title_D;
        phone_s = Phonenum_D;
        address1_s = Address1_D;
        address2_s = Address2_D;

        Bundle bundle = new Bundle();
        bundle.putString(title_s, Title_D);
        bundle.putString(phone_s,Phonenum_D);
        bundle.putString(address1_s,Address1_D);
        bundle.putString(address2_s,Address2_D);
        //Log.d("WANTSLEEP","BUNDLE\n"+"Place.name: "+title_s+" Phone num: "+phone_s+" Address1: " +address1_s+" Address2: "+address2_s+"\n");

        DetailInfo fragment = new DetailInfo();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.popup, null);
        ((TextView)view.findViewById(R.id.title_sing)).setText(title_s);
        ((TextView)view.findViewById(R.id.phone_sing)).setText(phone_s);
        ((TextView)view.findViewById(R.id.address_1)).setText(address1_s);
        ((TextView)view.findViewById(R.id.address_2)).setText(address2_s);

        builder.setView(view);

        return builder.create();

    }
}