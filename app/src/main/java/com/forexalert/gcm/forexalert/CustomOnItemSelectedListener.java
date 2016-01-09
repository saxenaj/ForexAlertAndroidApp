package com.forexalert.gcm.forexalert;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by SaxenaJ on 12/30/2015.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      /*  Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();*/


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
